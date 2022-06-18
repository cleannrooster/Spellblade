package com.cleannrooster.spellblademod;

import com.cleannrooster.spellblademod.items.Spellblade;
import com.cleannrooster.spellblademod.manasystem.data.PlayerMana;
import com.cleannrooster.spellblademod.manasystem.data.PlayerManaProvider;
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
        Player player = (Player) event.player;
        PlayerMana playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
        if (playerMana == null){
            return;
        }

        if (player.hasEffect(StatusEffectsModded.WARDING.get()) && !player.hasEffect(StatusEffectsModded.WARDLOCKED.get())){
            playerMana.addMana(2);
        }
        if (player.hasEffect(StatusEffectsModded.WARD_DRAIN.get()) && !player.hasEffect(StatusEffectsModded.WARDLOCKED.get())){
            playerMana.addMana(-8*(1+player.getEffect(StatusEffectsModded.WARD_DRAIN.get()).getAmplifier()));
        }
        if (player.hasEffect(StatusEffectsModded.SPELLWEAVING.get())){
            playerMana.addMana(-1*(1+player.getEffect(StatusEffectsModded.SPELLWEAVING.get()).getAmplifier()));
        }
        if (player.hasEffect(StatusEffectsModded.TOTEMIC_ZEAL.get()) && !player.hasEffect(StatusEffectsModded.WARDLOCKED.get())){
            playerMana.addMana((float) (1+0.25*player.getEffect(StatusEffectsModded.TOTEMIC_ZEAL.get()).getAmplifier()));
        }
    }

}
