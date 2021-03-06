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

import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Libraries.ReikaGuiAPI;
import Reika.RotaryCraft.Base.GuiMachine;
import Reika.RotaryCraft.Containers.ContainerExtractor;
import Reika.RotaryCraft.TileEntities.TileEntityExtractor;

public class GuiExtractor extends GuiMachine
{
	private TileEntityExtractor ext;

	public GuiExtractor(EntityPlayer p5ep, TileEntityExtractor Extractor)
	{
		super(new ContainerExtractor(p5ep, Extractor), Extractor);
		ext = Extractor;
		ep = p5ep;
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		super.drawGuiContainerBackgroundLayer(par1, par2, par3);

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;

		int i1 = ext.getCookProgressScaled(32, 0);
		int i2 = ext.getCookProgressScaled(28, 1);
		int i3 = ext.getCookProgressScaled(28, 2);
		int i4 = ext.getCookProgressScaled(32, 3);
		this.drawTexturedModalRect(j + 29, k + 34, 176, 48, 10, i1);
		this.drawTexturedModalRect(j + 63, k + 35, 186, 48, 14, i2);
		this.drawTexturedModalRect(j + 99, k + 35, 200, 48, 14, i3);
		this.drawTexturedModalRect(j + 133, k + 49-i4, 176, 79-i4, 17, i4);
	}

	@Override
	protected void drawPowerTab(int var5, int var6) {
		String var4 = "/Reika/RotaryCraft/Textures/GUI/powertab.png";
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(var4);
		this.drawTexturedModalRect(xSize+var5, var6+4, 42, 4, 42, ySize-4);

		for (int i = 0; i < 4; i++) {
			int frac = (int)((ext.power*31L)/ext.machine.getMinPower(i));
			if (frac > 31)
				frac = 31;
			this.drawTexturedModalRect(xSize+var5+7+7*i, ySize+var6-144+31-frac, 0, 200-frac, 5, frac);
		}

		for (int i = 0; i < 4; i++) {
			int frac = ext.omega*31/ext.machine.getMinSpeed(i);
			if (frac > 31)
				frac = 31;
			this.drawTexturedModalRect(xSize+var5+7+7*i, ySize+var6-93+31-frac, 0, 200-frac, 5, frac);
		}

		for (int i = 0; i < 4; i++) {
			int frac = ext.torque*31/ext.machine.getMinTorque(i);
			if (frac > 31)
				frac = 31;
			this.drawTexturedModalRect(xSize+var5+7+7*i, ySize+var6-42+31-frac, 0, 200-frac, 5, frac);
		}

		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, "Power:", xSize+var5+20, var6+9, 0xff000000);
		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, "Speed:", xSize+var5+20, var6+60, 0xff000000);
		ReikaGuiAPI.instance.drawCenteredStringNoShadow(fontRenderer, "Torque:", xSize+var5+20, var6+111, 0xff000000);
		//this.drawCenteredStringNoShadow(fontRenderer, String.format("%d/%d", ext.power, ext.MINPOWER), xSize+var5+16, var6+16, 0xff000000);
	}

	@Override
	public String getGuiTexture() {
		return "extractorgui";
	}
}
