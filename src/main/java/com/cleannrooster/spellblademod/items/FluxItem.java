package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.entity.FluxEntity;
import com.cleannrooster.spellblademod.entity.InvisiVex;
import com.cleannrooster.spellblademod.entity.ModEntities;
import com.cleannrooster.spellblademod.manasystem.manatick;
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
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.item.Items;
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
    public boolean targeted = true;
    public Item getIngredient1() {return Items.GLOW_BERRIES;};
    public Item getIngredient2() {return Items.GLASS_BOTTLE;};

    @Override
    public boolean isTargeted() {
        return true;
    }
    public int getColor() {
        return 5081087;
    }

    public net.minecraft.world.InteractionResult interactLivingEntity(ItemStack stack, net.minecraft.world.entity.player.Player playerIn, LivingEntity entity, net.minecraft.world.InteractionHand hand) {
        List<LivingEntity> list = new ArrayList<>();
        Player player = playerIn;
        if (entity instanceof ServerPlayer) {
            if (!(((ServerPlayer) entity).gameMode.getGameModeForPlayer() == GameType.SURVIVAL)) {
                return InteractionResult.FAIL;
            }
        }
        if (player.getCooldowns().isOnCooldown(this)){
            return InteractionResult.FAIL;
        }
        ((Player)player).getAttribute(manatick.WARD).setBaseValue(((Player) player).getAttributeBaseValue(manatick.WARD)-15);

        if (((Player)player).getAttributes().getBaseValue(manatick.WARD) < -21) {
            player.hurt(DamageSource.MAGIC,2);
        }
            FluxEntity flux = new FluxEntity(ModEntities.FLUX_ENTITY.get(), entity.getLevel());
            flux.target = entity;
            flux.setOwner(player);
            flux.list = list;
        flux.first = true;

        flux.setPos(player.getBoundingBox().getCenter());
            player.level.addFreshEntity(flux);
        return InteractionResult.sidedSuccess(player.getLevel().isClientSide());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        Player player = p_41433_;
        ItemStack itemstack = p_41433_.getItemInHand(p_41434_);
        if (player.isShiftKeyDown()) {
            CompoundTag nbt;
            if (itemstack.hasTag())
            {
                if(itemstack.getTag().get("Triggerable") != null) {
                    nbt = itemstack.getTag();
                    nbt.remove("Triggerable");
                    player.getInventory().setChanged();
                    return InteractionResultHolder.success(itemstack);

                }
                else{
                    nbt = itemstack.getOrCreateTag();
                    nbt.putInt("Triggerable", 1);
                    player.getInventory().setChanged();
                    return InteractionResultHolder.success(itemstack);

                }

            }
            else
            {
                nbt = itemstack.getOrCreateTag();
                nbt.putInt("Triggerable", 1);
                player.getInventory().setChanged();
                return InteractionResultHolder.success(itemstack);

            }

        }
        List<LivingEntity> list = new ArrayList<>();

        boolean flag1 = player.getInventory().contains(ModItems.FRIENDSHIP.get().getDefaultInstance());

        List<LivingEntity> entities = p_41432_.getEntitiesOfClass(LivingEntity.class,player.getBoundingBox().inflate(6D),livingEntity -> {return FriendshipBracelet.PlayerFriendshipPredicate((Player) player,livingEntity);});
        List<LivingEntity> validentities = new ArrayList<>();
        Object[] entitiesarray = entities.toArray();
        int entityamount = entitiesarray.length;
        for (int ii = 0; ii < entityamount; ii = ii + 1) {
            LivingEntity target = (LivingEntity) entities.get(ii);
            boolean flag2 = target.getClassification(false).isFriendly() || target instanceof Player || (target instanceof NeutralMob);
            if (target != player  && target.hasLineOfSight(player)) {
                validentities.add(target);
            }
        }

        validentities.removeIf(livingEntity -> {
            if (livingEntity instanceof InvisiVex vex) {
                return vex.owner2 == player;
            } else {
                return false;
            }
        });

        LivingEntity chained = player.getLevel().getNearestEntity(validentities, TargetingConditions.forNonCombat().ignoreLineOfSight(), player, player.getX(), player.getY(), player.getZ());
        if (chained != null) {
            ((Player)player).getAttribute(manatick.WARD).setBaseValue(((Player) player).getAttributeBaseValue(manatick.WARD)-15);

            if (((Player)player).getAttributes().getBaseValue(manatick.WARD) < -21) {
                player.hurt(DamageSource.MAGIC,2);
            }

            Random rand = new Random();
            FluxEntity flux = new FluxEntity(ModEntities.FLUX_ENTITY.get(), chained.getLevel());
            flux.target = chained;
            flux.setOwner(player);
            flux.list = list;
            flux.first = true;

            flux.setPos(player.getBoundingBox().getCenter().x + rand.nextDouble(-2,2),player.getBoundingBox().getCenter().y+ rand.nextDouble(0,2),player.getBoundingBox().getCenter().z+ rand.nextDouble(-2,2));
            player.level.addFreshEntity(flux);
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
        }
        return InteractionResultHolder.success(itemstack);

    }

    public static void FluxFlux(Player entity, LivingEntity target, Level level, List<LivingEntity> list, boolean first,FluxEntity thisFlux) {
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
        boolean flag2 = false;
        if (target.getClassification(false).isFriendly()|| target instanceof Player || (target instanceof NeutralMob)){
            flag2 = true;
        }
        if (true)/*&& !Objects.requireNonNullElse(this.blacklist, new ArrayList()).contains(target)*/ {
            {
                SoundEvent soundEvent = SoundEvents.AMETHYST_BLOCK_HIT;
                level.playSound(null, target,soundEvent,SoundSource.PLAYERS,1.0F, 0.5F + level.random.nextFloat() * 1.2F);
                list.add(target);


                if (target.hasEffect(StatusEffectsModded.FLUXED.get()) || first) {
                    List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,target.getBoundingBox().inflate(3D),livingEntity -> {return FriendshipBracelet.PlayerFriendshipPredicate((Player) entity,livingEntity);});
                    entities.removeIf(livingEntity -> {
                        if (livingEntity instanceof InvisiVex vex) {
                            return vex.owner2 == entity;
                        } else {
                            return false;
                        }
                    });
                    Object[] entitiesarray = entities.toArray();
                    int iii = 0;
                    for (int ii = 0; ii < entitiesarray.length; ii = ii + 1) {

                        LivingEntity target2 = (LivingEntity) entities.get(ii);
                        if (!list.contains(target2) && target2 != entity) {
                            FluxEntity flux = new FluxEntity(ModEntities.FLUX_ENTITY.get(), target2.getLevel());
                            flux.target = target2;
                            flux.setOwner(entity);
                            flux.list = list;

                            flux.setPos(target.getBoundingBox().getCenter());
                            if(!target2.hasEffect(StatusEffectsModded.FLUXED.get())){
                                flux.bool = true;
                            }
                            flux.setDeltaMovement(thisFlux.getDeltaMovement());
                            target.level.addFreshEntity(flux);

                            iii++;
                        }
                    }
                }
                    target.addEffect(new MobEffectInstance(StatusEffectsModded.FLUXED.get(), 120, 0, true, true));
                    target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 120, 0));
            }
        }
    }

    public boolean trigger(Level level, Player player, float modifier) {
        List<LivingEntity> list = new ArrayList<>();

        boolean flag1 = player.getInventory().contains(ModItems.FRIENDSHIP.get().getDefaultInstance());

        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,player.getBoundingBox().inflate(6D),livingEntity -> {return FriendshipBracelet.PlayerFriendshipPredicate((Player) player,livingEntity);});
        List<LivingEntity> validentities = new ArrayList<>();
        Object[] entitiesarray = entities.toArray();
        int entityamount = entitiesarray.length;
        for (int ii = 0; ii < entityamount; ii = ii + 1) {
            LivingEntity target = (LivingEntity) entities.get(ii);
            boolean flag2 = target.getClassification(false).isFriendly() || target instanceof Player || (target instanceof NeutralMob);
            if (target != player  && target.hasLineOfSight(player)) {
                validentities.add(target);
            }

        }
        validentities.removeIf(livingEntity -> {
            if (livingEntity instanceof InvisiVex vex) {
                return vex.owner2 == player;
            } else {
                return false;
            }
        });

        LivingEntity chained = player.getLevel().getNearestEntity(validentities, TargetingConditions.forNonCombat().ignoreLineOfSight(), player, player.getX(), player.getY(), player.getZ());
        if (chained != null) {
            ((Player)player).getAttribute(manatick.WARD).setBaseValue(((Player) player).getAttributeBaseValue(manatick.WARD)-15);

            if (((Player)player).getAttributes().getBaseValue(manatick.WARD) < -21) {
                player.hurt(DamageSource.MAGIC,2);
            }
            Random rand = new Random();
            FluxEntity flux = new FluxEntity(ModEntities.FLUX_ENTITY.get(), chained.getLevel());
            flux.target = chained;
            flux.setOwner(player);
            flux.list = list;
            flux.first = true;

            flux.setPos(player.getBoundingBox().getCenter().x + rand.nextDouble(-2,2),player.getBoundingBox().getCenter().y+ rand.nextDouble(0,2),player.getBoundingBox().getCenter().z+ rand.nextDouble(-2,2));
            player.level.addFreshEntity(flux);
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

    @Override
    public boolean triggeron(Level level, Player player, LivingEntity target, float modifier) {
        List<LivingEntity> list = new ArrayList<>();
        if (target instanceof ServerPlayer) {
            if (!(((ServerPlayer) target).gameMode.getGameModeForPlayer() == GameType.SURVIVAL)) {
                return false;
            }
        }
        if (player.getCooldowns().isOnCooldown(this)) {
            return false;
        }
        if(((Player)player).getAttributes().getBaseValue(manatick.WARD) < -1 && player.getHealth() <= 2)
        {
            return true;
        }
        ((Player)player).getAttribute(manatick.WARD).setBaseValue(((Player) player).getAttributeBaseValue(manatick.WARD)-20);


        FluxEntity flux = new FluxEntity(ModEntities.FLUX_ENTITY.get(), target.getLevel());
        flux.target = target;
        flux.setOwner(player);
        flux.list = list;
        flux.first = true;

        flux.setPos(player.getBoundingBox().getCenter());
        player.level.addFreshEntity(flux);
        if (((Player)player).getAttributes().getBaseValue(manatick.WARD) < -20 ) {
            player.hurt(DamageSource.MAGIC, 2);
            return true;
        }
        return false;
    }

    @Override
    public int triggerCooldown() {
        return 10;
    }
}

