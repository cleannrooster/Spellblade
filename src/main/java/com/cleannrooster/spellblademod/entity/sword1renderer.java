package com.cleannrooster.spellblademod.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.Objects;

public class sword1renderer extends EntityRenderer<sword1> {
    public static final ResourceLocation TEXTURE  = new ResourceLocation("spellblademod", "textures/entity/sword1.png");
    public static final ResourceLocation TEXTURE2  = new ResourceLocation("spellblademod", "textures/entity/sword2.png");

    private final sword1model model;

    public sword1renderer(EntityRendererProvider.Context p_174420_) {
        super(p_174420_);
        this.model = new sword1model<>(p_174420_.bakeLayer(sword1model.LAYER_LOCATION));
    }
    public void render(sword1 p_116111_, float p_116112_, float p_116113_, PoseStack p_116114_, MultiBufferSource p_116115_, int p_116116_) {
        if(!p_116111_.isInvisible()) {
            p_116114_.pushPose();
            p_116114_.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(p_116113_, p_116111_.yRotO, p_116111_.getYRot()) - 90.0F));
            p_116114_.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(p_116113_, p_116111_.xRotO, p_116111_.getXRot()) + 90.0F));
            VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(p_116115_, this.model.renderType(this.getTextureLocation(p_116111_)), false, false);
            this.model.renderToBuffer(p_116114_, vertexconsumer, p_116116_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            p_116114_.popPose();
        }

    }


    public ResourceLocation getTextureLocation(sword1 p_116109_) {
        if(Objects.equals(p_116109_.getCustomName(), new TextComponent("guard"))){
            return TEXTURE2;
        }else {
            return TEXTURE;
        }
    }

}
