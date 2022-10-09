package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Objects;

public class Wardloop extends Item{
    public Wardloop(Properties p_41383_) {
        super(p_41383_);
    }

    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        Player player = p_41433_;
        ItemStack itemstack = p_41433_.getItemInHand(p_41434_);
        if (!p_41432_.isClientSide()) {
            CompoundTag nbt;
            if (itemstack.hasTag())
            {
                nbt = itemstack.getTag();
                nbt.remove("CustomModelData");
                return super.use(p_41432_, p_41433_, p_41434_);

            }
            else
            {
                nbt = itemstack.getOrCreateTag();
                nbt.putInt("CustomModelData", 1);
                return super.use(p_41432_, p_41433_, p_41434_);
            }
        }
        return super.use(p_41432_, p_41433_, p_41434_);
    }


    @Override
    public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) {
        //Player player = (Player) p_41406_;

        int ii = 0;
        if (p_41404_.getTag() != null && p_41406_ instanceof  Player player){
            if (p_41404_.getTag().get("CustomModelData") != null) {

                for (int i = 0; i <= 9; i++) {
                    if (player.getInventory().getItem(i).getItem() != this && ((Player)p_41406_).getInventory().getItem(i).getItem() instanceof Spell) {
                        if (player.getInventory().getItem(i).getTag() != null) {
                            if (Objects.requireNonNull(player.getInventory().getItem(i).getTag()).getInt("Triggerable") == 1) {

                                if (!player.getCooldowns().isOnCooldown(((Player)p_41406_).getInventory().getItem(i).getItem())) {
                                    if (((Spell) ((Player)p_41406_).getInventory().getItem(i).getItem()).trigger(p_41405_, (Player) p_41406_, (float) 1 / 8)){
                                        if (p_41404_.hasTag())
                                        {

                                            CompoundTag nbt;
                                            nbt = p_41404_.getTag();
                                            nbt.remove("CustomModelData");

                                        }
                                    }
                                }
                                ii++;
                            }
                        }

                    }
                }
                player.addEffect(new MobEffectInstance(StatusEffectsModded.SPELLWEAVING.get(), 5, ii-1));
            }
        }
        super.inventoryTick(p_41404_,p_41405_,p_41406_,p_41407_,p_41408_);


    }
    public void trytotrigger(ItemStack p_41404_, Level p_41405_, Entity p_41406_){

    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}