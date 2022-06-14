package com.cleannrooster.spellblademod.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class WardingMail extends Item {
    public WardingMail(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
