package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.manasystem.manatick;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
    public Item getIngredient1() {return Items.PRISMARINE_CRYSTALS;};
    public Item getIngredient2() {return ModItems.FLUXITEM.get();};
    public boolean isTriggerable() {return false;}


    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        TextComponent text = new TextComponent("Not Triggerable");
                p_41423_.add(text);


        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
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
    @Override
    public boolean overrideOtherStackedOnMe(ItemStack thisStack, ItemStack onStack, Slot slot, ClickAction clickAction, Player player, SlotAccess slotAccess) {
        return false;
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
            ((Player)p_43406_).getAttribute(manatick.WARD).setBaseValue(((Player) p_43406_).getAttributeBaseValue(manatick.WARD)-20);

            if (((Player)p_43406_).getAttributes().getBaseValue(manatick.WARD) < -21) {
                p_43406_.hurt(DamageSource.MAGIC,2);
            }
                p_43406_.getCooldowns().addCooldown(this,40);

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
    public int getColor() {
        return 10978560;
    }

    @Override
    public int triggerCooldown() {
        return 40;
    }

    /*public boolean trigger(Level level, Player player, float modifier) {
*//*
        if(((Player)player).getAttributes().getBaseValue(manatick.WARD) < -1 && player.getHealth() <= 2)
        {
            return true;
        }
        player.getCooldowns().addCooldown(this, 40);
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
        ((Player)player).getAttribute(manatick.WARD).setBaseValue(((Player) player).getAttributeBaseValue(manatick.WARD)-20);

        if (((Player)player).getAttributes().getBaseValue(manatick.WARD) < -21) {
            player.hurt(DamageSource.MAGIC,2);
        }
        return false;*//*
    }*/
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
