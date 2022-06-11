package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.manasystem.data.PlayerMana;
import com.cleannrooster.spellblademod.manasystem.data.PlayerManaProvider;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class VengefulStanceItem extends Wardlock{

    public VengefulStanceItem(Properties p_41383_) {
        super(p_41383_);
    }
    public InteractionResultHolder<ItemStack> use(Level p_43405_, Player p_43406_, InteractionHand p_43407_) {
        ItemStack itemstack = p_43406_.getItemInHand(p_43407_);
        if (p_43406_ instanceof Player) {

            Player player = (Player) p_43406_;
            PlayerMana playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);

            if (playerMana.getMana() > 39 && !player.hasEffect(StatusEffectsModded.WARD_DRAIN.get())) {
                if (player.getEffect(StatusEffectsModded.WARDLOCKED.get()) != null){
                    if (player.getEffect(StatusEffectsModded.WARDLOCKED.get()).getAmplifier() == 1){
                        return InteractionResultHolder.fail(itemstack);
                    }
                }
                player.addEffect(new MobEffectInstance(StatusEffectsModded.VENGEFUL_STANCE.get(), 160, 0));
                player.addEffect(new MobEffectInstance(StatusEffectsModded.WARDLOCKED.get(), 160, 1));
            }
            else{
                return InteractionResultHolder.fail(itemstack);
            }

        }
        return InteractionResultHolder.consume(itemstack);
    }
}
