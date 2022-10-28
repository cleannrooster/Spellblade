package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.entity.BouncingEntity;
import com.cleannrooster.spellblademod.manasystem.manatick;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class BouncingItem extends Spell{
    public BouncingItem(Properties p_41383_) {
        super(p_41383_);
    }
    public Item getIngredient1() {return Items.MAGMA_CREAM;};
    public Item getIngredient2() {return ModItems.FLUXITEM.get();};

    @Override
    public boolean isTargeted() {
return true;
    }

    @Override
    public boolean triggeron(Level worldIn, Player playerIn, LivingEntity target, float modifier) {
        if(!playerIn.getCooldowns().isOnCooldown(this)) {
            BouncingEntity thrown = new BouncingEntity(playerIn.getLevel(), playerIn);
            thrown.setPos(target.getEyePosition());
            thrown.setDeltaMovement(0 + playerIn.getDeltaMovement().x, -1 + playerIn.getDeltaMovement().y, 0 + playerIn.getDeltaMovement().z);
            if (!worldIn.isClientSide()) {
                playerIn.getLevel().addFreshEntity(thrown);
            }
            ((Player)playerIn).getAttribute(manatick.WARD).setBaseValue(((Player) playerIn).getAttributeBaseValue(manatick.WARD)-20);

            if (((Player)playerIn).getAttributes().getBaseValue(manatick.WARD)< -21) {

                playerIn.hurt(DamageSource.MAGIC, 2);
            }
        }
        return false;    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand p_41434_) {
        ItemStack itemstack = playerIn.getItemInHand(p_41434_);
        Player player = playerIn;

        if (((Player) playerIn).isShiftKeyDown()) {
            CompoundTag nbt;
            if (itemstack.hasTag())
            {
                if(itemstack.getTag().get("Triggerable") != null) {
                    nbt = itemstack.getTag();
                    nbt.remove("Triggerable");
                    player.getInventory().setChanged();
                }
                else{
                    nbt = itemstack.getOrCreateTag();
                    nbt.putInt("Triggerable", 1);
                    player.getInventory().setChanged();
                }

            }
            else
            {
                nbt = itemstack.getOrCreateTag();
                nbt.putInt("Triggerable", 1);
                player.getInventory().setChanged();
            }
            return InteractionResultHolder.success(itemstack);

        }
        ((Player)player).getAttribute(manatick.WARD).setBaseValue(((Player) player).getAttributeBaseValue(manatick.WARD)-20);

        if (((Player)player).getAttributes().getBaseValue(manatick.WARD) < -21) {
            playerIn.hurt(DamageSource.MAGIC,2);
        }

        BouncingEntity thrown = new BouncingEntity(playerIn.getLevel(), playerIn);

        thrown.setDeltaMovement(0.5*playerIn.getViewVector(0).x+playerIn.getDeltaMovement().x,0.5*playerIn.getViewVector(0).y+playerIn.getDeltaMovement().y,0.5*playerIn.getViewVector(0).z+playerIn.getDeltaMovement().z);
        if(!worldIn.isClientSide()) {
            playerIn.getLevel().addFreshEntity(thrown);
        }
        return super.use(worldIn, playerIn, p_41434_);
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
    public boolean trigger(Level worldIn, Player playerIn, float modifier) {
        if(!playerIn.getCooldowns().isOnCooldown(this)) {
            BouncingEntity thrown = new BouncingEntity(playerIn.getLevel(), playerIn);

            thrown.setDeltaMovement(0 + playerIn.getDeltaMovement().x, -1 + playerIn.getDeltaMovement().y, 0 + playerIn.getDeltaMovement().z);
            if (!worldIn.isClientSide()) {
                playerIn.getLevel().addFreshEntity(thrown);
            }
            ((Player)playerIn).getAttribute(manatick.WARD).setBaseValue(((Player) playerIn).getAttributeBaseValue(manatick.WARD)-20);

            if (((Player)playerIn).getAttributes().getBaseValue(manatick.WARD)< -21) {

                playerIn.hurt(DamageSource.MAGIC, 2);
            }
        }
        return false;
    }
    public int getColor() {
        return 16735232;
    }

    @Override
    public int triggerCooldown() {
        return 20;
    }
}
