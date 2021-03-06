/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
// Date: 02/03/2013 11:44:37 PM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package Reika.RotaryCraft.Models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelArrow extends ModelBase
{
	//fields
	ModelRenderer Shape2;
	ModelRenderer Shape2a;
	ModelRenderer Shape2b;
	ModelRenderer Shape1;
	ModelRenderer Shape1a;
	ModelRenderer Shape2c;
	ModelRenderer Shape2d;
	ModelRenderer Shape2e;
	ModelRenderer Shape2f;
	ModelRenderer Shape2g;

	public ModelArrow()
	{
		textureWidth = 64;
		textureHeight = 64;

		Shape2 = new ModelRenderer(this, 0, 0);
		Shape2.addBox(-2F, -2F, 0F, 4, 4, 1);
		Shape2.setRotationPoint(0F, 39F, -5F);
		Shape2.setTextureSize(64, 64);
		Shape2.mirror = true;
		this.setRotation(Shape2, 0F, 0F, 0F);
		Shape2a = new ModelRenderer(this, 0, 0);
		Shape2a.addBox(-1.5F, -1.5F, 0F, 3, 3, 1);
		Shape2a.setRotationPoint(0F, 39F, -6F);
		Shape2a.setTextureSize(64, 64);
		Shape2a.mirror = true;
		this.setRotation(Shape2a, 0F, 0F, 0F);
		Shape2b = new ModelRenderer(this, 0, 0);
		Shape2b.addBox(-1F, -1F, 0F, 2, 2, 1);
		Shape2b.setRotationPoint(0F, 39F, -7F);
		Shape2b.setTextureSize(64, 64);
		Shape2b.mirror = true;
		this.setRotation(Shape2b, 0F, 0F, 0F);
		Shape1 = new ModelRenderer(this, 0, 0);
		Shape1.addBox(-0.5F, -0.5F, 0F, 1, 1, 16);
		Shape1.setRotationPoint(0F, 39F, -8F);
		Shape1.setTextureSize(64, 64);
		Shape1.mirror = true;
		this.setRotation(Shape1, 0F, 0F, 0.7853982F);
		Shape1a = new ModelRenderer(this, 0, 0);
		Shape1a.addBox(-0.5F, -1.5F, 0F, 1, 1, 16);
		Shape1a.setRotationPoint(0F, 40F, -8F);
		Shape1a.setTextureSize(64, 64);
		Shape1a.mirror = true;
		this.setRotation(Shape1a, 0F, 0F, 0F);
		Shape2c = new ModelRenderer(this, 0, 0);
		Shape2c.addBox(-1F, -1F, 0F, 2, 2, 1);
		Shape2c.setRotationPoint(0F, 39F, -7F);
		Shape2c.setTextureSize(64, 64);
		Shape2c.mirror = true;
		this.setRotation(Shape2c, 0F, 0F, 0.7853982F);
		Shape2d = new ModelRenderer(this, 0, 0);
		Shape2d.addBox(-1.5F, -1.5F, 0F, 3, 3, 1);
		Shape2d.setRotationPoint(0F, 39F, -6F);
		Shape2d.setTextureSize(64, 64);
		Shape2d.mirror = true;
		this.setRotation(Shape2d, 0F, 0F, 0.7853982F);
		Shape2e = new ModelRenderer(this, 0, 0);
		Shape2e.addBox(-2F, -2F, 0F, 4, 4, 1);
		Shape2e.setRotationPoint(0F, 39F, -5F);
		Shape2e.setTextureSize(64, 64);
		Shape2e.mirror = true;
		this.setRotation(Shape2e, 0F, 0F, -0.3926991F);
		Shape2f = new ModelRenderer(this, 0, 0);
		Shape2f.addBox(-2F, -2F, 0F, 4, 4, 1);
		Shape2f.setRotationPoint(0F, 39F, -5F);
		Shape2f.setTextureSize(64, 64);
		Shape2f.mirror = true;
		this.setRotation(Shape2f, 0F, 0F, 0.3926991F);
		Shape2g = new ModelRenderer(this, 0, 0);
		Shape2g.addBox(-2F, -2F, 0F, 4, 4, 1);
		Shape2g.setRotationPoint(0F, 39F, -5F);
		Shape2g.setTextureSize(64, 64);
		Shape2g.mirror = true;
		this.setRotation(Shape2g, 0F, 0F, 0.7853982F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles(f, f1, f2, f3, f4, f5);
		Shape2.render(f5);
		Shape2a.render(f5);
		Shape2b.render(f5);
		Shape1.render(f5);
		Shape1a.render(f5);
		Shape2c.render(f5);
		Shape2d.render(f5);
		Shape2e.render(f5);
		Shape2f.render(f5);
		Shape2g.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
	}

}
