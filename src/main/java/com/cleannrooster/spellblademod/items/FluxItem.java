package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.manasystem.data.PlayerMana;
import com.cleannrooster.spellblademod.manasystem.data.PlayerManaProvider;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class FluxItem extends Spell {
    Random random = new Random();

    public FluxItem(Properties p_41383_) {
        super(p_41383_);
    }

    public net.minecraft.world.InteractionResult interactLivingEntity(ItemStack stack, net.minecraft.world.entity.player.Player playerIn, LivingEntity entity, net.minecraft.world.InteractionHand hand) {
        List<LivingEntity> list = new ArrayList<>();
        Player player = playerIn;
        PlayerMana playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
        if (entity instanceof ServerPlayer) {
            if (!(((ServerPlayer) entity).gameMode.getGameModeForPlayer() == GameType.SURVIVAL)) {
                return InteractionResult.FAIL;
            }
        }
        if (player.getCooldowns().isOnCooldown(this)){
            return InteractionResult.FAIL;
        }
        playerMana.addMana(-40);

        if (playerMana.getMana() < -21) {
            player.hurt(DamageSource.MAGIC,2);
        }
            FluxFlux(player, entity, player.level, list);
            player.getCooldowns().addCooldown(this, 20);
            return net.minecraft.world.InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        Player player = p_41433_;
        PlayerMana playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
        ItemStack itemstack = p_41433_.getItemInHand(p_41434_);
        if (player.isShiftKeyDown()) {
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
        else{
            return InteractionResultHolder.fail(itemstack);
        }
    }

    public void FluxFlux(Player entity, LivingEntity target, Level level, List<LivingEntity> list) {
        if (target == null) {
            return;
        }
        if (target instanceof ServerPlayer) {
            if (!(((ServerPlayer) target).gameMode.getGameModeForPlayer() == GameType.SURVIVAL)) {
                return;
            }
        }
        if (target == entity) {
            return;
        }
        boolean flag1 = false;
        if (entity.getInventory().contains(ModItems.FRIENDSHIP.get().getDefaultInstance())){
            flag1 = true;
        }
        boolean flag2 = false;
        if (target.getClassification(false).isFriendly()|| target instanceof Player || (target instanceof NeutralMob)){
            flag2 = true;
        }
        if (!(flag1 && flag2)/*&& !Objects.requireNonNullElse(this.blacklist, new ArrayList()).contains(target)*/) {
            {
                target.hurt(DamageSource.MAGIC, 1);
                list.add(target);
                int num_pts = 100;
                Vec3 targetcenter = target.getBoundingBox().getCenter();
                for (int i = 0; i <= num_pts; i = i + 1) {
                    double[] indices = IntStream.rangeClosed(0, (int) ((num_pts - 0) / 1))
                            .mapToDouble(x -> x * 1 + 0).toArray();

                    double phi = Math.acos(1 - 2 * indices[i] / num_pts);
                    double theta = Math.PI * (1 + Math.pow(5, 0.5) * indices[i]);
                    double x = cos(theta) * sin(phi);
                    double y = Math.sin(theta) * sin(phi);
                    double z = cos(phi);
                    level.addParticle(ParticleTypes.DRAGON_BREATH.getType(), true, targetcenter.x + random.nextDouble(-0.1, 0.1), targetcenter.y + random.nextDouble(-0.1, 0.1), targetcenter.z + random.nextDouble(-0.1, 0.1), x * 0.2, y * 0.2, z * 0.2);

                }

                if (target.hasEffect(StatusEffectsModded.FLUXED.get())) {
                    List entities = level.getEntitiesOfClass(LivingEntity.class, new AABB(target.getX() - 4, target.getY() + 0.5 - 4, target.getZ() - 4, target.getX() + 4, target.getY() + 4, target.getZ() + 4));
                    Object[] entitiesarray = entities.toArray();
                    int entityamount = entitiesarray.length;
                    for (int ii = 0; ii < entityamount; ii = ii + 1) {

                        LivingEntity target2 = (LivingEntity) entities.get(ii);
                        if (!list.contains(target2)) {
                            FluxFlux(entity, target2, level, list);
                        }
                    }
                }
                    target.addEffect(new MobEffectInstance(StatusEffectsModded.FLUXED.get(), 120, 0, true, true));
                    target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 120, 0));
            }
        }
    }

    public boolean trigger(Level level, Player player, float modifier) {
        PlayerMana playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
        List<LivingEntity> list = new ArrayList<>();

        boolean flag1 = false;
        if (player.getInventory().contains(ModItems.FRIENDSHIP.get().getDefaultInstance())){
            flag1 = true;
        }

        List entities = player.level.getEntitiesOfClass(LivingEntity.class, new AABB(player.getX() - 6, player.getY() + 0.5 - 6, player.getZ() - 6, player.getX() + 6, player.getY() + 6, player.getZ() + 6));
        List<LivingEntity> validentities = new ArrayList<>();
        Object[] entitiesarray = entities.toArray();
        int entityamount = entitiesarray.length;
        for (int ii = 0; ii < entityamount; ii = ii + 1) {
            LivingEntity target = (LivingEntity) entities.get(ii);
            boolean flag2 = false;
            if (target.getClassification(false).isFriendly()|| target instanceof Player || (target instanceof NeutralMob)){
                flag2 = true;
            }
            if (target != player && !(flag1 && flag2)&& target.hasLineOfSight(player)) {
                validentities.add(target);
            }

        }

        LivingEntity chained = player.getLevel().getNearestEntity(validentities, TargetingConditions.forNonCombat().ignoreLineOfSight(), player, player.getX(), player.getY(), player.getZ());
        if (chained != null) {
            if(playerMana.getMana() < -1 && player.getHealth() <= 2)
            {
                return true;
            }
            player.getCooldowns().addCooldown(this,10);
            int num_pts_line = 50;
            for (int iii = 0; iii < num_pts_line; iii++) {
                double X = player.getBoundingBox().getCenter().x + (chained.getBoundingBox().getCenter().x - player.getBoundingBox().getCenter().x) * ((double) iii / (num_pts_line));
                double Y = player.getBoundingBox().getCenter().y + (chained.getBoundingBox().getCenter().y - player.getBoundingBox().getCenter().y) * ((double) iii / (num_pts_line));
                double Z = player.getBoundingBox().getCenter().z + (chained.getBoundingBox().getCenter().z - player.getBoundingBox().getCenter().z) * ((double) iii / (num_pts_line));
                chained.level.addParticle(ParticleTypes.DRAGON_BREATH, X, Y, Z, 0, 0, 0);
            }
            /*ist entities2 = player.level.getEntitiesOfClass(LivingEntity.class, new AABB(chained.getX() - 4, chained.getY() + 0.5 - 4, chained.getZ() - 4, chained.getX() + 4, chained.getY() + 4, chained.getZ() + 4));
            List<LivingEntity> validentities2 = new ArrayList<>();
            Object[] entitiesarray2 = entities2.toArray();
            int entityamount2 = entitiesarray2.length;
            for (int ii = 0; ii < entityamount2; ii = ii + 1) {
                LivingEntity target = (LivingEntity) entities2.get(ii);
                if (target != player && target != living && target != chained) {
                    validentities2.add(target);
                }

            }*/
            FluxFlux(player, chained, player.level, list);
            player.getCooldowns().addCooldown(this, 10);
            if (playerMana.getMana() < -1 && player.getHealth() > 2) {

                player.invulnerableTime = 0;
                player.hurt(DamageSource.MAGIC, 2);
                player.invulnerableTime = 0;
            }
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

