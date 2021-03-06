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

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import Reika.DragonAPI.Instantiable.ImagedGuiButton;
import Reika.DragonAPI.Libraries.ReikaGuiAPI;
import Reika.RotaryCraft.Auxiliary.HandbookAuxData;
import Reika.RotaryCraft.Auxiliary.RotaryDescriptions;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.HandbookRegistry;
import Reika.RotaryCraft.Registry.MobBait;

public class GuiHandbook extends GuiScreen
{

	private int mx;
	private int my;
	protected final int xSize = 256;
	protected final int ySize = 220;
	public World worldObj;
	private EntityPlayer player;

	/** One second in nanoseconds. */
	public static final long SECOND = 2000000000;

	private static final int descX = 8;
	private static final int descY = 88;

	protected int screen = 0;
	protected int page = 0;
	protected int subpage = 0;
	private byte bcg;
	private int tickcount;

	public static long time;
	private long buttontime;
	public static int i = 0;
	private int buttoni = 0;
	protected int buttontimer = 0;

	private static int staticwidth;
	private static int staticheight;

	public GuiHandbook(EntityPlayer p5ep, World world, int s, int p)
	{
		//super();
		player = p5ep;
		worldObj = world;
		staticwidth = xSize;
		staticheight = ySize;

		screen = s;
		page = p;

		if (ConfigRegistry.DYNAMICHANDBOOK.getState())
			RotaryDescriptions.reload();
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();

		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2 - 8;

		String file = HandbookRegistry.TOC.getTabImageFile();
		buttonList.add(new ImagedGuiButton(10, j-20, 17+k+163, 20, 20, "-", 220, 0, 0, false, file)); //Prev Page
		buttonList.add(new ImagedGuiButton(11, j-20, 17+k+143, 20, 20, "+", 220, 20, 0, false, file));	//Next page
		buttonList.add(new ImagedGuiButton(15, j-20, 17+k+183, 20, 20, "<<", 220, 20, 0, false, file));	//Next page
		buttonList.add(new GuiButton(12, j+xSize-27, k+6, 20, 20, "X"));	//Close gui button

		HandbookRegistry h = HandbookRegistry.getEntry(screen, page);

		if (h.hasSubpages()) {
			buttonList.add(new GuiButton(13, j+xSize-27, k+40, 20, 20, ">"));
			buttonList.add(new GuiButton(14, j+xSize-27, k+60, 20, 20, "<"));
		}
		HandbookRegistry.addRelevantButtons(j, k, screen, buttonList);
	}


	/**
	 * Returns true if this GUI should pause the game when it is displayed in single-player
	 */
	@Override
	public boolean doesGuiPauseGame()
	{
		return true;
	}

	public int getMaxPage() {
		return HandbookRegistry.RESOURCEDESC.getScreen()+HandbookRegistry.RESOURCEDESC.getNumberChildren()/8;
	}

	@Override
	public void actionPerformed(GuiButton button) {
		if (button.id == 12) {
			mc.thePlayer.closeScreen();
			return;
		}
		if (buttontimer > 0)
			return;
		buttontimer = 20;
		if (button.id == 15) {
			screen = 0;
			page = 0;
			subpage = 0;
			this.initGui();
			//this.refreshScreen();
			return;
		}
		if (button.id == 10) {
			if (screen > 0) {
				screen--;
				page = 0;
				subpage = 0;
			}
			this.initGui();
			//this.refreshScreen();
			return;
		}
		if (button.id == 11) {
			if (screen < this.getMaxPage()) {
				screen++;
				page = 0;
				subpage = 0;
			}
			else {
				screen = 0;
				page = 0;
				subpage = 0;
			}
			this.initGui();
			//this.refreshScreen();
			return;
		}
		if (screen == HandbookRegistry.TOC.getScreen()) {
			switch(button.id) {
			case 0:
				screen = HandbookRegistry.TERMS.getScreen();
				break;
			case 1:
				screen = HandbookRegistry.MISC.getScreen();
				break;
			case 2:
				screen = HandbookRegistry.ENGINEDESC.getScreen();
				break;
			case 3:
				screen = HandbookRegistry.TRANSDESC.getScreen();
				break;
			case 4:
				screen = HandbookRegistry.PROCMACHINEDESC.getScreen();
				break;
			case 5:
				screen = HandbookRegistry.TOOLDESC.getScreen();
				break;
			case 6:
				screen = HandbookRegistry.RESOURCEDESC.getScreen();
				break;
			}
			this.initGui();
			page = 0;
			subpage = 0;
			return;
		}
		if (button.id == 13) {
			if (subpage != 1)
				subpage++;
			this.initGui();
			return;
		}
		if (button.id == 14) {
			if (subpage != 0)
				subpage--;
			this.initGui();
			return;
		}
		time = System.nanoTime();
		i = 0;
		page = button.id;
		subpage = 0;
		this.initGui();
	}

	public void refreshScreen() {
		int lastx = mx;
		int lasty = my;
		mc.thePlayer.closeScreen();
		//ModLoader.openGUI(player, new GuiHandbook(player, worldObj));
		Mouse.setCursorPosition(lastx, lasty);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		mx = Mouse.getX();
		my = Mouse.getY();
	}

	/** 0 = crafting, 1 = plain, 2 = smelt, 3 = extractor, 4 = compressor, 5 = fermenter, 6 = fractionator, 7 = grinder, 8 = blast */
	public byte getGuiLayout() {
		HandbookRegistry h = HandbookRegistry.getEntry(screen, page);
		if (h.isPlainGui())
			return 1;
		if (h == HandbookRegistry.BAITBOX && subpage == 1)
			return 9;
		if (subpage == 1)
			return 1;
		if (h == HandbookRegistry.STEELINGOT)
			return 8;
		if (h == HandbookRegistry.EXTRACTS)
			return 3;
		if (h == HandbookRegistry.FLAKES)
			return 2;
		if (h == HandbookRegistry.COMPACTS)
			return 4;
		if (h == HandbookRegistry.GLASS)
			return 2;
		if (h == HandbookRegistry.NETHERDUST)
			return 2;
		if (h == HandbookRegistry.YEAST)
			return 5;
		if (h == HandbookRegistry.ETHANOL)
			return 2;
		if (h == HandbookRegistry.SILVERINGOT)
			return 2;
		if (h == HandbookRegistry.BUCKETS) {
			if ((System.nanoTime()/SECOND)%2 == 0)
				return 6;
			else
				return 7;
		}

		return 0;
	}

	private void drawRecipes() {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		int posX = (width - xSize) / 2;
		int posY = (height - ySize) / 2;
		HandbookAuxData.drawPage(fontRenderer, screen, page, subpage, posX, posY);
	}

	private void drawTabIcons() {
		int posX = (width - xSize) / 2;
		int posY = (height - ySize) / 2;
		List<HandbookRegistry> li = HandbookRegistry.getEntriesForScreen(screen);
		for (int i = 0; i < li.size(); i++) {
			HandbookRegistry h = li.get(i);
			ReikaGuiAPI.instance.drawItemStack(new RenderItem(), fontRenderer, h.getTabIcon(), posX-17, posY-6+i*20);
		}
	}

	private void drawGraphics() {
		int posX = (width - xSize) / 2-2;
		int posY = (height - ySize) / 2-8;

		HandbookRegistry h = HandbookRegistry.getEntry(screen, page);
		if (h == HandbookRegistry.TERMS) {
			int xc = posX+xSize/2; int yc = posY+43; int r = 35;
			ReikaGuiAPI.instance.drawCircle(xc, yc, r, 0);
			ReikaGuiAPI.instance.drawLine(xc, yc, xc+r, yc, 0);
			ReikaGuiAPI.instance.drawLine(xc, yc, (int)(xc+r-0.459*r), (int)(yc-0.841*r), 0);/*
    		for (float i = 0; i < 1; i += 0.1)
    			ReikaGuiAPI.instance.drawLine(xc, yc, (int)(xc+Math.cos(i)*r), (int)(yc-Math.sin(i)*r), 0x000000);*/
			String s = "One radian";
			fontRenderer.drawString(s, xc+r+10, yc-4, 0x000000);
		}

		if (h == HandbookRegistry.PHYSICS) {
			int xc = posX+xSize/8; int yc = posY+45; int r = 5;
			ReikaGuiAPI.instance.drawCircle(xc, yc, r, 0);
			ReikaGuiAPI.instance.drawLine(xc, yc, xc+45, yc, 0x0000ff);
			ReikaGuiAPI.instance.drawLine(xc+45, yc, xc+45, yc+20, 0xff0000);
			ReikaGuiAPI.instance.drawLine(xc+45, yc, xc+50, yc+5, 0xff0000);
			ReikaGuiAPI.instance.drawLine(xc+45, yc, xc+40, yc+5, 0xff0000);
			fontRenderer.drawString("Distance", xc+4, yc-10, 0x0000ff);
			fontRenderer.drawString("Force", xc+30, yc+20, 0xff0000);

			ReikaGuiAPI.instance.drawLine(xc-2*r, (int)(yc-1.4*r), xc-r, yc-r*2-2, 0x8800ff);
			ReikaGuiAPI.instance.drawLine(xc-2*r, (int)(yc-1.4*r), xc-2*r-2, yc, 0x8800ff);
			ReikaGuiAPI.instance.drawLine(xc-2*r, (int)(yc+1.4*r), xc-2*r-2, yc, 0x8800ff);
			ReikaGuiAPI.instance.drawLine(xc-2*r, (int)(yc+1.4*r), xc-r, yc+r*2+2, 0x8800ff);
			ReikaGuiAPI.instance.drawLine(xc+2, yc+r*2+2, xc-r, yc+r*2+2, 0x8800ff);
			ReikaGuiAPI.instance.drawLine(xc+2, yc+r*2+2, xc-3, yc+r*2+7, 0x8800ff);
			ReikaGuiAPI.instance.drawLine(xc+2, yc+r*2+2, xc-3, yc+r*2-3, 0x8800ff);
			fontRenderer.drawString("Torque", xc-24, yc+18, 0x8800ff);

			r = 35; xc = posX+xSize/2+r+r/2; yc = posY+43;
			ReikaGuiAPI.instance.drawCircle(xc, yc, r, 0);
			double a = 57.3*System.nanoTime()/1000000000%360;
			double b = 3*57.3*System.nanoTime()/1000000000%360;
			ReikaGuiAPI.instance.drawLine(xc, yc, (int)(xc+Math.cos(Math.toRadians(a))*r), (int)(yc+Math.sin(Math.toRadians(a))*r), 0xff0000);
			ReikaGuiAPI.instance.drawLine(xc, yc, (int)(xc+Math.cos(Math.toRadians(b))*r), (int)(yc+Math.sin(Math.toRadians(b))*r), 0x0000ff);

			fontRenderer.drawString("1 rad/s", xc+r-4, yc+18, 0xff0000);
			fontRenderer.drawString("3 rad/s", xc+r-4, yc+18+10, 0x0000ff);
		}
		if (h == HandbookRegistry.BAITBOX && subpage == 1) {
			RenderItem ri = new RenderItem();
			int k = (int)((System.nanoTime()/2000000000)%MobBait.baitList.length);
			MobBait b = MobBait.baitList[k];
			int u = b.getMobIconU();
			int v = b.getMobIconV();
			ItemStack is1 = b.getAttractorItemStack();
			ItemStack is2 = b.getRepellentItemStack();
			ReikaGuiAPI.instance.drawItemStack(ri, fontRenderer, is1, posX+162, posY+27);
			ReikaGuiAPI.instance.drawItemStack(ri, fontRenderer, is2, posX+162, posY+27+18);
			String var4 = "/Reika/RotaryCraft/Textures/GUI/mobicons.png";
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			mc.renderEngine.bindTexture(var4);
			int UNIT = 4;
			this.drawTexturedModalRect(posX+88-UNIT/2, posY+41-UNIT/2, u, v, UNIT*2, UNIT*2);
			fontRenderer.drawString("Attractor", posX+110, posY+30, 0);
			fontRenderer.drawString("Repellent", posX+110, posY+48, 0);
		}
	}

	@Override
	public void drawScreen(int x, int y, float f)
	{
		if (System.nanoTime()-buttontime > SECOND/20) {
			buttoni++;
			buttontime = System.nanoTime();
			buttontimer = 0;
		}
		//drawDefaultBackground();

		String var4;

		bcg = this.getGuiLayout();

		switch(bcg) {
		case 0:
			var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookgui.png";
			break;
		case 1:
			var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookguib.png";
			break;
		case 2:
			var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookguic.png";
			break;
		case 3:
			var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookguid.png";
			break;
		case 4:
			var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookguie.png";
			break;
		case 5:
			var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookguif.png";
			break;
		case 6:
			var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookguig.png";
			break;
		case 7:
			var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookguih.png";
			break;
		case 8:
			var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookguij.png";
			break;
		case 9:
			var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookguik.png";
			break;
		default:
			var4 = "/Reika/RotaryCraft/Textures/GUI/Handbook/handbookguib.png"; //default to plain gui
			break;
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(var4);

		int posX = (width - xSize) / 2;
		int posY = (height - ySize) / 2 - 8;

		this.drawTexturedModalRect(posX, posY, 0, 0, xSize, ySize);

		int xo = 0;
		int yo = 0;
		fontRenderer.drawString(HandbookRegistry.getEntry(screen, page).getTitle(), posX+xo+6, posY+yo+6, 0x000000);
		HandbookRegistry h = HandbookRegistry.getEntry(screen, page);
		if (subpage == 0) {
			fontRenderer.drawSplitString(String.format("%s", h.getData()), posX+descX, posY+descY, 242, 0xffffff);
		}
		else {
			fontRenderer.drawSplitString(String.format("%s", h.getNotes()), posX+descX, posY+descY, 242, 0xffffff);
		}

		this.drawGraphics();

		super.drawScreen(x, y, f);

		if (subpage == 0)
			this.drawRecipes();

		if (!(this instanceof GuiHandbookPage))
			this.drawTabIcons();
	}
}
