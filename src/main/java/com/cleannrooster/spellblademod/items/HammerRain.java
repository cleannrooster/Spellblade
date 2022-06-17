package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.entity.HammerEntity;
import com.cleannrooster.spellblademod.entity.ModEntities;
import com.cleannrooster.spellblademod.manasystem.data.PlayerMana;
import com.cleannrooster.spellblademod.manasystem.data.PlayerManaProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class HammerRain extends Spell {
    public HammerRain(Properties p_41383_) {
        super(p_41383_);
    }
    public InteractionResultHolder<ItemStack> use(Level p_43405_, Player p_43406_, InteractionHand p_43407_) {
        ItemStack itemstack = p_43406_.getItemInHand(p_43407_);

        PlayerMana playerMana = p_43406_.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
        if (playerMana.getMana() > 79) {
            p_43406_.getCooldowns().addCooldown(this,10);
            p_43406_.addEffect(new MobEffectInstance(StatusEffectsModded.WARD_DRAIN.get(), 5, 1));
            HammerEntity hammer1 = new HammerEntity(ModEntities.TRIDENT.get(),p_43406_.getLevel());
            hammer1.setPos(p_43406_.getEyePosition());
            hammer1.setOwner(p_43406_);
            hammer1.pickup = AbstractArrow.Pickup.DISALLOWED;
            hammer1.shootFromRotation(p_43406_, p_43406_.getXRot(), p_43406_.getYRot(), 0.0F, 1.6F, 1.0F);
            p_43405_.addFreshEntity(hammer1);
            return InteractionResultHolder.success(itemstack);

        }
        else{
            return InteractionResultHolder.fail(itemstack);
        }
    }
    public void trigger(Level level, Player player, float modifier){
        super.trigger(level, player, modifier);
        PlayerMana playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
        playerMana.addMana(-80*modifier);
        HammerEntity hammer1 = new HammerEntity(ModEntities.TRIDENT.get(),player.getLevel());
        hammer1.triggered = true;
        hammer1.setPos(player.getEyePosition());
        hammer1.setOwner(player);
        hammer1.pickup = AbstractArrow.Pickup.DISALLOWED;

        hammer1.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.6F, 1.0F);
        level.addFreshEntity(hammer1);
    }
}
