package com.cleannrooster.spellblademod.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Zombie;

public class VolleyballRenderer<T extends VolleyballEntity, M extends HumanoidModel<T>> extends HumanoidMobRenderer<T, M> {
    private static final ResourceLocation DEFAULT_LOCATION = new ResourceLocation("textures/entity/steve.png");

    public VolleyballRenderer(EntityRendererProvider.Context p_174456_) {
        this(p_174456_,ModelLayers.ZOMBIE,ModelLayers.ZOMBIE_INNER_ARMOR,ModelLayers.ZOMBIE_OUTER_ARMOR );
    }
    public VolleyballRenderer(EntityRendererProvider.Context p_174458_, ModelLayerLocation p_174459_, ModelLayerLocation p_174460_, ModelLayerLocation p_174461_) {
        this(p_174458_, (M) new VolleyballModel<VolleyballEntity>(p_174458_.bakeLayer(p_174459_)), (M) new VolleyballModel<VolleyballEntity>(p_174458_.bakeLayer(p_174460_)), (M) new VolleyballModel<VolleyballEntity>(p_174458_.bakeLayer(p_174461_)));
    }
    protected VolleyballRenderer(EntityRendererProvider.Context p_173910_, M p_173911_, M p_173912_, M p_173913_) {
        super(p_173910_, p_173911_, 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this, p_173912_, p_173913_));
    }


    public ResourceLocation getTextureLocation(T p_114891_) {
        return DEFAULT_LOCATION;
    }
}