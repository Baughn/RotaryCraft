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

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Reika.DragonAPI.Interfaces.GuiController;
import Reika.DragonAPI.Libraries.ReikaArrayHelper;
import Reika.DragonAPI.Libraries.ReikaEntityHelper;
import Reika.RotaryCraft.Auxiliary.RangedEffect;
import Reika.RotaryCraft.Base.RotaryModelBase;
import Reika.RotaryCraft.Base.TileEntityPowerReceiver;
import Reika.RotaryCraft.Registry.MachineRegistry;

public class TileEntityMobRadar extends TileEntityPowerReceiver implements GuiController, RangedEffect {

	/// Too RAM intensive
	//public EntityLiving[][] mobs = new EntityLiving[49][49];

	public int[][] colors = new int[49][49]; // |<--- 24 ---- R ---- 24 --->|
	public int[][] mobs = new int[49][49];
	public List inzone;
	public String owner;

	public static final int FALLOFF = 4096; //4kW per extra meter

	public boolean hostile = true;
	public boolean animal = true;
	public boolean player = true;

	@Override
	public void updateEntity(World world, int x, int y, int z, int meta) {
		super.updateTileEntity();
		this.getPowerBelow();
		this.getMobs(world, x, y, z);
	}

	public int getRange() {
		int range = (int)(8+(power-MINPOWER)/FALLOFF);
		if (range > 24)
			return 24;
		return range;
	}

	public int[] getBounds() {
		int range = this.getRange();
		int[] bounds = {24-range, 24+range};
		return bounds;
	}

	public void getMobs(World world, int x, int y, int z) {
		colors = ReikaArrayHelper.fillMatrix(colors, 0);
		mobs = ReikaArrayHelper.fillMatrix(mobs, 0);
		int range = this.getRange();
		AxisAlignedBB zone = AxisAlignedBB.getBoundingBox(x-range, 0, z-range, x+1+range, 255, z+1+range);
		inzone = world.getEntitiesWithinAABB(EntityLiving.class, zone);
		for (int i = 0; i < inzone.size(); i++) {
			EntityLiving ent = (EntityLiving)inzone.get(i);
			int ex = (int)ent.posX-x;
			int ey = (int)ent.posY-y;
			int ez = (int)ent.posZ-z;
			if (EntityList.getEntityID(ent) > 0 && Math.abs(ex) < 25 && Math.abs(ez) < 25 && ((ReikaEntityHelper.isHostile(ent) && hostile) || (!ReikaEntityHelper.isHostile(ent) && animal))) {
				colors[ex+24][ez+24] = ReikaEntityHelper.mobToColor(ent);
				mobs[ex+24][ez+24] = EntityList.getEntityID(ent);
			}
			else if (ent instanceof EntityPlayer && Math.abs(ex) < 25 && Math.abs(ez) < 25 && player) {
				colors[ex+24][ez+24] = ReikaEntityHelper.mobToColor(ent);
				mobs[ex+24][ez+24] = -1;
			}
		}
	}

	@Override
	public boolean hasModelTransparency() {
		return false;
	}

	@Override
	public RotaryModelBase getTEModel(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void animateWithTick(World world, int x, int y, int z) {
		if (!this.isInWorld()) {
			phi = 0;
			return;
		}
		if (power < MINPOWER)
			return;
		//this.phi += ReikaMathLibrary.doubpow(ReikaMathLibrary.logbase(this.omega+1, 2), 1.05);
		phi += 4F;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	@Override
	public void writeToNBT(NBTTagCompound NBT)
	{
		super.writeToNBT(NBT);
		if (owner != null && !owner.isEmpty())
			NBT.setString("own", owner);
	}

	@Override
	public void readFromNBT(NBTTagCompound NBT)
	{
		super.readFromNBT(NBT);
		owner = NBT.getString("own");
	}

	@Override
	public int getMachineIndex() {
		return MachineRegistry.MOBRADAR.ordinal();
	}

	@Override
	public int getMaxRange() {
		return 24;
	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

}
