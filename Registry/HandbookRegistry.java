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

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.Instantiable.ImagedGuiButton;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.RotaryCraft.RotaryCraft;
import Reika.RotaryCraft.RotaryNames;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.RotaryDescriptions;
import Reika.RotaryCraft.Base.RotaryCraftTileEntity;
import Reika.RotaryCraft.TileEntities.TileEntityEngine;

public enum HandbookRegistry {

	//---------------------TOC--------------------//
	TOC("Table of Contents", "TOC"),
	MISC(),
	ENGINES(),
	TRANS(),
	MACHINES(),
	TOOLS(),
	RESOURCE(),
	//---------------------INFO--------------------//
	TERMS("Basic Terms", "Terms and Physics Explanations"),
	PHYSICS("Relevant Physics"),
	MATERIAL("Material Properties"),
	MODINTERFACE("Inter-Mod Interactions"),
	//---------------------MISC--------------------//
	MISCDESC("Important Notes", "Important Notes"),
	LUBE("Lubricant"),
	HOSE("Lubricant Hose", MachineRegistry.HOSE),
	CANOLA("Canola", ItemRegistry.CANOLA),
	METER("Angular Transducer", ItemRegistry.METER),
	SCREW("Screwdriver", ItemRegistry.SCREWDRIVER),
	PIPE("Pipe", MachineRegistry.PIPE),
	FUELLINE("Fuel Line", MachineRegistry.FUELLINE),
	//---------------------ENGINES--------------------//
	ENGINEDESC("Power Supply", "Engines"),
	DCENGINE(MachineRegistry.ENGINE, EnumEngineType.DC.ordinal()),
	WINDENGINE(MachineRegistry.ENGINE, EnumEngineType.WIND.ordinal()),
	STEAMENGINE(MachineRegistry.ENGINE, EnumEngineType.STEAM.ordinal()),
	GASENGINE(MachineRegistry.ENGINE, EnumEngineType.GAS.ordinal()),
	ACENGINE(MachineRegistry.ENGINE, EnumEngineType.AC.ordinal()),
	PERFENGINE(MachineRegistry.ENGINE, EnumEngineType.SPORT.ordinal()),
	HYDROENGINE(MachineRegistry.ENGINE, EnumEngineType.HYDRO.ordinal()),
	MICROTURB(MachineRegistry.ENGINE, EnumEngineType.MICRO.ordinal()),
	JETENGINE(MachineRegistry.ENGINE, EnumEngineType.JET.ordinal()),
	SOLAR(MachineRegistry.SOLARTOWER),

	//---------------------TRANSMISSION--------------------//
	TRANSDESC("Power Transfer", "Transmission"),
	SHAFT(MachineRegistry.SHAFT),
	GEARBOX(MachineRegistry.GEARBOX),
	BEVEL(MachineRegistry.BEVELGEARS),
	SPLITTER(MachineRegistry.SPLITTER),
	CLUTCH(MachineRegistry.CLUTCH),
	DYNA(MachineRegistry.DYNAMOMETER),
	FLYWHEEL(MachineRegistry.FLYWHEEL),
	WORM(MachineRegistry.ADVANCEDGEARS, 0),
	CVT(MachineRegistry.ADVANCEDGEARS, 1),
	COIL(MachineRegistry.ADVANCEDGEARS, 2),
	MULTI(MachineRegistry.MULTICLUTCH),

	//---------------------MACHINES--------------------//
	PRODMACHINEDESC("Production Machines", "Production"),
	BLAST(MachineRegistry.BLASTFURNACE),
	WORKTABLE(MachineRegistry.WORKTABLE),
	FERMENTER(MachineRegistry.FERMENTER),
	FRACTION(MachineRegistry.FRACTIONATOR),
	BEDROCK(MachineRegistry.BEDROCKBREAKER),
	BORER(MachineRegistry.BORER),
	PUMP(MachineRegistry.PUMP),
	PNEUMATIC(MachineRegistry.PNEUENGINE),

	PROCMACHINEDESC("Processing Machines", "Processing"),
	GRINDER(MachineRegistry.GRINDER),
	EXTRACTOR(MachineRegistry.EXTRACTOR),
	PULSEJET(MachineRegistry.PULSEJET),
	COMPACTOR(MachineRegistry.COMPACTOR),
	PURIFIER(MachineRegistry.PURIFIER),
	ENHANCER(MachineRegistry.FUELENHANCER),
	MAGNET(MachineRegistry.MAGNETIZER),
	FURNACEHEATER(MachineRegistry.FRICTION),
	AIRCOMPRESSOR(MachineRegistry.COMPRESSOR),
	CONVERTER(MachineRegistry.LIQUIDCONVERTER),

	FARMMACHINEDESC("Farming Machines", "Farming"),
	FAN(MachineRegistry.FAN),
	BREEDER(MachineRegistry.AUTOBREEDER),
	SPAWNER(MachineRegistry.SPAWNERCONTROLLER),
	SPRINKLER(MachineRegistry.SPRINKLER),
	WOODCUTTER(MachineRegistry.WOODCUTTER),
	HARVESTER(MachineRegistry.MOBHARVESTER),

	ACCMACHINEDESC("Accessory Machines", "Aux Machines"),
	HEATER(MachineRegistry.HEATER),
	VACUUM(MachineRegistry.VACUUM),
	ECU(MachineRegistry.ECU),
	WINDER(MachineRegistry.WINDER),
	REFRESHER(MachineRegistry.REFRESHER),
	CCTVSCREEN(MachineRegistry.SCREEN),
	MIRROR(MachineRegistry.MIRROR),
	COOLINGFIN(MachineRegistry.COOLINGFIN),

	WEPMACHINEDESC("Defence/Offence Machines", "Defence/Offense"),
	HEATRAY(MachineRegistry.HEATRAY),
	TNT(MachineRegistry.TNTCANNON),
	SONIC(MachineRegistry.SONICWEAPON),
	FORCE(MachineRegistry.FORCEFIELD),
	RAILGUN(MachineRegistry.RAILGUN),
	FREEZE(MachineRegistry.FREEZEGUN),
	CONTAIN(MachineRegistry.CONTAINMENT),
	LASERGUN(MachineRegistry.LASERGUN),
	LANDMINE(MachineRegistry.LANDMINE),
	BLOCKCANNON(MachineRegistry.BLOCKCANNON),
	SELFDESTRUCT(MachineRegistry.SELFDESTRUCT),
	EMP(MachineRegistry.EMP),

	SURVMACHINEDESC("Surveying Machines", "Surveying"),
	GPR(MachineRegistry.GPR),
	RADAR(MachineRegistry.MOBRADAR),
	SCANNER(MachineRegistry.CAVESCANNER),
	CCTV(MachineRegistry.CCTV),
	SPYCAM(MachineRegistry.SPYCAM),

	COSMACHINEDESC("Cosmetic Machines", "Cosmetic"),
	FIREWORK(MachineRegistry.FIREWORK),
	MUSIC(MachineRegistry.MUSICBOX),
	PROJECTOR(MachineRegistry.PROJECTOR),
	WEATHER(MachineRegistry.WEATHERCONTROLLER),
	DISPLAY(MachineRegistry.DISPLAY),

	UTILMACHINEDESC("Utility Machines", "Utility"),
	FLOODLIGHT(MachineRegistry.FLOODLIGHT),
	PILEDRIVER(MachineRegistry.PILEDRIVER),
	AEROSOL(MachineRegistry.AEROSOLIZER),
	LIGHTBRID(MachineRegistry.LIGHTBRIDGE),
	RESERVOIR(MachineRegistry.RESERVOIR),
	BAITBOX(MachineRegistry.BAITBOX),
	DETECTOR(MachineRegistry.PLAYERDETECTOR),
	CHEST(MachineRegistry.SCALECHEST),
	SPILLER(MachineRegistry.SPILLER),
	SMOKE(MachineRegistry.SMOKEDETECTOR),
	FIRESTARTER(MachineRegistry.IGNITER),
	ITEMCANNON(MachineRegistry.ITEMCANNON),
	BUCKETFILLER(MachineRegistry.BUCKETFILLER),
	LAMP(MachineRegistry.LAMP),
	TERRA(MachineRegistry.TERRAFORMER),
	LINE(MachineRegistry.LINEBUILDER),

	//---------------------TOOLS--------------------//
	TOOLDESC("Tool Items", "Tools"),
	SPRING(ItemRegistry.SPRING),
	ULTRA(ItemRegistry.ULTRASOUND),
	MOTION(ItemRegistry.MOTION),
	VAC(ItemRegistry.VACUUM),
	STUN(ItemRegistry.STUNGUN),
	GRAVEL(ItemRegistry.GRAVELGUN),
	FIREBALL(ItemRegistry.FIREBALL),
	HANDCRAFT(ItemRegistry.HANDCRAFT),
	NVG(ItemRegistry.NVG),
	BUCKETS(ItemRegistry.BUCKET),
	BEDTOOLS("Bedrock Tools"),
	BEDARMOR("Bedrock Armor"),
	TARGETER(ItemRegistry.TARGET),
	IOGOGGLES(ItemRegistry.IOGOGGLES),
	CKEY(ItemRegistry.KEY),
	MINECART(ItemRegistry.MINECART),
	TILESELECT(ItemRegistry.TILESELECTOR),
	JETPACK(ItemRegistry.JETCHEST),
	//---------------------RESOURCE--------------------//
	RESOURCEDESC("Resource Items", "Resource Items"),
	STEELINGOT("Steel Ingot"),
	OTHERSHAFT("Alternative Shafts"),
	OTHERGEARUNIT("Alternative Gear Units"),
	OTHERGEAR("Alternative Gearboxes"),
	NETHERDUST("Netherrack Dust and Tar"),
	SAWDUST("Sawdust"),
	BEDDUST("Bedrock Dust"),
	EXTRACTS("Dust/Slurry/Solution"),
	FLAKES("Ore Flakes"),
	COMPACTS("Anthracite/Prismane/Lonsdaleite"),
	DECOBLOCKS("Decorative Blocks"),
	GLASS("Blast Glass"),
	SPAWNERS("Monster Spawners"),
	YEAST("Yeast"),
	ETHANOL("Ethanol"),
	BEDINGOT("Bedrock Ingot"),
	SILVERINGOT("Silver Ingot"),
	SALT("Salt"),
	AMMONIUM("Ammonium Nitrate"),
	SILVERIODIDE("Silver Iodide"),
	ALUMINUM("Aluminum Powder"),
	RAILGUNAMMO("Railgun Ammunition"),
	SLIDES("Projector Slides"),
	EXPLOSIVES("Explosive Shells");

	private MachineRegistry machine;
	private ItemRegistry item;
	private int offset;
	private int parentindex;
	private int basescreen;
	private boolean isParent;
	private String title;
	private ItemStack crafted;
	private String name;


	public static final HandbookRegistry[] tabList = HandbookRegistry.values();

	private HandbookRegistry(MachineRegistry m, int o) {
		machine = m;
		offset = o;
		isParent = false;
		item = null;
		crafted = m.getCraftedMetadataProduct(o);
	}

	private HandbookRegistry(MachineRegistry m) {
		machine = m;
		offset = -1;
		isParent = false;
		item = null;
		crafted = m.getCraftedProduct();
	}

	private HandbookRegistry(ItemRegistry i) {
		offset = -1;
		isParent = false;
		item = i;
		crafted = i.getStackOf();
	}

	private HandbookRegistry(String s) {
		machine = null;
		offset = -1;
		isParent = false;
		item = null;
		title = s;
		crafted = null;
	}

	private HandbookRegistry(ItemStack is) {
		machine = null;
		offset = -1;
		isParent = false;
		crafted = is.copy();
	}

	private HandbookRegistry(String s, MachineRegistry m) {
		machine = m;
		offset = -1;
		isParent = false;
		item = null;
		title = s;
		crafted = m.getCraftedProduct();
	}

	private HandbookRegistry(String s, ItemRegistry m) {
		machine = null;
		offset = -1;
		isParent = false;
		item = m;
		title = s;
		crafted = m.getStackOf();
	}

	private HandbookRegistry() {
		machine = null;
		offset = -1;
		isParent = false;
		item = null;
	}

	private HandbookRegistry(String s, String toc) {
		machine = null;
		offset = -1;
		parentindex = RotaryDescriptions.getCategoryCount();
		isParent = true;
		item = null;
		title = s;
		crafted = null;
		name = toc;
		RotaryDescriptions.addCategory(this);
	}

	public static int getScreen(MachineRegistry m, TileEntity te) {
		if (m == MachineRegistry.ENGINE)
			return getEngineScreen(te);
		if (m == MachineRegistry.ADVANCEDGEARS)
			return TRANSDESC.ordinal()+1;
		for (int i = ENGINEDESC.ordinal(); i < TOOLDESC.ordinal(); i++) {
			if (tabList[i].machine == m)
				return tabList[i].getScreen();
		}
		return -1;
	}

	public static HandbookRegistry[] getEngineTabs() {
		int size = TRANSDESC.ordinal()-ENGINEDESC.ordinal()-1;
		HandbookRegistry[] tabs = new HandbookRegistry[size];
		System.arraycopy(tabList, ENGINEDESC.ordinal()+1, tabs, 0, size);
		return tabs;
	}


	public static List<HandbookRegistry> getMachineTabs() {
		List<HandbookRegistry> tabs = new ArrayList<HandbookRegistry>();
		for (int i = 0; i < tabList.length; i++) {
			HandbookRegistry h = tabList[i];
			if (h.isMachine() && !h.isParent)
				tabs.add(h);
		}
		return tabs;
	}

	public static HandbookRegistry[] getTransTabs() {
		int size = PROCMACHINEDESC.ordinal()-TRANSDESC.ordinal()-1;
		HandbookRegistry[] tabs = new HandbookRegistry[size];
		System.arraycopy(tabList, TRANSDESC.ordinal()+1, tabs, 0, size);
		return tabs;
	}

	public static HandbookRegistry[] getToolTabs() {
		int size = RESOURCEDESC.ordinal()-TOOLDESC.ordinal()-1;
		HandbookRegistry[] tabs = new HandbookRegistry[size];
		System.arraycopy(tabList, TOOLDESC.ordinal()+1, tabs, 0, size);
		return tabs;
	}

	public static HandbookRegistry[] getResourceTabs() {
		int size = tabList.length-RESOURCEDESC.ordinal()-1;
		HandbookRegistry[] tabs = new HandbookRegistry[size];
		System.arraycopy(tabList, RESOURCEDESC.ordinal()+1, tabs, 0, size);
		return tabs;
	}

	public static HandbookRegistry[] getMiscTabs() {
		int size = ENGINEDESC.ordinal()-MISCDESC.ordinal()-1;
		HandbookRegistry[] tabs = new HandbookRegistry[size];
		System.arraycopy(tabList, MISCDESC.ordinal()+1, tabs, 0, size);
		return tabs;
	}

	public static HandbookRegistry[] getInfoTabs() {
		int size = MISCDESC.ordinal()-TERMS.ordinal();
		HandbookRegistry[] tabs = new HandbookRegistry[size];
		System.arraycopy(tabList, TERMS.ordinal(), tabs, 0, size);
		return tabs;
	}

	public static List<HandbookRegistry> getCategoryTabs() {
		ArrayList<HandbookRegistry> li = new ArrayList<HandbookRegistry>();
		for (int i = 0; i < tabList.length; i++) {
			if (tabList[i].isParent && tabList[i] != TOC && tabList[i] != TERMS)
				li.add(tabList[i]);
		}
		return li;
	}

	public static List<HandbookRegistry> getTOCTabs() {
		ArrayList<HandbookRegistry> li = new ArrayList<HandbookRegistry>();
		for (int i = 0; i < tabList.length; i++) {
			if (tabList[i].isParent && tabList[i] != TOC)
				li.add(tabList[i]);
		}
		return li;
	}

	public String getTOCTitle() {
		return name;
	}

	public boolean isMachine() {
		if (this.getParent() == PROCMACHINEDESC)
			return true;
		if (this.getParent() == UTILMACHINEDESC)
			return true;
		if (this.getParent() == WEPMACHINEDESC)
			return true;
		if (this.getParent() == SURVMACHINEDESC)
			return true;
		if (this.getParent() == PRODMACHINEDESC)
			return true;
		if (this.getParent() == FARMMACHINEDESC)
			return true;
		if (this.getParent() == COSMACHINEDESC)
			return true;
		if (this.getParent() == ACCMACHINEDESC)
			return true;
		return false;
	}

	public MachineRegistry getMachine() {
		return machine;
	}

	public ItemRegistry getItem() {
		return item;
	}

	public int getBaseScreen() {
		int sc = 0;
		for (int i = 0; i < this.ordinal(); i++) {
			HandbookRegistry h = tabList[i];
			if (h.isParent) {
				sc += h.getNumberChildren()/8+1;
			}
		}
		return sc;
	}

	public int getNumberChildren() {
		if (!isParent)
			return 0;
		int ch = 0;
		for (int i = this.ordinal()+1; i < tabList.length; i++) {
			HandbookRegistry h = tabList[i];
			if (h.isParent) {
				return ch;
			}
			else {
				ch++;
			}
		}
		return ch;
	}

	public int getScreen() {
		return this.getParent().getBaseScreen()+this.getRelativeScreen();
	}

	private int getPage() {
		return (this.ordinal()-this.getParent().ordinal())%8;
	}

	public boolean hasOffset() {
		return offset > -1;
	}

	public static int getPage(MachineRegistry m, TileEntity te) {
		if (m == MachineRegistry.ENGINE)
			return getEnginePage(te);
		if (m == MachineRegistry.ADVANCEDGEARS)
			return getAdvGearPage(te);
		for (int i = 0; i < tabList.length; i++) {
			if (tabList[i].machine == m)
				return tabList[i].getPage();
		}
		return -1;
	}

	public int getShiftedOrdinal() {
		return this.getParent().getBaseScreen()+this.ordinal()-this.getParent().ordinal();
	}

	private static int getAdvGearPage(TileEntity te) {
		return ((RotaryCraftTileEntity)te).getBlockMetadata()/4;
	}

	private static int getEnginePage(TileEntity te) {
		EnumEngineType e = ((TileEntityEngine)te).type;
		return 1+e.ordinal()-(getEngineScreen(te)-ENGINEDESC.ordinal())*8;
	}

	private static int getEngineScreen(TileEntity te) {
		EnumEngineType e = ((TileEntityEngine)te).type;
		return ENGINEDESC.ordinal()+(1+e.ordinal())/8;
	}

	public String getTabImageFile() {
		//return "/Reika/RotaryCraft/Textures/GUI/Handbook/tabs_"+this.getParent().name().toLowerCase()+".png";
		return "/Reika/RotaryCraft/Textures/GUI/Handbook/tabs_"+TOC.getParent().name().toLowerCase()+".png";
	}

	public int getTabRow() {
		return (this.getRelativePage()/12)*20;
	}

	public int getTabColumn() {
		return (this.getRelativePage()%12)*20;
	}

	public int getRelativePage() {
		int offset = this.ordinal()-this.getParent().ordinal();
		return offset;
	}

	public int getRelativeTabPosn() {
		int offset = this.ordinal()-this.getParent().ordinal();
		return offset-this.getRelativeScreen()*8;
	}

	public int getRelativeScreen() {
		int offset = this.ordinal()-this.getParent().ordinal();
		return offset/8;
	}

	public HandbookRegistry getParent() {
		HandbookRegistry parent = null;
		for (int i = 0; i < tabList.length; i++) {
			if (tabList[i].isParent) {
				if (this.ordinal() >= tabList[i].ordinal()) {
					parent = tabList[i];
				}
			}
		}
		//ReikaJavaLibrary.pConsole("Setting parent for "+this+" to "+parent);
		return parent;
	}

	public static void addRelevantButtons(int j, int k, int screen, List<GuiButton> li) {
		int id = 0;
		for (int i = 0; i < tabList.length; i++) {
			if (tabList[i].getScreen() == screen) {
				li.add(new ImagedGuiButton(id, j-20, k+tabList[i].getRelativeTabPosn()*20, 20, 20, 0*tabList[i].getTabColumn(), 0*tabList[i].getTabRow(), tabList[i].getTabImageFile()));
				//ReikaJavaLibrary.pConsole("Adding "+tabList[i]+" with ID "+id+" to screen "+screen);
				id++;
			}
		}
	}

	public static HandbookRegistry getEntry(int screen, int page) {
		//ReikaJavaLibrary.pConsole(screen+"   "+page);
		for (int i = 0; i < tabList.length; i++) {
			if (tabList[i].getScreen() == screen && tabList[i].getPage() == page) {
				return tabList[i];
			}
		}
		return TOC;
		//throw new RuntimeException("Handbook screen "+screen+" and page "+page+" do not correspond to an entry!");
	}

	public static List<HandbookRegistry> getEntriesForScreen(int screen) {
		//ReikaJavaLibrary.pConsole(screen);
		List<HandbookRegistry> li = new ArrayList<HandbookRegistry>();
		for (int i = 0; i < tabList.length; i++) {
			if (tabList[i].getScreen() == screen) {
				li.add(tabList[i]);
			}
		}
		return li;
	}

	public boolean isPlainGui() {
		//ReikaJavaLibrary.pConsole(this);
		if (this == TOC)
			return true;
		if (this.getParent() == TERMS)
			return true;
		if (isParent)
			return true;
		if (this == BEDDUST)
			return true;
		if (this == SPAWNERS)
			return true;
		if (this == LUBE)
			return true;
		if (this == CANOLA)
			return true;
		if (this == ALUMINUM)
			return true;
		return false;
	}

	public String getTitle() {
		if (isParent)
			return title;
		if (this.getParent() == ENGINEDESC) {
			if (this == SOLAR)
				return MachineRegistry.SOLARTOWER.getName();
			else
				return RotaryNames.engineNames[offset];
		}
		if (this.isMachine())
			return machine.getName();
		if (machine == MachineRegistry.ADVANCEDGEARS)
			return RotaryNames.advGearItemNames[offset];
		if (this.getParent() == TRANSDESC)
			return machine.getName();
		if (this.getParent() == TOOLDESC && this != BEDTOOLS && this != BEDARMOR)
			return item.getBasicName();
		return title;
	}

	public boolean isCrafting() {
		if (isParent)
			return false;
		if (this.isSmelting())
			return false;
		if (this.getParent() == TOC || this.getParent() == TERMS)
			return false;
		if (this == LUBE)
			return false;
		if (this == STEELINGOT)
			return false;
		if (this == NETHERDUST)
			return false;
		if (this == GLASS)
			return false;
		if (this == EXTRACTS)
			return false;
		if (this == COMPACTS)
			return false;
		if (this == BEDDUST)
			return false;
		if (this == SAWDUST)
			return false;
		if (this == SPAWNERS)
			return false;
		if (this == YEAST)
			return false;
		if (this == ALUMINUM)
			return false;
		if (this == RAILGUNAMMO)
			return false;
		if (this == CANOLA)
			return false;
		if (this == FLAKES)
			return false;
		return true;
	}

	public List<ItemStack> getCrafting() {
		if (!this.isCrafting())
			return null;
		if (this == SHAFT) {
			List<ItemStack> li = new ArrayList<ItemStack>();
			for (int i = 0; i < MaterialRegistry.values().length; i++) {
				li.add(MachineRegistry.SHAFT.getCraftedMetadataProduct(i));
			}
			return li;
		}
		if (this == GEARBOX) {
			List<ItemStack> li = new ArrayList<ItemStack>();
			for (int i = 0; i < MaterialRegistry.values().length; i++) {
				li.add(MachineRegistry.GEARBOX.getCraftedMetadataProduct(i));
			}
			return li;
		}
		if (this == FLYWHEEL) {
			List<ItemStack> li = new ArrayList<ItemStack>();
			for (int i = 0; i < 4; i++) {
				li.add(MachineRegistry.FLYWHEEL.getCraftedMetadataProduct(i));
			}
			return li;
		}
		if (this == OTHERGEAR) {
			List<ItemStack> li = new ArrayList<ItemStack>();
			li.add(ItemStacks.woodgear);
			li.add(ItemStacks.stonegear);
			li.add(ItemStacks.diamondgear);
			li.add(ItemStacks.bedrockgear);
			return li;
		}
		if (this == OTHERSHAFT) {
			List<ItemStack> li = new ArrayList<ItemStack>();
			li.add(ItemStacks.stonerod);
			li.add(ItemStacks.diamondshaft);
			li.add(ItemStacks.bedrockshaft);
			return li;
		}
		if (this == OTHERGEARUNIT) {
			List<ItemStack> li = new ArrayList<ItemStack>();
			li.add(ItemStacks.wood2x);
			li.add(ItemStacks.wood4x);
			li.add(ItemStacks.wood8x);
			li.add(ItemStacks.wood16x);
			li.add(ItemStacks.stone2x);
			li.add(ItemStacks.stone4x);
			li.add(ItemStacks.stone8x);
			li.add(ItemStacks.stone16x);
			li.add(ItemStacks.diamond2x);
			li.add(ItemStacks.diamond4x);
			li.add(ItemStacks.diamond8x);
			li.add(ItemStacks.diamond16x);
			li.add(ItemStacks.bedrock2x);
			li.add(ItemStacks.bedrock4x);
			li.add(ItemStacks.bedrock8x);
			li.add(ItemStacks.bedrock16x);
			return li;
		}
		if (crafted != null)
			return ReikaJavaLibrary.makeListFrom(crafted);
		if (machine != null && machine.isPipe())
			return ReikaJavaLibrary.makeListFrom(machine.getCraftedProduct());
		if (this == BEDTOOLS) {
			List<ItemStack> li = new ArrayList<ItemStack>();
			li.add(ItemRegistry.BEDPICK.getStackOf());
			li.add(ItemRegistry.BEDSHOVEL.getStackOf());
			li.add(ItemRegistry.BEDAXE.getStackOf());
			return li;
		}
		if (this == BEDARMOR) {
			List<ItemStack> li = new ArrayList<ItemStack>();
			li.add(ItemRegistry.BEDHELM.getStackOf());
			li.add(ItemRegistry.BEDCHEST.getStackOf());
			li.add(ItemRegistry.BEDLEGS.getStackOf());
			li.add(ItemRegistry.BEDBOOTS.getStackOf());
			return li;
		}
		if (this == SOLAR)
			return ReikaJavaLibrary.makeListFrom(MachineRegistry.SOLARTOWER.getCraftedProduct());
		if (this.getParent() == ENGINEDESC)
			return ReikaJavaLibrary.makeListFrom(EnumEngineType.engineList[offset].getCraftedProduct());
		if (machine == MachineRegistry.ADVANCEDGEARS)
			return ReikaJavaLibrary.makeListFrom(MachineRegistry.ADVANCEDGEARS.getCraftedMetadataProduct(offset));
		if (this.getParent() == TRANSDESC || this.isMachine()) {
			if (machine.hasCustomPlacerItem())
				return ReikaJavaLibrary.makeListFrom(machine.getCraftedMetadataProduct(0));
			return ReikaJavaLibrary.makeListFrom(machine.getCraftedProduct());
		}
		if (this == DECOBLOCKS) {
			List<ItemStack> li = new ArrayList<ItemStack>();
			li.add(ItemStacks.steelblock);
			li.add(ItemStacks.anthrablock);
			li.add(ItemStacks.lonsblock);
			return li;
		}
		if (this == SLIDES) {
			List<ItemStack> li = new ArrayList<ItemStack>();
			for (int i = 0; i < ItemRegistry.SLIDE.getNumberMetadatas(); i++)
				li.add(ItemRegistry.SLIDE.getStackOfMetadata(i));
			return li;
		}
		if (this == BEDINGOT)
			return ReikaJavaLibrary.makeListFrom(ItemStacks.bedingot);
		if (this == AMMONIUM)
			return ReikaJavaLibrary.makeListFrom(ItemStacks.nitrate);
		if (this == SALT)
			return ReikaJavaLibrary.makeListFrom(ItemStacks.salt);
		if (this == SILVERIODIDE)
			return ReikaJavaLibrary.makeListFrom(ItemStacks.silveriodide);
		if (this == EXPLOSIVES)
			return ReikaJavaLibrary.makeListFrom(ItemRegistry.SHELL.getStackOf());
		if (this == MINECART)
			return ReikaJavaLibrary.makeListFrom(ItemRegistry.MINECART.getStackOf());
		return ReikaJavaLibrary.makeListFrom(ItemStacks.basepanel);
	}

	public boolean isCustomRecipe() {
		if (this.getParent() == ENGINEDESC)
			return true;
		if (this.getParent() == TRANSDESC)
			return true;
		if (this.isMachine())
			return true;
		if (machine != null && machine.isPipe())
			return true;
		return false;
	}

	public int getTabIconIndex() {
		if (this == SHAFT)
			return MaterialRegistry.STEEL.ordinal();
		if (this == GEARBOX)
			return MaterialRegistry.STEEL.ordinal();
		if (this == FLYWHEEL)
			return 3;
		if (this == DECOBLOCKS)
			return 1;
		if (this == OTHERSHAFT)
			return 2;
		if (this == OTHERGEARUNIT)
			return 8;
		if (this == OTHERGEAR)
			return 0;
		return 0;
	}

	public boolean isSmelting() {
		if (this == ETHANOL)
			return true;
		if (this == SILVERINGOT)
			return true;
		return false;
	}

	public ItemStack getSmelting() {
		if (!this.isSmelting())
			return null;
		if (this == ETHANOL)
			return ItemRegistry.ETHANOL.getStackOf();
		if (this == SILVERINGOT)
			return ItemStacks.silveringot;
		return ItemStacks.barrel;
	}

	public ItemStack getTabIcon() {
		if (this == ENGINES)
			return EnumEngineType.AC.getCraftedProduct();
		if (this == MISC)
			return ItemRegistry.SCREWDRIVER.getStackOf();
		if (this == TRANS)
			return MachineRegistry.GEARBOX.getCraftedMetadataProduct(RotaryNames.gearboxItemNames.length-3);
		if (this == MACHINES)
			return MachineRegistry.RAILGUN.getCraftedProduct();
		if (this == TOOLS)
			return ItemRegistry.MOTION.getStackOf();
		if (this == RESOURCE)
			return ItemStacks.bedrockdust;
		if (this == FLAKES)
			return ItemStacks.goldoreflakes;
		if (this == BEDTOOLS)
			return ItemRegistry.BEDPICK.getEnchantedStack();
		if (this == BEDARMOR)
			return ItemRegistry.BEDHELM.getEnchantedStack();
		if (this == JETPACK)
			return ItemRegistry.JETCHEST.getEnchantedStack();
		if (this.isCrafting())
			return this.getCrafting().get(this.getTabIconIndex());
		if (this.isSmelting())
			return this.getSmelting();
		if (this == STEELINGOT)
			return ItemStacks.steelingot;
		if (this == NETHERDUST)
			return ItemStacks.netherrackdust;
		if (this == SAWDUST)
			return ItemStacks.sawdust;
		if (this == BEDDUST)
			return ItemStacks.bedrockdust;
		if (this == EXTRACTS)
			return ItemStacks.goldoredust;
		if (this == COMPACTS)
			return ItemStacks.prismane;
		if (this == GLASS)
			return new ItemStack(RotaryCraft.obsidianglass);
		if (this == SPAWNERS)
			return new ItemStack(DragonAPICore.getItem("spawner"));
		if (this == YEAST)
			return ItemStacks.sludge;
		if (this == SALT)
			return ItemStacks.salt;
		if (this == ALUMINUM)
			return ItemStacks.aluminumpowder;
		if (this == RAILGUNAMMO)
			return ItemRegistry.RAILGUN.getStackOf();
		if (this == CANOLA)
			return ItemRegistry.CANOLA.getStackOf();
		return null;
	}

	public String getData() {
		if (this == TOC)
			return RotaryDescriptions.getTOC();
		return RotaryDescriptions.getData(this);
	}

	public String getNotes() {
		return RotaryDescriptions.getNotes(this);
	}

	public boolean hasSubpages() {
		if (isParent)
			return false;
		if (this.getParent() == ENGINEDESC)
			return true;
		if (this.isMachine())
			return true;
		return false;
	}

}
