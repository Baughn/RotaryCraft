/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Reika.DragonAPI.Base.TileEntityBase;
import Reika.DragonAPI.Instantiable.StepTimer;
import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Interfaces.TextureFetcher;
import Reika.DragonAPI.Libraries.ReikaChatHelper;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;
import Reika.RotaryCraft.API.ShaftPowerEmitter;
import Reika.RotaryCraft.Auxiliary.InertIInv;
import Reika.RotaryCraft.Auxiliary.RotaryRenderList;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class RotaryCraftTileEntity extends TileEntityBase implements RenderFetcher {

	protected RotaryModelBase rmb;
	protected int tickcount = 0;
	/** Rotational speed in radians per render tick. */
	public float phi = 0;

	/** For emp */
	private boolean disabled;

	public int[] paint = {-1, -1, -1};

	protected StepTimer second = new StepTimer(20);

	@Override
	public final boolean canUpdate() {
		return true;
	}

	public abstract RotaryModelBase getTEModel(World world, int x, int y, int z);

	@Override
	public abstract void animateWithTick(World world, int x, int y, int z);

	public abstract int getMachineIndex();

	public final TextureFetcher getRenderer() {
		if (this.getMachine().hasRender())
			return RotaryRenderList.getRenderForMachine(this.getMachine());
		else
			return null;
	}

	public final String getInvName() {
		return this.getMultiValuedName();
	}

	public final MachineRegistry getMachine() {
		return MachineRegistry.machineList[this.getMachineIndex()];
	}

	public final String getName() {
		return MachineRegistry.machineList[this.getMachineIndex()].getName();
	}

	public final String getMultiValuedName() {
		if (MachineRegistry.machineList[this.getMachineIndex()].isMultiNamed())
			return MachineRegistry.machineList[this.getMachineIndex()].getMultiName(this);
		return MachineRegistry.machineList[this.getMachineIndex()].getName();
	}

	//public abstract int getMachineIndex();

	@Override
	public final int getTileEntityBlockID() {
		return MachineRegistry.machineList[this.getMachineIndex()].getBlockID();
	}

	public void giveNoSuperWarning() {
		ReikaJavaLibrary.spamConsole("TileEntity "+this.getName()+" does not call super()!");
		ReikaChatHelper.write("TileEntity "+this.getName()+" does not call super()!");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public final double getMaxRenderDistanceSquared() {
		return 4096D;
	}

	@Override
	public final boolean shouldRenderInPass(int pass)
	{
		if (!this.isInWorld())
			return true;
		if (pass == 0)
			return true;
		if (pass == 1 && (this.hasModelTransparency() || this.getMachine().renderInPass1()))
			return true;
		return false;
	}

	public abstract boolean hasModelTransparency();

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		NBT.setFloat("phi", phi);
		NBT.setInteger("tick", tickcount);
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		phi = NBT.getFloat("phi");
		tickcount = NBT.getInteger("tick");
	}

	public int[] getAccessibleSlotsFromSide(int var1) {
		if (this instanceof InertIInv)
			return new int[0];
		return ReikaInventoryHelper.getWholeInventoryForISided((ISidedInventory)this);
	}

	public boolean canInsertItem(int i, ItemStack is, int side) {
		if (this instanceof InertIInv)
			return false;
		return ((IInventory)this).isStackValidForSlot(i, is);
	}
	/*
	public String getName() {
		return Block.blocksList[this.getTileEntityBlockID()].getLocalizedName();
	}*/

	public boolean isSelfBlock() {
		if (worldObj.getBlockId(xCoord, yCoord, zCoord) != this.getTileEntityBlockID())
			return false;
		int meta = this.getMachine().getMachineMetadata();
		return ReikaMathLibrary.isValueInsideBoundsIncl(meta, meta+this.getMachine().getNumberMetadatas()-1, worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
	}

	public abstract int getRedstoneOverride();

	public boolean isUseableByPlayer(EntityPlayer var1) {
		if (ConfigRegistry.LOCKMACHINES.getState() && !var1.getEntityName().equals(placer)) {
			ReikaChatHelper.write("This "+this.getName()+" is locked and can only be used by "+placer+"!");
			return false;
		}
		double dist = ReikaMathLibrary.py3d(xCoord+0.5-var1.posX, yCoord+0.5-var1.posY, zCoord+0.5-var1.posZ);
		return (dist <= 8) && worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this;
	}

	@Override
	public String getTEName() {
		return this.getName();
	}

	@Override
	public boolean isIDTEMatch(World world, int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof ShaftPowerEmitter)
			return world.getBlockId(x, y, z) == te.getBlockType().blockID;
		return super.isIDTEMatch(world, x, y, z);
	}

	public boolean isShutdown() {
		return disabled;
	}

	public void shutdown() {
		disabled = true;
	}

	public int getTextureStateForSide(int s) {
		switch(this.getBlockMetadata()) {
		case 0:
			return s == 4 ? this.getActiveTexture() : 0;
		case 1:
			return s == 5 ? this.getActiveTexture() : 0;
		case 2:
			return s == 2 ? this.getActiveTexture() : 0;
		case 3:
			return s == 3 ? this.getActiveTexture() : 0;
		}
		return 0;
	}

	protected int getActiveTexture() {
		return 0;
	}
}
