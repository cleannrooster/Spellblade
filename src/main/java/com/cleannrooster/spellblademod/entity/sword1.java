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
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
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

public class sword1 extends ThrownTrident implements ItemSupplier {
    public Vec3 vector = Vec3.ZERO;
    public int number;
    public int mode;
    public boolean guard = false;
    public boolean firstTick = true;
    public LivingEntity target;
    public float damage = 6;

    public sword1(EntityType<? extends sword1> p_37561_, Level p_37562_) {
        super(p_37561_, p_37562_);
        this.setNoGravity(true);
        this.setNoPhysics(true);
        this.setInvisible(true);
        this.vector = Vec3.ZERO;

    }

    @Override
    protected boolean tryPickup(Player p_150196_) {
        return false;
    }

    public sword1(EntityType<? extends sword1> p_37561_, Level p_37562_, Player owner1) {
        super(p_37561_, p_37562_);
        Vec3 vec3 = owner1.getViewVector(0);
        double d0 = vec3.horizontalDistance();
        /*this.setYRot(((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI))));
        this.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
        this.xOld = this.getX();
        this.yOld = this.getY();
        this.zOld = this.getZ();*/
        this.setOwner(owner1);
        this.setNoGravity(true);
        this.setNoPhysics(true);

        this.setInvisible(true);
        this.vector = Vec3.ZERO;
    }
    public sword1(EntityType<? extends sword1> p_37561_, Level p_37562_, Player owner1, Vec3 vec3) {
        super(p_37561_, p_37562_);
        this.vector = owner1.getViewVector(0);
        double d0 = vec3.horizontalDistance();
        this.setYRot(((float) (Mth.atan2(owner1.getViewVector(0).x, owner1.getViewVector(0).z) * (double) (180F / (float) Math.PI))));
        this.setXRot((float) (Mth.atan2(owner1.getViewVector(0).y, d0) * (double) (180F / (float) Math.PI)));
        this.yRotO = ((float) (Mth.atan2(owner1.getViewVector(0).x, owner1.getViewVector(0).z) * (double) (180F / (float) Math.PI)));
        this.xRotO = (float) (Mth.atan2(owner1.getViewVector(0).y, d0) * (double) (180F / (float) Math.PI));

        /*this.setYRot(((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI))));
        this.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
        this.xOld = this.getX();
        this.yOld = this.getY();
        this.zOld = this.getZ();*/
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
        p_37573_.getEntity().hurt(EntityDamageSource.playerAttack((Player) this.getOwner()), (float) Math.max(6,(float) this.damage));

        }
    }

    @Override
    public void tick() {
        this.xOld = this.getX();
        this.yOld = this.getY();
        this.zOld = this.getZ();
        if (this.getOwner() != null) {
            if (this.getOwner().isAlive()&& this.getOwner() instanceof Player) {
                if(this.mode == 3){
                    if(this.tickCount > 10 && !this.getLevel().isClientSide()){
                        this.discard();
                        return;
                    }
                    this.setInvisible(true);
                    Vec3 pos = this.position();
                    float xrot = this.getXRot();
                    float yrot = this.getYRot();
                    Vec3 vec3 = Vec3.ZERO;
                    List<LivingEntity> entities = this.getLevel().getEntitiesOfClass(LivingEntity.class,this.getBoundingBox().inflate(0.25D),livingEntity -> {if(livingEntity != null) return FriendshipBracelet.PlayerFriendshipPredicate((Player) this.getOwner(), (LivingEntity) livingEntity); else return true;});
                    entities.removeIf(livingEntity -> livingEntity == this.getOwner());
                    entities.removeIf(livingEntity -> !((Player)this.getOwner()).hasLineOfSight(livingEntity));
                    entities.removeIf(livingEntity -> livingEntity == this.getOwner());
                    LivingEntity entity = this.level.getNearestEntity(entities,TargetingConditions.forCombat(),null,this.getX(),this.getY(),this.getZ());
                    if(this.tickCount == 0){
                        this.shoot(vector.x,vector.y, vector.z, 2.5F, 0.0F);

                        SoundEvent event = SoundEvents.PLAYER_ATTACK_SWEEP;
                        this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 0.25F, 1.0F);
                        if(entity != null){
                            entity.hurt(EntityDamageSource.playerAttack((Player) this.getOwner()), (float) Math.max(6,this.damage));
                            this.discard();
                        }
                    }

                    if(this.tickCount >= 0) {
                        Vec3 vec32 = this.position();
                        Vec3 vec33 = vec32.add(this.getDeltaMovement());
                        HitResult hitresult = this.level.clip(new ClipContext(vec32, vec33, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
                        if(hitresult.getType() != HitResult.Type.MISS){
                            this.onHit(hitresult);
                        }
                        this.setNoPhysics(false);
                        this.setNoGravity(true);
                        if(entity != null){
                            entity.hurt(EntityDamageSource.playerAttack((Player) this.getOwner()), (float) Math.max(6,(float) this.damage));
                            this.discard();
                        }
                        this.setInvisible(false);
                    }
                }
                if (this.tickCount >= 32 && !this.getLevel().isClientSide()){
                    this.discard();
                }
                    if (this.mode == 1) {

                        double number2 = 0;
                        float f7 = this.getOwner().getYRot()% 360;
                        float f8 = this.getOwner().getYRot() + 60;
                        float f9 = this.getOwner().getYRot() - 60;
                        float f = this.getOwner().getXRot();
                        float f1 = -Mth.sin(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                        float f2 = -Mth.sin(f * ((float) Math.PI / 180F));
                        float f3 = Mth.cos(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                        int i = (((1000 + this.tickCount + number) - (number * 1000 / 32))) % 1000;
                        if (this.tickCount <= -0) {
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
                        this.xRotO = (float)(Mth.atan2(vec3d.x, vec3d.z) * (double)(180F / (float)Math.PI));
                        this.yRotO = (float)(Mth.atan2(vec3d.y, d0) * (double)(180F / (float)Math.PI));



                        //this.setXRot((float) (f+phi*180/Math.PI));
                        //this.setYRot((float) (f7+theta*180/Math.PI));
                        /*if(this.tickCount < 0){
                            this.setYRot((float)(Mth.atan2(vec3d.x, vec3d.z) * (double)(180F / (float)Math.PI))+45);
                            this.setXRot((float)(Mth.atan2(vec3d.y, d0) * (double)(180F / (float)Math.PI))+45);
                        }*/

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
                                            living.hurt(new EntityDamageSource("spell", this.getOwner()), Math.max(6,(float) this.damage));

                                            if (level.getServer() != null) {
                                                int intarray[];
                                                intarray = new int[3];
                                                intarray[0] = (int) Math.round(target.getBoundingBox().getCenter().x);
                                                intarray[1] = (int) Math.round(target.getBoundingBox().getCenter().y);
                                                intarray[2] = (int) Math.round(target.getBoundingBox().getCenter().z);
                                                for (ServerPlayer player2 : ((ServerLevel) level).getPlayers(serverPlayer -> serverPlayer.hasLineOfSight(this.getOwner()))) {
                                                    FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer()).writeVarIntArray(intarray);
                                                    Stream<ServerPlayer> serverplayers = level.getServer().getPlayerList().getPlayers().stream();
                                                    ParticlePacket packet = new ParticlePacket(buf);
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
                                                for (ServerPlayer player2 : ((ServerLevel) level).getPlayers(serverPlayer -> serverPlayer.hasLineOfSight(this.getOwner()))) {
                                                    FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer()).writeVarIntArray(intarray);
                                                    Stream<ServerPlayer> serverplayers = level.getServer().getPlayerList().getPlayers().stream();
                                                    ParticlePacket packet = new ParticlePacket(buf);

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
                    }
                    if (this.mode == 2 && this.target != null) {
                        this.setNoPhysics(true);
                        this.setNoGravity(true);
                        double number2 = 0;
                        float f7 = this.target.getYRot()% 360;
                        float f8 = this.target.getYRot() + 60;
                        float f9 = this.target.getYRot() - 60;
                        float f = this.target.getXRot();
                        float f1 = -Mth.sin(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                        float f2 = -Mth.sin(f * ((float) Math.PI / 180F));
                        float f3 = Mth.cos(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                        int i = (((1000 + this.tickCount + number) - (number * 1000 / 32))) % 1000;
                        if (this.tickCount <= -16) {
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
                        float rotX = ReverberatingRay.getXRotD(this, this.target);
                        float rotY = ReverberatingRay.getYRotD(this, this.target);
                        float f11 = rotX * ((float)Math.PI / 180F);
                        float f12 = -rotY * ((float)Math.PI / 180F);
                        float f22 = Mth.cos(f12);
                        float f32 = Mth.sin(f12);
                        float f4 = Mth.cos(f11);
                        float f5 = Mth.sin(f11);
                        Vec3 vec31 =  new Vec3((double)(f32 * f4), (double)(-f5), (double)(f22 * f4));

                        if(this.tickCount < 1) {
                            this.setPos(this.target.position().x + 4 * vec3d.x + number2 * f1, this.target.position().y + 4 * vec3d.y + number2 * f2, this.target.position().z + 4 * vec3d.z + number2 * f3);
                            Vec3 vec = new Vec3(x, y, z);
                            double d0 = vec3d.horizontalDistance();

                            Vec3 vec3 = vec31;
                            double d1 = vec3.horizontalDistance();
                            this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
                            this.setXRot((float)(Mth.atan2(vec3.y, d1) * (double)(180F / (float)Math.PI)));
                            this.xRotO = (float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI));
                            this.yRotO = (float)(Mth.atan2(vec3.y, d1) * (double)(180F / (float)Math.PI));
                        }
                        //this.setXRot((float) (f+phi*180/Math.PI));
                        //this.setYRot((float) (f7+theta*180/Math.PI));
                        /*if(this.tickCount < 0){
                            this.setYRot((float)(Mth.atan2(vec3d.x, vec3d.z) * (double)(180F / (float)Math.PI))+45);
                            this.setXRot((float)(Mth.atan2(vec3d.y, d0) * (double)(180F / (float)Math.PI))+45);
                        }*/

                        if(this.tickCount > 10 && !this.getLevel().isClientSide()){
                            this.discard();
                            return;
                        }
                        Vec3 pos = this.position();
                        float xrot = this.getXRot();
                        float yrot = this.getYRot();
                        Vec3 vec3 = Vec3.ZERO;
                        List<LivingEntity> entities = this.getLevel().getEntitiesOfClass(LivingEntity.class,this.getBoundingBox().expandTowards(this.getDeltaMovement().x(),this.getDeltaMovement().y(),this.getDeltaMovement().z()),livingEntity -> {if(livingEntity != null) return FriendshipBracelet.PlayerFriendshipPredicate((Player) this.getOwner(), (LivingEntity) livingEntity); else return true;});
                        entities.removeIf(livingEntity -> livingEntity == this.getOwner());
                        entities.removeIf(livingEntity -> !((Player)this.getOwner()).hasLineOfSight(livingEntity));
                        entities.removeIf(livingEntity -> livingEntity == this.getOwner());
                        LivingEntity entity = this.level.getNearestEntity(entities,TargetingConditions.forCombat(),null,this.getX(),this.getY(),this.getZ());
                        if(this.tickCount == 1){
                            double d1 = vec31.horizontalDistance();
                            this.setYRot((float)(Mth.atan2(vec31.x, vec31.z) * (double)(180F / (float)Math.PI)));
                            this.setXRot((float)(Mth.atan2(vec31.y, d1) * (double)(180F / (float)Math.PI)));
                            this.shoot(vec31.x,vec31.y,vec31.z, 2,0);
                            SoundEvent event = SoundEvents.PLAYER_ATTACK_SWEEP;
                            this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 0.25F, 1.0F);
                            if(entity != null){
                                entity.hurt(EntityDamageSource.playerAttack((Player) this.getOwner()), (float) Math.max(3,(float) this.damage/2));
                                this.discard();
                            }

                        }
                        if(this.tickCount > 0) {
                            this.setNoPhysics(true);
                            this.setNoGravity(true);

                            if(entity != null){
                                entity.hurt(EntityDamageSource.playerAttack((Player) this.getOwner()), (float) Math.max(3,(float) this.damage/2));
                                this.discard();
                            }
                        }
                        /*if(this.tickCount > 2){
                            double d1 = vec31.horizontalDistance();
                            this.setYRot((float)(Mth.atan2(-vec31.x, -vec31.z) * (double)(180F / (float)Math.PI)));
                            this.setXRot((float)(Mth.atan2(-vec31.y, d1) * (double)(180F / (float)Math.PI)));

                        }*/

                    }

                    if(this.mode == 3) {

                    }
                }
            }

        else if(!this.getLevel().isClientSide()){
            this.discard();
        }

        this.checkInsideBlocks();
        Vec3 vec3 = this.getDeltaMovement();
        double d2 = this.getX() + vec3.x;
        double d0 = this.getY() + vec3.y;
        double d1 = this.getZ() + vec3.z;
        float f;
        if (this.isInWater()) {
            for(int i = 0; i < 4; ++i) {
                float f1 = 0.25F;
                this.level.addParticle(ParticleTypes.BUBBLE, d2 - vec3.x * 0.25D, d0 - vec3.y * 0.25D, d1 - vec3.z * 0.25D, vec3.x, vec3.y, vec3.z);
            }

            f = 0.8F;
        } else {
            f = 0.99F;
        }

        if(vector != Vec3.ZERO){
            this.setYRot(((float) (Mth.atan2(vector.x, vector.z) * (double) (180F / (float) Math.PI))));
            this.setXRot((float) (Mth.atan2(vector.y, vector.horizontalDistance()) * (double) (180F / (float) Math.PI)));
            this.yRotO = ((float) (Mth.atan2(vector.x, vector.z) * (double) (180F / (float) Math.PI)));
            this.xRotO = (float) (Mth.atan2(vector.y, vector.horizontalDistance()) * (double) (180F / (float) Math.PI));


        }
        this.setPos(d2, d0, d1);

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

    @Override
    public ItemStack getItem() {
        if(this.getCustomName().getString().equals("emerald")){
            return Items.EMERALD.getDefaultInstance();
        }
        return null;
    }
}
