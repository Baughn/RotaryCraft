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

import java.util.Random;

import Reika.DragonAPI.Auxiliary.EnumDifficulty;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.ReikaMathLibrary;

public enum DifficultyEffects {

	BEDROCKDUST(3, 2, 1),
	PIPECRAFT(32, 16, 8),
	PARTCRAFT(6, 3, 2),
	BELTCRAFT(3, 2, 1),
	COMPACTOR(2, 2, 1),
	BONUSSTEEL(2D, 1D, 0.5D),
	JETFAILURE(4500, 1800, 900),
	CONSUMEFRAC(0.03125F, 0.0625F, 0.5F),
	PRODUCEFRAC(new int[]{8,16}, new int[]{5,11}, new int[]{2,4}),
	BREAKCOIL(16, 4, 1);

	private boolean isChance = false;

	private boolean isRandom = false;

	private static final Random rand = new Random();

	private int easyInt;
	private int mediumInt;
	private int hardInt;

	private int easyMinimum;
	private int mediumMinimum;
	private int hardMinimum;

	private int easyMaximum;
	private int mediumMaximum;
	private int hardMaximum;

	private float easyChance;
	private float mediumChance;
	private float hardChance;

	private double easyDouble;
	private double mediumDouble;
	private double hardDouble;


	private DifficultyEffects(int easy, int med, int hard) {
		easyInt = easy;
		mediumInt = med;
		hardInt = hard;
	}

	private DifficultyEffects(int[] easy, int[] med, int[] hard) {
		isRandom = true;

		easyMinimum = easy[0];
		mediumMinimum = med[0];
		hardMinimum = hard[0];

		easyMaximum = easy[1];
		mediumMaximum = med[1];
		hardMaximum = hard[1];
	}

	//chances (out of 1F)
	private DifficultyEffects(float easy, float med, float hard) {
		isChance = true;

		easyChance = easy;
		mediumChance = med;
		hardChance = hard;
	}

	private DifficultyEffects(double easy, double med, double hard) {
		easyDouble = easy;
		mediumDouble = med;
		hardDouble = hard;
	}

	public int getInt() {
		if (isRandom) {
			switch(getDifficulty()) {
			case EASY:
				return easyMinimum+rand.nextInt(1+easyMaximum-easyMinimum);
			case MEDIUM:
				return mediumMinimum+rand.nextInt(1+mediumMaximum-mediumMinimum);
			case HARD:
				return hardMinimum+rand.nextInt(1+hardMaximum-hardMinimum);
			default:
				return mediumMinimum+rand.nextInt(1+mediumMaximum-mediumMinimum);
			}
		}
		else {
			switch(getDifficulty()) {
			case EASY:
				return easyInt;
			case MEDIUM:
				return mediumInt;
			case HARD:
				return hardInt;
			default:
				return mediumInt;
			}
		}
	}

	private float getChance() {
		switch(getDifficulty()) {
		case EASY:
			return easyChance;
		case MEDIUM:
			return mediumChance;
		case HARD:
			return hardChance;
		default:
			return mediumChance;
		}
	}

	public double getDouble() {
		switch(getDifficulty()) {
		case EASY:
			return easyDouble;
		case MEDIUM:
			return mediumDouble;
		case HARD:
			return hardDouble;
		default:
			return mediumDouble;
		}
	}

	public boolean testChance() {
		if (!isChance) {
			ReikaJavaLibrary.pConsole(this+" is not chance, but was called for it!");
			Thread.dumpStack();
			return false;
		}
		float chance = this.getChance();
		return ReikaMathLibrary.doWithChance(chance);
	}

	private static EnumDifficulty getDifficulty() {
		return EnumDifficulty.getDifficulty(ConfigRegistry.DIFFICULTY.getValue());
	}

}
