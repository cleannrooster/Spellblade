package com.cleannrooster.spellblademod;

import com.cleannrooster.spellblademod.items.Flask;
import com.cleannrooster.spellblademod.items.ModItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ColorHandler {
    @SubscribeEvent
    public static void colorevent(ColorHandlerEvent.Item event) {
        event.getItemColors().register((p_92696_, p_92697_) -> {
            return p_92697_ == 0 ? Flask.getColor(p_92696_) : -1;
        }, ModItems.OIL.get());
    }

}
