package com.cleannrooster.spellblademod.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class BouncingEntity extends Fireball {
    int count = 0;
    LivingEntity thrower;
    int changetime = 0;
    public boolean canspawn;


    public BouncingEntity(Level entityWorld, Player playerIn) {
        super(ModEntities.BETTY.get(),entityWorld);
        this.setPos(playerIn.getX(), playerIn.getEyeY(), playerIn.getZ());
        this.thrower = playerIn;
        this.canspawn = true;

    }

    public BouncingEntity(EntityType<BouncingEntity> bouncingEntityEntityType, Level level) {
        super(ModEntities.BETTY.get(),level);
    }

    @Override
    public boolean hurt(DamageSource p_36839_, float p_36840_) {
        if(p_36839_.getEntity() != null && this.thrower != null){
            if(p_36839_.getEntity() instanceof LivingEntity) {
                this.markHurt();
                if (p_36839_.getEntity() instanceof VolleyballEntity || p_36839_.getEntity() instanceof Player && this.thrower != p_36839_.getEntity()) {
                    double x = this.random.nextDouble(-0.4, 0.4);
                    double y = this.random.nextDouble(-0.4, 0.4);
                    double z = this.random.nextDouble(-0.4, 0.4);
                    this.setDeltaMovement((this.thrower.getPosition(0).add(p_36839_.getEntity().getPosition(0).multiply(-1, -1, -1))).normalize().multiply(1 + x, 0, 1 + z).add(0, 0.35, 0));
                    this.thrower = (LivingEntity) p_36839_.getEntity();

                    this.changetime = 10;
                    this.canspawn = false;
                    this.count = 0;

                    return true;
                }
                this.thrower = (LivingEntity) p_36839_.getEntity();
                this.canspawn = false;
                this.count = 0;
                this.changetime = 10;
            }


        }

        return super.hurt(p_36839_, p_36840_);
    }

    @Override
    public void tick() {

        if(firstTick){
            SoundEvent soundEvent = SoundEvents.BLAZE_SHOOT;
            this.playSound(soundEvent, 1F, 1F);
        }

        super.baseTick();
        if(this.thrower == null && !this.getLevel().isClientSide){
            this.discard();
        }
        if(this.changetime > 0) {
            this.changetime--;
        }
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
        return 0.06F;
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if(result.getType() == HitResult.Type.BLOCK && !this.getLevel().isClientSide()) {
            final int NUM_POINTS = 24;
            final double RADIUS = 4d;

                if((result.getDirection() == Direction.NORTH) || result.getDirection() == Direction.SOUTH) {
                    this.setDeltaMovement(1*this.getDeltaMovement().x, 1*this.getDeltaMovement().y, -1*this.getDeltaMovement().z);
				/*for (int i = 0; i < NUM_POINTS; ++i)
				{
				    final double angle = Math.toRadians(((double) i / NUM_POINTS) * 360d);

				        double x = Math.cos(angle) * RADIUS;
				        double y = Math.sin(angle) * RADIUS;

					ThrownItems thrown = new ThrownItems(world, this.thrower);
					thrown.setNoGravity(true);
					Vec3d vec3 = new Vec3d(x,0,y);
					thrown.shoot(vec3.x, vec3.y, vec3.z, 1, 0);

				}
				if(count >= 8) {
					this.setDead();
				}
				this.count++;*/
                }
                if((result.getDirection() == Direction.EAST || result.getDirection() == Direction.WEST )) {
                    this.setDeltaMovement(-1*this.getDeltaMovement().x, 1*this.getDeltaMovement().y, 1*this.getDeltaMovement().z);
				/*for (int i = 0; i < NUM_POINTS; ++i)
				{
				    final double angle = Math.toRadians(((double) i / NUM_POINTS) * 360d);

				        double x = Math.cos(angle) * RADIUS;
				        double y = Math.sin(angle) * RADIUS;

					ThrownItems thrown = new ThrownItems(world, this.thrower);
					thrown.setNoGravity(true);
					Vec3d vec3 = new Vec3d(x,0,y);
					thrown.shoot(vec3.x, vec3.y, vec3.z, 1, 0);

				}
				if(count >= 8) {
					this.setDead();
				}
				this.count++;*/

                }
                if(result.getDirection() == Direction.UP || result.getDirection() == Direction.DOWN) {
                    this.setDeltaMovement(1*this.getDeltaMovement().x, -1*this.getDeltaMovement().y, 1*this.getDeltaMovement().z);
                    if(result.getDirection() == Direction.UP){
                        if(this.getDeltaMovement().y<0.2) {
                            this.setDeltaMovement(this.getDeltaMovement().x, 0.2, this.getDeltaMovement().z);
                        }
                        for (int i = 0; i < NUM_POINTS; ++i)
                        {
                            final double angle = Math.toRadians(((double) i / NUM_POINTS) * 360d);

                            double x = Math.cos(angle) * RADIUS;
                            double y = Math.sin(angle) * RADIUS;

                            ThrownItems thrown = new ThrownItems(this.level, this.thrower,true);
                            thrown.setPos(this.getX(), this.getY()+0.5, this.getZ());
                            Vec3 vec3 = new Vec3(x,0,y);
                            thrown.setDeltaMovement(vec3.x/10, 0.12, vec3.z/10);
                            if(this.canspawn) {
                                this.level.addFreshEntity(thrown);
                            }

                        }
                        SoundEvent soundEvent = SoundEvents.BLAZE_SHOOT;
                        this.playSound(soundEvent, 1F, 1F);
                        if(count >= 4) {
                            this.discard();
                        }
                        this.count++;

                    }
                }

        }
        super.onHitBlock(result);
    }


    @Override
    public ItemStack getItem() {
        ItemStack itemStack = new ItemStack(Items.FIRE_CHARGE);
        return itemStack;
    }
}
