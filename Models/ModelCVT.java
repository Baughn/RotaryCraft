/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
// Date: 26/03/2013 10:24:02 PM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package Reika.RotaryCraft.Models;

import java.util.List;

import net.minecraft.client.model.ModelRenderer;

import org.lwjgl.opengl.GL11;

import Reika.RotaryCraft.Base.RotaryModelBase;

public class ModelCVT extends RotaryModelBase
{
	//fields
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape12;
	ModelRenderer Shape13;
	ModelRenderer Shape14;
	ModelRenderer Shape14a;
	ModelRenderer Shape14b;
	ModelRenderer Shape6;
	ModelRenderer Shape7;

	public ModelCVT()
	{
		textureWidth = 128;
		textureHeight = 128;

		Shape1 = new ModelRenderer(this, 0, 0);
		Shape1.addBox(0F, 0F, 0F, 16, 1, 16);
		Shape1.setRotationPoint(-8F, 23F, -8F);
		Shape1.setTextureSize(128, 128);
		Shape1.mirror = true;
		this.setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 64, 0);
		Shape2.addBox(0F, 0F, 0F, 1, 10, 16);
		Shape2.setRotationPoint(7F, 13F, -8F);
		Shape2.setTextureSize(128, 128);
		Shape2.mirror = true;
		this.setRotation(Shape2, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 64, 0);
		Shape3.addBox(0F, 0F, 0F, 1, 10, 16);
		Shape3.setRotationPoint(-8F, 13F, -8F);
		Shape3.setTextureSize(128, 128);
		Shape3.mirror = true;
		this.setRotation(Shape3, 0F, 0F, 0F);
		Shape4 = new ModelRenderer(this, 0, 34);
		Shape4.addBox(0F, 0F, 0F, 14, 10, 1);
		Shape4.setRotationPoint(-7F, 13F, 7F);
		Shape4.setTextureSize(128, 128);
		Shape4.mirror = true;
		this.setRotation(Shape4, 0F, 0F, 0F);
		Shape5 = new ModelRenderer(this, 0, 34);
		Shape5.addBox(0F, 0F, 0F, 14, 10, 1);
		Shape5.setRotationPoint(-7F, 13F, -8F);
		Shape5.setTextureSize(128, 128);
		Shape5.mirror = true;
		this.setRotation(Shape5, 0F, 0F, 0F);
		Shape12 = new ModelRenderer(this, 0, 27);
		Shape12.addBox(0F, 0F, 0F, 17, 2, 2);
		Shape12.setRotationPoint(-8.5F, 15F, -1F);
		Shape12.setTextureSize(128, 128);
		Shape12.mirror = true;
		this.setRotation(Shape12, 0F, 0F, 0F);
		Shape13 = new ModelRenderer(this, 0, 27);
		Shape13.addBox(0F, 0F, 0F, 17, 2, 2);
		Shape13.setRotationPoint(-8.5F, 16F, -1.4F);
		Shape13.setTextureSize(128, 128);
		Shape13.mirror = true;
		this.setRotation(Shape13, 0.7853982F, 0F, 0F);
		Shape14 = new ModelRenderer(this, 0, 48);
		Shape14.addBox(0F, 0F, 0F, 15, 5, 7);
		Shape14.setRotationPoint(-7.5F, 8.1F, 3F);
		Shape14.setTextureSize(128, 128);
		Shape14.mirror = true;
		this.setRotation(Shape14, -0.7853982F, 0F, 0F);
		Shape14a = new ModelRenderer(this, 0, 64);
		Shape14a.addBox(0F, 0F, 0F, 16, 5, 6);
		Shape14a.setRotationPoint(-8F, 8.1F, -3F);
		Shape14a.setTextureSize(128, 128);
		Shape14a.mirror = true;
		this.setRotation(Shape14a, 0F, 0F, 0F);
		Shape14b = new ModelRenderer(this, 0, 48);
		Shape14b.addBox(0F, 0F, 0F, 15, 5, 7);
		Shape14b.setRotationPoint(-7.5F, 13F, -8F);
		Shape14b.setTextureSize(128, 128);
		Shape14b.mirror = true;
		this.setRotation(Shape14b, 0.7853982F, 0F, 0F);
		Shape6 = new ModelRenderer(this, 0, 80);
		Shape6.addBox(0F, 0F, -1F, 8, 5, 1);
		Shape6.setRotationPoint(-4F, 8F, -5F);
		Shape6.setTextureSize(128, 128);
		Shape6.mirror = true;
		this.setRotation(Shape6, -0.3490659F, 0F, 0F);
		Shape7 = new ModelRenderer(this, 3, 4);
		Shape7.addBox(0F, 0F, 0F, 2, 1, 3);
		Shape7.setRotationPoint(-1F, 8.2F, -6F);
		Shape7.setTextureSize(128, 128);
		Shape7.mirror = true;
		this.setRotation(Shape7, 0F, 0F, 0F);
	}

	@Override
	public void renderAll(List li, float phi)
	{
		Shape1.render(f5);
		Shape2.render(f5);
		Shape3.render(f5);
		Shape4.render(f5);
		Shape5.render(f5);

		GL11.glTranslated(0, 1, 0);
		GL11.glRotatef(phi, 1, 0, 0);
		GL11.glTranslated(0, -1, 0);
		Shape12.render(f5);
		Shape13.render(f5);
		GL11.glTranslated(0, 1, 0);
		GL11.glRotatef(-phi, 1, 0, 0);
		GL11.glTranslated(0, -1, 0);

		Shape14.render(f5);
		Shape14a.render(f5);
		Shape14b.render(f5);
		Shape6.render(f5);
		Shape7.render(f5);
	}

}
