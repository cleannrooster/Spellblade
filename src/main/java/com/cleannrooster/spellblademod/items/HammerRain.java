package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.entity.HammerEntity;
import com.cleannrooster.spellblademod.entity.ModEntities;
import com.cleannrooster.spellblademod.manasystem.data.PlayerMana;
import com.cleannrooster.spellblademod.manasystem.data.PlayerManaProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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

import java.util.Random;

public class HammerRain extends Spell {
    public HammerRain(Properties p_41383_) {
        super(p_41383_);
    }
    public InteractionResultHolder<ItemStack> use(Level p_43405_, Player p_43406_, InteractionHand p_43407_) {
        ItemStack itemstack = p_43406_.getItemInHand(p_43407_);

        PlayerMana playerMana = p_43406_.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
        if (!p_43405_.isClientSide() && p_43406_.isShiftKeyDown()) {
            CompoundTag nbt;
            if (itemstack.hasTag())
            {
                nbt = itemstack.getTag();
                nbt.remove("Triggerable");
                return InteractionResultHolder.success(itemstack);

            }
            else
            {
                nbt = itemstack.getOrCreateTag();
                nbt.putInt("Triggerable", 1);
                return InteractionResultHolder.success(itemstack);
            }
        }
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
        Random rand = new Random();

        HammerEntity hammer1 = new HammerEntity(ModEntities.TRIDENT.get(),player.getLevel());
        hammer1.triggered = true;
        hammer1.setPos(player.position().add(rand.nextDouble(-6,6), 6, rand.nextDouble(-6,6)));
        hammer1.setOwner(player);
        hammer1.pickup = AbstractArrow.Pickup.DISALLOWED;
        hammer1.secondary = true;
        HammerEntity hammer2 = new HammerEntity(ModEntities.TRIDENT.get(),player.getLevel());
        hammer2.triggered = true;
        hammer2.setPos(player.position().add(rand.nextDouble(-6,6), 6, rand.nextDouble(-6,6)));
        hammer2.setOwner(player);
        hammer2.pickup = AbstractArrow.Pickup.DISALLOWED;
        hammer2.secondary = true;
        HammerEntity hammer3 = new HammerEntity(ModEntities.TRIDENT.get(),player.getLevel());
        hammer3.triggered = true;
        hammer3.setPos(player.position().add(rand.nextDouble(-6,6), 6, rand.nextDouble(-6,6)));
        hammer3.setOwner(player);
        hammer3.pickup = AbstractArrow.Pickup.DISALLOWED;
        hammer3.secondary = true;
        HammerEntity hammer4 = new HammerEntity(ModEntities.TRIDENT.get(),player.getLevel());
        hammer4.triggered = true;
        hammer4.setPos(player.position().add(rand.nextDouble(-6,6), 6, rand.nextDouble(-6,6)));
        hammer4.setOwner(player);
        hammer4.pickup = AbstractArrow.Pickup.DISALLOWED;
        hammer4.secondary = true;
        hammer1.setXRot(90);
        hammer1.setYRot(0);
        hammer2.setXRot(90);
        hammer2.setYRot(0);
        hammer3.setXRot(90);
        hammer3.setYRot(0);
        hammer4.setXRot(90);
        hammer4.setYRot(0);
        Vec3 vec3 = player.getViewVector(1F);
        hammer1.shoot(0, -1, 0, 1.6F, 0);
        hammer2.shoot(0,-1,0, 1.6F, 0);
        hammer3.shoot(0,-1,0, 1.6F, 0);
        hammer4.shoot(0,-1,0, 1.6F, 0);

        level.addFreshEntity(hammer1);
        level.addFreshEntity(hammer2);
        level.addFreshEntity(hammer3);

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
