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
import Reika.RotaryCraft.Auxiliary.IORenderer;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.Base.RotaryTERenderer;
import Reika.RotaryCraft.Models.ModelHeater;
import Reika.RotaryCraft.TileEntities.TileEntityHeater;

public class RenderHeater extends RotaryTERenderer
{

	private ModelHeater HeaterModel = new ModelHeater();
	//private ModelHeaterV HeaterModelV = new ModelHeaterV();

	/**
	 * Renders the TileEntity for the position.
	 */
	public void renderTileEntityHeaterAt(TileEntityHeater tile, double par2, double par4, double par6, float par8)
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
				//((BlockHeaterBlock1)var10).unifyAdjacentChests(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
				var9 = tile.getBlockMetadata();
			}
		}

		if (true)
		{
			ModelHeater var14;
			var14 = HeaterModel;
			//ModelHeaterV var15;
			//var14 = this.HeaterModelV;

			this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/heatertex.png");
			if (tile.temperature >= 200)
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/heatertex200C.png");
			if (tile.temperature >= 400)
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/heatertex400C.png");
			if (tile.temperature >= 600)
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/heatertex600C.png");
			if (tile.temperature >= 800)
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/heatertex800C.png");
			if (tile.temperature >= 900)
				this.bindTextureByName("/Reika/RotaryCraft/Textures/TileEntityTex/heatertex900C.png");

			GL11.glPushMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6 + 1.0F);
			GL11.glScalef(1.0F, -1.0F, -1.0F);
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			int var11 = 0;	 //used to rotate the model about metadata

			//float var12 = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * par8;
			float var13;/*

            var12 = 1.0F - var12;
            var12 = 1.0F - var12 * var12 * var12;*/
			// if (tile.getBlockMetadata() < 4)


			var14.renderAll(null, 0);
			// else
			//var15.renderAll();
			if (tile.isInWorld())
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double par2, double par4, double par6, float par8)
	{
		if (this.isValidMachineRenderpass((RotaryCraftTileEntity)tile))
			this.renderTileEntityHeaterAt((TileEntityHeater)tile, par2, par4, par6, par8);
		if (((RotaryCraftTileEntity) tile).isInWorld() && MinecraftForgeClient.getRenderPass() == 1)
			IORenderer.renderIO(tile, par2, par4, par6);
	}

	@Override
	public String getImageFileName(RenderFetcher te) {
		TileEntityHeater tile = (TileEntityHeater)te;
		String name;
		if (tile.temperature >= 200)
			name = "heatertex200C.png";
		else if (tile.temperature >= 400)
			name = "heatertex400C.png";
		else if (tile.temperature >= 600)
			name = "heatertex600C.png";
		else if (tile.temperature >= 800)
			name = "heatertex800C.png";
		else if (tile.temperature >= 900)
			name = "heatertex900C.png";
		else
			name = "heatertex.png";
		return name;
	}
}
