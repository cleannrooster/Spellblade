package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.setup.Messages;
import io.netty.buffer.Unpooled;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.stream.Stream;

public class VengefulStanceItem extends Guard{

    public VengefulStanceItem(Properties p_41383_) {
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
    @Override
    public void guard(Player player, Level level, LivingEntity entity) {
        level.playSound((Player) null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);
        player.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);
        entity.hurt(DamageSource.playerAttack(player), (float) player.getAttribute(Attributes.ATTACK_DAMAGE).getValue());

        if (level.getServer() != null) {
            int intarray[];
            intarray = new int[3];
            intarray[0] = (int) Math.round(entity.getBoundingBox().getCenter().x);
            intarray[1] = (int) Math.round(entity.getBoundingBox().getCenter().y);
            intarray[2] = (int) Math.round(entity.getBoundingBox().getCenter().z);
            FriendlyByteBuf buf =new FriendlyByteBuf(Unpooled.buffer()).writeVarIntArray(intarray);
            Stream<ServerPlayer> serverplayers = level.getServer().getPlayerList().getPlayers().stream();
            ParticlePacket packet = new ParticlePacket(buf);
            for (ServerPlayer player2 : ((ServerLevel) level).getPlayers(serverPlayer -> serverPlayer.hasLineOfSight(entity))){
                Messages.sendToPlayer(packet, (ServerPlayer) player2);
            }

        }
    }
}
