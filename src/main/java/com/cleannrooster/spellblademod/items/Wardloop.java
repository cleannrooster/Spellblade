package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.manasystem.data.PlayerMana;
import com.cleannrooster.spellblademod.manasystem.data.PlayerManaProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Wardloop extends Item{
    public Wardloop(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        Player player = p_41433_;
        PlayerMana playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
        ItemStack itemstack = p_41433_.getItemInHand(p_41434_);
        if (!p_41432_.isClientSide()) {
            CompoundTag nbt;
            if (itemstack.hasTag())
            {
                nbt = itemstack.getTag();
                nbt.remove("CustomModelData");
                return InteractionResultHolder.success(itemstack);

            }
            else
            {
                nbt = itemstack.getOrCreateTag();
                nbt.putInt("CustomModelData", 1);
                return InteractionResultHolder.success(itemstack);
            }
        }
        return InteractionResultHolder.sidedSuccess(itemstack,p_41432_.isClientSide());
    }
    public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) {
        if (p_41406_ instanceof Player) {
            int ii = 0;
            Player player = (Player) p_41406_;
            PlayerMana playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
            if (p_41404_.getTag() != null){
                if (p_41404_.getTag().get("CustomModelData") != null) {
                    for (int i = 0; i <= 9; i++) {
                        if (player.getInventory().getItem(i).getItem() != this && player.getInventory().getItem(i).getItem() instanceof Spell) {
                            if (player.getInventory().getItem(i).hasTag()) {
                                if (player.getInventory().getItem(i).getTag().getInt("Triggerable") == 1) {
                                    if (!player.getCooldowns().isOnCooldown(player.getInventory().getItem(i).getItem())) {
                                        if (playerMana.getMana() < -2) {
                                            player.invulnerableTime = 0;
                                            player.hurt(DamageSource.MAGIC, 1);
                                            player.invulnerableTime = 0;
                                        }
                                        ((Spell) player.getInventory().getItem(i).getItem()).trigger(p_41405_, player, (float) 1 / 8);
                                        player.getCooldowns().addCooldown(player.getInventory().getItem(i).getItem(), 10);
                                    }
                                    ii++;
                                }
                            }

                        }
                    }
                    player.addEffect(new MobEffectInstance(StatusEffectsModded.SPELLWEAVING.get(), 5, ii-1));
                }
            }
        }
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}