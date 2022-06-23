package com.cleannrooster.spellblademod.manasystem;

import com.cleannrooster.spellblademod.SoundRegistrator;
import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.blocks.WardIronBlock;
import com.cleannrooster.spellblademod.items.ModArmorMaterials;
import com.cleannrooster.spellblademod.items.ModItems;
import com.cleannrooster.spellblademod.items.WardArmorItem;
import com.cleannrooster.spellblademod.manasystem.client.ManaOverlay;
import com.cleannrooster.spellblademod.manasystem.data.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "spellblademod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class manatick {
    @SubscribeEvent
    public static void manatickevent(TickEvent.PlayerTickEvent event){
          float basestep;
          float basewaving;
         float basearmor;
         float basewarding;
         float basetotem;
        if(event.phase != TickEvent.Phase.START) {
            return;
        }
        PlayerMana playerMana = event.player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);

        if (playerMana == null){
            return;
        }
        basemana.baseadditional = basemana.sum();

        int wardeff = 0;
        float flagd = 0;
        int minWard = 0;



        if (event.player.getFeetBlockState().getBlock() instanceof WardIronBlock){
            basestep = 3;
        }
        else{
            basestep = 0;
        }
        if (event.player.hasEffect(StatusEffectsModded.WARD_DRAIN.get())){
            basemana.addBaseadditional("drain", -(1F+(float)event.player.getEffect(StatusEffectsModded.WARD_DRAIN.get()).getAmplifier()));
        }
        else{
            basemana.addBaseadditional("drain", 0F);
        }


        if (event.player.getInventory().getArmor(0).getItem() instanceof WardArmorItem)
        {
            wardeff = wardeff + 1;
            if (event.player.getInventory().getArmor(0).getItem() == ModItems.DIAMOND_WARDING_BOOTS.get())
            {
                flagd = flagd + 1;
            }
        }

        if (event.player.hasEffect(StatusEffectsModded.WARDING.get()) && !event.player.hasEffect(StatusEffectsModded.WARDLOCKED.get())){
            //playerMana.addMana((1 + event.player.getEffect(StatusEffectsModded.WARDING.get()).getAmplifier()));
            basewarding = (1 + event.player.getEffect(StatusEffectsModded.WARDING.get()).getAmplifier());
        }
        else{
            basewarding = 0;
        }
        if (event.player.hasEffect(StatusEffectsModded.WARD_DRAIN.get()) && !event.player.hasEffect(StatusEffectsModded.WARDLOCKED.get())){
            //playerMana.addMana(-8*(1+event.player.getEffect(StatusEffectsModded.WARD_DRAIN.get()).getAmplifier()));
        }
        if (event.player.hasEffect(StatusEffectsModded.SPELLWEAVING.get())){
            //playerMana.addMana(-1*(1+event.player.getEffect(StatusEffectsModded.SPELLWEAVING.get()).getAmplifier()));
            basewaving = -1*(1+event.player.getEffect(StatusEffectsModded.SPELLWEAVING.get()).getAmplifier());
        }
        else{
            basewaving = 0;
        }
        if (event.player.hasEffect(StatusEffectsModded.TOTEMIC_ZEAL.get()) && !event.player.hasEffect(StatusEffectsModded.WARDLOCKED.get())){
            //playerMana.addMana((float) (1+0.25*event.player.getEffect(StatusEffectsModded.TOTEMIC_ZEAL.get()).getAmplifier()));
            basetotem = (float) (1+0.25*event.player.getEffect(StatusEffectsModded.TOTEMIC_ZEAL.get()).getAmplifier());
        }
        else{
            basetotem = 0;

        }
        if (event.player.getInventory().getArmor(1).getItem() instanceof WardArmorItem)
        {
            wardeff = wardeff + 1;
            if (event.player.getInventory().getArmor(1).getItem() == ModItems.DIAMOND_WARDING_LEGGINGS.get())
            {
                flagd = flagd + 1;
            }
        }
        if (event.player.getInventory().getArmor(2).getItem() instanceof WardArmorItem)
        {
            wardeff = wardeff + 1;
            if (event.player.getInventory().getArmor(2).getItem() == ModItems.DIAMOND_WARDING_CHEST.get())
            {
                flagd = flagd + 1;
            }
        }
        if (event.player.getInventory().getArmor(3).getItem() instanceof WardArmorItem)
        {
            wardeff = wardeff + 1;
            if (event.player.getInventory().getArmor(3).getItem() == ModItems.DIAMOND_WARDING_HELMET.get())
            {
                flagd = flagd + 1;
            }
        }
        if (event.player.getInventory().getArmor(0).isEnchanted()) {
            if (event.player.getInventory().getArmor(0).getEnchantmentTags().toString().contains("lesserwarding")) {
                flagd = flagd + 0.5F;
            }
        }
        if (event.player.getInventory().getArmor(1).isEnchanted()) {
            if (event.player.getInventory().getArmor(1).getEnchantmentTags().toString().contains("lesserwarding")) {
                flagd = flagd + 0.5F;
            }
        }
        if (event.player.getInventory().getArmor(2).isEnchanted()) {
            if (event.player.getInventory().getArmor(2).getEnchantmentTags().toString().contains("lesserwarding")) {
                flagd = flagd + 0.5F;
            }
        }
        if (event.player.getInventory().getArmor(3).isEnchanted()) {
            if (event.player.getInventory().getArmor(3).getEnchantmentTags().toString().contains("lesserwarding")) {
                flagd = flagd + 0.5F;
            }
        }
        if (event.player.getInventory().getArmor(0).isEnchanted()) {
            if (event.player.getInventory().getArmor(0).getEnchantmentTags().toString().contains("greaterwarding")) {
                flagd = flagd + 1.5F;
            }
        }
        if (event.player.getInventory().getArmor(1).isEnchanted()) {
            if (event.player.getInventory().getArmor(1).getEnchantmentTags().toString().contains("greaterwarding")) {
                flagd = flagd + 1.5F;
            }
        }
        if (event.player.getInventory().getArmor(2).isEnchanted()) {
            if (event.player.getInventory().getArmor(2).getEnchantmentTags().toString().contains("greaterwarding")) {
                flagd = flagd + 1.5F;
            }
        }
        if (event.player.getInventory().getArmor(3).isEnchanted()) {
            if (event.player.getInventory().getArmor(3).getEnchantmentTags().toString().contains("greaterwarding")) {
                flagd = flagd + 1.5F;
            }
        }



        if (!event.player.hasEffect(StatusEffectsModded.WARDLOCKED.get())){
            //playerMana.addMana( ((float)flagd*(float)0.25));
        }
        basearmor = (float) (0.25*flagd);
        if (event.player.isCrouching()) {
            playerMana.addBasemodifiers("Shift", 1F);
        }
        else {
            playerMana.addBasemodifiers("Shift", 0F);
        }
        float base = basestep + basewaving + basearmor + basewarding + basetotem + playerMana.sumBasemodifiers();
        float baseWard = base/(0.025F);
        playerMana.setBasemana(baseWard);
        if (!event.player.hasEffect(StatusEffectsModded.WARDLOCKED.get())){
            playerMana.addMana((float) ((playerMana.getBasemana()-playerMana.getMana())*0.01856026932));
        }



        /*if (!event.player.hasEffect(StatusEffectsModded.WARDLOCKED.get())){
            playerMana.setMana(minWard);
        }*/
    }


}

