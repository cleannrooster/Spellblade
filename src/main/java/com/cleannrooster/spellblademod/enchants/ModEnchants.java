package com.cleannrooster.spellblademod.enchants;

import com.cleannrooster.spellblademod.items.Spellblade;
import com.cleannrooster.spellblademod.items.WardArmorItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.IExtensibleEnum;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.lwjgl.system.SharedLibrary;

import java.util.function.Predicate;

public class ModEnchants{
    static Predicate<Item> delegate = WardArmorItem::isWarding;
    static Predicate<Item> delegate2 = Spellblade::isSpellblade;

    public static EnchantmentCategory category = EnchantmentCategory.create("warding", delegate);
    public static EnchantmentCategory category2 = EnchantmentCategory.create("wardtempered", delegate2);

    public static final DeferredRegister<Enchantment> ENCHANTMENTS
            = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, "spellblademod");
    private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    public static RegistryObject<Enchantment> LESSERWARDING =
            ENCHANTMENTS.register("lesserwarding", () -> new WardingEnchant(
                    Enchantment.Rarity.COMMON, category, ARMOR_SLOTS)
            );
    public static RegistryObject<Enchantment> GREATERWARDING =
            ENCHANTMENTS.register("greaterwarding", () -> new GreaterWardingEnchant(
                    Enchantment.Rarity.UNCOMMON,  category, ARMOR_SLOTS)
            );
    public static RegistryObject<Enchantment> WARDTEMPERED =
            ENCHANTMENTS.register("wardtempered", () -> new WardTempered(
                    Enchantment.Rarity.UNCOMMON,  category2, EquipmentSlot.MAINHAND)
            );

    public static void register (IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
