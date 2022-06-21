package com.cleannrooster.spellblademod.enchants;

import com.cleannrooster.spellblademod.items.WardArmorItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class GreaterWardingEnchant extends Enchantment{
    protected GreaterWardingEnchant(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot... p_44678_) {
        super(p_44676_, p_44677_, p_44678_);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        if(stack.getItem() instanceof ArmorItem){
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
