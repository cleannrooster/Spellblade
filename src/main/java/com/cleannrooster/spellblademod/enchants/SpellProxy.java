package com.cleannrooster.spellblademod.enchants;

import com.cleannrooster.spellblademod.SpellbladeMod;
import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.items.*;
import com.google.common.collect.Maps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.FireAspectEnchantment;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class SpellProxy extends Enchantment {
    private final EquipmentSlot[] slots;

    public SpellProxy(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot... p_44678_) {
        super(p_44676_, p_44677_, p_44678_);
        this.slots = p_44678_;
    }

    public int getMinCost(int p_45000_) {
        return 10 + 20 * (p_45000_ - 1);
    }

    public int getMaxCost(int p_45002_) {
        return super.getMinCost(p_45002_) + 50;
    }

    public int getMaxLevel() {
        return 2;
    }


    @Override
    public void doPostAttack(LivingEntity p_44692_, Entity living, int p_44694_) {


    }
    public static Iterable<ItemStack> getOnTrigger(Enchantment p_44837_, LivingEntity p_44838_) {
        Iterable<ItemStack> iterable = p_44837_.getSlotItems(p_44838_).values();
        return iterable;
    }

    @Override
    public Component getFullname(int p_44701_) {
        return super.getFullname(p_44701_);
    }

    @Override
    public boolean canEnchant(ItemStack p_44689_) {
        if(p_44689_.getItem() instanceof Spellblade){
            return false;
        }
        else if(p_44689_.getItem() instanceof SwordItem || p_44689_.getItem() instanceof AxeItem|| p_44689_.getItem() == Items.STICK){
            return true;
        }
        else {
            return super.canEnchant(p_44689_);
        }
    }
    public boolean canApplyAtEnchantingTable(ItemStack p_44689_) {
        if(p_44689_.getItem() instanceof Spellblade){
            return false;
        }
        else if(p_44689_.getItem() instanceof SwordItem || p_44689_.getItem() instanceof AxeItem || p_44689_.getItem() == Items.STICK ){
            return true;
        }
        else {
            return super.canApplyAtEnchantingTable(p_44689_);
        }
    }

}
