package com.cleannrooster.spellblademod;

import com.cleannrooster.spellblademod.items.ModItems;
import com.cleannrooster.spellblademod.items.ParticlePacket;
import com.cleannrooster.spellblademod.items.Spellblade;
import com.cleannrooster.spellblademod.items.WardArmorItem;
import com.cleannrooster.spellblademod.manasystem.manatick;
import com.cleannrooster.spellblademod.manasystem.network.Hurt;
import com.cleannrooster.spellblademod.setup.Messages;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import io.netty.buffer.Unpooled;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.pow;
@Mod.EventBusSubscriber(modid = "spellblademod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ArmorHandler {
    @SubscribeEvent
    public static void damageevent(LivingHurtEvent event){
        Random rand = new Random();

        if (event.getEntityLiving() instanceof Player){
            Player player = (Player) event.getEntityLiving();
            int flagd = 0;
            int flagg = 0;
            if (player.getInventory().getArmor(0).getItem() == ModItems.DIAMOND_WARDING_BOOTS.get())
            {
                flagd = flagd + 1;
            }
            if (player.getInventory().getArmor(1).getItem() == ModItems.DIAMOND_WARDING_LEGGINGS.get())
            {
                flagd = flagd + 1;
            }
            if (player.getInventory().getArmor(2).getItem() == ModItems.DIAMOND_WARDING_CHEST.get())
            {
                flagd = flagd + 1;
            }
            if (player.getInventory().getArmor(3).getItem() == ModItems.DIAMOND_WARDING_HELMET.get())
            {
                flagd = flagd + 1;
            }


            if (player.getInventory().getArmor(0).isEnchanted()) {
                if (player.getInventory().getArmor(0).getEnchantmentTags().contains("greaterwarding")) {
                    flagd = flagd + 1;
                }
            }

            /*if (player.getInventory().getArmor(0).getItem() == ModItems.GOLD_WARDING_BOOTS.get())
            {
                flagg = flagg + 1;
            }
            if (player.getInventory().getArmor(1).getItem() == ModItems.GOLD_WARDING_LEGGINGS.get())
            {
                flagg = flagg + 1;
            }
            if (player.getInventory().getArmor(2).getItem() == ModItems.GOLD_WARDING_CHEST.get())
            {
                flagg = flagg + 1;
            }
            if (player.getInventory().getArmor(3).getItem() == ModItems.GOLD_WARDING_HELMET.get())
            {
                flagg = flagg + 1;
            }*/
            double multiplier = (double) Math.pow(0.5,(double)(player.getAttribute(manatick.WARD).getValue()+0)/50);
            if (event.getSource().isMagic()){
                multiplier = 1;
            }
            if(player.getAttribute(manatick.WARD).getValue() > -1) {
                player.addEffect(new MobEffectInstance(StatusEffectsModded.WARDABSORPTION.get(), 0, (int) Math.round((player.getAttribute(manatick.WARD).getValue()+1)/40F)));

            }

            if(player.getAttribute(manatick.WARD).getValue() > 19){
                double amount = player.getAttribute(manatick.WARD).getValue()/2;

                player.getAttribute(manatick.WARD).setBaseValue(amount);

            }

            int threshold = 160;
            if (flagd >= 1){
                threshold = threshold+5;
            }
            if (flagd >= 2){
                threshold = threshold+5;
            }
            if (flagd >= 3){
                threshold = threshold+5;
            }
            if (flagd >= 4){
                threshold = threshold+5;
            }
            /*if(flagg >= 4 && playerMana.getMana() >= threshold){
                if (rand.nextBoolean()){
                    return;
                }
            }
            if(flagg >= 3 && playerMana.getMana() >= threshold){
                if (rand.nextBoolean()){
                    return;
                }

            }
            if(flagg >= 1 && playerMana.getMana() >= threshold){
                if (rand.nextBoolean()){
                    return;
                }
            }
            if(flagg >= 1){
                if (playerMana.getMana() >= threshold) {
                    player.level.playSound(null, event.getEntityLiving().blockPosition(), SoundRegistrator.SHATTER_1, SoundSource.PLAYERS, 1.5F, 1);
                }
                playerMana.setMana(0);
            }
            else{
                playerMana.setMana(playerMana.getMana() / 2);
            }*/
        }
    }
}
