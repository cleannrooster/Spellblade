package com.cleannrooster.spellblademod.effects;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.manasystem.manatick;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.AbsoptionMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.Map;

public class WardAbsorption extends AbsoptionMobEffect {
    public WardAbsorption(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }
    @Override
    public void removeAttributeModifiers(LivingEntity p_19417_, AttributeMap p_19418_, int p_19419_) {

        if(p_19417_ instanceof Player player) {
            p_19417_.setAbsorptionAmount(p_19417_.getAbsorptionAmount() - p_19419_);
        }
        for(Map.Entry<Attribute, AttributeModifier> entry : this.getAttributeModifiers().entrySet()) {
            AttributeInstance attributeinstance = p_19418_.getInstance(entry.getKey());
            if (attributeinstance != null) {
                attributeinstance.removeModifier(entry.getValue());
            }
        }
    }
    @Override
    public void addAttributeModifiers(LivingEntity p_19421_, AttributeMap p_19422_, int p_19423_) {

        if(p_19421_ instanceof Player player) {
            p_19421_.setAbsorptionAmount(p_19421_.getAbsorptionAmount() + p_19423_);
        }
        for(Map.Entry<Attribute, AttributeModifier> entry : this.getAttributeModifiers().entrySet()) {
            AttributeInstance attributeinstance = p_19422_.getInstance(entry.getKey());
            if (attributeinstance != null) {
                attributeinstance.removeModifier(entry.getValue());
            }
        }
    }
}
