/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft.GUIs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Base.CoreContainer;
import Reika.RotaryCraft.Base.GuiPowerOnlyMachine;
import Reika.RotaryCraft.TileEntities.TileEntityMobRadar;

public class GuiMobRadar extends GuiPowerOnlyMachine
{

	private TileEntityMobRadar radar;
	//private World worldObj = ModLoader.getMinecraftInstance().theWorld;

	int x;
	int y;
	public static final int UNIT = 4;
	private boolean hostile = true;
	private boolean animal = true;
	private boolean players = true;
	private int direction;

	public GuiMobRadar(EntityPlayer p5ep, TileEntityMobRadar MobRadar)
	{
		super(new CoreContainer(p5ep, MobRadar), MobRadar);
		radar = MobRadar;
		ySize = 223;
		xSize = 214;
		ep = p5ep;
		hostile = radar.hostile;
		animal = radar.animal;
		players = radar.player;
		direction = MathHelper.floor_double((ep.rotationYaw * 4F) / 360F + 0.5D);
		while (direction > 3)
			direction -= 4;
		while (direction < 0)
			direction += 4;
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2+1;

		this.drawRect(j+xSize/2-1-radar.getRange()*UNIT, k+ySize/2+5+radar.getRange()*UNIT, j+xSize/2+1+radar.getRange()*UNIT, k+ySize/2+6+radar.getRange()*UNIT, 0xffffffff);
		this.drawRect(j+xSize/2-1-radar.getRange()*UNIT, k+ySize/2+4-radar.getRange()*UNIT, j+xSize/2+1+radar.getRange()*UNIT, k+ySize/2+5-radar.getRange()*UNIT, 0xffffffff);
		this.drawRect(j+xSize/2-1-radar.getRange()*UNIT, k+ySize/2+4-radar.getRange()*UNIT, j+xSize/2-radar.getRange()*UNIT, k+ySize/2+6+radar.getRange()*UNIT, 0xffffffff);
		this.drawRect(j+xSize/2+radar.getRange()*UNIT, k+ySize/2+4-radar.getRange()*UNIT, j+xSize/2+1+radar.getRange()*UNIT, k+ySize/2+6+radar.getRange()*UNIT, 0xffffffff);

		this.drawRadar(j, k);
	}

	private void drawRadar(int a, int b) {
		for (int i = radar.getBounds()[0]; i <= radar.getBounds()[1]; i++) {
			for (int j = radar.getBounds()[0]; j <=	 radar.getBounds()[1]; j++) {
				this.drawMobIcon(a+7, b+16, UNIT*i, UNIT*j, radar.mobs[i][j], i, j);
			}
		}
	}

	private void drawMobIcon(int a, int b, int px, int py, int id, int i, int j) {
		String var4 = "/Reika/RotaryCraft/Textures/GUI/mobicons.png";
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(var4);
		int v = 2*UNIT*(id/16);
		int u = 2*UNIT*(id-(v/UNIT/2)*16);
		if (id == -1) {
			u = 2*UNIT;
			v = 0;
		}
		this.drawTexturedModalRect(a+px-UNIT/2, b+py-UNIT/2, u, v, UNIT*2, UNIT*2);
	}


	@Override
	public String getGuiTexture() {
		return "mobradargui";
	}
}
