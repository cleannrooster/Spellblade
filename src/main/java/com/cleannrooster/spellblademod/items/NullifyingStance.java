package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.manasystem.network.ClickSpell;
import com.cleannrooster.spellblademod.setup.Messages;
import io.netty.buffer.Unpooled;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class NullifyingStance extends Guard{
    public NullifyingStance(Properties p_41383_) {
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
    public void guardtick(Player player, Level level) {
        List entities = level.getEntitiesOfClass(Projectile.class, new AABB(player.getX() - 6, player.getY() - 6, player.getZ() - 6, player.getX() + 6, player.getY() + 6, player.getZ() + 6));
        Object[] entitiesarray = entities.toArray();
        int entityamount = entitiesarray.length;
        for (int ii = 0; ii < entityamount; ii = ii + 1) {
            Projectile projectile = (Projectile) entities.get(ii);
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(projectile.blockPosition());
            buf.writeInt(0);
            if (level.getServer() != null) {
                Stream<ServerPlayer> serverplayers = level.getServer().getPlayerList().getPlayers().stream();
                serverplayers.forEach(player2 ->
                        Messages.sendToPlayer(new ParticlePacket(buf), (ServerPlayer) player2));

                level.playSound((Player) null, projectile.getX(), projectile.getY(), projectile.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);
                player.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);
                projectile.discard();
            }
        }
    }
}
