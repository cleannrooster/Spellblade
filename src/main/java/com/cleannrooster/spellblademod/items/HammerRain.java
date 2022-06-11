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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class HammerRain extends Item {
    public HammerRain(Properties p_41383_) {
        super(p_41383_);
    }
    public InteractionResultHolder<ItemStack> use(Level p_43405_, Player p_43406_, InteractionHand p_43407_) {
        ItemStack itemstack = p_43406_.getItemInHand(p_43407_);
        if (p_43406_ instanceof Player) {

            Player player = (Player) p_43406_;
            PlayerMana playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
            if (playerMana.getMana() > 79 && player.getEffect(StatusEffectsModded.WARD_DRAIN.get()) == null) {
                player.addEffect(new MobEffectInstance(StatusEffectsModded.WARD_DRAIN.get(), 5, 1));
                HammerEntity hammer1 = new HammerEntity(ModEntities.TRIDENT.get(),p_43405_);
                hammer1.setPos(player.getEyePosition());
                hammer1.setOwner(player);
                hammer1.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.6F, 1.0F);
                p_43405_.addFreshEntity(hammer1);

            }
            else{
                return InteractionResultHolder.fail(itemstack);
            }
        }
        return InteractionResultHolder.consume(itemstack);
    }
}
