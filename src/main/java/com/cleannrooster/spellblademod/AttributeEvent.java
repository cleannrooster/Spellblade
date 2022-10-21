package com.cleannrooster.spellblademod;

import com.cleannrooster.spellblademod.manasystem.manatick;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class AttributeEvent {
    @SubscribeEvent
    public static void onEntityAttributeModificationEvent(EntityAttributeModificationEvent event) {

        System.out.println("hello");
        event.add(EntityType.PLAYER, manatick.WARD);
        event.add(EntityType.PLAYER, manatick.BASEWARD);
        for(EntityType entityType: event.getTypes()) {
            event.add(entityType, manatick.SMOTE);
        }

    }
}
