package com.cleannrooster.spellblademod.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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
    public int explosionPower = 2;
    public LivingEntity target;
    boolean flag = false;
    int waiting = 0;
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
            boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner());
            this.level.explode((Entity)null, this.getX(), this.getY(), this.getZ(), (float)this.explosionPower, false, Explosion.BlockInteraction.NONE);
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
        if(tickCount > 400){
            if (!this.level.isClientSide) {
                boolean flag = false;
                this.level.explode((Entity)this.getOwner(), this.getX(), this.getY(), this.getZ(), (float)this.explosionPower, flag, flag ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE);
                this.discard();
            }
        }
        if(this.flag){

            if (waiting >= 10){
                boolean flag = false;
                if(this.target != null) {
                    target.invulnerableTime= 0;
                }
                this.level.explode((Entity)this.getOwner(), this.getX(), this.getY(), this.getZ(), (float)this.explosionPower, flag, flag ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE);
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
            super.tick();
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
        else{
            super.tick();
        }

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
