package com.cleannrooster.spellblademod.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class VolatileEntity extends LargeFireball implements ItemSupplier{
    public float explosionPower = 2;
    public LivingEntity target;
    boolean flag = false;
    int waiting = 0;
    public float damage = 6;
    public  VolatileEntity(EntityType<? extends VolatileEntity> p_37006_, Level p_37007_) {
        super(p_37006_, p_37007_);
    }
    @Override
    protected void onHit(HitResult p_37218_) {
        if(this.target != null){
            return;
        }
        HitResult.Type hitresult$type = p_37218_.getType();
        if (hitresult$type == HitResult.Type.ENTITY) {
            if(!(((EntityHitResult)(p_37218_)).getEntity() instanceof VolatileEntity)) {
                this.onHitEntity((EntityHitResult) p_37218_);

            }
            else{
                return;
            }
        } else if (hitresult$type == HitResult.Type.BLOCK) {
            this.onHitBlock((BlockHitResult)p_37218_);
        }

        if (hitresult$type != HitResult.Type.MISS) {
            this.gameEvent(GameEvent.PROJECTILE_LAND, this.getOwner());
        }
        if (!this.level.isClientSide) {
            if(this.getOwner() instanceof Player player){
                this.explosionPower = (float)Math.max(2,((Player)this.getOwner()).getAttributeValue(Attributes.ATTACK_DAMAGE)/3);
            }
            boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner());
            this.level.explode((Entity)null, this.getX(), this.getY(), this.getZ(), (float)Math.max(1.5,1.5*this.damage/6), false, Explosion.BlockInteraction.NONE);
            this.discard();
        }
    }

    protected void onHitEntity(EntityHitResult p_37259_) {
        if(p_37259_.getEntity() instanceof VolatileEntity){
            return;
        }
        else{
            super.onHitEntity(p_37259_);
        }
    }


    @Override
    public void tick() {
        this.setSecondsOnFire(5);
        this.setNoGravity(true);
        if(firstTick){
            SoundEvent soundEvent = SoundEvents.BLAZE_SHOOT;
            this.playSound(soundEvent, 0.25F, 1F);
        }
        if(tickCount > 400){
            if (!this.level.isClientSide) {
                boolean flag = false;
                if(this.getOwner() instanceof Player player){
                    this.explosionPower = (float)Math.max(2,((Player)this.getOwner()).getAttributeValue(Attributes.ATTACK_DAMAGE)/3);
                }
                this.level.explode((Entity)this.getOwner(), this.getX(), this.getY(), this.getZ(), (float)Math.max(1.5,1.5*this.damage/6), flag, flag ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE);
                this.discard();
            }
        }
        if(this.flag){

            if (waiting >= 10){
                boolean flag = false;
                if(this.target != null) {
                    target.invulnerableTime= 0;
                }
                if(this.getOwner() instanceof Player player){
                    this.explosionPower = (float)Math.max(2,((Player)this.getOwner()).getAttributeValue(Attributes.ATTACK_DAMAGE)/3);
                }
                this.level.explode((Entity)this.getOwner(), this.getX(), this.getY(), this.getZ(), (float)Math.max(1.5,1.5*this.damage/6), flag, flag ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE);
                if(this.target != null) {
                    target.invulnerableTime= 0;
                }
                this.discard();
            }
            this.setDeltaMovement(Vec3.ZERO);
            waiting++;
            return;
        }

        if(this.target != null){
            this.noPhysics = true;
            Vec3 vec3 = target.getBoundingBox().getCenter().subtract(this.position());
            this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015D * (double)2, this.getZ());
            if (this.level.isClientSide) {
                this.yOld = this.getY();
            }

            double d0 = 0.05D * (double)2;
            this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vec3.normalize().scale(d0)));
            if(vec3.length() < 0.5){
                this.flag = true;
            }
        }

        super.tick();

    }

    @Override
    public boolean hurt(DamageSource p_36839_, float p_36840_) {
        if(this.target == null) {
            super.hurt(p_36839_, p_36840_);
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public boolean mayInteract(Level p_150167_, BlockPos p_150168_) {

        return this.target == null;
    }
    @Override
    public boolean isAttackable() {
        return this.target == null;
    }
    @Override
    public boolean skipAttackInteraction(Entity p_20357_) {

        return this.target != null;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Items.FIRE_CHARGE);
    }
}
