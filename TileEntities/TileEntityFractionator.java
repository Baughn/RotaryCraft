/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.TileEntities;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import Reika.DragonAPI.Auxiliary.EnumLook;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.PipeConnector;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityInventoriedPowerReceiver;
import Reika.RotaryCraft.Items.ItemFuelLubeBucket;
import Reika.RotaryCraft.Models.ModelFraction;
import Reika.RotaryCraft.Registry.DifficultyEffects;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class TileEntityFractionator extends TileEntityInventoriedPowerReceiver implements PipeConnector {

	public int fuel;
	public int mixTime;
	public int storeTime;

	public static final int CAPACITY = 240;
	public static final int BASETIME = 9600/100; //70s at 0rpm
	public static final int MINTIME = 10;

	public boolean idle = false;

	public static final ItemStack[] ingredients =
		{new ItemStack(Item.blazePowder.itemID, 1, 0), new ItemStack(Item.coal.itemID, 1, 0),
		new ItemStack(RotaryCraft.powders.itemID, 1, 0), new ItemStack(RotaryCraft.powders.itemID, 1, 1),
		ItemRegistry.ETHANOL.getStackOf(), new ItemStack(Item.magmaCream.itemID, 1, 0)};

	public ItemStack[] inv = new ItemStack[ingredients.length+1+1];

	public void testIdle() {
		idle = !this.getAllIngredients();
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	public int getFuelScaled(int par1)
	{
		return (fuel*par1)/CAPACITY;
	}

	public int getStorageScaled(int par1)
	{
		return (storeTime*par1)/CAPACITY;
	}

	public int getMixScaled(int par1)
	{
		//ReikaChatHelper.writeInt(this.omega);
		return (mixTime*par1)/this.operationTime(omega, 0);
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();
		power = omega * torque;
		if (inv[ingredients.length+1] != null && fuel >= ItemFuelLubeBucket.JET_VALUE) {
			if (inv[ingredients.length+1].itemID == Item.bucketEmpty.itemID && inv[ingredients.length+1].stackSize == 1) {
				inv[ingredients.length+1] = ItemStacks.fuelbucket;
				fuel -= ItemFuelLubeBucket.JET_VALUE;
			}
		}
		if (power < MINPOWER || omega < MINSPEED)
			return;
		this.testIdle();
		//int operationtime = ReikaMathLibrary.extrema(BASETIME-this.omega, MINTIME, "max");
		if (this.process()) {
			mixTime++;
			//ReikaChatHelper.writeInt(this.operationTime(this.omega, 0));
			if (this.operationComplete(mixTime, 0)) {
				mixTime = 0;
				this.make();
			}
		}
		else {
			mixTime = 0;
		}
		if (fuel < 0)
			fuel = 0;
	}

	public void make() {
		RotaryAchievements.JETFUEL.triggerAchievement(this.getPlacer());
		for (int i = 0; i < ingredients.length; i++) {
			if (DifficultyEffects.CONSUMEFRAC.testChance())
				ReikaInventoryHelper.decrStack(i, inv);
		}
		fuel += DifficultyEffects.PRODUCEFRAC.getInt();
		if (fuel > CAPACITY)
			fuel = CAPACITY;
	}

	public boolean process() {
		if (fuel >= CAPACITY)
			return false;
		boolean allitems = this.getAllIngredients();
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.valueOf(allitems));
		if (inv[ingredients.length] == null)
			return false;
		if (inv[ingredients.length].itemID != Item.ghastTear.itemID) //need a ghast tear as fuel solvent
			return false;
		return allitems;
	}

	public boolean getAllIngredients() {
		for (int k = 0; k < ingredients.length; k++)
			if (inv[k] == null)
				return false;
		for (int i = 0; i < ingredients.length; i++) {
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d  %d", ingredients[i].itemID, ingredients[i].getItemDamage()));
			//ModLoader.getMinecraftInstance().thePlayer.addChatMessage(String.format("%d", i)+String.valueOf(this.haveIngredient(ingredients[i].itemID, ingredients[i].getItemDamage())));
			if (!ReikaInventoryHelper.checkForItemStack(ingredients[i].itemID, ingredients[i].getItemDamage(), inv))
				return false;
		}
		return true;
	}

	public void getIOSides(World world, int x, int y, int z, int metadata) {
		readx = x;
		ready = y;
		readz = z;
	}


	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory()
	{
		return inv.length;
	}

	public static boolean func_52005_b(ItemStack par0ItemStack)
	{
		return true;
	}

	/**
	 * Returns the stack in slot i
	 */
	public ItemStack getStackInSlot(int par1)
	{
		return inv[par1];
	}

	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		inv[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setInteger("fuel", fuel);
		NBT.setInteger("mix", mixTime);

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inv.length; i++)
		{
			if (inv[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				inv[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		NBT.setTag("Items", nbttaglist);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		fuel = NBT.getInteger("fuel");
		mixTime = NBT.getInteger("mix");

		NBTTagList nbttaglist = NBT.getTagList("Items");
		inv = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");

			if (byte0 >= 0 && byte0 < inv.length)
			{
				inv[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelFraction();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.FRACTIONATOR.ordinal();
	}

	@Override
	public boolean isStackValidForSlot(int slot, ItemStack is) {
		if (slot == ingredients.length+1)
			return is.itemID == Item.bucketEmpty.itemID;
		if (slot == ingredients.length)
			return is.itemID == Item.ghastTear.itemID;
		return ReikaInventoryHelper.checkForItem(is.itemID, ingredients);
	}

	@Override
	public int getRedstoneOverride() {
		if (!this.getAllIngredients())
			return 15;
		return 15*fuel/CAPACITY;
	}

	@Override
	public boolean canConnectToPipe(MachineRegistry m) {
		return m == MachineRegistry.FUELLINE;
	}

	@Override
	public boolean canConnectToPipeOnSide(MachineRegistry p, EnumLook side) {
		return side == EnumLook.DOWN;
	}
}
