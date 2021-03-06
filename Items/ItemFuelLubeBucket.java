/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import Reika.DragonAPI.Interfaces.IndexedItemSprites;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Base.ItemRotaryTool;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFuelLubeBucket extends ItemRotaryTool implements IndexedItemSprites {

	public static final int LUBE_VALUE = 64;
	public static final int JET_VALUE = 8;
	public static final int ETHANOL_VALUE = 16;

	public ItemFuelLubeBucket(int ID, int tex) {
		super(ID, tex);
		hasSubtypes = true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) //Adds the metadata blocks to the creative inventory
	{
		for (int i = 0; i < RotaryNames.bucketNames.length; i++)
			par3List.add(new ItemStack(par1, 1, i));
	}

	@Override
	public String getUnlocalizedName(ItemStack is)
	{
		int d = is.getItemDamage();
		return super.getUnlocalizedName() + "." + String.valueOf(d);
	}

	@Override
	public int getItemSpriteIndex(ItemStack item) {
		return 104+item.getItemDamage();
	}
}
