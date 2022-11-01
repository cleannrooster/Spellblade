package com.cleannrooster.spellblademod.entity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = "spellblademod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EndersGazeEvents {
    @SubscribeEvent
    public static void deathEvent(LivingDeathEvent event){
        List<EndersEyeEntity> eyes = event.getEntity().getLevel().getEntitiesOfClass(EndersEyeEntity.class,event.getEntity().getBoundingBox().inflate(8),endersEyeEntity -> endersEyeEntity.target == event.getEntity());
        for(EndersEyeEntity eye : eyes){
            eye.tickCount = 0;
        }

    }
    @SubscribeEvent
    public static void hitEvent(AttackEntityEvent event){
        if(event.getEntity() instanceof Player player && event.getTarget() instanceof EndersEyeEntity eye && player != eye.getOwner()){

            eye.playSound(SoundEvents.GLASS_BREAK,0.5F,1);
            eye.discard();
        }
    }
}
