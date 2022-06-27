package com.cleannrooster.spellblademod.enchants;

import com.cleannrooster.spellblademod.items.Spellblade;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class WardTempered extends Enchantment{
    protected WardTempered(Enchantment.Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot... p_44678_) {
        super(p_44676_, p_44677_, p_44678_);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(stack.getItem() instanceof Spellblade){
            return true;
        }
        else {
            return false;
        }

    }
    @Override
    public boolean isTreasureOnly() {
        return true;
    }

}
