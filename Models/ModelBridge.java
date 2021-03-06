/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
// Date: 13/02/2013 9:57:43 PM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package Reika.RotaryCraft.Models;

import java.util.List;

import net.minecraft.client.model.ModelRenderer;
import Reika.RotaryCraft.Base.RotaryModelBase;

public class ModelBridge extends RotaryModelBase
{
	//fields
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape6;
	ModelRenderer Shape7;
	ModelRenderer Shape8;
	ModelRenderer Shape9;
	ModelRenderer Shape10;
	ModelRenderer Shape11;
	ModelRenderer Shape12;
	ModelRenderer Shape13;
	ModelRenderer Shape14;
	ModelRenderer Shape15;
	ModelRenderer Shape17;

	public ModelBridge()
	{
		textureWidth = 128;
		textureHeight = 128;

		Shape2 = new ModelRenderer(this, 0, 27);
		Shape2.addBox(0F, 0F, 0F, 1, 1, 15);
		Shape2.setRotationPoint(8F, 21.7F, -7.5F);
		Shape2.setTextureSize(128, 128);
		Shape2.mirror = true;
		this.setRotation(Shape2, 0F, 0F, 0.7853982F);
		Shape3 = new ModelRenderer(this, 50, 20);
		Shape3.addBox(0F, 0F, 0F, 1, 1, 14);
		Shape3.setRotationPoint(7F, 21F, -7F);
		Shape3.setTextureSize(128, 128);
		Shape3.mirror = true;
		this.setRotation(Shape3, 0F, 0F, 0F);
		Shape4 = new ModelRenderer(this, 50, 20);
		Shape4.addBox(0F, 0F, 0F, 1, 1, 14);
		Shape4.setRotationPoint(7F, 23F, -7F);
		Shape4.setTextureSize(128, 128);
		Shape4.mirror = true;
		this.setRotation(Shape4, 0F, 0F, 0F);
		Shape5 = new ModelRenderer(this, 80, 20);
		Shape5.addBox(0F, 0F, 0F, 1, 3, 1);
		Shape5.setRotationPoint(7F, 21F, 7F);
		Shape5.setTextureSize(128, 128);
		Shape5.mirror = true;
		this.setRotation(Shape5, 0F, 0F, 0F);
		Shape6 = new ModelRenderer(this, 80, 20);
		Shape6.addBox(0F, 0F, 0F, 1, 3, 1);
		Shape6.setRotationPoint(7F, 21F, -8F);
		Shape6.setTextureSize(128, 128);
		Shape6.mirror = true;
		this.setRotation(Shape6, 0F, 0F, 0F);
		Shape7 = new ModelRenderer(this, 0, 43);
		Shape7.addBox(0F, 0F, 0F, 5, 3, 16);
		Shape7.setRotationPoint(2F, 21F, -8F);
		Shape7.setTextureSize(128, 128);
		Shape7.mirror = true;
		this.setRotation(Shape7, 0F, 0F, 0F);
		Shape8 = new ModelRenderer(this, 42, 35);
		Shape8.addBox(0F, 0F, 0F, 9, 8, 16);
		Shape8.setRotationPoint(-7F, 16F, -8F);
		Shape8.setTextureSize(128, 128);
		Shape8.mirror = true;
		this.setRotation(Shape8, 0F, 0F, 0F);
		Shape9 = new ModelRenderer(this, 50, 0);
		Shape9.addBox(0F, 0F, 0F, 7, 4, 15);
		Shape9.setRotationPoint(2F, 16F, -7.5F);
		Shape9.setTextureSize(128, 128);
		Shape9.mirror = true;
		this.setRotation(Shape9, 0F, 0F, 0.7853982F);
		Shape10 = new ModelRenderer(this, 0, 78);
		Shape10.addBox(0F, 0F, 0F, 1, 2, 14);
		Shape10.setRotationPoint(0F, 14F, -7F);
		Shape10.setTextureSize(128, 128);
		Shape10.mirror = true;
		this.setRotation(Shape10, 0F, 0F, 0F);
		Shape11 = new ModelRenderer(this, 0, 98);
		Shape11.addBox(0F, 0F, 0F, 1, 3, 14);
		Shape11.setRotationPoint(-7F, 13F, -7F);
		Shape11.setTextureSize(128, 128);
		Shape11.mirror = true;
		this.setRotation(Shape11, 0F, 0F, 0F);
		Shape12 = new ModelRenderer(this, 0, 94);
		Shape12.addBox(0F, 0F, 0F, 7, 3, 1);
		Shape12.setRotationPoint(-6F, 13F, -7.1F);
		Shape12.setTextureSize(128, 128);
		Shape12.mirror = true;
		this.setRotation(Shape12, 0F, 0F, 0.1570796F);
		Shape13 = new ModelRenderer(this, 0, 94);
		Shape13.addBox(0F, 0F, 0F, 7, 3, 1);
		Shape13.setRotationPoint(-6F, 13F, 6.1F);
		Shape13.setTextureSize(128, 128);
		Shape13.mirror = true;
		this.setRotation(Shape13, 0F, 0F, 0.1570796F);
		Shape14 = new ModelRenderer(this, 48, 59);
		Shape14.addBox(0F, 0F, 0F, 6, 1, 12);
		Shape14.setRotationPoint(-6F, 15F, -6F);
		Shape14.setTextureSize(128, 128);
		Shape14.mirror = true;
		this.setRotation(Shape14, 0F, 0F, 0F);
		Shape15 = new ModelRenderer(this, 0, 62);
		Shape15.addBox(0F, 0F, 0F, 9, 1, 15);
		Shape15.setRotationPoint(-7.5F, 15.5F, -7.5F);
		Shape15.setTextureSize(128, 128);
		Shape15.mirror = true;
		this.setRotation(Shape15, 0F, 0F, 0F);
		Shape17 = new ModelRenderer(this, 0, 0);
		Shape17.addBox(0F, 0F, 0F, 1, 11, 16);
		Shape17.setRotationPoint(-8F, 13F, -8F);
		Shape17.setTextureSize(128, 128);
		Shape17.mirror = true;
		this.setRotation(Shape17, 0F, 0F, 0F);
	}

	@Override
	public void renderAll(List li, float phi)
	{
		//this.Shape1.render(f5);
		Shape2.render(f5);
		Shape3.render(f5);
		Shape4.render(f5);
		Shape5.render(f5);
		Shape6.render(f5);
		Shape7.render(f5);
		Shape8.render(f5);
		Shape9.render(f5);
		Shape10.render(f5);
		Shape11.render(f5);
		Shape12.render(f5);
		Shape13.render(f5);
		Shape14.render(f5);
		Shape15.render(f5);
		//this.Shape16.render(f5);
		Shape17.render(f5);
	}

}
