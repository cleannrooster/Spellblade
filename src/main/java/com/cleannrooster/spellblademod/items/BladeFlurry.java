package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.entity.ModEntities;
import com.cleannrooster.spellblademod.entity.sword1;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class BladeFlurry extends Guard{
    public BladeFlurry(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (player.isShiftKeyDown()) {
            CompoundTag nbt;
            if (itemstack.hasTag()) {
                if (itemstack.getTag().get("Triggerable") != null) {
                    nbt = itemstack.getTag();
                    nbt.remove("Triggerable");
                } else {
                    nbt = itemstack.getOrCreateTag();
                    nbt.putInt("Triggerable", 1);
                }

            } else {
                nbt = itemstack.getOrCreateTag();
                nbt.putInt("Triggerable", 1);
            }
            return InteractionResultHolder.success(itemstack);

        }
        return InteractionResultHolder.fail(itemstack);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        CompoundTag nbt;
        TextComponent textTrig = new TextComponent("Triggerable");
        TextComponent mode1 = new TextComponent("Sphere Mode");
        TextComponent mode2 = new TextComponent("Circles Mode");
        ItemStack itemStack = p_41421_;
        if (itemStack.hasTag())
        {
            if(itemStack.getTag().get("Triggerable") != null) {
                if(itemStack.getTag().getInt("Mode") == 1) {
                    p_41423_.add(mode1);
                    p_41423_.add(textTrig);
                    if(p_41423_.contains(mode2)) {
                        p_41423_.remove(mode2);
                    }
                }
                if(itemStack.getTag().getInt("Mode") == 2) {
                    p_41423_.add(mode2);
                    p_41423_.add(textTrig);
                    if(p_41423_.contains(mode1)) {

                        p_41423_.remove(mode1);
                    }
                }

            }
        }
        else {
            if(p_41423_.contains(mode1)) {
                p_41423_.remove(mode1);
            }
            if(p_41423_.contains(mode2)) {
                p_41423_.remove(mode2);
            }
            if(p_41423_.contains(textTrig)) {
                p_41423_.remove(textTrig);
            }
        }
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        if (p_41453_.hasTag()){
            if(p_41453_.getTag().getInt("Triggerable") == 1){
                return true;
            }
        }
        return false;
    }
    @Override
    public void guardstart(Player player, Level level, int slot){
        sword1[] swords = new sword1[32];

        for(int i = 0; i < swords.length; i++) {
            swords[i] = new sword1(ModEntities.SWORD.get(), level, player);
            swords[i].setPos(player.getEyePosition().add(0, 4, 0));
            swords[i].number = i + 1;
            swords[i].tickCount = -i;
            if (player.getInventory().getItem(slot).getTag() != null) {
                swords[i].mode = Objects.requireNonNull(player.getInventory().getItem(slot).getTag()).getInt("Mode");
                level.addFreshEntity(swords[i]);
            }
        }

    }
}
