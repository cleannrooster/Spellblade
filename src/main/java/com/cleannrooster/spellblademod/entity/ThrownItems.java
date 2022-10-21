package com.cleannrooster.spellblademod.entity;

import com.cleannrooster.spellblademod.items.FriendshipBracelet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ThrownItems extends Fireball {
    LivingEntity thrower;
    boolean fromBetty = false;
    public ThrownItems(Level worldIn, LivingEntity owner, boolean betty) {
        super(ModEntities.FIREBALL.get(),worldIn);
        this.thrower = owner;

        this.setInvisible(true);
        this.setSecondsOnFire(64);
        this.fromBetty = betty;
        // TODO Auto-generated constructor stub
    }

    public ThrownItems(EntityType<ThrownItems> thrownItemsEntityType, Level level) {
        super(ModEntities.FIREBALL.get(),level);
    }
    @Override
    public ItemStack getItem() {
        ItemStack itemStack = new ItemStack(Items.BLAZE_POWDER);
        return itemStack;
    }

    @Override
    protected boolean canHitEntity(Entity p_36842_) {
        return true;
    }

    @Override
    public void tick() {
        if(this.thrower == null && !this.getLevel().isClientSide){
            this.discard();
        }
        super.baseTick();
        HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
        boolean flag = false;
        if (hitresult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockpos = ((BlockHitResult)hitresult).getBlockPos();
            BlockState blockstate = this.level.getBlockState(blockpos);
            if (blockstate.is(Blocks.NETHER_PORTAL)) {
                this.handleInsidePortal(blockpos);
                flag = true;
            } else if (blockstate.is(Blocks.END_GATEWAY)) {
                BlockEntity blockentity = this.level.getBlockEntity(blockpos);
                if (blockentity instanceof TheEndGatewayBlockEntity && TheEndGatewayBlockEntity.canEntityTeleport(this)) {
                    TheEndGatewayBlockEntity.teleportEntity(this.level, blockpos, blockstate, this, (TheEndGatewayBlockEntity)blockentity);
                }

                flag = true;
            }
        }

        if (hitresult.getType() != HitResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.onHit(hitresult);
        }

        this.checkInsideBlocks();
        Vec3 vec3 = this.getDeltaMovement();
        double d2 = this.getX() + vec3.x;
        double d0 = this.getY() + vec3.y;
        double d1 = this.getZ() + vec3.z;
        this.updateRotation();
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

        this.setDeltaMovement(vec3.scale((double)f));
        if (!this.isNoGravity()) {
            Vec3 vec31 = this.getDeltaMovement();
            this.setDeltaMovement(vec31.x, vec31.y - (double)this.getGravity(), vec31.z);
        }

        this.setPos(d2, d0, d1);
    }
    protected float getGravity() {
        return 0.03F;
    }

    @Override
    protected void onHitEntity(EntityHitResult p_37259_) {
        if(this.thrower != null && p_37259_.getEntity() instanceof LivingEntity && !this.getLevel().isClientSide){
            if(this.thrower instanceof Player player) {
                if (FriendshipBracelet.PlayerFriendshipPredicate(player, (LivingEntity) p_37259_.getEntity()) && p_37259_.getEntity() != this.thrower) {
                    p_37259_.getEntity().invulnerableTime = 0;
                    p_37259_.getEntity().setSecondsOnFire(4);
                    p_37259_.getEntity().hurt(new EntityDamageSource("spell", this.thrower).setIsFire(), 2);
                }
            }
            else if (p_37259_.getEntity() != this.thrower){
                p_37259_.getEntity().invulnerableTime = 0;
                p_37259_.getEntity().setSecondsOnFire(4);
                p_37259_.getEntity().hurt(new EntityDamageSource("spell", this.thrower).setIsFire(), 2);

            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult p_37258_) {
        this.discard();
    }
}
