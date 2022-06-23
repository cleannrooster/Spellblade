package com.cleannrooster.spellblademod;

import com.cleannrooster.spellblademod.items.ModItems;
import com.cleannrooster.spellblademod.items.WardArmorItem;
import com.cleannrooster.spellblademod.manasystem.data.PlayerMana;
import com.cleannrooster.spellblademod.manasystem.data.PlayerManaProvider;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
            PlayerMana playerMana = event.getEntityLiving().getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
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
            double multiplier = (double) Math.pow(0.5,(double)(playerMana.getMana()+0)/50);
            if (event.getSource().isMagic()){
                multiplier = 1;
            }
            event.setAmount((float)(event.getAmount()*multiplier));

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
