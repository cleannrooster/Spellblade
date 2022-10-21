package com.cleannrooster.spellblademod.setup;


import com.cleannrooster.spellblademod.SpellbladeMod;
import com.cleannrooster.spellblademod.enchants.WardTempered;
import com.cleannrooster.spellblademod.items.*;
import com.cleannrooster.spellblademod.manasystem.client.ClientSetup;
import com.cleannrooster.spellblademod.manasystem.data.ManaEvents;
import com.cleannrooster.spellblademod.manasystem.data.ManaManager;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = "spellblademod", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSetup {

    public static final String TAB_NAME = "spellblade";
    public static final CreativeModeTab ITEM_GROUP = (new CreativeModeTab(TAB_NAME) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.SPELLBLADE.get());
        }

        @Override
        public boolean hasEnchantmentCategory(@Nullable EnchantmentCategory p_40777_) {
            if (p_40777_ == ModItems.ARMORENCHANT || p_40777_ == ModItems.SWORDENCHANT) {
                return true;
            }
            return false;
        }

        @Override
        public void fillItemList(NonNullList<ItemStack> p_40778_) {
            super.fillItemList(p_40778_);
             int iii = 0;
            NonNullList<ItemStack> newList = NonNullList.create();
            for (ItemStack value : p_40778_) {
                if (value.getItem() instanceof Spellblade) {
                    Item itemstack2 = value.getItem();
                    newList.add(new ItemStack(itemstack2));
                    iii++;
                }

            }
            for (ItemStack stack : p_40778_) {
                if (stack.getItem() instanceof WardingMail) {
                    Item itemstack2 = stack.getItem();
                    newList.add(new ItemStack(itemstack2));
                    iii++;
                }

            }

            for (ItemStack itemStack : p_40778_) {
                if (itemStack.getItem() instanceof ArmorItem) {
                    Item itemstack2 = itemStack.getItem();
                    newList.add(new ItemStack(itemstack2));
                    iii++;
                }

            }

            if(iii % 9 != 0) {
                while(iii % 9 != 0){
                    newList.add(new ItemStack(Items.AIR));
                    iii++;
                }
            }
            for (ItemStack itemStack : p_40778_) {
                if (itemStack.getItem() instanceof Spell spell) {
                    Item itemstack2 = itemStack.getItem();
                    newList.add(new ItemStack(spell));
                    iii++;
                    ItemStack stack = Flask.newFlaskItem(spell);
                    newList.add(stack);
                    iii++;
                    if (iii % 9 == 8) {
                        newList.add(new ItemStack(Items.AIR));
                        iii++;

                    }
                }
            }

            if(iii % 9 != 0) {
                while(iii % 9 != 0){
                    newList.add(new ItemStack(Items.AIR));
                    iii++;
                }
            }
            for (ItemStack itemStack : p_40778_) {
                if (itemStack.getItem() instanceof Guard) {
                    Item itemstack2 = itemStack.getItem();
                    newList.add(new ItemStack(itemstack2));
                    iii++;
                }
            }

            if(iii % 9 != 0) {
                while(iii % 9 != 0){
                    newList.add(new ItemStack(Items.AIR));
                    iii++;
                }
            }
            for (ItemStack itemStack : p_40778_) {
                if (itemStack.getItem() instanceof BlockItem) {
                    Item itemstack2 = itemStack.getItem();
                    newList.add(new ItemStack(itemstack2));
                    iii++;
                }

            }
            if(iii % 9 != 0) {
                while(iii % 9 != 0){
                    newList.add(new ItemStack(Items.AIR));
                    iii++;
                }
            }
            for (ItemStack itemStack : p_40778_) {
                if (!(itemStack.getItem() instanceof Flask) && !(itemStack.getItem() instanceof BlockItem) && !(itemStack.getItem() instanceof Guard) && !(itemStack.getItem() instanceof Spell) && !(itemStack.getItem() instanceof ArmorItem) && !(itemStack.getItem() instanceof WardingMail) && !(itemStack.getItem() instanceof Spellblade)) {
                    Item itemstack2 = itemStack.getItem();
                    newList.add(new ItemStack(itemstack2));
                    iii++;
                }

            }
            p_40778_.clear();
            for (ItemStack itemStack : newList) {
                p_40778_.add(itemStack);
            }
            if(iii % 9 != 0) {
                while(iii % 9 != 0){
                    p_40778_.add(new ItemStack(Items.AIR));
                    iii++;
                }
            }
            p_40778_.add(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(SpellbladeMod.warding,3)));
            p_40778_.add(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(SpellbladeMod.greaterwarding,3)));
            p_40778_.add(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(SpellbladeMod.wardTempered,3)));
            p_40778_.add(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(SpellbladeMod.spellproxy,2)));



        }
    });

    public static void setup() {
        IEventBus bus = MinecraftForge.EVENT_BUS;
        bus.addListener(ManaEvents::onWorldTick);
    }

    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
        });
        Messages.register();
    }
    
}
