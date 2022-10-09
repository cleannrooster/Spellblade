package com.cleannrooster.spellblademod.entity;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.effects.DamageSourceModded;
import com.cleannrooster.spellblademod.items.FriendshipBracelet;
import com.cleannrooster.spellblademod.items.ModItems;
import com.cleannrooster.spellblademod.items.ParticlePacket2;
import com.cleannrooster.spellblademod.setup.Messages;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mojang.math.Vector3f;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import org.antlr.v4.runtime.misc.MultiMap;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.IntStream;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class EndersEyeEntity extends Projectile implements ItemSupplier {

    public List<Entity> blacklist;
    public LivingEntity target;
    public Vec3 pos1;

    public EndersEyeEntity(EntityType<? extends EndersEyeEntity> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }
    @Override
    protected void defineSynchedData() {

    }
    double lastx = 0;
    double lasty = 0;
    double lastz = 0;
    @Override
    public void tick() {
        super.tick();
        this.noPhysics = true;
        if (this.tickCount > 160 && !this.getLevel().isClientSide()){
            this.discard();
        }
        if(this.getOwner()!=null){
            if (this.distanceTo(this.getOwner()) > 32 && !this.getLevel().isClientSide()){
                this.discard();
            }
            if(this.getOwner().isAlive() && this.getOwner() instanceof Player player) {

                if (this.lastx == 0 && this.lasty == 0 && this.lastz == 0) {
                    this.lastx = this.getOwner().getEyePosition().x;
                    this.lasty = this.getOwner().getEyePosition().y;
                    this.lastz = this.getOwner().getEyePosition().z;
                }


                if (target == null || !target.isAlive() || target.isDeadOrDying()) {
                    List<LivingEntity> entities = this.getLevel().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue() * 1.5), livingEntity -> {
                        return FriendshipBracelet.PlayerFriendshipPredicate((Player) this.getOwner(), livingEntity);
                    });
                    entities.removeIf(livingEntity -> !livingEntity.hasLineOfSight(this));
                    entities.removeIf(livingEntity -> livingEntity == this.getOwner() || livingEntity.isDeadOrDying());
                    entities.removeIf(livingEntity -> {
                        if (livingEntity instanceof InvisiVex vex) {
                            return vex.owner2 == this.getOwner();
                        } else {
                            return false;
                        }
                    });

                    if (!entities.isEmpty()) {
                        LivingEntity target1 = entities.get(0);
                        this.target = entities.get(0);
                        for (LivingEntity entity : entities) {
                            if (entity.distanceTo(this) < target1.distanceTo(this)) {
                                target1 = entity;
                                this.target = entity;
                            }
                        }
                    }
                }


                if (this.target == this.getOwner()) {
                    this.target = null;
                }
                if(this.target == null){
                    if(this.pos1 != null && !this.getLevel().isClientSide()) {
                        this.setPos(this.pos1.add(random.nextDouble(-0.5, 0.5), random.nextDouble(-0.5, 0.5), random.nextDouble(-0.5, 0.5)));

                    }
                }



                if (this.target != null) {
                    if (this.target.isAlive() && !this.target.isDeadOrDying()) {
                        if (!this.getLevel().isClientSide()) {
                            this.pos1 = this.target.position().add(new Vec3(0, 1.5, 0)).add(new Vec3(0, this.target.getBoundingBox().getYsize() / 2, 0));
                            this.setPos(this.target.position().add(new Vec3(0, 1.5, 0)).add(new Vec3(0, this.target.getBoundingBox().getYsize() / 2, 0)).add(random.nextDouble(-0.5, 0.5), random.nextDouble(-0.5, 0.5), random.nextDouble(-0.5, 0.5)));

                        }


                        if (tickCount % 10 == 5) {
                            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, this.target.getBoundingBox().inflate(3D), livingEntity -> {
                                return FriendshipBracelet.PlayerFriendshipPredicate((Player) this.getOwner(), livingEntity);
                            });
                            entities.removeIf(livingEntity -> {
                                if (livingEntity instanceof InvisiVex vex) {
                                    return vex.owner2 == this.getOwner();
                                } else {
                                    return false;
                                }
                            });
                            if (((Player) this.getOwner()).getLastHurtMob() != null) {
                                if (((Player) this.getOwner()).getLastHurtMob().isAlive() && this.getOwner().distanceTo(((Player) this.getOwner()).getLastHurtMob()) <= 8) {
                                    if (!entities.contains(((Player) this.getOwner()).getLastHurtMob())) {
                                        entities.add(((Player) this.getOwner()).getLastHurtMob());
                                    }
                                }
                            }
                            Object[] entitiesarray = entities.toArray();

                            int entityamount = entitiesarray.length;
                            for (int ii = 0; ii < entityamount; ii = ii + 1) {
                                LivingEntity target = (LivingEntity) entities.get(ii);
                                boolean flag2 = false;
                                if (target.getClassification(false).isFriendly() || target instanceof Player || (target instanceof NeutralMob)) {
                                    flag2 = true;
                                }
                                if (target != this.getOwner() && !Objects.requireNonNullElse(this.blacklist, new ArrayList()).contains(target) && !this.getLevel().isClientSide()) {
                                    if(this.getOwner() instanceof ServerPlayer serverPlayer){
                                        Messages.sendToPlayer(new ParticlePacket2(this.getX(),this.getY(),this.getZ(),this.target.getX(),this.target.getEyeY(),this.target.getZ()),serverPlayer);
                                    }
                                    target.invulnerableTime = 0;
                                    AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "knockbackresist", 1, AttributeModifier.Operation.ADDITION);
                                    ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
                                    builder.put(Attributes.KNOCKBACK_RESISTANCE, modifier);
                                    target.getAttributes().addTransientAttributeModifiers(builder.build());
                                    target.hurt(new EntityDamageSource("spell", (Player) this.getOwner()), 2);
                                    target.getAttributes().removeAttributeModifiers(builder.build());
                                    target.invulnerableTime = 0;

                                }
                            }
                        }
                    }
                    else{
                        if(this.pos1 != null&& !this.getLevel().isClientSide()) {
                            this.setPos(this.pos1.add(random.nextDouble(-0.5, 0.5), random.nextDouble(-0.5, 0.5), random.nextDouble(-0.5, 0.5)));

                        }
                        if (this.distanceToSqr(new Vec3(this.lastx, this.lasty, this.lastz)) > 1) {
                            for (int iii = 0; iii < 25; iii++) {
                                double X = this.position().x + (this.lastx - this.position().x) * ((double) iii / (25));
                                double Y = this.position().y + (this.lasty - this.position().y) * ((double) iii / (25));
                                double Z = this.position().z + (this.lastz - this.position().z) * ((double) iii / (25));
                                this.level.addParticle(ParticleTypes.DRAGON_BREATH, X, Y, Z, 0, 0, 0);
                            }
                        }
                        this.lastx = this.position().x;
                        this.lasty = this.position().y;
                        this.lastz = this.position().z;
                        return;
                    }


                }
                if (this.distanceToSqr(new Vec3(this.lastx, this.lasty, this.lastz)) > 1) {
                    for (int iii = 0; iii < 25; iii++) {
                        double X = this.position().x + (this.lastx - this.position().x) * ((double) iii / (25));
                        double Y = this.position().y + (this.lasty - this.position().y) * ((double) iii / (25));
                        double Z = this.position().z + (this.lastz - this.position().z) * ((double) iii / (25));
                        this.level.addParticle(ParticleTypes.DRAGON_BREATH, X, Y, Z, 0, 0, 0);
                    }
                }
                this.lastx = this.position().x;
                this.lasty = this.position().y;
                this.lastz = this.position().z;

            }
        }
        else {
            this.discard();
        }
    }

    @Override
    public ItemStack getItem() {
        return ModItems.ENDERSEYE.get().getDefaultInstance();
    }

}
