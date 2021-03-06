/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Reika.DragonAPI.Libraries.ReikaInventoryHelper;
import Reika.RotaryCraft.Auxiliary.WorldEditHelper;
import Reika.RotaryCraft.Registry.LiquidRegistry;

public class ContainerWorldEdit extends Container
{
	/** The crafting matrix inventory (3x3). */
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 1, 1);
	private World worldObj;

	public ContainerWorldEdit(EntityPlayer player, World par2World)
	{
		worldObj = par2World;
		int var6;
		int var7;

		this.addSlotToContainer(new Slot(craftMatrix, 0, 80, 35));

		for (var6 = 0; var6 < 3; ++var6)
			for (var7 = 0; var7 < 9; ++var7)
				this.addSlotToContainer(new Slot(player.inventory, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
		for (var6 = 0; var6 < 9; ++var6)
			this.addSlotToContainer(new Slot(player.inventory, var6, 8 + var6 * 18, 142));
		this.onCraftMatrixChanged(craftMatrix);
	}

	@Override
	public void onCraftGuiClosed(EntityPlayer par1EntityPlayer) {
		super.onCraftGuiClosed(par1EntityPlayer);
		ItemStack var3 = craftMatrix.getStackInSlotOnClosing(0);
		if (var3 != null) {
			if (!ReikaInventoryHelper.addToIInv(var3, par1EntityPlayer.inventory)) {
				if (!worldObj.isRemote)
					par1EntityPlayer.dropPlayerItem(var3);
			}
			if (LiquidRegistry.isLiquidItem(var3)) {
				LiquidRegistry liq = LiquidRegistry.getLiquidFromIDAndMetadata(var3.itemID, var3.getItemDamage());
				if (liq.hasBlock())
					WorldEditHelper.addCommand(par1EntityPlayer, liq.getLiquidBlockID(), 0);
				return;
			}
			if (!(var3.getItem() instanceof ItemBlock))
				return;
			WorldEditHelper.addCommand(par1EntityPlayer, var3.itemID, var3.getItemDamage());
		}
		else {
			WorldEditHelper.addCommand(par1EntityPlayer, 0, 0);
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return true;
	}

	/**
	 * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack var3 = null;
		Slot var4 = (Slot)inventorySlots.get(par2);

		if (var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if (par2 == 0)
			{
				if (!this.mergeItemStack(var5, 10, 46, true))
				{
					return null;
				}

				var4.onSlotChange(var5, var3);
			}
			else if (par2 >= 10 && par2 < 37)
			{
				if (!this.mergeItemStack(var5, 37, 46, false))
				{
					return null;
				}
			}
			else if (par2 >= 37 && par2 < 46)
			{
				if (!this.mergeItemStack(var5, 10, 37, false))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(var5, 10, 46, false))
			{
				return null;
			}

			if (var5.stackSize == 0)
			{
				var4.putStack((ItemStack)null);
			}
			else
			{
				var4.onSlotChanged();
			}

			if (var5.stackSize == var3.stackSize)
			{
				return null;
			}

			var4.onPickupFromSlot(par1EntityPlayer, var5);
		}

		return var3;
	}
}
