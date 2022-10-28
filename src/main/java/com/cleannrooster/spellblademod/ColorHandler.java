package com.cleannrooster.spellblademod;

import com.cleannrooster.spellblademod.items.Flask;
import com.cleannrooster.spellblademod.items.ModItems;
import com.google.common.collect.Sets;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ColorHandler {
    @SubscribeEvent
    public static void colorevent(ColorHandlerEvent.Item event) {
        event.getItemColors().register((p_92696_, p_92697_) -> {
            return p_92697_ == 0 ? Flask.getColor(p_92696_) : -1;
        }, ModItems.OIL.get());
    }
    @SubscribeEvent
    public static void modelEvent(TextureStitchEvent.Pre event) {

        event.addSprite(new ResourceLocation("spellblademod", "block/fire_0"));
        event.addSprite(new ResourceLocation("spellblademod", "block/fire_1"));
        event.addSprite(new ResourceLocation("spellblademod", "block/fire_2"));
        event.addSprite(new ResourceLocation("spellblademod", "block/fire_3"));
        event.addSprite(new ResourceLocation("spellblademod", "block/fire_5"));
        event.addSprite(new ResourceLocation("spellblademod", "block/fire_6"));
        event.addSprite(new ResourceLocation("spellblademod", "block/fire_7"));
        event.addSprite(new ResourceLocation("spellblademod", "block/fire_8"));
        event.addSprite(new ResourceLocation("spellblademod", "block/fire_9"));
        event.addSprite(new ResourceLocation("spellblademod", "block/fire_10"));

    }

}
