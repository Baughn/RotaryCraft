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

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Models.ModelLandmine;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.Registry.RotaryAchievements;

public class TileEntityLandmine extends RotaryCraftTileEntity implements ISidedInventory {

	private ItemStack[] inv = new ItemStack[9];

	private boolean flaming = false;
	private boolean poison = false;
	private boolean chain = false;
	private boolean shrapnel = false;

	private int explosionDelay = 0;
	private boolean isChainExploding = false;

	@Override
	public int getSizeInventory() {
		return inv.length;
	}

	private boolean checkForPlayer(World world, int x, int y, int z) {
		AxisAlignedBB above = AxisAlignedBB.getAABBPool().getAABB(x, y+1, z, x+1, y+3, z+1);
		List in = world.getEntitiesWithinAABB(EntityLiving.class, above);
		for (int i = 0; i < in.size(); i++) {
			EntityLiving e = (EntityLiving)in.get(i);
			if (e.onGround && !e.isSneaking())
				return true;
		}
		return false;
	}

	private boolean checkForArrow(World world, int x, int y, int z) {
		AxisAlignedBB above = AxisAlignedBB.getAABBPool().getAABB(x, y, z, x+1, y+1, z+1).expand(1, 1, 1);
		List in = world.getEntitiesWithinAABB(EntityArrow.class, above);
		return in.size() > 0;
	}

	private boolean ageFail() {
		if (par5Random.nextInt(20) > 0)
			return false;
		int age = this.getAge();
		if (age == 0)
			return false;
		return (par5Random.nextInt(1+65536-this.getAge()) == 0);
	}

	private void maxPowerExplosion(World world, int x, int y, int z) {
		world.spawnParticle("hugeexplosion", x+0.5, y+0.5, z+0.5, 0, 0, 0);
		world.playSoundEffect(x+0.5, y+0.5, z+0.5, "random.explode", 1, 1);
		for (int i = 1; i < 6; i++) {
			ReikaWorldHelper.recursiveBreakWithinSphere(world, x-i, y, z, world.getBlockId(x-1, y, z), -1, x, y, z, 4);
			ReikaWorldHelper.recursiveBreakWithinSphere(world, x+i, y, z, world.getBlockId(x+1, y, z), -1, x, y, z, 4);
			ReikaWorldHelper.recursiveBreakWithinSphere(world, x, y-i, z, world.getBlockId(x, y-1, z), -1, x, y, z, 4);
			ReikaWorldHelper.recursiveBreakWithinSphere(world, x, y+i, z, world.getBlockId(x, y+1, z), -1, x, y, z, 4);
			ReikaWorldHelper.recursiveBreakWithinSphere(world, x, y, z-i, world.getBlockId(x, y, z-1), -1, x, y, z, 4);
			ReikaWorldHelper.recursiveBreakWithinSphere(world, x, y, z+i, world.getBlockId(x, y, z+1), -1, x, y, z, 4);
			this.chainedExplosion(world, x, y, z);
		}
	}

	public void detonate(World world, int x, int y, int z) {
		if (chain)
			this.chainedExplosion(world, x, y, z);
		if (inv[1] != null && inv[2] != null && inv[3] != null && inv[4] != null) {
			if (inv[1].itemID == Block.tnt.blockID && inv[2].itemID == Block.tnt.blockID && inv[3].itemID == Block.tnt.blockID && inv[4].itemID == Block.tnt.blockID)
				this.maxPowerExplosion(world, x, y, z);
		}
		float power = this.getExplosionPower();
		world.setBlock(x, y, z, 0);
		if (flaming) {
			if (!world.isRemote)
				world.newExplosion(null, x+0.5, y+0.5, z+0.5, power, true, true);
		}
		else if (!world.isRemote)
			world.createExplosion(null, x+0.5, y+0.5, z+0.5, power, true);
		AxisAlignedBB region = AxisAlignedBB.getAABBPool().getAABB(x, y, z, x+1, y+1, z+1).expand(2, 2, 2);
		List in = world.getEntitiesWithinAABB(EntityLiving.class, region);
		for (int i = 0; i < in.size(); i++) {
			EntityLiving e = (EntityLiving)in.get(i);
			boolean invuln = false;
			if (e instanceof EntityPlayer) {
				if (((EntityPlayer)e).capabilities.isCreativeMode)
					invuln = true;
				else
					RotaryAchievements.LANDMINE.triggerAchievement((EntityPlayer) e);
			}
			if (!invuln) {
				e.attackEntityFrom(DamageSource.setExplosionSource(new Explosion(world, null, e.posX, e.posY, e.posZ, power)), (int)power*4);
				e.addPotionEffect(new PotionEffect(Potion.blindness.id, 400, 0));
				e.addPotionEffect(new PotionEffect(Potion.confusion.id, 450, 5));
				if (poison)
					e.addPotionEffect(new PotionEffect(Potion.poison.id, 200, 0));
			}
			if (e instanceof EntityCreeper) {
				world.createExplosion(e, x+0.5, y+0.5, z+0.5, 3, true);
			}
		}
		if (shrapnel) {
			AxisAlignedBB region2 = AxisAlignedBB.getAABBPool().getAABB(x, y, z, x+1, y+1, z+1).expand(8, 8, 8);
			List in2 = world.getEntitiesWithinAABB(EntityLiving.class, region);
			for (int i = 0; i < in.size(); i++) {
				EntityLiving e = (EntityLiving)in.get(i);
				double dx = e.posX-x-0.5;
				double dy = e.posY-y-0.5;
				double dz = e.posZ-z-0.5;
				double dd = ReikaMathLibrary.py3d(dx, dy, dz);
				e.attackEntityFrom(DamageSource.generic, (int)(10D/Math.sqrt(dd)));
				ReikaEntityHelper.spawnParticlesAround("crit", world, e, 8);
			}
		}/*
		for (int i = -8; i <= 8; i++) {
			for (int j = -8; j <= 8; j++) {
				for (int k = -8; k <= 8; k++) {
					if (world.getBlockId(x+i, y+j, z+k) == this.getTileEntityBlockID()) {
						TileEntity te = world.getBlockTileEntity(x+i, y+j, z+k);
						if (te instanceof TileEntityLandmine)
							((TileEntityLandmine)te).detonate(world, te.xCoord, te.yCoord, te.zCoord);
					}
				}
			}
		}*/
	}

	private void getExplosionModifiers() {
		for (int i = 5; i <= 8; i++)
			if (inv[i] != null) {
				if (inv[i].itemID == Item.blazePowder.itemID)
					flaming = true;
				if (inv[i].itemID == Item.spiderEye.itemID)
					flaming = true;
				if (inv[i].itemID == Block.tnt.blockID)
					chain = true;
				if (inv[i].itemID == Block.glass.blockID)
					shrapnel = true;
			}
	}

	private float getExplosionPower() {
		int num = 0;
		for (int i = 1; i <= 4; i++)
			if (inv[i] != null) {
				if (inv[i].itemID == Item.gunpowder.itemID)
					num++;
			}
		return 2F*num; //Each item is 1/2 block TNT (so capped at 2x)
	}

	private int getAge() {
		return 65536-inv[0].getItemDamage();
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inv[i];
	}

	public ItemStack decrStackSize(int par1, int par2)
	{
		if (inv[par1] != null) {
			if (inv[par1].stackSize <= par2) {
				ItemStack itemstack = inv[par1];
				inv[par1] = null;
				return itemstack;
			}
			ItemStack itemstack1 = inv[par1].splitStack(par2);
			if (inv[par1].stackSize == 0)
				inv[par1] = null;
			return itemstack1;
		}
		else
			return null;
	}

	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if (inv[par1] != null) {
			ItemStack itemstack = inv[par1];
			inv[par1] = null;
			return itemstack;
		}
		else
			return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inv[i] = itemstack;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void openChest() {
		if (inv[0] == null)
			return;
		if (par5Random.nextInt(65536-this.getAge())/2 == 0)
			this.detonate(worldObj, xCoord, yCoord, zCoord);
	}

	@Override
	public void closeChest() {

	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack is) {
		switch (i) {
		case 0:
			return is.itemID == ItemRegistry.SPRING.getShiftedID();
		case 1:
		case 2:
		case 3:
		case 4:
			return is.itemID == Item.gunpowder.itemID;
		case 5:
		case 6:
		case 7:
		case 8:
			return this.isModifier(is);
		default:
			return false;
		}
	}

	private boolean isModifier(ItemStack is) {
		if (is.itemID == Item.blazePowder.itemID)
			return true;
		if (is.itemID == Item.spiderEye.itemID)
			return true;
		if (is.itemID == Block.tnt.blockID)
			return true;
		if (is.itemID == Block.glass.blockID)
			return true;
		return false;
	}

	private void chainedExplosion(World world, int x, int y, int z) {
		for (int i = 0; i < 12; i++) {
			EntityTNTPrimed tnt = new EntityTNTPrimed(world, x-5+par5Random.nextInt(11), y-5+par5Random.nextInt(11), z-5+par5Random.nextInt(11), null);
			tnt.fuse = 5+par5Random.nextInt(10);
			if (!world.isRemote)
				world.spawnEntityInWorld(tnt);
		}
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return new ModelLandmine();
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {

	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.LANDMINE.ordinal();
	}

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		if (inv[0] == null)
			return;
		if (inv[0].itemID != ItemRegistry.SPRING.getShiftedID())
			return;
		tickcount++;
		if (inv[0].getItemDamage() > 0) {
			int dmg = inv[0].getItemDamage();
			if (tickcount > 120) {
				ItemStack is = new ItemStack(ItemRegistry.SPRING.getShiftedID(), 1, dmg-1);
				inv[0] = is;
				tickcount = 0;
			}
		}

		this.getExplosionModifiers();
		if (this.ageFail())
			this.detonate(world, x, y, z);
		if (this.checkForArrow(world, x, y, z))
			this.detonate(world, x, y, z);
		if (this.checkForPlayer(world, x, y, z))
			this.detonate(world, x, y, z);
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);

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

}
