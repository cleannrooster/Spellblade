package com.cleannrooster.spellblademod.entity;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.effects.DamageSourceModded;
import com.cleannrooster.spellblademod.effects.Flux;
import com.cleannrooster.spellblademod.effects.FluxHandler;
import com.cleannrooster.spellblademod.items.FluxItem;
import com.cleannrooster.spellblademod.items.Spell;
import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
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
import java.util.Objects;
import java.util.UUID;

public class FluxEntity extends AbstractArrow implements ItemSupplier {
    public float explosionPower = 1F;
    public LivingEntity target;
    public boolean overload = false;
    public boolean first = false;
    public List<Spell> spells = new ArrayList<>();
    public float amount = 0;
    public boolean bool;
    public boolean bool2;
    public UUID fluxid;


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
        if(Objects.equals(this.getCustomName(), new TranslatableComponent("overload"))){
            this.setInvisible(true);
            float tridentEntity = this.getYRot() % 360;

            float f = this.getXRot();
            float g = (float) (-Math.sin(tridentEntity * ((float) Math.PI / 180)) * Math.cos(f * ((float) Math.PI / 180)));
            float h = (float) -Math.sin(f * ((float) Math.PI / 180));
            float k2 = (float) (Math.cos(tridentEntity * ((float) Math.PI / 180)) * Math.cos(f * ((float) Math.PI / 180)));
            float l2 = (float) Math.sqrt(g * g + h * h + k2 * k2);
            float m2 = 2.0f;
            float f7 = 360 + this.getYRot() % 360;

            int ii = this.tickCount % 20;
            final double theta = Math.toRadians((ii / 10d) * 360d);
            double x = Math.cos(theta);
            double y = Math.sin(theta);
            double z = 0;
            Vec3 vec3d = EssenceBoltEntity.rotate(x, y, z, -Math.toRadians(f7), Math.toRadians(f), 0);
            /*final double angle = Math.toRadians(((double) ii / 20) * 360d);
                double x = Math.cos(angle);
                double y = Math.sin(angle);
                double z = 0;*/
            double x1 = this.getX() + 0.25 * vec3d.x;
            double y1 = this.getY() + 0.25 * vec3d.y;
            double z1 = this.getZ() + 0.25 * vec3d.z;
            double xx1 = this.getX() - 0.25 * vec3d.x;
            double yy1 = this.getY() - 0.25 * vec3d.y;
            double zz1 = this.getZ() - 0.25 * vec3d.z;
            double x2 = this.getX()+this.getDeltaMovement().x + 0.25 * vec3d.x;
            double y2 = this.getY()+this.getDeltaMovement().y + 0.25 * vec3d.y;
            double z2 = this.getZ()+this.getDeltaMovement().z + 0.25 * vec3d.z;
            double xx2 = this.getX()+this.getDeltaMovement().x - 0.25 * vec3d.x;
            double yy2 = this.getY()+this.getDeltaMovement().y - 0.25 * vec3d.y;
            double zz2 = this.getZ()+this.getDeltaMovement().z - 0.25 * vec3d.z;
            this.level.addParticle(new DustParticleOptions(new Vector3f(Vec3.fromRGB24(42495)),1F), true, x1 , y1 , z1, 0,0, 0);

            this.level.addParticle( ParticleTypes.ELECTRIC_SPARK, true, this.getX(), this.getY(), this.getZ(), this.getDeltaMovement().x, this.getDeltaMovement().y, this.getDeltaMovement().z);

            this.level.addParticle(new DustParticleOptions(new Vector3f(Vec3.fromRGB24(42495)),1F), true, xx1, yy1, zz1, 0, 0, 0);
            this.level.addParticle(new DustParticleOptions(new Vector3f(Vec3.fromRGB24(42495)),1F), true, x2 , y2 , z2, 0,0, 0);
            this.level.addParticle(ParticleTypes.ELECTRIC_SPARK, true, this.getX()+this.getDeltaMovement().x, this.getY()+this.getDeltaMovement().z, this.getZ()+this.getDeltaMovement().z, 0, 0, 0);

            this.level.addParticle(new DustParticleOptions(new Vector3f(Vec3.fromRGB24(42495)),1F), true, xx2, yy2, zz2, 0, 0, 0);

        }
        pickup = Pickup.DISALLOWED;

        this.noPhysics = true;
        if(tickCount > 200){
                this.discard();
        }
        if(firstTick && !this.overload){
            if(this.level.getEntitiesOfClass(FluxEntity.class, AABB.ofSize(this.getBoundingBox().getCenter(),16,16,16)).toArray().length > 128){
             this.discard();
            }
        }
        if(this.target != null){
            if(this.list.contains(this.target)){
                this.discard();
            }

            Vec3 vec3 = target.getBoundingBox().getCenter().subtract(this.position());
            //this.setPos(this.getX(), this.getY() + vec3.y * 0.015D * (double)2, this.getZ());
            if (this.level.isClientSide) {
                this.yOld = this.getY();
            }

            double d0 = 0.05D * (double)2;
            this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vec3.normalize().scale(d0)));
            if(this.getBoundingBox().expandTowards(this.getDeltaMovement()).intersects(this.target.getBoundingBox())){
                if(this.getOwner() instanceof Player) {
                    if (this.overload) {
                        if(this.target.hasEffect(StatusEffectsModded.FLUXED.get())){
                                this.target.removeEffect(StatusEffectsModded.FLUXED.get());
                                this.spells.removeIf(Objects::isNull);
                                if(this.spells.isEmpty()) {
                                    this.target.invulnerableTime = 0;

                                    this.target.hurt(DamageSourceModded.fluxed((Player) this.getOwner()), this.amount * 0.5F);
                                    this.target.invulnerableTime = 0;
                                    this.target.hurt(DamageSourceModded.fluxed((Player) this.getOwner()), this.amount);
                                }
                                else if (this.getOwner() instanceof Player player){
                                    for(Spell spell : this.spells){

                                        spell.triggeron(this.level,player,this.target,1);
                                    }
                                    this.target.removeEffect(StatusEffectsModded.FLUXED.get());
                                    this.target.removeEffect(MobEffects.GLOWING);

                                    this.discard();
                                    return;
                                }

                            FluxHandler.fluxHandler2(this.target, (Player) this.getOwner(), this.amount, this.level, this.list,this.spells, this.fluxid);
                            for(FluxEntity flux : this.getLevel().getEntitiesOfClass(FluxEntity.class,this.getBoundingBox().inflate(32),fluxEntity -> fluxEntity.fluxid == this.fluxid)){
                                flux.list = this.list;
                            }
                        }


                    } else {
                        if(this.list.contains(this.target)){
                            this.discard();
                        }
                        else {
                            FluxItem.FluxFlux((Player) this.getOwner(), this.target, this.level, this.list, this.first, this.fluxid);
                            for (FluxEntity flux : this.getLevel().getEntitiesOfClass(FluxEntity.class, this.getBoundingBox().inflate(32), fluxEntity -> fluxEntity.fluxid == this.fluxid)) {
                                flux.list = this.list;
                            }
                        }
                    }
                }
                this.discard();
            }
        }
            this.setDeltaMovement(this.getDeltaMovement().normalize().multiply(10F / 20F, 10F / 20F, 10F / 20F));
        super.tick();

    }

    @Override
    public boolean isCustomNameVisible() {
        return false;
    }

    @Override
    public ItemStack getItem() {
            ItemStack itemStack = new ItemStack(Items.HEART_OF_THE_SEA);
            itemStack.enchant(Enchantments.PIERCING,1);
            if(this.getCustomName() != null){
                return new ItemStack(Items.AIR);
            }
            return itemStack;
    }
    @Override
    public boolean mayInteract(Level p_150167_, BlockPos p_150168_) {
        return false;
    }
}
