package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.setup.ModSetup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

public class ModEnchantmentBook extends EnchantedBookItem {
    public ModEnchantmentBook(Properties p_41149_) {
        super(p_41149_);
    }
    @Override
    public void fillItemCategory(CreativeModeTab p_41151_, NonNullList<ItemStack> p_41152_) {
        if (p_41151_ == CreativeModeTab.TAB_SEARCH) {
            for(Enchantment enchantment : Registry.ENCHANTMENT) {
                if (enchantment.category != null) {
                    for(int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i) {
                        p_41152_.add(createForEnchantment(new EnchantmentInstance(enchantment, i)));
                    }
                }
            }
        } else if (p_41151_.getEnchantmentCategories().length != 0) {
            for(Enchantment enchantment1 : Registry.ENCHANTMENT) {
                if (p_41151_ == ModSetup.ITEM_GROUP) {
                    p_41152_.add(createForEnchantment(new EnchantmentInstance(enchantment1, enchantment1.getMaxLevel())));
                }
            }
        }

    }
}
