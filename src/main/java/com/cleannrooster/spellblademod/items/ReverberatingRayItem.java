package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.entity.ModEntities;
import com.cleannrooster.spellblademod.entity.ReverberatingRay;
import com.cleannrooster.spellblademod.manasystem.manatick;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.List;

public class ReverberatingRayItem extends Spell {
    public ReverberatingRayItem(Properties p_41383_) {
        super(p_41383_);
    }
    public int getColor() {
        return 34693;
    }

    @Override
    public boolean isTriggerable() {
        return true;
    }

    @Override
    public boolean isTargeted() {
        return true;
    }

    public Item getIngredient1() {return Items.ENDER_PEARL;};
    public Item getIngredient2() {return ModItems.FLUXITEM.get();};

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        ItemStack itemstack = ((Player) p_41433_).getItemInHand(p_41434_);

        if (p_41433_.isShiftKeyDown()) {
            CompoundTag nbt;
            if (itemstack.hasTag())
            {
                if(itemstack.getTag().get("Triggerable") != null) {
                    nbt = itemstack.getTag();
                    nbt.remove("Triggerable");
                }
                else{
                    nbt = itemstack.getOrCreateTag();
                    nbt.putInt("Triggerable", 1);
                }

            }
            else
            {
                nbt = itemstack.getOrCreateTag();
                nbt.putInt("Triggerable", 1);
            }
            return InteractionResultHolder.success(itemstack);

        }
        ((Player)p_41433_).getAttribute(manatick.WARD).setBaseValue(((Player) p_41433_).getAttributeBaseValue(manatick.WARD)-20);

            ReverberatingRay orb = new ReverberatingRay(ModEntities.REVERBERATING_RAY_ORB.get(), ((Player) p_41433_).getLevel(),(LivingEntity)null);
            orb.damage = (float) p_41433_.getAttributeValue(Attributes.ATTACK_DAMAGE);
            orb.setPos(((Player) p_41433_).getEyePosition());
            orb.pickup = AbstractArrow.Pickup.DISALLOWED;
            orb.setNoPhysics(true);
            orb.setOwner((Player) p_41433_);
            orb.primary = true;
            orb.triggered = true;
            orb.shootFromRotation((Player) p_41433_, 0, 0, 0, 0, 0);

        if(!p_41432_.isClientSide()) {
            p_41432_.addFreshEntity(orb);
        }
        if (((Player)p_41433_).getAttributes().getBaseValue(manatick.WARD) < -21 ) {

            p_41433_.hurt(DamageSource.MAGIC, 2);

        }
        return InteractionResultHolder.success(itemstack);
    }

    @Override
    public int triggerCooldown() {
        return 20;
    }

    @Override
    public boolean trigger(Level level, Player player, float modifier) {
        List<LivingEntity> entities = level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT,player,player.getBoundingBox().inflate(20));
        entities.removeIf(entity -> !FriendshipBracelet.PlayerFriendshipPredicate(player,entity));
        entities.removeIf(entity -> !entity.hasLineOfSight(player));
        for(int ii = 0; ii < 3; ii++){
            LivingEntity target = level.getNearestEntity(entities,TargetingConditions.forNonCombat(),player,player.getX(),player.getY(),player.getZ());
            if(target != null) {
                ReverberatingRay orb = new ReverberatingRay(ModEntities.REVERBERATING_RAY_ORB.get(), ((Player) player).getLevel(), target);
                orb.setPos(((Player) player).getEyePosition());
                orb.damage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                orb.pickup = AbstractArrow.Pickup.DISALLOWED;
                orb.setNoPhysics(true);
                orb.setOwner((Player) player);
                orb.primary = true;
                orb.triggered = true;
                orb.shootFromRotation((Player) player, 0, 0, 0, 0, 0);
                if (!level.isClientSide()) {
                    level.addFreshEntity(orb);

                }
                entities.remove(target);

                ((Player)player).getAttribute(manatick.WARD).setBaseValue(((Player) player).getAttributeBaseValue(manatick.WARD)-20);
                if (((Player)player).getAttributes().getBaseValue(manatick.WARD) < -21) {
                    player.hurt(DamageSource.MAGIC,2);
                }
            }

        }

        return false;
    }
    @Override
    public boolean triggeron(Level level, Player player, LivingEntity target, float modifier){
            ReverberatingRay orb = new ReverberatingRay(ModEntities.REVERBERATING_RAY_ORB.get(), ((Player) player).getLevel(), target);
            orb.setPos(((Player) player).getEyePosition());
            orb.pickup = AbstractArrow.Pickup.DISALLOWED;
            orb.setNoPhysics(true);
            orb.damage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
            orb.setOwner((Player) player);
            orb.primary = true;
            orb.triggered = true;
            orb.shootFromRotation((Player) player, 0, 0, 0, 0, 0);
        if(!level.isClientSide()) {
            level.addFreshEntity(orb);
        }
        ((Player)player).getAttribute(manatick.WARD).setBaseValue(((Player) player).getAttributeBaseValue(manatick.WARD)-20);

        if (((Player)player).getAttributes().getBaseValue(manatick.WARD) < -21) {
            player.hurt(DamageSource.MAGIC,2);
        }
        return false;
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
    public UseAnim getUseAnimation(ItemStack p_43417_) {
        return UseAnim.BOW;
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {

    }
    @Override
    public int getUseDuration(ItemStack p_43419_) {
        return 800000;
    }
}
