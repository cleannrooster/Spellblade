package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.manasystem.data.PlayerMana;
import com.cleannrooster.spellblademod.manasystem.data.PlayerManaProvider;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Spellblade extends SwordItem{
    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public int tier = 0;
    public Spellblade(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
        this.attackDamage = (float) (((float)p_43270_ + p_43269_.getAttackDamageBonus())*Math.pow(1.25,this.tier));
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)p_43271_, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }
    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count)
    {
        Player playa = (Player) player;
        PlayerMana playerMana = playa.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
        if ( player.getEffect((StatusEffectsModded.WARDLOCKED.get())) == null) {

            playerMana.addMana(1);
        }
    }
    public void releaseUsing(ItemStack p_43394_, Level p_43395_, LivingEntity p_43396_, int p_43397_) {
        if ( p_43396_.getEffect((StatusEffectsModded.WARDLOCKED.get())) == null) {
            p_43396_.addEffect(new MobEffectInstance(StatusEffectsModded.WARDLOCKED.get(), 30, 0));
        }
    }
    public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) {
        if (p_41406_ instanceof Player){
            Player player = (Player) p_41406_;
            PlayerMana playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
            CompoundTag nbt;
            ItemStack stack = p_41404_;
            if (stack.hasTag())
            {
                nbt = stack.getTag();
            }
            else
            {
                nbt = new CompoundTag();
            }
            if (playerMana.getMana() > 39 && playerMana.getMana() < 79){
                nbt = stack.getOrCreateTag();
                tier = 1;
                nbt.putInt("CustomModelData", tier);
            }
            if (playerMana.getMana() > 79 && playerMana.getMana() < 119){
                nbt = stack.getOrCreateTag();
                tier = 2;
                nbt.putInt("CustomModelData", tier);
            }
             if (playerMana.getMana() >= 119 && playerMana.getMana() < 159){
                 nbt = stack.getOrCreateTag();
                 tier = 3;
                nbt.putInt("CustomModelData", tier);
            }
            if (playerMana.getMana() >= 159){
                nbt = stack.getOrCreateTag();
                tier = 4;
                nbt.putInt("CustomModelData", tier);
            }
            if (playerMana.getMana() < 39)
            {
                nbt = stack.getOrCreateTag();
                tier = 0;
                nbt.putInt("CustomModelData", tier);
            }
        }
    }
    public boolean hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
        p_43278_.hurtAndBreak(1, p_43280_, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);

        });

        if (p_43280_ instanceof Player){
            p_43280_.addEffect(new MobEffectInstance(StatusEffectsModded.WARDING.get(),40,1));
        }

        return true;
    }
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.BOW;
    }
    public int getUseDuration(ItemStack p_43419_) {
        return 72000;
    }

    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        if (p_41433_.isShiftKeyDown()) {
            ItemStack itemstack = p_41433_.getItemInHand(p_41434_);
            p_41433_.startUsingItem(p_41434_);
            return InteractionResultHolder.consume(itemstack);
        } else {
            return InteractionResultHolder.pass(p_41433_.getItemInHand(p_41434_));
        }
    }
}
