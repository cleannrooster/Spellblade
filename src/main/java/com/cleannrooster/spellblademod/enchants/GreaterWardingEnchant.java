package com.cleannrooster.spellblademod.enchants;

import com.cleannrooster.spellblademod.items.WardArmorItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;

public class GreaterWardingEnchant extends ProtectionEnchantment{
    public GreaterWardingEnchant(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot... p_44678_) {
        super(p_44676_, Type.ALL, p_44678_);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
    public int getMaxCost(int p_45144_) {
        return this.getMinCost(p_45144_) + 50;
    }

    public int getDamageProtection(int p_45133_, DamageSource p_45134_) {
        if (p_45134_.isBypassInvul()) {
            return 0;
        } else if (this.type == ProtectionEnchantment.Type.ALL) {
            return p_45133_;
        } else if (this.type == ProtectionEnchantment.Type.FIRE && p_45134_.isFire()) {
            return p_45133_ * 2;
        } else if (this.type == ProtectionEnchantment.Type.FALL && p_45134_.isFall()) {
            return p_45133_ * 3;
        } else if (this.type == ProtectionEnchantment.Type.EXPLOSION && p_45134_.isExplosion()) {
            return p_45133_ * 2;
        } else {
            return this.type == ProtectionEnchantment.Type.PROJECTILE && p_45134_.isProjectile() ? p_45133_ * 2 : 0;
        }
    }

    public boolean checkCompatibility(Enchantment p_45142_) {

            return super.checkCompatibility(p_45142_);

    }
}
