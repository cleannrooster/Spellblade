package com.cleannrooster.spellblademod.entity;

import com.cleannrooster.spellblademod.ModBlocks;
import com.cleannrooster.spellblademod.StatusEffectsModded;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.lang.Math.*;

public class SentinelEntity extends PathfinderMob {

    private boolean noTotem;

    public SentinelEntity(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
        this.noPhysics = true;
        this.setNoGravity(true);

        this.moveControl = new VexMoveControl(this);
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 40.0D).add(Attributes.ATTACK_DAMAGE, 4.0D).add(Attributes.MOVEMENT_SPEED,0.20F);
    }
    public void move(MoverType p_33997_, Vec3 p_33998_) {
        super.move(p_33997_, p_33998_);
        this.checkInsideBlocks();
    }
    class VexMoveControl extends MoveControl {
        public VexMoveControl(SentinelEntity p_34062_) {
            super(p_34062_);
        }

        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                Vec3 vec3 = new Vec3(this.wantedX - SentinelEntity.this.getX(), this.wantedY - SentinelEntity.this.getY(), this.wantedZ - SentinelEntity.this.getZ());
                double d0 = vec3.length();
                if (d0 < SentinelEntity.this.getBoundingBox().getSize()) {
                    this.operation = MoveControl.Operation.WAIT;
                    SentinelEntity.this.setDeltaMovement(SentinelEntity.this.getDeltaMovement().scale(0.5D));
                } else {

                        SentinelEntity.this.setDeltaMovement(SentinelEntity.this.getDeltaMovement().add(vec3.scale(this.speedModifier * 0.05D / d0)));
                    if (SentinelEntity.this.getTarget() == null) {
                        Vec3 vec31 = SentinelEntity.this.getDeltaMovement();
                        SentinelEntity.this.setYRot(-((float)Mth.atan2(vec31.x, vec31.z)) * (180F / (float)Math.PI));
                        SentinelEntity.this.yBodyRot = SentinelEntity.this.getYRot();
                    } else {
                        double d2 = SentinelEntity.this.getTarget().getX() - SentinelEntity.this.getX();
                        double d1 = SentinelEntity.this.getTarget().getZ() - SentinelEntity.this.getZ();
                        SentinelEntity.this.setYRot(-((float)Mth.atan2(d2, d1)) * (180F / (float)Math.PI));
                        SentinelEntity.this.yBodyRot = SentinelEntity.this.getYRot();
                    }
                }

            }
        }
    }
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(4, new SentinelMoveTowardsEnemyGoal());
        this.goalSelector.addGoal(8, new VexRandomMoveGoal());
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true, p_21024_ ->{
            return !p_21024_.hasEffect(StatusEffectsModded.TOTEMIC_ZEAL.get());}));
    }
    public void tick() {
        this.addEffect(new MobEffectInstance(StatusEffectsModded.TOTEMIC_ZEAL.get(), 190, 0));
        super.tick();
        List entities = this.level.getEntitiesOfClass(LivingEntity.class, new AABB(this.getX() - 9, this.getY() - 9, this.getZ() - 9, this.getX() + 9, this.getY() + 9, this.getZ() + 9));
        Object[] entitiesarray = entities.toArray();
        int entityamount = entitiesarray.length;
            for (int ii = 0; ii < entityamount; ii = ii + 1) {
                LivingEntity livingentity = (LivingEntity) entities.get(ii);
                boolean flag2 = false;
                if (this.distanceToSqr(livingentity) < 81 && SentinelEntity.this.tickCount % 10 == 0 && !livingentity.hasEffect(StatusEffectsModded.TOTEMIC_ZEAL.get())) {
                    int i = 0;
                    int num_pts = 100;
                    int num_pts_line = 25;
                    for (int iii = 0; iii < num_pts_line; iii++) {
                        double X = this.getEyePosition().x + (livingentity.getEyePosition().x - this.getEyePosition().x) * ((double) iii / (num_pts_line));
                        double Y = this.getEyePosition().y + (livingentity.getEyePosition().y - this.getEyePosition().y) * ((double) iii / (num_pts_line));
                        double Z = this.getEyePosition().z + (livingentity.getEyePosition().z - this.getEyePosition().z) * ((double) iii / (num_pts_line));
                        this.getLevel().addParticle(DustParticleOptions.REDSTONE, X, Y, Z, 0, 0, 0);
                    }
                    for (i = 0; i <= num_pts; i = i + 1) {
                        double[] indices = IntStream.rangeClosed(0, (int) ((100)))
                                .mapToDouble(x -> x * 1 + 0).toArray();

                        double phi = Math.acos(1 - 2 * indices[i] / num_pts);
                        double theta = Math.PI * (1 + Math.pow(5, 0.5) * indices[i]);
                        double x = cos(theta) * sin(phi);
                        double y = Math.sin(theta) * sin(phi);
                        double z = cos(phi);
                        this.getLevel().addParticle((ParticleOptions) DustParticleOptions.REDSTONE, livingentity.getEyePosition().x + 1.5 * x, livingentity.getEyePosition().y + 1.5 * y, livingentity.getEyePosition().z + 1.5 * z, 0, 0, 0);
                    }
                    livingentity.invulnerableTime = 0;
                    livingentity.hurt(new EntityDamageSource("spell", SentinelEntity.this), 6);
                    livingentity.invulnerableTime = 0;
                }
            }
    }

    class VexRandomMoveGoal extends Goal {
        public VexRandomMoveGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            return !SentinelEntity.this.getMoveControl().hasWanted() && SentinelEntity.this.random.nextInt(reducedTickDelay(7)) == 0;
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void tick() {
            BlockPos blockpos = SentinelEntity.this.blockPosition();
            Optional<BlockPos> totem = BlockPos.findClosestMatch(SentinelEntity.this.getOnPos(),64, 64, (p_186148_) -> {
                return SentinelEntity.this.getLevel().getBlockState(p_186148_).is(ModBlocks.SENTINEL_TOTEM_BLOCK.get());
            });
            if (totem.isPresent()) {
                blockpos = totem.get();
                SentinelEntity.this.setNoGravity(true);
                SentinelEntity.this.noPhysics = true;
                SentinelEntity.this.setPersistenceRequired();
                for (int i = 0; i < 3; ++i) {
                    BlockPos blockpos1 = blockpos.offset(SentinelEntity.this.random.nextInt(15) - 7, SentinelEntity.this.random.nextInt(11) - 5, SentinelEntity.this.random.nextInt(15) - 7);
                    if (SentinelEntity.this.level.isEmptyBlock(blockpos1)) {
                        SentinelEntity.this.moveControl.setWantedPosition((double) blockpos1.getX() + 0.5D, (double) blockpos1.getY() + 0.5D, (double) blockpos1.getZ() + 0.5D, 0.25D);
                        if (SentinelEntity.this.getTarget() == null) {
                            SentinelEntity.this.getLookControl().setLookAt((double) blockpos1.getX() + 0.5D, (double) blockpos1.getY() + 0.5D, (double) blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
                        }
                        break;
                    }
                }
            }
            else{
                SentinelEntity.this.moveControl.setWantedPosition(SentinelEntity.this.getX(),SentinelEntity.this.getY(),SentinelEntity.this.getZ(),0.25D);
                SentinelEntity.this.setNoGravity(false);
                SentinelEntity.this.noPhysics = false;
                SentinelEntity.this.noTotem = true;

            }

        }
    }
    class SentinelMoveTowardsEnemyGoal extends Goal {
        public SentinelMoveTowardsEnemyGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            if (SentinelEntity.this.getTarget() != null /*&& SentinelEntity.this.random.nextInt(reducedTickDelay(7)) == 0*/) {
                Optional<BlockPos> totem = BlockPos.findClosestMatch(SentinelEntity.this.getTarget().getOnPos(),8, 8, (p_186148_) -> {
                    return SentinelEntity.this.getLevel().getBlockState(p_186148_).is(ModBlocks.SENTINEL_TOTEM_BLOCK.get());
                });
                if (totem.isPresent()){
                    return true;
                }
                else{
                    return false;
                }
            } else {
                return false;
            }
        }

        public boolean canContinueToUse() {
            return SentinelEntity.this.getTarget() != null && SentinelEntity.this.getTarget().isAlive();
        }

        public void start() {
            LivingEntity livingentity = SentinelEntity.this.getTarget();
            if (livingentity != null ) {
                float f7 = livingentity.getYHeadRot();
                float f8 = livingentity.getYRot()+60;
                float f9 = livingentity.getYRot()-60;
                float f = livingentity.getXRot();
                float f1 = -Mth.sin(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                float f2 = -Mth.sin(f * ((float) Math.PI / 180F));
                float f3 = Mth.cos(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));

                Vec3 vec3 = livingentity.getEyePosition();
                SentinelEntity.this.moveControl.setWantedPosition(vec3.x+f1*5, vec3.y+f2*5, vec3.z+f3*5, 1.0D);
            }

        }

        public void stop() {
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingentity = SentinelEntity.this.getTarget();
            if (livingentity != null) {
                float f7 = livingentity.getYHeadRot();
                float f8 = livingentity.getYRot()+60;
                float f9 = livingentity.getYRot()-60;
                float f = livingentity.getXRot();
                float f1 = -Mth.sin(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                float f2 = -Mth.sin(f * ((float) Math.PI / 180F));
                float f3 = Mth.cos(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));

                double d0 = SentinelEntity.this.distanceToSqr(livingentity);
                if (d0 < 64*64) {

                    Vec3 vec3 = livingentity.getEyePosition();
                    SentinelEntity.this.moveControl.setWantedPosition(vec3.x+f1*5, vec3.y+f2*5, vec3.z+f3*5, 1.05);
                }

            }
        }
    }

}
