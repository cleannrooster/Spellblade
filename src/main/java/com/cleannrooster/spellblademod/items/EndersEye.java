package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.effects.DamageSourceModded;
import com.cleannrooster.spellblademod.entity.EndersEyeEntity;
import com.cleannrooster.spellblademod.entity.HammerEntity;
import com.cleannrooster.spellblademod.entity.ModEntities;
import com.cleannrooster.spellblademod.manasystem.data.PlayerMana;
import com.cleannrooster.spellblademod.manasystem.data.PlayerManaProvider;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class EndersEye extends Spell {
    public EndersEye(Properties p_41383_) {
        super(p_41383_);
    }
    public InteractionResultHolder<ItemStack> use(Level p_43405_, Player p_43406_, InteractionHand p_43407_) {

        ItemStack itemstack = p_43406_.getItemInHand(p_43407_);
        Player player = p_43406_;
        PlayerMana playerMana = ((Player) p_43406_).getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);

        if (((Player) p_43406_).isShiftKeyDown()) {
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
        playerMana.addMana(-80);

        if (playerMana.getMana() < -21) {
            p_43406_.hurt(DamageSource.MAGIC,2);
        }
            ((Player) p_43406_).addEffect(new MobEffectInstance(StatusEffectsModded.ENDERSGAZE.get(), 120, 0));
        if (!((Player) p_43406_).isShiftKeyDown()) {

            ((Player) p_43406_).getCooldowns().addCooldown(this, 160);
        }

            EndersEyeEntity eye = new EndersEyeEntity(ModEntities.ENDERS_EYE.get(),p_43406_.getLevel());
            eye.setPos(((Player) p_43406_).getEyePosition());
            eye.setOwner((Player) p_43406_);
            eye.shootFromRotation(p_43406_,0,0,0,0,0);
            p_43405_.addFreshEntity(eye);
            return InteractionResultHolder.success(itemstack);

    }
    public boolean trigger(Level level, Player player, float modifier){
        PlayerMana playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);

        if(playerMana.getMana() < -1 && player.getHealth() <= 2)
        {
            return true;
        }
        EndersEyeEntity eye = new EndersEyeEntity(ModEntities.ENDERS_EYE.get(),player.getLevel());
        eye.setPos(player.getEyePosition());
        eye.setOwner(player);
        eye.shootFromRotation(player,0,0,0,0,0);
        level.addFreshEntity(eye);
        player.getCooldowns().addCooldown(this, 160);
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

}
