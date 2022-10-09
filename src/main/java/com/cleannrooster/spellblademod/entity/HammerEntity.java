package com.cleannrooster.spellblademod.entity;

import com.cleannrooster.spellblademod.items.FriendshipBracelet;
import com.google.common.collect.ImmutableMultimap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class HammerEntity extends ThrownTrident {
    public boolean triggered = false;
    public boolean dealtDamage = false;

public boolean secondary = false;
    public HammerEntity(EntityType<? extends ThrownTrident> p_37561_, Level p_37562_) {
        super(p_37561_, p_37562_);
    }
    private ItemStack tridentItem = new ItemStack(Items.TRIDENT);
    protected void onHitEntity(EntityHitResult p_37573_) {
        Entity entity = p_37573_.getEntity();
        float f = 8.0F;
        if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity) entity;
            f += EnchantmentHelper.getDamageBonus(this.tridentItem, livingentity.getMobType());
        }
        float multi;
        if (!this.triggered) {
            multi = 1;
        } else {
            multi = 0.5F;
        }
        Entity entity1 = this.getOwner();
        if (entity1 == p_37573_.getEntity()) {
            this.discard();
            return;
        }
        DamageSource damagesource = DamageSource.trident(this, this.getOwner());
        this.dealtDamage = true;
        SoundEvent soundevent = SoundEvents.TRIDENT_HIT;
        entity.invulnerableTime = 0;

        if (entity instanceof LivingEntity) {
            if (FriendshipBracelet.PlayerFriendshipPredicate((Player) this.getOwner(), (LivingEntity) entity)) {
                entity.hurt(damagesource, 4);
                if (entity.getType() == EntityType.ENDERMAN) {
                    return;
                }

                LivingEntity livingentity1 = (LivingEntity) entity;
                if (entity1 instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingentity1, entity1);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity) entity1, livingentity1);
                }
                this.doPostHurtEffects(livingentity1);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        float f1 = 1.0F;
        if (this.level instanceof ServerLevel && this.level.isThundering() && this.isChanneling()) {
            BlockPos blockpos = entity.blockPosition();
            if (this.level.canSeeSky(blockpos)) {
                LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(this.level);
                lightningbolt.moveTo(Vec3.atBottomCenterOf(blockpos));
                lightningbolt.setCause(entity1 instanceof ServerPlayer ? (ServerPlayer)entity1 : null);
                this.level.addFreshEntity(lightningbolt);
                soundevent = SoundEvents.TRIDENT_THUNDER;
                f1 = 5.0F;
            }
        }

        this.playSound(soundevent, f1, 1.0F);

    }
    protected void onHit(HitResult p_36913_) {
        super.onHit(p_36913_);
        Random random = new Random();
        if (this.onGround){
            return;
        }
            float multi;
            if (!this.triggered){
                multi = 1F;
            }
            else{
                multi = 0.75F;
            }
            int num_pts = 100;
            LivingEntity entity = (LivingEntity) this.getOwner();
            /*if (this.getLevel().isClientSide)
            {
                ClientLevel level = (ClientLevel) this.getLevel();*/
            int i = 0;
                for ( i = 0; i <= num_pts*multi*multi; i = i + 1) {
                    double[] indices = IntStream.rangeClosed(0, (int) ((1000 - 0) / 1))
                            .mapToDouble(x -> x * 1 + 0).toArray();

                    double phi = Math.acos(1 - 2 * indices[i] / num_pts);
                    double theta = Math.PI * (1 + Math.pow(5, 0.5) * indices[i]);
                    double x = cos(theta) * sin(phi);
                    double y = Math.sin(theta) * sin(phi);
                    double z = cos(phi);

                            this.getLevel().addParticle(ParticleTypes.FIREWORK, this.getX() + 4D * x, this.getY() + 4D * y, this.getZ() + 4D * z, 0, 0, 0);
                        }

            //}
        if (this.getOwner() != null) {

            List<LivingEntity> entities = this.getLevel().getEntitiesOfClass(LivingEntity.class,this.getBoundingBox().inflate(6D),livingEntity -> {return FriendshipBracelet.PlayerFriendshipPredicate((Player) this.getOwner(),livingEntity);});

            Object[] entitiesarray = entities.toArray();
            int entityamount = entitiesarray.length;

            for (int ii = 0; ii < entityamount; ii = ii + 1) {
                LivingEntity target = (LivingEntity) entities.get(ii);
                if (target != this.getOwner() && target.hasLineOfSight(this)) {
                    AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(),"knockbackresist",1, AttributeModifier.Operation.ADDITION);

                    ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
                    builder.put(Attributes.KNOCKBACK_RESISTANCE, modifier);
                    target.getAttributes().addTransientAttributeModifiers(builder.build());
                    target.invulnerableTime = 0;
                    target.hurt(new IndirectEntityDamageSource("spell",this,this.getOwner()), 3);

                    target.getAttributes().removeAttributeModifiers(builder.build());
                }

            }
        }



    }
    /*@Override
    protected void onHitEntity(EntityHitResult p_37573_) {
        Entity entity = p_37573_.getEntity();
        float multiplier;
        if (this.triggered){
            multiplier = 0.5F;
        }
        else{
            multiplier = 1;
        }
        float f = 8.0F;
        if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)entity;
            f += EnchantmentHelper.getDamageBonus(this.tridentItem, livingentity.getMobType());
        }

        Entity entity1 = this.getOwner();
        DamageSource damagesource = DamageSource.trident(this, (Entity)(entity1 == null ? this : entity1));
        this.dealtDamage = true;
        SoundEvent soundevent = SoundEvents.TRIDENT_HIT;
        if (entity.hurt(damagesource, f*multiplier)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity1 = (LivingEntity)entity;
                if (entity1 instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingentity1, entity1);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity)entity1, livingentity1);
                }

                this.doPostHurtEffects(livingentity1);
                int num_pts = 75;
                int i = 0;
                for ( i = 0; i <= num_pts*0.25; i = i + 1) {
                    double[] indices = IntStream.rangeClosed(0, (int) ((1000 - 0) / 1))
                            .mapToDouble(x -> x * 1 + 0).toArray();

                    double phi = Math.acos(1 - 2 * indices[i] / num_pts);
                    double theta = Math.PI * (1 + Math.pow(5, 0.5) * indices[i]);
                    double x = cos(theta) * sin(phi);
                    double y = Math.sin(theta) * sin(phi);
                    double z = cos(phi);

                    this.getLevel().addParticle(ParticleTypes.FIREWORK, this.getX() + 4D * x, this.getY() + 4D * y, this.getZ() + 4D * z, 0, 0, 0);
                }
                this.playSound(soundevent, 1F, 1.0F);
                if (this.getOwner() != null) {

                    List<LivingEntity> entities = this.level.getEntitiesOfClass(LivingEntity.class, new AABB(this.getX() - 4, this.getY() + 0.5 - 4, this.getZ() - 4, this.getX() + 4, this.getY() + 4, this.getZ() + 4));
                    Object[] entitiesarray = entities.toArray();
                    int entityamount = entitiesarray.length;
                    for (int ii = 0; ii < entityamount; ii = ii + 1) {
                        LivingEntity target = (LivingEntity) entities.get(ii);
                        if (target != this.getOwner()) {
                            target.invulnerableTime = 0;
                            target.hurt(DamageSource.trident(this, this.getOwner()), 4);
                            target.invulnerableTime = 0;
                        }

                    }
                }
            }
        }*/
/*
        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        float f1 = 1.0F;
        if (this.level instanceof ServerLevel && this.level.isThundering() && this.isChanneling()) {
            BlockPos blockpos = entity.blockPosition();
            if (this.level.canSeeSky(blockpos)) {
                LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(this.level);
                lightningbolt.moveTo(Vec3.atBottomCenterOf(blockpos));
                lightningbolt.setCause(entity1 instanceof ServerPlayer ? (ServerPlayer)entity1 : null);
                this.level.addFreshEntity(lightningbolt);
                soundevent = SoundEvents.TRIDENT_THUNDER;
                f1 = 5.0F;
            }
        }

        this.playSound(soundevent, f1, 1.0F);
    }*/
    public void tick() {

        Entity entity = this.getOwner();
        if (this.tickCount > 80){
            this.discard();
        }
        if (this.dealtDamage){
            this.discard();
        }
        if (tickCount%5 == 0 && !secondary && !this.inGround) {
            if (this.getOwner() != null) {
                /*if (this.secondary == false){
                    System.out.println(this.getXRot());
                    System.out.println(this.getYRot());
                }*/
                HammerEntity lance = new HammerEntity(ModEntities.TRIDENT.get(), this.level);

                lance.shootFromRotation(this, -this.xRotO+10, -this.yRotO+10, 0.0F, 1F, 1.0F);
                lance.setPos(this.getEyePosition());
                lance.setOwner(this.getOwner());
                lance.secondary = true;
                if (this.triggered){
                    lance.triggered = true;
                }
                lance.pickup = Pickup.DISALLOWED;
                HammerEntity lance2 = new HammerEntity(ModEntities.TRIDENT.get(), this.level);

                lance2.setOwner(this.getOwner());
                lance2.shootFromRotation(this, -this.xRotO+10, -this.yRotO-10, 0.0F, 1F, 1.0F);
                lance2.setPos(this.getEyePosition());

                lance2.secondary = true;
                if (this.triggered){
                    lance2.triggered = true;
                }
                lance2.pickup = Pickup.DISALLOWED;
                entity.getLevel().addFreshEntity(lance);
                entity.getLevel().addFreshEntity(lance2);
            }
        }
        super.tick();
    }
}
