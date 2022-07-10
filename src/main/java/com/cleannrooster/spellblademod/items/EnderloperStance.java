package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class EnderloperStance extends Guard{
    public EnderloperStance(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (player.isShiftKeyDown()) {
            CompoundTag nbt;
            if (itemstack.hasTag()) {
                if (itemstack.getTag().get("Triggerable") != null) {
                    nbt = itemstack.getTag();
                    nbt.remove("Triggerable");
                } else {
                    nbt = itemstack.getOrCreateTag();
                    nbt.putInt("Triggerable", 1);
                }

            } else {
                nbt = itemstack.getOrCreateTag();
                nbt.putInt("Triggerable", 1);
            }
            return InteractionResultHolder.success(itemstack);

        }
        return InteractionResultHolder.fail(itemstack);
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
    private boolean teleport(double p_32544_, double p_32545_, double p_32546_, Level level, Player player) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(p_32544_, p_32545_, p_32546_);

        while(blockpos$mutableblockpos.getY() > level.getMinBuildHeight() && !level.getBlockState(blockpos$mutableblockpos).getMaterial().blocksMotion()) {
            blockpos$mutableblockpos.move(Direction.DOWN);
        }

        BlockState blockstate = level.getBlockState(blockpos$mutableblockpos);
        boolean flag = blockstate.getMaterial().blocksMotion();
        boolean flag1 = blockstate.getFluidState().is(FluidTags.WATER);
        if (flag && !flag1) {
            net.minecraftforge.event.entity.EntityTeleportEvent.EnderEntity event = net.minecraftforge.event.ForgeEventFactory.onEnderTeleport(player, p_32544_, p_32545_, p_32546_);
            /*if (event.isCanceled()) return false;*/
            boolean flag2 = false;
             flag2 = player.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);

            if (flag2) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void guard(Player player, Level level, LivingEntity entity) {
        Random random = new Random();
        Vec3 vec3 = new Vec3(player.getX() - entity.getX(), player.getY(0.5D) - entity.getEyeY(), player.getZ() - entity.getZ());
        vec3 = vec3.normalize();
        double d0 = 16.0D;
        double d1 = player.getX() + (random.nextDouble() - 0.5D) * 8.0D - vec3.x * 4.0D;
        double d2 = player.getY() + (double)(random.nextInt(8) - 4) - vec3.y * 4.0D;
        double d3 = player.getZ() + (random.nextDouble() - 0.5D) * 8.0D - vec3.z * 4.0D;
        if (!level.isClientSide() && player.distanceTo(entity) <= 4.0D) {
            entity.addEffect(new MobEffectInstance(StatusEffectsModded.FLUXED.get(),80,0));
            entity.addEffect(new MobEffectInstance(MobEffects.GLOWING,80,0));
            if (!this.teleport(d1, d2, d3, entity.getLevel(), player)){
                player.randomTeleport(entity.getX(), entity.getY(), entity.getZ(),true);
            };
            if (!player.isSilent()) {
                level.playSound((Player)null, player.xo, player.yo, player.zo, SoundEvents.ENDERMAN_TELEPORT, player.getSoundSource(), 1.0F, 1.0F);
                player.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }
        }
    }

}
