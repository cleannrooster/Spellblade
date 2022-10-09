package com.cleannrooster.spellblademod;

import com.cleannrooster.spellblademod.items.Spellblade;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "spellblademod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WardGainHandler {
    /*@SubscribeEvent
    public static void WardOnHit(LivingDamageEvent event){
        if (event.getSource().getEntity() == null){
            return;
        }

        if (event.getSource().getEntity() instanceof Player){
            Player player = (Player) event.getSource().getEntity();
            PlayerMana playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
            playerMana.addMana(24);
        }
    }*/
    @SubscribeEvent
    public static void WardWhileActing(TickEvent.PlayerTickEvent event){
        if (event.player == null){
            return;
        }
        if (event.phase != TickEvent.Phase.START){
            return;
        }

    }

}
