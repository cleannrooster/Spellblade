package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.ModBlocks;
import com.cleannrooster.spellblademod.blocks.WardIronBlock;
import com.cleannrooster.spellblademod.setup.ModSetup;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "spellblademod");
    public static final RegistryObject<Item> WARDING_MAIL = ITEMS.register("wardiron",
            () -> new WardingMail(
                    new Item.Properties().tab(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<Item> WARD_IRON_BLOCK = ITEMS.register("ward_iron_block",
            () -> new WardIronBlockItem(ModBlocks.WARD_IRON_BLOCK.get(),new Item.Properties().tab(ModSetup.ITEM_GROUP)));
    /*public static final RegistryObject<Item> WARDING_HELMET = ITEMS.register("warding_helmet",
            () -> new WardArmorItem(ModArmorMaterials.WARDING_MAIL, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> WARDING_CHEST = ITEMS.register("warding_chest",
            () -> new WardArmorItem(ModArmorMaterials.WARDING_MAIL, EquipmentSlot.CHEST, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> WARDING_LEGGINGS = ITEMS.register("warding_leggings",
            () -> new WardArmorItem(ModArmorMaterials.WARDING_MAIL, EquipmentSlot.LEGS, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> WARDING_BOOTS = ITEMS.register("warding_boots",
            () -> new WardArmorItem(ModArmorMaterials.WARDING_MAIL, EquipmentSlot.FEET, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> GOLD_WARDING_HELMET = ITEMS.register("gold_warding_helmet",
            () -> new WardArmorItem(ModArmorMaterials.GOLD_WARDING, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> GOLD_WARDING_CHEST = ITEMS.register("gold_warding_chest",
            () -> new WardArmorItem(ModArmorMaterials.GOLD_WARDING, EquipmentSlot.CHEST, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> GOLD_WARDING_LEGGINGS = ITEMS.register("gold_warding_leggings",
            () -> new WardArmorItem(ModArmorMaterials.GOLD_WARDING, EquipmentSlot.LEGS, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> GOLD_WARDING_BOOTS = ITEMS.register("gold_warding_boots",
            () -> new WardArmorItem(ModArmorMaterials.GOLD_WARDING, EquipmentSlot.FEET, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> IRON_WARDING_HELMET = ITEMS.register("iron_warding_helmet",
            () -> new WardArmorItem(ModArmorMaterials.IRON_WARDING, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> IRON_WARDING_CHEST = ITEMS.register("iron_warding_chest",
            () -> new WardArmorItem(ModArmorMaterials.IRON_WARDING, EquipmentSlot.CHEST, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> IRON_WARDING_LEGGINGS = ITEMS.register("iron_warding_leggings",
            () -> new WardArmorItem(ModArmorMaterials.IRON_WARDING, EquipmentSlot.LEGS, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> IRON_WARDING_BOOTS = ITEMS.register("iron_warding_boots",
            () -> new WardArmorItem(ModArmorMaterials.IRON_WARDING, EquipmentSlot.FEET, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));*/
    public static final RegistryObject<Item> SPELLBLADE = ITEMS.register("spellblade",
            () -> new Spellblade(Tiers.DIAMOND, 1, -1.8F, (new Item.Properties()).tab(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<Item> DIAMOND_WARDING_HELMET = ITEMS.register("warding_helmet",
            () -> new WardArmorItem(ModArmorMaterials.DIAMOND_WARDING, EquipmentSlot.HEAD, new Item.Properties().tab(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<Item> DIAMOND_WARDING_CHEST = ITEMS.register("warding_chest",
            () -> new WardArmorItem(ModArmorMaterials.DIAMOND_WARDING, EquipmentSlot.CHEST, new Item.Properties().tab(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<Item> DIAMOND_WARDING_LEGGINGS = ITEMS.register("warding_leggings",
            () -> new WardArmorItem(ModArmorMaterials.DIAMOND_WARDING, EquipmentSlot.LEGS, new Item.Properties().tab(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<Item> DIAMOND_WARDING_BOOTS = ITEMS.register("warding_boots",
            () -> new WardArmorItem(ModArmorMaterials.DIAMOND_WARDING, EquipmentSlot.FEET, new Item.Properties().tab(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<Item> FIRE_EFFIGY = ITEMS.register("fire_effigy",
            () -> new FireEffigy( new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> ICE_EFFIGY = ITEMS.register("ice_effigy",
            () -> new IceEffigy( new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> LIGHTNING_EFFIGY = ITEMS.register("lightning_effigy",
            () -> new LightningEffigy( new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> WARDING_TOTEM = ITEMS.register("warding_totem",
            () -> new WardingTotem(ModBlocks.WARDING_TOTEM_BLOCK.get(), new Item.Properties().tab(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<Item> LIGHTNING_WHIRL = ITEMS.register("lightning_whirl",
            () -> new LightningWhirl( new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> VENGEFUL_STANCE = ITEMS.register("vengeful_stance",
            () -> new VengefulStanceItem( new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> SPLITTING_TRIDENT = ITEMS.register("splitting_trident",
            () -> new HammerRain( new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> TELEPORT = ITEMS.register("teleport",
            () -> new EffigyOfUnity( new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> FLUXITEM = ITEMS.register("flux_item",
            () -> new FluxItem( new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> WARDLIGHT = ITEMS.register("wardlight",
            () -> new WardlightItem(ModBlocks.WARDLIGHT_BLOCK.get(),new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> ENDERSEYE = ITEMS.register("enders_eye",
            () -> new EndersEye( new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> ENDERSEEKER = ITEMS.register("ender_seeker",
            () -> new EnderSeeker( new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> WARDLOOP = ITEMS.register("wardloop",
            () -> new Wardloop(new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> FRIENDSHIP = ITEMS.register("friendshipbracelet",
            () -> new FriendshipBracelet(new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> VOLATILE = ITEMS.register("volatile",
            () -> new Volatile(new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));






    //public static final RegistryObject<Item> NETHERITE_WARDING_HELMET = ITEMS.register("netherite_warding_helmet",
    //        () -> new WardArmorItem(ModArmorMaterials.NETHERITE_WARDING, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    /*public static final RegistryObject<Item> NETHERITE_WARDING_CHEST = ITEMS.register("netherite_warding_chest",
            () -> new WardArmorItem(ModArmorMaterials.NETHERITE_WARDING, EquipmentSlot.CHEST, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> NETHERITE_WARDING_LEGGINGS = ITEMS.register("netherite_warding_leggings",
            () -> new WardArmorItem(ModArmorMaterials.NETHERITE_WARDING, EquipmentSlot.LEGS, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> NETHERITE_WARDING_BOOTS = ITEMS.register("netherite_warding_boots",
            () -> new WardArmorItem(ModArmorMaterials.NETHERITE_WARDING, EquipmentSlot.FEET, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));*/
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
