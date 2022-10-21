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
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
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
    public int number;
    public int mode;
    public boolean guard = false;
    public boolean firstTick = true;


    public sword1(EntityType<? extends sword1> p_37561_, Level p_37562_) {
        super(p_37561_, p_37562_);
        this.setNoGravity(true);
        this.setNoPhysics(true);

        this.setInvisible(true);

    }

    @Override
    protected boolean tryPickup(Player p_150196_) {
        return false;
    }

    public sword1(EntityType<? extends sword1> p_37561_, Level p_37562_, Player owner1) {
        super(p_37561_, p_37562_);
        Vec3 vec3 = owner1.getViewVector(0);
        double d0 = vec3.horizontalDistance();
        this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
        this.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
        this.xOld = this.getX();
        this.yOld = this.getY();
        this.zOld = this.getZ();
        this.setOwner(owner1);
        this.setNoGravity(true);
        this.setNoPhysics(true);

        this.setInvisible(true);
    }


    @Override
    protected void onHit(HitResult p_37260_) {
        if(this.mode == 3) {
            super.onHit(p_37260_);
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult p_37573_) {
        if(this.mode == 3 && this.getOwner() instanceof Player && p_37573_.getEntity() instanceof LivingEntity && p_37573_.getEntity() != this.getOwner()){
        p_37573_.getEntity().hurt(EntityDamageSource.playerAttack((Player) this.getOwner()), (float) Math.max(6,(float) ((Player) this.getOwner()).getAttribute(Attributes.ATTACK_DAMAGE).getValue()));

        }
    }

    @Override
    public void tick() {
        if (this.getOwner() != null) {
            if (this.getOwner().isAlive()&& this.getOwner() instanceof Player) {
                if(this.mode == 3){
                    this.setInvisible(false);
                    if(this.tickCount > 10 && !this.getLevel().isClientSide()){
                        this.discard();
                        return;
                    }
                    Vec3 pos = this.position();
                    float xrot = this.getXRot();
                    float yrot = this.getYRot();
                    Vec3 vec3 = Vec3.ZERO;
                    List<LivingEntity> entities = this.getLevel().getEntitiesOfClass(LivingEntity.class,this.getBoundingBox().inflate(0.25D),livingEntity -> {if(livingEntity != null) return FriendshipBracelet.PlayerFriendshipPredicate((Player) this.getOwner(), (LivingEntity) livingEntity); else return true;});
                    entities.removeIf(livingEntity -> livingEntity == this.getOwner());
                    entities.removeIf(livingEntity -> !((Player)this.getOwner()).hasLineOfSight(livingEntity));
                    entities.removeIf(livingEntity -> livingEntity == this.getOwner());
                    LivingEntity entity = this.level.getNearestEntity(entities,TargetingConditions.forCombat(),null,this.getX(),this.getY(),this.getZ());
                    if(this.tickCount == 1){
                        SoundEvent event = SoundEvents.PLAYER_ATTACK_SWEEP;
                        this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 0.25F, 1.0F);
                        if(entity != null){
                            entity.hurt(EntityDamageSource.playerAttack((Player) this.getOwner()), (float) Math.max(6,(float) ((Player) this.getOwner()).getAttribute(Attributes.ATTACK_DAMAGE).getValue()));
                            this.discard();
                        }
                        super.tick();
                        return;

                    }
                    if(this.tickCount > 0) {
                        this.setNoPhysics(false);
                        this.setNoGravity(true);
                        if(entity != null){
                            entity.hurt(EntityDamageSource.playerAttack((Player) this.getOwner()), (float) Math.max(6,(float) ((Player) this.getOwner()).getAttribute(Attributes.ATTACK_DAMAGE).getValue()));
                            this.discard();
                        }
                        super.tick();
                        return;
                    }
                    return;

                }
                if (this.tickCount >= 32 && !this.getLevel().isClientSide()){
                    this.discard();
                }
                    if (this.mode == 1) {

                        double number2 = 3.5;
                        float f7 = this.getOwner().getYRot()% 360;
                        float f8 = this.getOwner().getYRot() + 60;
                        float f9 = this.getOwner().getYRot() - 60;
                        float f = this.getOwner().getXRot();
                        float f1 = -Mth.sin(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                        float f2 = -Mth.sin(f * ((float) Math.PI / 180F));
                        float f3 = Mth.cos(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                        int i = (((1000 + this.tickCount + number) - (number * 1000 / 32))) % 1000;
                        if (this.tickCount <= 0) {
                            i = 1000;
                            this.setInvisible(true);
                        }
                        else{
                            this.setInvisible(false);
                        }
                        double[] indices = IntStream.rangeClosed(0, (int) ((1000)))
                                .mapToDouble(x -> x).toArray();

                        double phi = Math.acos(1 - 2 * indices[i] / 1000);
                        double theta = Math.PI * (1 + Math.pow(5, 0.5) * indices[i]);
                        if(((phi < Math.toRadians(60) /*|| phi > Math.toRadians(240)*/ )   || (theta < Math.toRadians(60)/* || theta > Math.toRadians(240)*/)) && this.guard){
                            phi = Math.toRadians(180);
                            theta = Math.toRadians(180);
                        }
                        if(phi == Math.toRadians(180)  && theta == Math.toRadians(180)){
                            this.setInvisible(true);
                        }
                        double x = cos(theta) * sin(phi);
                        double y = -cos(phi);
                        double z = Math.sin(theta) * sin(phi);
                        Vec3 vec3d = rotate(x,y,z,-Math.toRadians(f7),Math.toRadians(f+90),0);

                        this.setPos(this.getOwner().getEyePosition().x + 4 * vec3d.x + number2 * f1, this.getOwner().getEyePosition().y + 4 * vec3d.y + number2 * f2, this.getOwner().getEyePosition().z + 4 * vec3d.z + number2 * f3);
                        Vec3 vec = new Vec3(x, y, z);
                        double d0 = vec3d.horizontalDistance();
                        this.setYRot((float)(Mth.atan2(vec3d.x, vec3d.z) * (double)(180F / (float)Math.PI))+45);
                        this.setXRot((float)(Mth.atan2(vec3d.y, d0) * (double)(180F / (float)Math.PI))+45);

                        //this.setXRot((float) (f+phi*180/Math.PI));
                        //this.setYRot((float) (f7+theta*180/Math.PI));
                        /*if(this.tickCount < 0){
                            this.setYRot((float)(Mth.atan2(vec3d.x, vec3d.z) * (double)(180F / (float)Math.PI))+45);
                            this.setXRot((float)(Mth.atan2(vec3d.y, d0) * (double)(180F / (float)Math.PI))+45);
                        }*/

                        super.tick();
                        if (this.tickCount % 10 == 5) {
                            List<Entity> entities = this.getLevel().getEntitiesOfClass(Entity.class,this.getBoundingBox().inflate(2D),livingEntity -> {if(livingEntity instanceof LivingEntity) return FriendshipBracelet.PlayerFriendshipPredicate((Player) this.getOwner(), (LivingEntity) livingEntity); else return true;});
                            entities.removeIf(livingEntity -> livingEntity == this.getOwner());
                            entities.removeIf(livingEntity -> !((Player)this.getOwner()).hasLineOfSight(livingEntity));
                            if (!entities.isEmpty()) {
                                if (((Player)this.getOwner()).getAttribute(Attributes.ATTACK_DAMAGE) != null) {
                                    for(Entity target : entities) {
                                        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(),"knockbackresist",1, AttributeModifier.Operation.ADDITION);
                                        if(target instanceof LivingEntity living && !this.guard) {
                                            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
                                            builder.put(Attributes.KNOCKBACK_RESISTANCE, modifier);

                                            living.getAttributes().addTransientAttributeModifiers(builder.build());
                                            living.hurt(new EntityDamageSource("spell", this.getOwner()), Math.max(6,(float) ((Player) this.getOwner()).getAttribute(Attributes.ATTACK_DAMAGE).getValue()));

                                            if (level.getServer() != null) {
                                                int intarray[];
                                                intarray = new int[3];
                                                intarray[0] = (int) Math.round(target.getBoundingBox().getCenter().x);
                                                intarray[1] = (int) Math.round(target.getBoundingBox().getCenter().y);
                                                intarray[2] = (int) Math.round(target.getBoundingBox().getCenter().z);
                                                FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer()).writeVarIntArray(intarray);
                                                Stream<ServerPlayer> serverplayers = level.getServer().getPlayerList().getPlayers().stream();
                                                ParticlePacket packet = new ParticlePacket(buf);
                                                for (ServerPlayer player2 : ((ServerLevel) level).getPlayers(serverPlayer -> serverPlayer.hasLineOfSight(this.getOwner()))) {
                                                    Messages.sendToPlayer(packet, (ServerPlayer) player2);
                                                }
                                            }
                                            living.getAttributes().removeAttributeModifiers(builder.build());
                                            this.getLevel().playSound((Player) null, target.getX(), target.getY(), target.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, this.getOwner().getSoundSource(), 0.25F, 1.0F);
                                            this.getOwner().playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 0.25F, 1.0F);

                                        }
                                        else if(target instanceof Projectile && !(target instanceof sword1) && this.guard){
                                            target.discard();
                                            this.getLevel().playSound((Player) null, target.getX(), target.getY(), target.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, this.getOwner().getSoundSource(), 0.25F, 1.0F);
                                            this.getOwner().playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 0.25F, 1.0F);
                                            if (level.getServer() != null) {
                                                int intarray[];
                                                intarray = new int[3];
                                                intarray[0] = (int) Math.round(target.getBoundingBox().getCenter().x);
                                                intarray[1] = (int) Math.round(target.getBoundingBox().getCenter().y);
                                                intarray[2] = (int) Math.round(target.getBoundingBox().getCenter().z);
                                                FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer()).writeVarIntArray(intarray);
                                                Stream<ServerPlayer> serverplayers = level.getServer().getPlayerList().getPlayers().stream();
                                                ParticlePacket packet = new ParticlePacket(buf);
                                                for (ServerPlayer player2 : ((ServerLevel) level).getPlayers(serverPlayer -> serverPlayer.hasLineOfSight(this.getOwner()))) {
                                                    Messages.sendToPlayer(packet, (ServerPlayer) player2);
                                                }
                                            }
                                        }
                                        Random rand = new Random();
                                        Vec3 vec3 = target.getBoundingBox().getCenter().add(new Vec3(rand.nextDouble(-2, 2), rand.nextDouble(-2, 2), rand.nextDouble(-2, 2)));
                                        Vec3 vec31 = target.getBoundingBox().getCenter().subtract(vec3).normalize();


                                    }

                                }

                            }
                        }
                        return;
                    }
                    if (this.mode == 2) {

                        this.tickCount = this.tickCount+1;
                        this.firstTick = false;
                        double number2 = 3.5;
                        float f7 = 360 + this.getOwner().getYRot() % 360;
                        float f8 = this.getOwner().getYRot() + 60;
                        float f9 = this.getOwner().getYRot() - 60;
                        float f = this.getOwner().getXRot();

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
                            Vec3 vec3d = rotate(x,y,z,-Math.toRadians(f7),Math.toRadians(f),0);
                            this.setPos(this.getOwner().getEyePosition().x + 2 * vec3d.x + 2 * f1, this.getOwner().getEyePosition().y + 2 * vec3d.y + 2 * f2, this.getOwner().getEyePosition().z + 2 * vec3d.z + 2 * f3);
                            Vec3 vec3 = this.getOwner().getViewVector(0);
                            double d0 = vec3.horizontalDistance();
                            this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
                            this.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
                            this.yRotO = this.getYRot();
                            this.xRotO = this.getXRot();

                        } else {
                            final double theta = Math.toRadians(((double) i / 24) * 360d);
                            double x = Math.cos(theta);
                            double y = Math.sin(theta);
                            double z = 0;
                            Vec3 vec3d = rotate(x,y,z,-Math.toRadians(f7),Math.toRadians(f),0);

                            this.setPos(this.getOwner().getEyePosition().x + 4 * vec3d.x + 6 * f1, this.getOwner().getEyePosition().y + 4 * vec3d.y + 6 * f2, this.getOwner().getEyePosition().z + 4 * vec3d.z + 6 * f3);
                            Vec3 vec3 = this.getOwner().getViewVector(0);
                            double d0 = vec3.horizontalDistance();
                            this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
                            this.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
                            this.yRotO = this.getYRot();
                            this.xRotO = this.getXRot();

                        }

                        if (this.tickCount % 10 == 5) {
                            List<LivingEntity> entities = this.getLevel().getEntitiesOfClass(LivingEntity.class,this.getBoundingBox().inflate(2D),livingEntity -> {return FriendshipBracelet.PlayerFriendshipPredicate((Player) this.getOwner(),livingEntity);});
                            entities.removeIf(livingEntity -> livingEntity == this.getOwner());
                            entities.removeIf(livingEntity -> !livingEntity.hasLineOfSight(this.getOwner()));

                            if (!entities.isEmpty()) {
                                if (((Player)this.getOwner()).getAttribute(Attributes.ATTACK_DAMAGE) != null) {
                                    for(LivingEntity target : entities) {
                                        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(),"knockbackresist",1, AttributeModifier.Operation.ADDITION);

                                        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
                                        builder.put(Attributes.KNOCKBACK_RESISTANCE, modifier);
                                        target.getAttributes().addTransientAttributeModifiers(builder.build());
                                        target.hurt(new EntityDamageSource("spell", this.getOwner()), (float) Math.max(6,(float) ((Player) this.getOwner()).getAttribute(Attributes.ATTACK_DAMAGE).getValue()));
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
                                            for (ServerPlayer player2 : ((ServerLevel) level).getPlayers(serverPlayer -> serverPlayer.hasLineOfSight(this.getOwner()))) {
                                                Messages.sendToPlayer(packet, (ServerPlayer) player2);
                                            }
                                        }

                                        this.getLevel().playSound((Player) null, target.getX(), target.getY(), target.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, this.getOwner().getSoundSource(), 0.25F, 1.0F);
                                        this.getOwner().playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 0.25F, 1.0F);

                                    }
                                }


                            }

                        }
                        return;
                    }

                    if(this.mode == 3) {

                    }
                }
            }
        if(!this.getLevel().isClientSide()){
            this.discard();
        }
    }

    public Vec3 rotate(double x, double y, double z, double pitch, double roll, double yaw) {
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

        Vec3 vec3 = new Vec3(Axx * x + Axy * y + Axz * z,Ayx * x + Ayy * y + Ayz * z,Azx * x + Azy * y + Azz * z);
        return vec3;
    }

}
