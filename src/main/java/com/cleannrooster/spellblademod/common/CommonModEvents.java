package com.cleannrooster.spellblademod.common;

import com.cleannrooster.spellblademod.entity.*;
import net.minecraft.world.entity.monster.Vex;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "spellblademod", bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(ModEntities.SENTINEL.get(), SentinelEntity.createAttributes().build());
        event.put(ModEntities.VOLLEYBALL.get(), VolleyballEntity.createAttributes().build());

        event.put(ModEntities.INVISIVEX.get(), Vex.createAttributes().build());
        event.put(ModEntities.SPARK.get(), SpiderSpark.createAttributes().build());
        event.put(ModEntities.CATSPARK.get(), SpiderSpark.createAttributes().build());

    }
}
