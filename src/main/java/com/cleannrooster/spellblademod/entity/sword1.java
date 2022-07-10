package com.cleannrooster.spellblademod.entity;

import com.cleannrooster.spellblademod.items.ParticlePacket;
import com.cleannrooster.spellblademod.items.Spellblade;
import com.cleannrooster.spellblademod.setup.Messages;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3d;
import io.netty.buffer.Unpooled;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class sword1 extends ThrownTrident {
    public Player owner;
    public int number;
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
                    super.tick();
                    double number2 = 3.5;
                    float f7 = this.owner.getYRot();
                    float f8 = this.owner.getYRot() + 60;
                    float f9 = this.owner.getYRot() - 60;
                    float f = this.owner.getXRot();
                    float f1 = -Mth.sin(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                    float f2 = -Mth.sin(f * ((float) Math.PI / 180F));
                    float f3 = Mth.cos(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                    int i = (((1000 + this.tickCount) - (number * 1000 / 32))) % 1000;
                    double[] indices = IntStream.rangeClosed(0, (int) ((1000)))
                            .mapToDouble(x -> x).toArray();

                    double phi = Math.acos(1 - 2 * indices[i] / 1000);
                    double theta = Math.PI * (1 + Math.pow(5, 0.5) * indices[i]);
                    double x = cos(theta) * sin(phi);
                    double y = Math.sin(theta) * sin(phi);
                    double z = cos(phi);
                    this.setPosRaw(this.owner.getEyePosition().x + 4 * x + number2 * f1, this.owner.getEyePosition().y + 4 * y + number2 * f2, this.owner.getEyePosition().z + 4 * z + number2 * f3);
                    Vec3 vec = new Vec3(x, y, z);
                    this.setXRot((float) theta);
                    this.setYRot((float) phi);
                    if (this.level.isClientSide) {
                        this.yOld = this.getY();
                        this.xOld = this.getX();
                        this.zOld = this.getZ();
                    }
                    if (this.tickCount%10 == 5){
                        LivingEntity target = this.getLevel().getNearestEntity(LivingEntity.class, TargetingConditions.forNonCombat().ignoreLineOfSight(), this.owner, this.getX(), this.getY(), this.getZ(),this.getBoundingBox().inflate(2D));
                        if (target != null){
                            if (this.owner.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
                                target.hurt(DamageSource.playerAttack(this.owner), (float) this.owner.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
                                Random rand = new Random();
                                Vec3 vec3 = target.getBoundingBox().getCenter().add(new Vec3(rand.nextDouble(-2, 2), rand.nextDouble(-2, 2), rand.nextDouble(-2, 2)));
                                Vec3 vec31 = target.getBoundingBox().getCenter().subtract(vec3).normalize();
                                if (level.getServer() != null) {
                                    FriendlyByteBuf buf =new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(target.blockPosition());
                                    buf.writeInt(1);
                                    Stream<ServerPlayer> serverplayers = level.getServer().getPlayerList().getPlayers().stream();
                                    serverplayers.forEach(player2 ->
                                            Messages.sendToPlayer(new ParticlePacket(buf), (ServerPlayer) player2));
                                }

                                    this.getLevel().playSound((Player) null, target.getX(), target.getY(), target.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, this.owner.getSoundSource(), 1.0F, 1.0F);
                                this.owner.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);


                            }

                        }
                    }
                    return;
                }
            }

        }
            if (!this.getLevel().isClientSide()) {
                this.discard();
            }
    }
}
