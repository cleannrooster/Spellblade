package com.cleannrooster.spellblademod.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.FireworkEntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.behavior.CelebrateVillagersSurvivedRaid;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;

public class FireworkRenderer extends EntityRenderer<FireworkEntity> {
    private final ItemRenderer itemRenderer;

    public FireworkRenderer(EntityRendererProvider.Context p_174114_) {
        super(p_174114_);
        this.itemRenderer = p_174114_.getItemRenderer();

    }

    @Override
    public void render(FireworkEntity p_114656_, float p_114657_, float p_114658_, PoseStack p_114659_, MultiBufferSource p_114660_, int p_114661_) {
        p_114659_.pushPose();
        p_114659_.mulPose(this.entityRenderDispatcher.cameraOrientation());
        p_114659_.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        if (p_114656_.isShotAtAngle()) {
            p_114659_.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
            p_114659_.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            p_114659_.mulPose(Vector3f.XP.rotationDegrees(90.0F));
        }
        this.itemRenderer.renderStatic(p_114656_.getItem(), ItemTransforms.TransformType.GROUND, p_114661_, OverlayTexture.NO_OVERLAY, p_114659_, p_114660_, p_114656_.getId());
        p_114659_.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(FireworkEntity p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
