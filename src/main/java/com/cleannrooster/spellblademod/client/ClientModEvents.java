package com.cleannrooster.spellblademod.client;

import com.cleannrooster.spellblademod.entity.HammerModel;
import com.cleannrooster.spellblademod.entity.HammerRenderer;
import com.cleannrooster.spellblademod.entity.ModEntities;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.EnderEyeItem;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "spellblademod", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    private ClientModEvents() {}
    @SubscribeEvent
    public static void registerlayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(HammerModel.LAYER_LOCATION, HammerModel::createLayer);
    }
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(ModEntities.TRIDENT.get(), HammerRenderer::new);
        event.registerEntityRenderer(ModEntities.ENDERS_EYE.get(), (p_174088_) -> {
            return new ThrownItemRenderer<>(p_174088_, 1.0F, true);
        });
        event.registerEntityRenderer(ModEntities.VOLATILE_ENTITY.get(), (p_174086_) -> {
            return new ThrownItemRenderer<>(p_174086_, 3.0F, true);
        });
        event.registerEntityRenderer(ModEntities.FLUX_ENTITY.get(), (p_174086_) -> {
            return new ThrownItemRenderer<>(p_174086_, 2.0F, true);
        });
        event.registerEntityRenderer(ModEntities.REVERBERATING_RAY_ORB.get(), (p_174086_) -> {
            return new ThrownItemRenderer<>(p_174086_, 2.0F, true);
        });
    }
}
