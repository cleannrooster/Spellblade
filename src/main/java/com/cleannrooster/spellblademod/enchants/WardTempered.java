package com.cleannrooster.spellblademod.enchants;

import com.cleannrooster.spellblademod.SpellbladeMod;
import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.items.ModItems;
import com.cleannrooster.spellblademod.items.Spellblade;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.*;

import static net.minecraft.world.item.EnchantedBookItem.createForEnchantment;

public class WardTempered extends DamageEnchantment {
    public WardTempered(Enchantment.Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot... p_44678_) {
        super(p_44676_, 0, p_44678_);
    }

    public int getMaxCost(int p_45144_) {
        return this.getMinCost(p_45144_) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
    public boolean checkCompatibility(Enchantment p_44644_) {
        return true;
    }

    @Override
    public void doPostAttack(LivingEntity p_44692_, Entity p_44693_, int p_44694_) {
        if(p_44692_ instanceof Player player) {
            int modifier = 0;
            if(EnchantmentHelper.getEnchantmentLevel(SpellbladeMod.spellproxy,player) > 0){
                modifier = 1;
            }
            player.addEffect(new MobEffectInstance(StatusEffectsModded.WARDING.get(), 80, -1+EnchantmentHelper.getEnchantmentLevel(this, player) + modifier));

        }
        super.doPostHurt(p_44692_, p_44693_, p_44694_);
    }
}
