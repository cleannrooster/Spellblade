package com.cleannrooster.spellblademod.entity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class VolatileEntity extends Fireball {
    public float explosionPower = 2F;
    public LivingEntity target;
    boolean flag = false;
    int waiting = 0;

    public VolatileEntity(EntityType<? extends Fireball> p_37006_, Level p_37007_) {
        super(p_37006_, p_37007_);
    }
    @Override
    protected void onHit(HitResult p_37218_) {

    }

    @Override
    protected void onHitBlock(BlockHitResult p_37258_) {

    }

    @Override
    protected void onHitEntity(EntityHitResult p_37259_) {

    }

    @Override
    public void tick() {
        this.setSecondsOnFire(5);
        if(tickCount > 200){
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
            waiting++;
            return;
        }
        if(this.target != null){
            super.tick();
            this.noPhysics = true;
            Vec3 vec3 = target.getBoundingBox().getCenter().subtract(this.position());
            this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015D * (double)2, this.getZ());
            if (this.level.isClientSide) {
                this.yOld = this.getY();
            }

            double d0 = 0.05D * (double)2;
            this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vec3.normalize().scale(d0)));
            if(vec3.length() < 0.3){
                this.flag = true;
            }
        }
    }
}
