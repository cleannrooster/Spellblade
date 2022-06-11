package com.cleannrooster.spellblademod.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.BiFunction;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, "spellblademod");
    //public static final RegistryObject<Item> HAMMER = ITEMS.register("steelball",
   //         () -> new SteelBall(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).fireResistant().stacksTo(16)));
    public static final RegistryObject<EntityType<HammerEntity>> TRIDENT = ENTITIES.register("splitting_trident",
            () -> EntityType.Builder.of(HammerEntity::new, MobCategory.MISC).sized(1.5F, 1.5F).setTrackingRange(100).setUpdateInterval(1).fireImmune().setShouldReceiveVelocityUpdates(true).build(new ResourceLocation("spellblademod","hammer").toString()));


}
