package com.cleannrooster.spellblademod.entity;

import com.cleannrooster.spellblademod.items.FriendshipBracelet;
import com.cleannrooster.spellblademod.items.ParticlePacket;
import com.cleannrooster.spellblademod.items.Spellblade;
import com.cleannrooster.spellblademod.setup.Messages;
import com.google.common.collect.ImmutableMultimap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3d;
import io.netty.buffer.Unpooled;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class sword1 extends ThrownTrident {
    public Player owner;
    public int number;
    public int mode;

    public sword1(EntityType<? extends sword1> p_37561_, Level p_37562_) {
        super(p_37561_, p_37562_);
        this.owner = null;
        this.setNoGravity(true);
        this.setNoPhysics(true);
    }

    public sword1(EntityType<? extends sword1> p_37561_, Level p_37562_, Player owner1) {
        super(p_37561_, p_37562_);
        this.owner = owner1;
        this.setNoGravity(true);
        this.setNoPhysics(true);
    }

    @Override
    public void tick() {
        if (this.owner != null) {
            if (this.owner.isAlive()) {
                if (this.owner.getUseItem().getItem() instanceof Spellblade) {
                    if (this.mode == 1) {
                        super.tick();
                        double number2 = 3.5;
                        float f7 = this.owner.getYRot();
                        float f8 = this.owner.getYRot() + 60;
                        float f9 = this.owner.getYRot() - 60;
                        float f = this.owner.getXRot();
                        float f1 = -Mth.sin(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                        float f2 = -Mth.sin(f * ((float) Math.PI / 180F));
                        float f3 = Mth.cos(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                        int i = (((1000 + this.tickCount + number) - (number * 1000 / 32))) % 1000;
                        if (this.tickCount < 0) {
                            i = 1000;
                        }
                        double[] indices = IntStream.rangeClosed(0, (int) ((1000)))
                                .mapToDouble(x -> x).toArray();

                        double phi = Math.acos(1 - 2 * indices[i] / 1000);
                        double theta = Math.PI * (1 + Math.pow(5, 0.5) * indices[i]);
                        double x = cos(theta) * sin(phi);
                        double y = -cos(phi);
                        double z = Math.sin(theta) * sin(phi);
                        this.setPosRaw(this.owner.getEyePosition().x + 4 * x + number2 * f1, this.owner.getEyePosition().y + 4 * y + number2 * f2, this.owner.getEyePosition().z + 4 * z + number2 * f3);
                        Vec3 vec = new Vec3(x, y, z);
                        this.setXRot((float) theta);
                        this.setYRot((float) phi);
                        if (this.level.isClientSide) {
                            this.yOld = this.getY();
                            this.xOld = this.getX();
                            this.zOld = this.getZ();
                        }
                        if (this.tickCount % 10 == 5) {
                            List<LivingEntity> entities = this.getLevel().getEntitiesOfClass(LivingEntity.class,this.getBoundingBox().inflate(2D),livingEntity -> {return FriendshipBracelet.PlayerFriendshipPredicate((Player) this.owner,livingEntity);});
                            entities.removeIf(livingEntity -> livingEntity == this.owner);
                            entities.removeIf(livingEntity -> !livingEntity.hasLineOfSight(this.owner));
                            if (!entities.isEmpty()) {
                                if (this.owner.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
                                    for(LivingEntity target : entities) {
                                        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(),"knockbackresist",1, AttributeModifier.Operation.ADDITION);

                                        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
                                        builder.put(Attributes.KNOCKBACK_RESISTANCE, modifier);
                                        target.getAttributes().addTransientAttributeModifiers(builder.build());
                                        target.hurt(new EntityDamageSource("spell", this.owner), (float) this.owner.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
                                        target.getAttributes().removeAttributeModifiers(builder.build());
                                        Random rand = new Random();
                                        Vec3 vec3 = target.getBoundingBox().getCenter().add(new Vec3(rand.nextDouble(-2, 2), rand.nextDouble(-2, 2), rand.nextDouble(-2, 2)));
                                        Vec3 vec31 = target.getBoundingBox().getCenter().subtract(vec3).normalize();
                                        if (level.getServer() != null) {
                                            int intarray[];
                                            intarray = new int[3];
                                            intarray[0] = (int) Math.round(target.getBoundingBox().getCenter().x);
                                            intarray[1] = (int) Math.round(target.getBoundingBox().getCenter().y);
                                            intarray[2] = (int) Math.round(target.getBoundingBox().getCenter().z);
                                            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer()).writeVarIntArray(intarray);
                                            Stream<ServerPlayer> serverplayers = level.getServer().getPlayerList().getPlayers().stream();
                                            ParticlePacket packet = new ParticlePacket(buf);
                                            for (ServerPlayer player2 : ((ServerLevel) level).getPlayers(serverPlayer -> serverPlayer.hasLineOfSight(this.owner))) {
                                                Messages.sendToPlayer(packet, (ServerPlayer) player2);
                                            }
                                        }

                                        this.getLevel().playSound((Player) null, target.getX(), target.getY(), target.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, this.owner.getSoundSource(), 1.0F, 1.0F);
                                        this.owner.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);
                                    }

                                }

                            }
                        }
                        return;
                    }
                    if (this.mode == 2) {
                        super.tick();
                        double number2 = 3.5;
                        float f7 = 360 + this.owner.getYRot() % 360;
                        float f8 = this.owner.getYRot() + 60;
                        float f9 = this.owner.getYRot() - 60;
                        float f = this.owner.getXRot();

                        double roll = (180+f)*((f7%180)/180);
                        float f1 = -Mth.sin(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                        float f2 = -Mth.sin(f * ((float) Math.PI / 180F));
                        float f3 = Mth.cos(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                        int i = (((1000 + this.tickCount + number) - (number * 1000 / 32))) % 1000;
                        if (this.tickCount < 0) {
                            i = 1000;
                        }
                        if (this.number < 8) {
                            final double theta = Math.toRadians(((double) i / 8) * 360d);
                            double x = Math.cos(theta);
                            double y = Math.sin(theta);
                            double z = 0;
                            Vector3d vec3d = rotate(x,y,z,-Math.toRadians(f7),Math.toRadians(f),0);
                            this.setPosRaw(this.owner.getEyePosition().x + 2 * vec3d.x + 2 * f1, this.owner.getEyePosition().y + 2 * vec3d.y + 2 * f2, this.owner.getEyePosition().z + 2 * vec3d.z + 2 * f3);
                            this.setXRot(-this.owner.getXRot());
                            this.setYRot(-this.owner.getYRot()-90);
                            if (this.level.isClientSide) {
                                this.yOld = this.getY();
                                this.xOld = this.getX();
                                this.zOld = this.getZ();
                            }
                        } else {
                            final double theta = Math.toRadians(((double) i / 24) * 360d);
                            double x = Math.cos(theta);
                            double y = Math.sin(theta);
                            double z = 0;
                            Vector3d vec3d = rotate(x,y,z,-Math.toRadians(f7),Math.toRadians(f),0);

                            this.setPosRaw(this.owner.getEyePosition().x + 4 * vec3d.x + 6 * f1, this.owner.getEyePosition().y + 4 * vec3d.y + 6 * f2, this.owner.getEyePosition().z + 4 * vec3d.z + 6 * f3);
                            this.setXRot(-this.owner.getXRot());
                            this.setYRot(-this.owner.getYRot()-90);
                            if (this.level.isClientSide) {
                                this.yOld = this.getY();
                                this.xOld = this.getX();
                                this.zOld = this.getZ();
                            }
                        }

                        if (this.tickCount % 10 == 5) {
                            List<LivingEntity> entities = this.getLevel().getEntitiesOfClass(LivingEntity.class,this.getBoundingBox().inflate(2D),livingEntity -> {return FriendshipBracelet.PlayerFriendshipPredicate((Player) this.owner,livingEntity);});
                            entities.removeIf(livingEntity -> livingEntity == this.owner);
                            entities.removeIf(livingEntity -> !livingEntity.hasLineOfSight(this.owner));

                            if (!entities.isEmpty()) {
                                if (this.owner.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
                                    for(LivingEntity target : entities) {
                                        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(),"knockbackresist",1, AttributeModifier.Operation.ADDITION);

                                        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
                                        builder.put(Attributes.KNOCKBACK_RESISTANCE, modifier);
                                        target.getAttributes().addTransientAttributeModifiers(builder.build());
                                        target.hurt(new EntityDamageSource("spell", this.owner), (float) this.owner.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
                                        target.getAttributes().removeAttributeModifiers(builder.build());

                                        Random rand = new Random();
                                        Vec3 vec3 = target.getBoundingBox().getCenter().add(new Vec3(rand.nextDouble(-2, 2), rand.nextDouble(-2, 2), rand.nextDouble(-2, 2)));
                                        Vec3 vec31 = target.getBoundingBox().getCenter().subtract(vec3).normalize();
                                        if (level.getServer() != null) {
                                            int intarray[];
                                            intarray = new int[3];
                                            intarray[0] = (int) Math.round(target.getBoundingBox().getCenter().x);
                                            intarray[1] = (int) Math.round(target.getBoundingBox().getCenter().y);
                                            intarray[2] = (int) Math.round(target.getBoundingBox().getCenter().z);
                                            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer()).writeVarIntArray(intarray);
                                            Stream<ServerPlayer> serverplayers = level.getServer().getPlayerList().getPlayers().stream();
                                            ParticlePacket packet = new ParticlePacket(buf);
                                            for (ServerPlayer player2 : ((ServerLevel) level).getPlayers(serverPlayer -> serverPlayer.hasLineOfSight(this.owner))) {
                                                Messages.sendToPlayer(packet, (ServerPlayer) player2);
                                            }
                                        }

                                        this.getLevel().playSound((Player) null, target.getX(), target.getY(), target.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, this.owner.getSoundSource(), 1.0F, 1.0F);
                                        this.owner.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);

                                    }
                                }

                            }
                        }
                        return;
                    }
                }
            }

        }
        if (!this.getLevel().isClientSide()) {
            this.discard();
        }
    }

    public Vector3d rotate(double x, double y, double z, double pitch, double roll, double yaw) {
        double cosa = Math.cos(yaw);
        double sina = Math.sin(yaw);

        double cosb = Math.cos(pitch);
        double sinb = Math.sin(pitch);
        double cosc = Math.cos(roll);
        double sinc = Math.sin(roll);

        double Axx = cosa * cosb;
        double Axy = cosa * sinb * sinc - sina * cosc;
        double Axz = cosa * sinb * cosc + sina * sinc;

        double Ayx = sina * cosb;
        double Ayy = sina * sinb * sinc + cosa * cosc;
        double Ayz = sina * sinb * cosc - cosa * sinc;

        double Azx = -sinb;
        double Azy = cosb * sinc;
        double Azz = cosb * cosc;

        Vector3d vec3 = new Vector3d(0,0,0);
        vec3.x = Axx * x + Axy * y + Axz * z;
        vec3.y = Ayx * x + Ayy * y + Ayz * z;
        vec3.z = Azx * x + Azy * y + Azz * z;
        return vec3;
    }
}
