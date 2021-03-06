/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Libraries.ReikaItemHelper;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Base.BlockModelledMachine;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Items.ItemDebug;
import Reika.RotaryCraft.Items.Tools.ItemMeter;
import Reika.RotaryCraft.Items.Tools.ItemScrewdriver;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MaterialRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityShaft;

public class BlockShaft extends BlockModelledMachine {


	public BlockShaft(int ID, Material mat) {
		super(ID, mat);
	}

	/**
	 * Returns the TileEntity used by this block.
	 */
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityShaft();
	}

	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face) {
		TileEntityShaft ts = (TileEntityShaft)world.getBlockTileEntity(x, y, z);
		if (ts == null)
			return 0;
		if (ts.type != MaterialRegistry.WOOD)
			return 0;
		return 60;
	}

	@Override
	public float getExplosionResistance(Entity ent, World world, int x, int y, int z, double eX, double eY, double eZ)
	{
		TileEntityShaft sha = (TileEntityShaft)world.getBlockTileEntity(x, y, z);
		if (sha == null)
			return 0;
		MaterialRegistry type = sha.type;
		switch(type) {
		case WOOD:
			return 3F;
		case STONE:
			return 8F;
		case STEEL:
		case DIAMOND:
		case BEDROCK:
			return 15F;
		}
		return 0;
	}

	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer ep, World world, int x, int y, int z)
	{
		TileEntityShaft sha = (TileEntityShaft)world.getBlockTileEntity(x, y, z);
		if (sha == null)
			return 0.01F;
		int mult = 1;
		if (ep.inventory.getCurrentItem() != null) {
			if (ep.inventory.getCurrentItem().itemID == ItemRegistry.BEDPICK.getShiftedID())
				mult = 4;
		}
		if (this.canHarvest(world, ep, x, y, z))
			return mult*0.2F/(sha.type.ordinal()+1);
		return 0.01F/(sha.type.ordinal()+1);
	}

	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		if (this.canHarvest(world, player, x, y, z))
			this.harvestBlock(world, player, x, y, z, 0);
		return ReikaWorldHelper.legacySetBlockWithNotify(world, x, y, z, 0);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer ep, int par6, float par7, float par8, float par9)
	{
		if (ep.isSneaking())
			return true;
		if (ep.getCurrentEquippedItem() != null && (ep.getCurrentEquippedItem().getItem() instanceof ItemScrewdriver || ep.getCurrentEquippedItem().getItem() instanceof ItemMeter || ep.getCurrentEquippedItem().getItem() instanceof ItemDebug)) {
			return false;
		}
		TileEntityShaft tile = (TileEntityShaft)world.getBlockTileEntity(x, y, z);
		if (tile != null) {
			ItemStack fix;
			if (tile.type == null)
				return false;
			switch(tile.type) {
			case WOOD:
				fix = new ItemStack(Item.stick);
				break;
			case STONE:
				fix = ItemStacks.stonerod;
				break;
			case STEEL:
				fix = ItemStacks.shaftitem;
				break;
			case DIAMOND:
				fix = ItemStacks.diamondshaft;
				break;
			case BEDROCK:
				fix = ItemStacks.bedrockshaft;
				break;
			default:
				fix = new ItemStack(Block.stone);
				break;
			}
			if (ep.getCurrentEquippedItem() != null && (ep.getCurrentEquippedItem().itemID == fix.itemID && ep.getCurrentEquippedItem().getItemDamage() == fix.getItemDamage())) {
				tile.repair();
				if (!ep.capabilities.isCreativeMode) {
					int num = ep.getCurrentEquippedItem().stackSize;
					if (num > 1)
						ep.inventory.setInventorySlotContents(ep.inventory.currentItem, new ItemStack(fix.itemID, num-1, fix.getItemDamage()));
					else
						ep.inventory.setInventorySlotContents(ep.inventory.currentItem, null);
				}
				return false;
			}
		}
		return false;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		if (world.getBlockMetadata(x, y, z) < 6)
			return;
		TileEntityShaft t = (TileEntityShaft)world.getBlockTileEntity(x, y, z);
		if (t != null) {
			t.type = MaterialRegistry.STEEL;
		}
	}

	@Override
	public void harvestBlock(World world, EntityPlayer ep, int x, int y, int z, int meta) {
		if (!this.canHarvest(world, ep, x, y, z))
			return;
		TileEntityShaft sha = (TileEntityShaft)world.getBlockTileEntity(x, y, z);
		if (sha != null) {
			if (sha.failed) {
				ItemStack todrop = null;
				switch(sha.type) {
				case WOOD:
					todrop = new ItemStack(Block.planks.blockID, 5, 0);
					break;
				case STONE:
					todrop = new ItemStack(ReikaItemHelper.cobbleSlab.itemID, 5, ReikaItemHelper.cobbleSlab.getItemDamage());
					break;
				case STEEL:
					todrop = new ItemStack(ItemStacks.mount.itemID, 1, ItemStacks.mount.getItemDamage());	//drop mount
					break;
				case DIAMOND:
					todrop = new ItemStack(ItemStacks.mount.itemID, 1, ItemStacks.mount.getItemDamage());	//drop mount
					break;
				case BEDROCK:
					todrop = new ItemStack(ItemStacks.mount.itemID, 1, ItemStacks.mount.getItemDamage());	//drop mount
					break;
				}
				EntityItem item = new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, todrop);
				item.delayBeforeCanPickup = 10;
				if (!world.isRemote && !ep.capabilities.isCreativeMode)
					world.spawnEntityInWorld(item);
			}
			else if (sha.getBlockMetadata() < 6) {
				int metat = sha.type.ordinal();
				ItemStack todrop = new ItemStack(RotaryCraft.shaftitems.itemID, 1, metat); //drop shaft item
				EntityItem item = new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, todrop);
				item.delayBeforeCanPickup = 10;
				if (!world.isRemote && !ep.capabilities.isCreativeMode)
					world.spawnEntityInWorld(item);
			}
			else {/*
				ItemStack todrop = new ItemStack(MachineRegistry.SHAFT.getBlockID(), 1, 6); //drop shaft block (cross)
				EntityItem item = new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, todrop);
				item.delayBeforeCanPickup = 10;
				if (!world.isRemote && !ep.capabilities.isCreativeMode)
					world.spawnEntityInWorld(item);*/
				ItemStack todrop = new ItemStack(RotaryCraft.shaftitems.itemID, 1, RotaryNames.shaftItemNames.length-1); //drop shaft cross
				EntityItem item = new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, todrop);
				item.delayBeforeCanPickup = 10;
				if (!world.isRemote && !ep.capabilities.isCreativeMode)
					world.spawnEntityInWorld(item);
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving par5EntityLiving, ItemStack is)		//Directional code
	{
		int base = 0;
		int heldmeta = par5EntityLiving.getHeldItem().getItemDamage();
		if (heldmeta == 6)
			base = 6;
		if (MathHelper.abs(par5EntityLiving.rotationPitch) < 60) {
			int i = MathHelper.floor_double((par5EntityLiving.rotationYaw * 4F) / 360F + 0.5D);
			while (i > 3)
				i -= 4;
			while (i < 0)
				i += 4;
			switch (i) {
			case 0:
				ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, base+0);
				break;
			case 1:
				ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, base+3);
				break;
			case 2:
				ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, base+2);
				break;
			case 3:
				ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, base+1);
				break;
			}
		}
		else { //Looking up/down
			if (base == 6) {
				ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, base+0);
				return;
			}
			if (par5EntityLiving.rotationPitch > 0)
				ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, 4); //set to up
			else
				ReikaWorldHelper.legacySetBlockMetadataWithNotify(world, x, y, z, 5); //set to down
		}
	}

	public boolean canHarvest(World world, EntityPlayer player, int x, int y, int z)
	{
		if (player.capabilities.isCreativeMode)
			return false;
		TileEntityShaft ts = (TileEntityShaft)world.getBlockTileEntity(x, y, z);
		if (ts == null)
			return false;
		MaterialRegistry type = ts.type;
		return type.isHarvestablePickaxe(player.inventory.getCurrentItem());
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z)
	{
		this.setFullBlockBounds();
		RotaryCraftTileEntity te = (RotaryCraftTileEntity)iba.getBlockTileEntity(x, y, z);
		if (te.getBlockMetadata() < 6)
			return;
		this.setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
		float minx = (float)minX;
		float maxx = (float)maxX;
		float miny = (float)minY;
		float maxy = (float)maxY;
		float minz = (float)minZ;
		float maxz = (float)maxZ;
		maxy -= 0.1825F;

		this.setBlockBounds(minx, miny, minz, maxx, maxy, maxz);
	}

	@Override
	public final ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		TileEntityShaft tile = (TileEntityShaft)world.getBlockTileEntity(x, y, z);
		if (tile == null)
			return ret;
		ret.add(new ItemStack(RotaryCraft.shaftitems.itemID, 1, tile.type.ordinal()));
		return ret;
	}
}
