package com.cleannrooster.spellblademod.items;


import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = "spellblademod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TradeHandler {

    @SubscribeEvent
    public static void setupWandererTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> rare = event.getGenericTrades();
         ItemStack fireinstance = new ItemStack(ModItems.FIRE_EFFIGY.get());
         ItemStack lightninginstance = new ItemStack(ModItems.LIGHTNING_EFFIGY.get());
         ItemStack iceinstance = new ItemStack(ModItems.ICE_EFFIGY.get());

        event.getGenericTrades().add(new ItemTrades(fireinstance,  4, 1, 2, 20));

        event.getGenericTrades().add(new ItemTrades(iceinstance,  4, 1, 2, 20));

        event.getGenericTrades().add(new ItemTrades(lightninginstance,  4, 1, 2, 20));



    }
    @SubscribeEvent
    public static void setupVillagerTrades(VillagerTradesEvent event) {
        VillagerProfession profession = event.getType();
         ItemStack fireinstance = new ItemStack(ModItems.FIRE_EFFIGY.get());
         ItemStack lightninginstance = new ItemStack(ModItems.LIGHTNING_EFFIGY.get());
         ItemStack iceinstance = new ItemStack(ModItems.ICE_EFFIGY.get());
        ItemStack lesserwarding = new ItemStack(Items.ENCHANTED_BOOK);

        if (VillagerProfession.CLERIC.equals(profession)) {
            event.getTrades().get(3).add(new ItemTrades(lightninginstance,  4, 1, 2, 20));
            event.getTrades().get(3).add(new ItemTrades(fireinstance,  4, 1, 2, 20));
            event.getTrades().get(3).add(new ItemTrades(iceinstance,  4, 1, 2, 20));


        }
        }
}
