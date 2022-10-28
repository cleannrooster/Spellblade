package com.cleannrooster.spellblademod.items;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Spell extends Item {
    public Spell(Properties p_41383_) {
        super(p_41383_);
    }
    public boolean targeted = false;
    public boolean trigger(Level level, Player player, float modifier){
        return false;
    }
    public boolean triggeron(Level level, Player player, LivingEntity target, float modifier){
        return false;
    }
    public boolean isTargeted(){
        return false;
    }
    public int useCooldown(){
        return 0;
    }
    public int triggerCooldown(){
        return 0;
    }
    public int getColor(){
        return 0;
    }
    public boolean isTriggerable() {return true;}
    public Item getIngredient1() {return Items.AIR;};
    public Item getIngredient2() {return Items.AIR;};
    public boolean canFail(){
        return false;
    }
    public boolean failState(Level level, Player player, InteractionHand hand){
        return true;
    }
    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        TextComponent text = new TextComponent("Spellweaving");
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

        MutableComponent text1 = new TextComponent("Right click to toggle Spellweaving");
        MutableComponent text2 = new TextComponent("Oil Brewing Recipe: ");
        MutableComponent text3 = new TranslatableComponent(this.getIngredient1().getDescriptionId());
        MutableComponent text4 = new TranslatableComponent(this.getIngredient2().getDescriptionId());
        MutableComponent text5 = text3.append(" + ").append(text4);
                if(this.getIngredient2() instanceof Spell){
                    text5.append(" Spell Oil");
                }


        p_41423_.add(text1);
        p_41423_.add(text2);
        p_41423_.add(text5);

        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
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
    public boolean overrideOtherStackedOnMe(ItemStack thisStack, ItemStack onStack, Slot slot, ClickAction clickAction, Player player, SlotAccess slotAccess) {
        if(clickAction == ClickAction.SECONDARY){
            if (thisStack.hasTag()) {
                if (thisStack.getTag().get("Triggerable") != null) {
                    CompoundTag nbt = thisStack.getTag();
                    nbt.remove("Triggerable");
                    return true;
                } else {
                    CompoundTag nbt = thisStack.getOrCreateTag();
                    nbt.putInt("Triggerable", 1);
                    return true;

                }

            } else {
                CompoundTag nbt = thisStack.getOrCreateTag();
                nbt.putInt("Triggerable", 1);
                return true;

            }
        }
        return false;
    }
}
