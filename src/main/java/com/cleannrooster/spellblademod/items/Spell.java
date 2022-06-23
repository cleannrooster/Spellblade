package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.manasystem.data.PlayerMana;
import com.cleannrooster.spellblademod.manasystem.data.PlayerManaProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Spell extends Item {
    public Spell(Properties p_41383_) {
        super(p_41383_);
    }
    public boolean trigger(Level level, Player player, float modifier){
        return false;
    }

}
