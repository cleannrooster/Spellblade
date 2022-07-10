package com.cleannrooster.spellblademod.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SentinelRenderer<Type extends SentinelEntity> extends MobRenderer<Type, SentinelModel<Type>> {
    public static final ResourceLocation TEXTURE  = new ResourceLocation("spellblademod", "textures/entity/sentinel.png");

    public SentinelRenderer(EntityRendererProvider.Context p_174420_) {
        super(p_174420_,  new SentinelModel<>(p_174420_.bakeLayer(SentinelModel.LAYER_LOCATION)), 0.5f);
    }


    public ResourceLocation getTextureLocation(SentinelEntity p_116109_) {
        return TEXTURE;
    }

}
