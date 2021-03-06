/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import Reika.DragonAPI.Exception.RegistrationException;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.Auxiliary.ItemStacks;

public enum LiquidRegistry {

	WATER(Item.bucketWater.itemID, LiquidDictionary.getCanonicalLiquid("Water")),
	LAVA(Item.bucketLava.itemID, LiquidDictionary.getCanonicalLiquid("Lava")),
	LUBRICANT(ItemStacks.lubebucket.itemID, ItemStacks.lubebucket.getItemDamage(), LiquidDictionary.getCanonicalLiquid("Lubricant")),
	JETFUEL(ItemStacks.fuelbucket.itemID, ItemStacks.fuelbucket.getItemDamage(), LiquidDictionary.getCanonicalLiquid("Jet Fuel")),
	ETHANOL(ItemStacks.ethanolbucket.itemID, ItemStacks.ethanolbucket.getItemDamage(), LiquidDictionary.getCanonicalLiquid("RC Ethanol"));

	public static final LiquidRegistry[] liquidList = LiquidRegistry.values();

	private int liquidID;
	private int liquidMeta;
	private LiquidStack forgeLiquid;

	private LiquidRegistry(int id, LiquidStack liq) {
		this(id, -1, liq);
	}

	private LiquidRegistry(int id, int meta, LiquidStack liq) {
		liquidID = id;
		liquidMeta = meta;
		forgeLiquid = liq;
	}

	public boolean isMetadata() {
		return liquidMeta > -1;
	}

	public static LiquidRegistry getLiquidFromIDAndMetadata(int id, int meta) {
		for (int i = 0; i < liquidList.length; i++) {
			if (liquidList[i].liquidID == id && (!liquidList[i].isMetadata() || liquidList[i].liquidMeta == meta))
				return liquidList[i];
		}
		throw new RegistrationException(RotaryCraft.instance, "Unregistered liquid ID "+id+" and metadata "+meta+"!");
	}

	public boolean hasBlock() {
		return this == WATER || this == LAVA;
	}

	public int getLiquidBlockID() {
		if (this == WATER)
			return 9;
		if (this == LAVA)
			return 11;
		throw new RegistrationException(RotaryCraft.instance, "Liquid "+this+" is not registered to have a block form and yet was called!");
	}

	public static LiquidRegistry getLiquidFromBlock(int block) {
		for (int i = 0; i < liquidList.length; i++) {
			if (liquidList[i].getLiquidBlockID() == block)
				return liquidList[i];
		}
		throw new RegistrationException(RotaryCraft.instance, "Unregistered liquid for block "+block+"!");
	}

	public static boolean hasLiquid(LiquidRegistry liq, ItemStack[] inv) {
		if (liq.isMetadata())
			return ReikaInventoryHelper.checkForItemStack(liq.liquidID, liq.liquidMeta, inv);
		else
			return ReikaInventoryHelper.checkForItem(liq.liquidID, inv);
	}

	public static boolean isLiquidItem(ItemStack is) {
		if (is == null)
			return false;
		for (int i = 0; i < liquidList.length; i++) {
			if (liquidList[i].liquidID == is.itemID && (!liquidList[i].isMetadata() || liquidList[i].liquidMeta == is.getItemDamage()))
				return true;
		}
		return false;
	}

	public String getName() {
		String name = this.name();
		String truename = name.charAt(0)+name.substring(1).toLowerCase();
		return truename;
	}

	public ItemStack getHeldItemFor() {
		return new ItemStack(liquidID, 1, liquidMeta);
	}

	public int convertLiquidToForge(int blocks) {
		return forgeLiquid.amount*blocks;
	}

	public int convertLiquidToRotary(int forgeliq) {
		return forgeliq/forgeLiquid.amount;
	}

	public int getWorldBlockID() {
		if (!this.hasForgeLiquid())
			throw new RegistrationException(RotaryCraft.instance, this+" has no world block for its liquid!");
		return forgeLiquid.itemID;
	}

	private boolean hasForgeLiquid() {
		return forgeLiquid != null;
	}

	public LiquidStack getForgeLiquid() {
		return forgeLiquid.copy();
	}
}
