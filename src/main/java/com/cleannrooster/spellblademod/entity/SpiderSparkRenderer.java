package com.cleannrooster.spellblademod.entity;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Spider;

public class SpiderSparkRenderer<T extends SpiderSpark> extends SpiderRenderer<T>  {
    public SpiderSparkRenderer(EntityRendererProvider.Context p_174401_) {
        super(p_174401_);
    }
    public static final ResourceLocation LAYER_LOCATION = new ResourceLocation("spellblademod", "textures/entity/spider.png");
    public ResourceLocation getTextureLocation(T p_116009_) {
        return LAYER_LOCATION;
    }

}
