package com.cleannrooster.spellblademod.entity;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.effects.DamageSourceModded;
import com.cleannrooster.spellblademod.effects.FluxHandler;
import com.cleannrooster.spellblademod.items.FluxItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import java.util.ArrayList;
import java.util.List;

public class FluxEntity extends AbstractArrow implements ItemSupplier {
    public float explosionPower = 1F;
    public LivingEntity target;
    public boolean overload = false;
    public boolean first = false;

    public float amount = 0;
    public boolean bool;
    public boolean bool2;


    boolean flag = false;
    int waiting = 0;
    public List<LivingEntity> list = new ArrayList<>();
    public FluxEntity(EntityType<? extends AbstractArrow> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }
    @Override
    protected void onHit(HitResult p_37218_) {
        if(this.target == null){
            super.onHit(p_37218_);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult p_37258_) {
        if(this.target == null){
            super.onHitBlock(p_37258_);
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return null;
    }

    @Override
    public boolean skipAttackInteraction(Entity p_20357_) {
        return true;
    }


    @Override
    protected void onHitEntity(EntityHitResult p_37259_) {
        if(this.target == null){
                super.onHitEntity(p_37259_);
        }

    }

    @Override
    public boolean isAttackable() {
        return false;
    }
    @Override
    public void tick() {
        pickup = Pickup.DISALLOWED;
        if(tickCount > 200){
                this.discard();
        }
        if(firstTick && !this.overload){
            if(this.level.getEntitiesOfClass(FluxEntity.class, AABB.ofSize(this.getBoundingBox().getCenter(),16,16,16)).toArray().length > 128){
             this.discard();
            }
        }
        if(this.target != null){
            if(this.bool && this.target.hasEffect(StatusEffectsModded.FLUXED.get())){
                this.discard();
            }
            if(bool2 && !this.target.hasEffect(StatusEffectsModded.FLUXED.get())){
                this.discard();
            }
            this.noPhysics = true;
            super.tick();

            Vec3 vec3 = target.getBoundingBox().getCenter().subtract(this.position());
            this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015D * (double)2, this.getZ());
            if (this.level.isClientSide) {
                this.yOld = this.getY();
            }

            double d0 = 0.05D * (double)2;
            this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vec3.normalize().scale(d0)));
            if(this.getBoundingBox().intersects(this.target.getBoundingBox())){
                if(this.getOwner() instanceof Player) {
                    if (this.overload) {
                        if(this.target.hasEffect(StatusEffectsModded.FLUXED.get())){
                                this.target.removeEffect(StatusEffectsModded.FLUXED.get());

                            this.target.invulnerableTime = 0;
                            this.target.hurt(DamageSourceModded.fluxed((Player) this.getOwner()), this.amount * 1.5F);
                            this.target.invulnerableTime = 0;
                            this.target.hurt(DamageSourceModded.fluxed((Player) this.getOwner()), this.amount);
                            FluxHandler.fluxHandler2(this.target, (Player) this.getOwner(), this.amount, this.level, this.list);

                        }


                    } else {
                        if(this.bool && this.target.hasEffect(StatusEffectsModded.FLUXED.get())){
                            this.discard();
                        }
                        else {
                            FluxItem.FluxFlux((Player) this.getOwner(), this.target, this.level, this.list,first);
                        }
                    }
                }
                this.discard();
            }
        }
        else{
            super.tick();
        }
    }
    @Override
    public ItemStack getItem() {
            ItemStack itemStack = new ItemStack(Items.HEART_OF_THE_SEA);
            itemStack.enchant(Enchantments.PIERCING,1);
            return itemStack;
    }
    @Override
    public boolean mayInteract(Level p_150167_, BlockPos p_150168_) {
        return false;
    }
}
