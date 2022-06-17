package com.cleannrooster.spellblademod.manasystem;

import com.cleannrooster.spellblademod.SoundRegistrator;
import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.items.ModArmorMaterials;
import com.cleannrooster.spellblademod.items.ModItems;
import com.cleannrooster.spellblademod.items.WardArmorItem;
import com.cleannrooster.spellblademod.manasystem.data.Mana;
import com.cleannrooster.spellblademod.manasystem.data.ManaManager;
import com.cleannrooster.spellblademod.manasystem.data.PlayerMana;
import com.cleannrooster.spellblademod.manasystem.data.PlayerManaProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "spellblademod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class manatick {
    @SubscribeEvent
    public static void manatickevent(TickEvent.PlayerTickEvent event){
        if (event.phase != TickEvent.Phase.START){
            return;
        }
        int wardeff = 0;
        int flagd = 0;
        int minWard = 0;

        if (event.player.getInventory().getArmor(0).getItem() instanceof WardArmorItem)
        {
            wardeff = wardeff + 1;
            if (event.player.getInventory().getArmor(0).getItem() == ModItems.DIAMOND_WARDING_BOOTS.get())
            {
                flagd = flagd + 1;
            }
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
        PlayerMana playerMana = event.player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);

        if (flagd >= 4 && !event.player.hasEffect(StatusEffectsModded.WARDLOCKED.get())){
            playerMana.addMana(1);
        }
        if (playerMana == null){
            return;
        }
        if (!event.player.hasEffect(StatusEffectsModded.WARDLOCKED.get())){
            playerMana.addMana((float) -(((float)(playerMana.getMana())*0.01856026932)));
        }
        /*if (!event.player.hasEffect(StatusEffectsModded.WARDLOCKED.get())){
            playerMana.setMana(minWard);
        }*/
    }
}

