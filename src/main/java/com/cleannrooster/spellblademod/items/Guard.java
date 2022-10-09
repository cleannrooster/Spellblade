package com.cleannrooster.spellblademod.items;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Guard extends Item {
    public Guard(Properties p_41383_) {
        super(p_41383_);
    }

    public void guard(Player player, Level level, LivingEntity entity){

    }
    public void guardtick(Player player, Level level){

    }
    public void guardstart(Player player, Level level, int slot){

    }
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        TextComponent text = new TextComponent("Triggerable");
        if (p_41421_.hasTag()) {
            if (p_41421_.getTag().get("Triggerable") != null) {
                p_41423_.add(text);
            }
        }
        else{
            if (p_41423_.contains(text)){
                p_41423_.remove(text);

            }
        }
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
    }
}
