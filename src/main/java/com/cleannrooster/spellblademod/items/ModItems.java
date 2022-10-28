package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.ModBlocks;
import com.cleannrooster.spellblademod.blocks.WardIronBlock;
import com.cleannrooster.spellblademod.enchants.GreaterWardingEnchant;
import com.cleannrooster.spellblademod.enchants.ModEnchantmentCategory;
import com.cleannrooster.spellblademod.enchants.WardTempered;
import com.cleannrooster.spellblademod.enchants.WardingEnchant;
import com.cleannrooster.spellblademod.setup.ModSetup;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "spellblademod");
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, "spellblademod");
    public static final DeferredRegister<Potion> POTION = DeferredRegister.create(ForgeRegistries.POTIONS, "spellblademod");

    public static final EnchantmentCategory ARMORENCHANT = EnchantmentCategory.create("armorspellblade",item -> item instanceof ArmorItem);
    public static final EnchantmentCategory SWORDENCHANT = EnchantmentCategory.create("swordspellblade",item -> item instanceof SwordItem);

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
    public static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    public static final EquipmentSlot[] HAND_SLOTS = new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND};


    public static final RegistryObject<Enchantment> WARDING = ENCHANTMENTS.register("lesserwarding",
            () -> new WardingEnchant(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.ARMOR,ARMOR_SLOTS));
    public static final RegistryObject<Enchantment> GREATERWARDING = ENCHANTMENTS.register("greaterwarding",
            () -> new GreaterWardingEnchant(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR,ARMOR_SLOTS));

    public static final RegistryObject<Enchantment> WARDTEMPERED = ENCHANTMENTS.register("wardtempered",
            () -> new WardTempered(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.WEAPON,EquipmentSlot.MAINHAND));

    public static final RegistryObject<Item> SPELLBLADE = ITEMS.register("spellblade",
            () -> new Spellblade(Tiers.DIAMOND, 2, -2.0F, (new Item.Properties()).tab(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<Item> CLAYMORE = ITEMS.register("claymore",
            () -> new BattlemageClaymore(Tiers.DIAMOND, 5, -2.8F, (new Item.Properties()).tab(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<Item> MACE = ITEMS.register("mace",
            () -> new BattlemagesMace(Tiers.DIAMOND, 4, -2.5F, (new Item.Properties()).tab(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<Item> DAGGER = ITEMS.register("dagger",
            () -> new SpellDagger(Tiers.DIAMOND, 0, -1.0F, (new Item.Properties()).tab(ModSetup.ITEM_GROUP)));

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
    public static final RegistryObject<Item> SENTINEL_TOTEM = ITEMS.register("sentinel_totem",
            () -> new SentinelTotemItem(ModBlocks.SENTINEL_TOTEM_BLOCK.get(), new Item.Properties().tab(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<Item> OIL = ITEMS.register("oil",
            () -> new FlaskItem(new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1).durability(8)));
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


    public static final RegistryObject<Item> REVERBERATING_RAY = ITEMS.register("reverberating_ray",
            () -> new ReverberatingRayItem(new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> ANIMATE = ITEMS.register("animate",
            () -> new Animate(new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> ENDERLOPERSTANCE = ITEMS.register("enderloperstance",
            () -> new EnderloperStance(new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> NULLIFYINGSTANCE = ITEMS.register("nullifyingstance",
            () -> new NullifyingStance(new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> BLADEFLURRY = ITEMS.register("bladeflurry",
            () -> new BladeFlurry(new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> BOUNCINGITEM = ITEMS.register("bouncing",
            () -> new BouncingItem(new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> SPARKITEM = ITEMS.register("spark",
            () -> new SpiderSparkItem(new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> AMORPHOUS = ITEMS.register("amorphous",
            () -> new AmorphousAmethyst(Tiers.DIAMOND, 2, -1.6F, (new Item.Properties()).tab(ModSetup.ITEM_GROUP)));
    public static final RegistryObject<Item> IMPALE = ITEMS.register("impale",
            () -> new Impale(new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> WINTERBURIAL = ITEMS.register("winterburial",
            () -> new WinterBurial(new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> ESSENCEBOLT = ITEMS.register("essencebolt",
            () -> new EssenceBolt(new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> BOMBARD = ITEMS.register("bombard",
            () -> new Bombard(new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> DISAPPEARINGACT = ITEMS.register("disappearingact",
            () -> new DisappearingAct(new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1)));








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
