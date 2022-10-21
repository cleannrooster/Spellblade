package com.cleannrooster.spellblademod.enchants;

import com.cleannrooster.spellblademod.items.WardArmorItem;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.IExtensibleEnum;

import static net.minecraft.world.item.enchantment.EnchantmentCategory.BREAKABLE;

public enum ModEnchantmentCategory implements IExtensibleEnum {
    WARDARMOR {
        public boolean canEnchant(Item p_44791_) {
            return p_44791_ instanceof ArmorItem;
        }
    },
    WARDSWORD{
        public boolean canEnchant(Item p_44791_) {
            return p_44791_ instanceof SwordItem;
        }
    };

    private java.util.function.Predicate<Item> delegate;

    private ModEnchantmentCategory() {}

    private ModEnchantmentCategory(java.util.function.Predicate<Item> delegate) {
        this.delegate = delegate;
    }

    public static EnchantmentCategory create(String name, java.util.function.Predicate<Item> delegate) {
        throw new IllegalStateException("Enum not extended");
    }

    public boolean canEnchant(Item p_44743_) {
        return this.delegate != null && this.delegate.test(p_44743_);
    }
}

