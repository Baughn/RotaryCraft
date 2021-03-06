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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Reika.DragonAPI.Auxiliary.APIRegistry;
import Reika.DragonAPI.Auxiliary.EnumLook;
import Reika.DragonAPI.Libraries.ReikaChatHelper;
import Reika.DragonAPI.Libraries.ReikaDyeHelper;
import Reika.DragonAPI.Libraries.ReikaEnchantmentHelper;
import Reika.DragonAPI.Libraries.ReikaItemHelper;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.ReikaWorldHelper;
import Reika.DragonAPI.ModInteract.DartItemHandler;
import Reika.RotaryCraft.RotaryConfig;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.EnchantableMachine;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RotaryAux;
import Reika.RotaryCraft.Auxiliary.TemperatureTE;
import Reika.RotaryCraft.Blocks.BlockPiping;
import Reika.RotaryCraft.Registry.GuiRegistry;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.LiquidRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import Reika.RotaryCraft.TileEntities.TileEntityBedrockBreaker;
import Reika.RotaryCraft.TileEntities.TileEntityBridgeEmitter;
import Reika.RotaryCraft.TileEntities.TileEntityCaveFinder;
import Reika.RotaryCraft.TileEntities.TileEntityDisplay;
import Reika.RotaryCraft.TileEntities.TileEntityFloodlight;
import Reika.RotaryCraft.TileEntities.TileEntityLamp;
import Reika.RotaryCraft.TileEntities.TileEntityLandmine;
import Reika.RotaryCraft.TileEntities.TileEntityMirror;
import Reika.RotaryCraft.TileEntities.TileEntityMusicBox;
import Reika.RotaryCraft.TileEntities.TileEntityReservoir;
import Reika.RotaryCraft.TileEntities.TileEntityScaleableChest;
import Reika.RotaryCraft.TileEntities.TileEntityScreen;
import Reika.RotaryCraft.TileEntities.TileEntityVacuum;

public abstract class BlockBasicMultiTE extends Block {

	protected Random par5Random = new Random();

	/** Icons by metadata 0-15 and side 0-6. Nonmetadata blocks can just set the first index to 0 at all times. */
	public Icon[][][][] icons = new Icon[16][16][6][8];

	public BlockBasicMultiTE(int ID, Material mat) {
		super(ID, mat);
		this.setHardness(4F);
		this.setResistance(15F);
		this.setLightValue(0F);
		if (mat == Material.iron)
			this.setStepSound(soundMetalFootstep);
	}

	@Override
	public final boolean hasTileEntity(int meta) {
		return true;
	}

	@Override
	public final Icon getBlockTexture(IBlockAccess iba, int x, int y, int z, int s)
	{
		RotaryCraftTileEntity te = (RotaryCraftTileEntity)iba.getBlockTileEntity(x, y, z);
		int meta = te.getBlockMetadata();
		int machine = te.getMachine().getMachineMetadata();
		return icons[machine][meta][s][te.getTextureStateForSide(s)];
	}

	@Override
	public final Icon getIcon(int s, int meta) {
		return icons[meta][0][s][0];
	}

	public final AxisAlignedBB getBlockAABB() {
		return AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 1, 1);
	}

	public final void setFullBlockBounds() {
		this.setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return true;
	}

	/** Sides: 0 bottom, 1 top, 2 back, 3 front, 4 right, 5 left */
	@Override
	public abstract void registerIcons(IconRegister ico);

	@Override
	public int getRenderType() {
		return 0;
	}

	@Override
	public int idDropped(int id, Random r, int fortune) {
		return 0;//return RotaryCraft.machineplacer.itemID;
	}

	@Override
	public int damageDropped(int par1)
	{
		return MachineRegistry.getMachineIndexFromIDandMetadata(blockID, par1);
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 1;
	}

	@Override
	public final void addCreativeItems(ArrayList list)
	{
		list.add(new ItemStack(this));
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z)
	{
		this.setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
		float minx = (float)minX;
		float maxx = (float)maxX;
		float miny = (float)minY;
		float maxy = (float)maxY;
		float minz = (float)minZ;
		float maxz = (float)maxZ;

		this.setBlockBounds(minx, miny, minz, maxx, maxy, maxz);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer ep, int par6, float par7, float par8, float par9) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		ItemStack is = ep.getCurrentEquippedItem();
		if (APIRegistry.DARTCRAFT.conditionsMet() && DartItemHandler.getInstance().isWrench(is)) {
			ep.setCurrentItemOrArmor(0, null);
			ep.playSound("random.break", 1, 1);
			ep.attackEntityFrom(DamageSource.inWall, 4);
			ReikaChatHelper.write("Your tool has shattered into a dozen pieces.");
			return true;
		}
		if (ep.isSneaking() && !((RotaryCraftTileEntity)te).getMachine().hasSneakActions())
			return false;
		if (is != null && ItemRegistry.isRegistered(is) && ItemRegistry.getEntry(is).overridesRightClick()) {
			return false;
		}
		if (is != null && is.itemID == Item.enchantedBook.itemID && m.isEnchantable()) {
			if (((EnchantableMachine)te).applyEnchants(is)) {
				if (!ep.capabilities.isCreativeMode)
					ep.setCurrentItemOrArmor(0, null);
				return true;
			}
			return false;
		}
		if (m == MachineRegistry.RESERVOIR) {
			TileEntityReservoir tr = (TileEntityReservoir)te;
			if (!tr.isUseableByPlayer(ep))
				return false;
			if (is != null) {
				if (LiquidRegistry.isLiquidItem(is)) {
					LiquidRegistry liq = LiquidRegistry.getLiquidFromIDAndMetadata(is.itemID, 0);
					if (liq.hasBlock()) {
						int size = is.stackSize;
						if (tr.liquidLevel+size <= tr.CAPACITY) {
							if (tr.liquidID == -1) {
								tr.liquidID = liq.getLiquidBlockID();
								tr.liquidLevel += size*RotaryConfig.MILLIBUCKET;
								if (!ep.capabilities.isCreativeMode)
									ep.setCurrentItemOrArmor(0, new ItemStack(Item.bucketEmpty.itemID, size, 0));
								return true;
							}
							else if (tr.liquidID == liq.getLiquidBlockID()) {
								tr.liquidLevel += size*RotaryConfig.MILLIBUCKET;
								if (!ep.capabilities.isCreativeMode)
									ep.setCurrentItemOrArmor(0, new ItemStack(Item.bucketEmpty.itemID, size, 0));
								return true;
							}
						}
					}
				}
				else if (is.itemID == Item.bucketEmpty.itemID) {
					int size = is.stackSize;
					if (tr.liquidLevel >= size) {
						LiquidRegistry liq = LiquidRegistry.getLiquidFromBlock(tr.liquidID);
						tr.liquidLevel -= size*RotaryConfig.MILLIBUCKET;
						ep.setCurrentItemOrArmor(0, ReikaItemHelper.getSizedItemStack(liq.getHeldItemFor(), size));
						return true;
					}
				}
				else if (is.itemID == Item.glassBottle.itemID) {
					int size = is.stackSize;
					if (tr.liquidID == LiquidRegistry.WATER.getLiquidBlockID()) {
						ep.setCurrentItemOrArmor(0, new ItemStack(Item.potion.itemID, size, 0));
						return true;
					}
				}
			}
		}
		if (m == MachineRegistry.SCALECHEST) {
			TileEntityScaleableChest tc = (TileEntityScaleableChest)te;
			if (!tc.isUseableByPlayer(ep))
				return false;
		}
		if (m == MachineRegistry.BEDROCKBREAKER && !ep.isSneaking()) {
			TileEntityBedrockBreaker tb = (TileEntityBedrockBreaker)te;
			tb.dropItemFromInventory();
			return true;
		}
		if (m == MachineRegistry.DISPLAY && ReikaDyeHelper.isDyeItem(is)) {
			TileEntityDisplay td = (TileEntityDisplay)te;
			td.setColor(ReikaDyeHelper.getColorFromItem(is));
			return true;
		}
		if (m == MachineRegistry.DISPLAY && is != null && is.itemID == Item.lightStoneDust.itemID) {
			TileEntityDisplay td = (TileEntityDisplay)te;
			td.setColorToArgon();
			return true;
		}
		if (m == MachineRegistry.DISPLAY && is != null && is.itemID == ItemRegistry.SPRING.getShiftedID()) {
			TileEntityDisplay td = (TileEntityDisplay)te;
			td.swapCoils(is);
			if (!ep.capabilities.isCreativeMode)
				ep.setCurrentItemOrArmor(0, null);
			return true;
		}
		if (m == MachineRegistry.MIRROR) {
			TileEntityMirror tm = (TileEntityMirror)te;
			if (tm.broken) {
				if (ReikaItemHelper.matchStacks(is, ItemStacks.mirror)) {
					tm.repair(world, x, y, z);
					if (!ep.capabilities.isCreativeMode) {
						ep.setCurrentItemOrArmor(0, new ItemStack(is.itemID, is.stackSize-1, is.getItemDamage()));
					}
					return true;
				}
			}
		}
		/*
		if (te instanceof TileEntityMusicBox) {
			TileEntityMusicBox mb = (TileEntityMusicBox)te;
			if (is != null && is.itemID == RotaryCraft.disk.itemID) {
				if (is.hasTagCompound()) {
					mb.setMusicFile(is);
				}
				else {
					ep.setCurrentItemOrArmor(0, mb.getMusicFile());
				}
				return true;
			}
		}*/
		if (te != null && RotaryAux.hasGui(world, x, y, z, ep) && ((RotaryCraftTileEntity)te).isUseableByPlayer(ep)) {
			ep.openGui(RotaryCraft.instance, GuiRegistry.MACHINE.ordinal(), world, x, y, z);
			return true;
		}
		if (m == MachineRegistry.SCREEN) {
			TileEntityScreen tc = (TileEntityScreen)te;
			if (ep.isSneaking()) {
				tc.activate(ep);
				return true;
			}
		}
		if (m == MachineRegistry.CAVESCANNER) {
			TileEntityCaveFinder tc = (TileEntityCaveFinder)te;
			EnumLook dir = EnumLook.getLookDir(ep, true);
			int mov = tc.getRange()/4;
			if (ep.isSneaking())
				mov *= -1;
			tc.moveSrc(mov, dir);
			return true;
		}
		return false;
	}

	@Override
	public final ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		int id = this.idPicked(world, x, y, z);
		if (id == 0)
			return null;
		int meta = world.getBlockMetadata(target.blockX, target.blockY, target.blockZ);
		MachineRegistry m = MachineRegistry.getMachineFromIDandMetadata(id, meta);
		TileEntity tile = world.getBlockTileEntity(target.blockX, target.blockY, target.blockZ);
		ItemStack core = m.getCraftedProduct();
		if (m.isEnchantable()) {
			HashMap<Enchantment, Integer> ench = ((EnchantableMachine)tile).getEnchantments();
			core = ReikaEnchantmentHelper.applyEnchantments(core, ench);
			return core;
		}
		return core;
	}

	@Override
	public final boolean canBeReplacedByLeaves(World world, int x, int y, int z)
	{
		return false;
	}

	@Override
	public final boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata)
	{
		return false;
	}

	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face)
	{
		return 0;
	}

	@Override
	public boolean canDragonDestroy(World world, int x, int y, int z)
	{
		return true;
	}

	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		if (this.canHarvest(world, player, x, y, z))
			this.harvestBlock(world, player, x, y, z, 0);
		return world.setBlock(x, y, z, 0);
	}

	private boolean canHarvest(World world, EntityPlayer ep, int x, int y, int z) {
		if (ep.capabilities.isCreativeMode)
			return false;
		if (this instanceof BlockPiping)
			return true;
		return RotaryAux.canHarvestSteelMachine(ep);
	}

	@Override
	public final void harvestBlock(World world, EntityPlayer ep, int x, int y, int z, int meta)
	{
		if (!this.canHarvest(world, ep, x, y, z))
			return;
		TileEntity te = world.getBlockTileEntity(x, y, z);
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		if (m != null) {
			ItemStack is = m.getCraftedProduct();
			List li;
			if (m.isEnchantable()) {
				HashMap<Enchantment,Integer> map = ((EnchantableMachine)te).getEnchantments();
				is = ReikaEnchantmentHelper.applyEnchantments(is, map);
			}
			if (m.isBroken((RotaryCraftTileEntity)te))
				li = m.getBrokenProducts();
			else
				li = ReikaJavaLibrary.makeListFrom(is);
			ReikaItemHelper.dropItems(world, x+par5Random.nextDouble(), y+par5Random.nextDouble(), z+par5Random.nextDouble(), li);
		}
	}

	@Override
	public final void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof IInventory)
			ReikaItemHelper.dropInventory(world, x, y, z);
		if (te instanceof TileEntityVacuum) {
			ReikaWorldHelper.splitAndSpawnXP(world, x+par5Random.nextFloat(), y+par5Random.nextFloat(), z+par5Random.nextFloat(), ((TileEntityVacuum)(te)).experience);
		}
		if (te instanceof TileEntityBridgeEmitter) {
			((TileEntityBridgeEmitter)te).lightsOut(world, x, y, z);
		}
		if (te instanceof TileEntityFloodlight) {
			((TileEntityFloodlight)te).lightsOut(world, x, y, z);
		}
		if (te instanceof TileEntityMusicBox) {
			((TileEntityMusicBox)te).deleteFiles(x, y, z);
		}
		if (te instanceof TileEntityDisplay) {
			((TileEntityDisplay)te).deleteFiles(x, y, z);
		}
		if (te instanceof TileEntityLamp) {
			((TileEntityLamp)te).clearAll();
		}
		super.breakBlock(world, x, y, z, par5, par6);
	}

	public final String getTextureFile(){
		return "/Reika/RotaryCraft/Textures/Terrain/textures.png";
	}

	@Override
	public final TileEntity createTileEntity(World world, int meta) {
		return MachineRegistry.createTEFromIDAndMetadata(blockID, meta);
	}

	/**
	 * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
	 */
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity e)
	{
		MachineRegistry m = MachineRegistry.getMachine(world, x, y, z);
		RotaryCraftTileEntity tile = (RotaryCraftTileEntity)world.getBlockTileEntity(x, y, z);
		if (m.dealsContactDamage(e)) {
			e.attackEntityFrom(DamageSource.generic, m.getContactDamage(tile));
		}
		if (m.dealsHeatDamage(e) && tile instanceof TemperatureTE) {
			int dmg = ((TemperatureTE)tile).getThermalDamage();
			if (dmg > 0) {
				e.attackEntityFrom(DamageSource.lava, dmg);
				e.setFire(6);
			}
		}
	}

	/**
	 * If this returns true, then comparators facing away from this block will use the value from
	 * getComparatorInputOverride instead of the actual redstone signal strength.
	 */
	@Override
	public final boolean hasComparatorInputOverride()
	{
		return true;
	}

	/**
	 * If hasComparatorInputOverride returns true, the return value from this is used instead of the redstone signal
	 * strength when this block inputs to a comparator.
	 */
	@Override
	public final int getComparatorInputOverride(World world, int x, int y, int z, int par5)
	{
		return ((RotaryCraftTileEntity)world.getBlockTileEntity(x, y, z)).getRedstoneOverride();
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(RotaryCraft.machineplacer.itemID, 1, MachineRegistry.getMachineIndexFromIDandMetadata(blockID, metadata)));
		return ret;
	}

	@Override
	public void fillWithRain(World par1World, int par2, int par3, int par4) {

	}

	@Override
	public final void onBlockExploded(World world, int x, int y, int z, Explosion explosion)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof TileEntityLandmine) {
			TileEntityLandmine tl = (TileEntityLandmine)te;
			tl.detonate(world, x, y, z);
		}
		super.onBlockExploded(world, x, y, z, explosion);
	}

}
