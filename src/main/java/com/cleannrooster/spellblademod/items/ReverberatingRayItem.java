package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.entity.ModEntities;
import com.cleannrooster.spellblademod.entity.ReverberatingRay;
import com.cleannrooster.spellblademod.manasystem.data.PlayerMana;
import com.cleannrooster.spellblademod.manasystem.data.PlayerManaProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class ReverberatingRayItem extends Spell {
    public ReverberatingRayItem(Properties p_41383_) {
        super(p_41383_);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        ItemStack itemstack = ((Player) p_41433_).getItemInHand(p_41434_);
        PlayerMana playerMana = p_41433_.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);

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

        if (p_41434_ == InteractionHand.OFF_HAND){
            return InteractionResultHolder.fail(((Player) p_41433_).getItemInHand(p_41434_));
        }

        ((Player) p_41433_).startUsingItem(p_41434_);
        ReverberatingRay orb = new ReverberatingRay(ModEntities.REVERBERATING_RAY_ORB.get(), ((Player) p_41433_).getLevel());
        orb.setPos(((Player) p_41433_).getEyePosition());
        orb.pickup = AbstractArrow.Pickup.DISALLOWED;
        orb.noPhysics = true;
        orb.setOwner((Player) p_41433_);
        orb.primary = true;
        orb.shootFromRotation((Player) p_41433_,0,0,0,0,0);
        p_41432_.addFreshEntity(orb);
        return InteractionResultHolder.success(itemstack);
    }

    @Override
    public boolean trigger(Level level, Player player, float modifier) {
        PlayerMana playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
        if(playerMana.getMana() < -1 && player.getHealth() <= 2)
        {
            return true;
        }
        player.getCooldowns().addCooldown(this,20);
        if(!level.isClientSide()) {
            ReverberatingRay orb = new ReverberatingRay(ModEntities.REVERBERATING_RAY_ORB.get(), ((Player) player).getLevel());
            orb.setPos(((Player) player).getEyePosition());
            orb.pickup = AbstractArrow.Pickup.DISALLOWED;
            orb.setNoPhysics(true);
            orb.setOwner((Player) player);
            orb.primary = true;
            orb.triggered = true;
            orb.shootFromRotation((Player) player, 0, 0, 0, 0, 0);
            level.addFreshEntity(orb);
        }
        if (playerMana.getMana() < -1 && player.getHealth() > 2) {

            player.invulnerableTime = 0;
            player.hurt(DamageSource.MAGIC, 2);
            player.invulnerableTime = 0;

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
