/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.Renders;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import Reika.DragonAPI.Interfaces.RenderFetcher;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Models.ModelObsidian;
import Reika.RotaryCraft.TileEntities.TileEntityObsidianMaker;

public class RenderObsidian extends RotaryTERenderer
{


	private ModelObsidian ObsidianModel = new ModelObsidian();
	//private ModelObsidianV ObsidianModelV = new ModelObsidianV();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityObsidianAt(TileEntityObsidianMaker tile, double par2, double par4, double par6, float par8)
	{
		int var9;

		if (!tile.isInWorld())
		{
			var9 = 0;
		}
		else
		{

			var9 = tile.getBlockMetadata();


			{
				//((BlockObsidianBlock1)var10).unifyAdjacentChests(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
				var9 = tile.getBlockMetadata();
			}
		}

		if (true)
		{
			ModelObsidian var14;
			var14 = ObsidianModel;
			//ModelObsidianV var15;
			//var14 = this.ObsidianModelV;
			if (tile.waterLevel > 0 && tile.lavaLevel > 0)
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/obsidiantex.png");
			else if (tile.waterLevel <= 0)
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/obsidiantexlava.png");
			else if (tile.lavaLevel <= 0)
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/obsidiantexwater.png");

			GL11.glPushMatrix();
			if (tile.isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
				GL11.glEnable(GL11.GL_BLEND);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glColor4f(1.0F+tile.overred, 1.0F+tile.overgreen, 1.0F+tile.overblue, 1.0F);
			GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
			GL11.glScalef(1.0F, -1.0F, -1.0F);
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			//float var12 = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * par8;
			float var13;/*

            var12 = 1.0F - var12;
            var12 = 1.0F - var12 * var12 * var12;*/
			// if (tile.getBlockMetadata() < 4)
			Object[] pars = new Object[2];
			pars[0] = (MinecraftForgeClient.getRenderPass() == 1 && (tile.waterLevel > 0 || tile.lavaLevel > 0));
			pars[1] = (tile.shouldRenderInPass(0) && MinecraftForgeClient.getRenderPass() == 0) || !tile.isInWorld();
			var14.renderAll(ReikaJavaLibrary.makeListFromArray(pars), 0);
			// else
			//var15.renderAll();
			if (tile.isInWorld() || MinecraftForgeClient.getRenderPass() == 1)
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityObsidianAt((TileEntityObsidianMaker)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		TileEntityObsidianMaker teo = (TileEntityObsidianMaker)te;
		if (teo.waterLevel > 0 && teo.lavaLevel > 0)
			return "obsidiantex.png";
		else if (teo.waterLevel <= 0)
			return "obsidiantexlava.png";
		else if (teo.lavaLevel <= 0)
			return "obsidiantexwater.png";
		return null;
	}
}
