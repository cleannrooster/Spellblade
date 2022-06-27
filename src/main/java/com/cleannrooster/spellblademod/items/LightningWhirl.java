package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.manasystem.data.PlayerMana;
import com.cleannrooster.spellblademod.manasystem.data.PlayerManaProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class LightningWhirl extends Spell{

    public LightningWhirl(Properties p_41383_) {
        super(p_41383_);
    }
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.BOW;
    }

    public int getUseDuration(ItemStack p_43419_) {
        return 72000;
    }

    public void releaseUsing(ItemStack p_41412_, Level p_41413_, LivingEntity p_41414_, int p_41415_) {

/*
        LivingEntity player = p_41414_;
        float f7 = player.getYRot();
        float f = player.getXRot();
        float f1 = -Mth.sin(f7 * ((float)Math.PI / 180F)) * Mth.cos(f * ((float)Math.PI / 180F));
        float f2 = -Mth.sin(f * ((float)Math.PI / 180F));
        float f3 = Mth.cos(f7 * ((float)Math.PI / 180F)) * Mth.cos(f * ((float)Math.PI / 180F));
        float f4 = Mth.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
        float f5 = 3.0F * ((1.0F + (float)4) / 4.0F);
        f1 *= f5 / f4;
        f2 *= f5 / f4;
        f3 *= f5 / f4;
        p_41413_.addFreshEntity(projectile);
        projectile.push((double)f1, (double)f2, (double)f3);
        projectile.setPose(Pose.SPIN_ATTACK);
        SoundEvent soundevent;
        if (4 >= 3) {
            soundevent = SoundEvents.TRIDENT_RIPTIDE_3;
        } else if (4 == 2) {
            soundevent = SoundEvents.TRIDENT_RIPTIDE_2;
        } else {
            soundevent = SoundEvents.TRIDENT_RIPTIDE_1;
        }

        p_41413_.playSound((Player)null, player, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
*/

    }
    public InteractionResultHolder<ItemStack> use(Level p_43405_, Player p_43406_, InteractionHand p_43407_) {
        ItemStack itemstack = p_43406_.getItemInHand(p_43407_);
        if (p_43406_.isShiftKeyDown()) {
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
        if (!p_43406_.getMainHandItem().isEdible()) {
            PlayerMana playerMana = p_43406_.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
            playerMana.addMana(-40);

            if (playerMana.getMana() < -21) {
                p_43406_.hurt(DamageSource.MAGIC,2);
            }
                p_43406_.getCooldowns().addCooldown(this,20);

                float f7 = p_43406_.getYRot();
                float f = p_43406_.getXRot();
                float f1 = -Mth.sin(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                float f2 = -Mth.sin(f * ((float) Math.PI / 180F));
                float f3 = Mth.cos(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                float f4 = Mth.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
                float f5 = 3.0F * ((1.0F + (float) 4) / 4.0F);
                f1 *= f5 / f4;
                f2 *= f5 / f4;
                f3 *= f5 / f4;
                p_43406_.push((double) f1, (double) f2, (double) f3);
                p_43406_.startAutoSpinAttack(20);
                if (p_43406_.isOnGround()) {
                    float f6 = 1.1999999F;
                    p_43406_.move(MoverType.SELF, new Vec3(0.0D, (double) 1.1999999F, 0.0D));
                }
                SoundEvent soundevent;
            soundevent = SoundEvents.TRIDENT_RIPTIDE_3;

            p_43405_.playSound((Player) null, p_43406_, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
                return InteractionResultHolder.consume(itemstack);
        }
        return InteractionResultHolder.fail(itemstack);
    }

    public boolean trigger(Level level, Player player, float modifier) {

        PlayerMana playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
        if(playerMana.getMana() < -1 && player.getHealth() <= 2)
        {
            return true;
        }
        player.getCooldowns().addCooldown(this, 20);
        float f7 = player.getYRot();
        float f = player.getXRot();
        float f1 = -Mth.sin(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
        float f2 = -Mth.sin(f * ((float) Math.PI / 180F));
        float f3 = Mth.cos(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
        float f4 = Mth.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
        float f5 = 3.0F * ((1.0F + (float) 4) / 4.0F);
        f1 *= f5 / f4;
        f2 *= f5 / f4;
        f3 *= f5 / f4;
        player.push((double) f1, (double) f2, (double) f3);
        player.startAutoSpinAttack(20);
        if (player.isOnGround()) {
            float f6 = 1.1999999F;
            player.move(MoverType.SELF, new Vec3(0.0D, (double) 1.1999999F, 0.0D));
        }
        SoundEvent soundevent;
        soundevent = SoundEvents.TRIDENT_RIPTIDE_3;

        level.playSound((Player) null, player, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
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
