package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.enchants.ModEnchants;
import com.cleannrooster.spellblademod.manasystem.client.ManaOverlay;
import com.cleannrooster.spellblademod.manasystem.manatick;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Spellblade extends SwordItem{
    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;
    private double ward = 0;
    public int tier = 0;
    public int tier1 = 0;
    public Spellblade(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
        this.attackDamage = (float) (((float)p_43270_ + p_43269_.getAttackDamageBonus())*Math.pow(1.25D,this.ward/40D));
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)p_43271_, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }


    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage*Math.pow(1.25, (int)((this.ward+1)/40D)), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)-2.0, AttributeModifier.Operation.ADDITION));
        if(slot == EquipmentSlot.MAINHAND) {
            return builder.build();
        }
        else{
            return super.getAttributeModifiers(slot, stack);
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        Player playa = (Player) player;
        if (player.getEffect((StatusEffectsModded.WARDLOCKED.get())) == null) {

            player.addEffect(new MobEffectInstance(StatusEffectsModded.WARDING.get(), 30, 1 + EnchantmentHelper.getItemEnchantmentLevel(ModEnchants.WARDTEMPERED.get(), stack)));
        }

        boolean flag = false;
        int ii = 0;
        for (int i = 0; i <= playa.getInventory().getContainerSize(); i++) {
            if (playa.getInventory().getItem(i).getItem() instanceof Guard) {
                if (playa.getInventory().getItem(i).hasTag()) {
                    if (playa.getInventory().getItem(i).getTag().getInt("Triggerable") == 1) {
                        ((Guard) playa.getInventory().getItem(i).getItem()).guardtick(playa, player.level);
                        ii++;
                    }
                }
            }
            if (ii > 0) {
                player.addEffect(new MobEffectInstance(StatusEffectsModded.SPELLWEAVING.get(), 30, ii - 1));
            }
        }
        if (player.hasEffect(StatusEffectsModded.SPELLWEAVING.get()) && ((Player)player).getAttributes().getBaseValue(manatick.WARD) < 0 && count % 10 ==5){
            player.hurt(DamageSource.MAGIC,player.getEffect(StatusEffectsModded.SPELLWEAVING.get()).getAmplifier());
        }
    }
    /*public void releaseUsing(ItemStack p_43394_, Level p_43395_, LivingEntity p_43396_, int p_43397_) {
        if ( p_43396_.getEffect((StatusEffectsModded.WARDLOCKED.get())) == null) {
            p_43396_.addEffect(new MobEffectInstance(StatusEffectsModded.WARDLOCKED.get(), 30, 0));
        }
    }*/

    @Override
    public InteractionResult interactLivingEntity(ItemStack p_41398_, Player p_41399_, LivingEntity p_41400_, InteractionHand p_41401_) {
        ItemStack itemstack = p_41398_;

        if (p_41399_.isShiftKeyDown()) {
            p_41399_.startUsingItem(p_41401_);
            int ii = 0;
            for (int i = 0; i <= p_41399_.getInventory().getContainerSize(); i++) {
                if (p_41399_.getInventory().getItem(i).getItem() instanceof Guard) {
                    if (p_41399_.getInventory().getItem(i).hasTag()) {
                        if (p_41399_.getInventory().getItem(i).getTag().getInt("Triggerable") == 1) {
                            ((Guard) p_41399_.getInventory().getItem(i).getItem()).guardstart(p_41399_, p_41399_.level, i);
                            ii++;
                        }
                    }
                }
                if (ii > 0) {
                    p_41399_.addEffect(new MobEffectInstance(StatusEffectsModded.SPELLWEAVING.get(), 30, ii - 1));
                }
            }
            return InteractionResult.SUCCESS;
        } else {
            p_41399_.getCooldowns().addCooldown(this,20);
            p_41399_.setLastHurtMob(p_41400_);
            int ii = 0;
            if (p_41399_.getMainHandItem().getItem() instanceof Spellblade && !(p_41399_.getOffhandItem().getItem() instanceof LightningWhirl)) {
                boolean flag = false;
                for (int i = 0; i <= p_41399_.getInventory().getContainerSize(); i++) {
                    if (p_41399_.getInventory().getItem(i).getItem() instanceof Spell) {
                        if (p_41399_.getInventory().getItem(i).hasTag()) {
                            if (p_41399_.getInventory().getItem(i).getTag().getInt("Triggerable") == 1) {
                                if (!p_41399_.getCooldowns().isOnCooldown(p_41399_.getInventory().getItem(i).getItem())) {
                                    if (((Spell)p_41399_.getInventory().getItem(i).getItem()).isTargeted()) {
                                        if (((Spell) p_41399_.getInventory().getItem(i).getItem()).triggeron(p_41399_.level, p_41399_, p_41400_, (float) 1 / 8)) {
                                        }
                                        if (p_41399_.getInventory().getItem(i).getItem() instanceof LightningWhirl){
                                             flag = true;
                                        }
                                    }
                                    else{
                                        if (((Spell) p_41399_.getInventory().getItem(i).getItem()).trigger(p_41399_.level, p_41399_, (float) 1 / 8)) {

                                        }

                                    }
                                }
                                ii++;
                            }
                        }
                    }
                }
                if (ii > 0) {
                    p_41399_.addEffect(new MobEffectInstance(StatusEffectsModded.SPELLWEAVING.get(), 80, ii - 1));
                    if (flag) {
                        return InteractionResult.CONSUME;
                    }
                    else{
                        return InteractionResult.SUCCESS;
                    }
                }
                else{
                    return InteractionResult.FAIL;
                }
            }
            return InteractionResult.FAIL;
        }
    }

    public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) {
        if (p_41406_ instanceof Player){
            Player player = (Player) p_41406_;
            CompoundTag nbt = null;
            ItemStack stack = p_41404_;
            this.ward = ((Player)player).getAttributes().getBaseValue(manatick.WARD);
            if (this.ward >= 39 && this.ward < 79){
                nbt = stack.getOrCreateTag();
                tier1 = 1;

            }
            if (this.ward >= 79 && this.ward < 119){
                nbt = stack.getOrCreateTag();
                tier1 = 2;
            }
             if (this.ward >= 119 && this.ward < 159){
                 nbt = stack.getOrCreateTag();
                 tier1 = 3;
            }
            if (this.ward >= 159){
                nbt = stack.getOrCreateTag();
                tier1 = 4;
            }
            if (this.ward < 39)
            {
                nbt = stack.getOrCreateTag();
                tier1 = 0;
            }
            tier = tier1;
            nbt.putInt("CustomModelData", tier1);
        }
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {

        return super.onLeftClickEntity(stack, player, entity);
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity living, LivingEntity player1) {
        if (stack.hasTag() && player1 instanceof Player player) {
            float f = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);

            float f1;
            if (stack.getTag().get("OnHit") != null) {
                player.setLastHurtMob(living);
                int ii = 0;
                if (player.getMainHandItem().getItem() instanceof Spellblade) {
                    for (int i = 0; i <= player.getInventory().getContainerSize(); i++) {
                        if (player.getInventory().getItem(i).getItem() instanceof Spell) {
                            if (player.getInventory().getItem(i).hasTag()) {
                                if (!player.getCooldowns().isOnCooldown(player.getInventory().getItem(i).getItem()) && player.getInventory().getItem(i).getTag().getInt("Triggerable") == 1 && !(player.getInventory().getItem(i).getItem() instanceof LightningWhirl)) {
                                    if (((Spell) player.getInventory().getItem(i).getItem()).isTargeted()) {
                                        if (((Spell) player.getInventory().getItem(i).getItem()).triggeron(player.level, player, living, (float) 1 / 8)) {
                                        }
                                    } else {
                                        if (((Spell) player.getInventory().getItem(i).getItem()).trigger(player.level, player, (float) 1 / 8)) {

                                        }

                                    }
                                    ii++;
                                }
                            }


                        }
                    }
                    if (ii > 0) {
                        player.addEffect(new MobEffectInstance(StatusEffectsModded.SPELLWEAVING.get(), 80, ii - 1));
                    }
                }
            }
        }

        stack.hurtAndBreak(1, player1, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);

        });

        if (player1 instanceof Player){
            player1.addEffect(new MobEffectInstance(StatusEffectsModded.WARDING.get(),80,1+EnchantmentHelper.getItemEnchantmentLevel(ModEnchants.WARDTEMPERED.get(),stack)));
        }

        return true;
    }
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.BOW;
    }
    public int getUseDuration(ItemStack p_43419_) {
        return 72000;
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        TextComponent text = new TextComponent("On Hit Mode");
        if (p_41421_.hasTag()) {
            if (p_41421_.getTag().get("OnHit") != null) {
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


    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        ItemStack itemstack = p_41433_.getItemInHand(p_41434_);
        if (p_41433_.isShiftKeyDown()) {
            p_41433_.startUsingItem(p_41434_);
            int ii = 0;
            for (int i = 0; i <= p_41433_.getInventory().getContainerSize(); i++) {
                if (p_41433_.getInventory().getItem(i).getItem() instanceof Guard) {
                    if (p_41433_.getInventory().getItem(i).hasTag()) {
                        if (p_41433_.getInventory().getItem(i).getTag().getInt("Triggerable") == 1) {
                            ((Guard) p_41433_.getInventory().getItem(i).getItem()).guardstart(p_41433_, p_41433_.level, i);
                            ii++;
                        }
                    }
                }
                if (ii > 0) {
                    p_41433_.addEffect(new MobEffectInstance(StatusEffectsModded.SPELLWEAVING.get(), 30, ii - 1));
                }
            }
            return InteractionResultHolder.consume(itemstack);
        } else {
            int ii = 0;
            p_41433_.getCooldowns().addCooldown(this,20);
            if (p_41433_.getMainHandItem().getItem() instanceof Spellblade && !(p_41433_.getOffhandItem().getItem() instanceof LightningWhirl)) {
                boolean flag = false;
                for (int i = 0; i <= p_41433_.getInventory().getContainerSize() ; i++) {
                    if (p_41433_.getInventory().getItem(i).getItem() instanceof Spell) {
                        if (p_41433_.getInventory().getItem(i).hasTag()) {
                            if (p_41433_.getInventory().getItem(i).getTag().getInt("Triggerable") == 1) {
                                if (!p_41433_.getCooldowns().isOnCooldown(p_41433_.getInventory().getItem(i).getItem())) {
                                    if (((Spell) p_41433_.getInventory().getItem(i).getItem()).trigger(p_41433_.level, p_41433_, (float) 1 / 8)) {
                                    }
                                    if (p_41433_.getInventory().getItem(i).getItem() instanceof LightningWhirl){
                                        flag = true;
                                    }
                                }
                                ii++;
                            }
                        }


                    }
                }
                if (ii > 0) {
                    p_41433_.addEffect(new MobEffectInstance(StatusEffectsModded.SPELLWEAVING.get(), 80, ii - 1));
                    if (flag) {
                        return InteractionResultHolder.consume(itemstack);
                    }
                    else{
                        return InteractionResultHolder.success(itemstack);
                    }                }
                else{
                    return InteractionResultHolder.fail(itemstack);
                }
            }
            return InteractionResultHolder.fail(itemstack);
        }

    }

    public static boolean isSpellblade(Item item) {
        if (item instanceof Spellblade){
            return true;
        }
        else{
            return false;
        }
    }
}
