package com.cleannrooster.spellblademod.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import static com.cleannrooster.spellblademod.items.ParticlePacket2.z;
import static java.lang.Math.sqrt;
import static net.minecraft.util.Mth.clamp;

public class VolleyballEntity extends PathfinderMob{
    private final BodyRotationControl bodyControl;
    private boolean strafingbackwards;
    private boolean strafingClockwise;
    private boolean jumping2;
    protected VolleyballEntity(EntityType<? extends VolleyballEntity> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
        this.moveControl = new MoveControl(this);
        this.lookControl = new LookControl(this);
        this.bodyControl = new BodyRotationControl((this));
        this.navigation = new GroundPathNavigation(this, p_21684_);
        this.jumpControl = new JumpControl(this);

    }

    Entity target = null;

    protected void registerGoals() {
        this.addBehaviourGoals();
    }

    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.6D, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 35.0D).add(Attributes.MOVEMENT_SPEED, (double)1F).add(Attributes.ATTACK_DAMAGE, 3.0D).add(Attributes.ARMOR, 2.0D).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.jumping){
            this.setSpeed(-10);
        }
        /*this.target = this.findTarget();

        *//*this.getLookControl().tick();

        this.getMoveControl().tick();
        this.getJumpControl().tick();*//*
        this.setYRot(this.getYHeadRot());
        Player player2 = this.getLevel().getNearestPlayer(this, 64);
        if(player2 != null && target == null) {
            this.getLookControl().setLookAt(player2);
        }
        if(this.target != null){
            this.getLookControl().setLookAt(this.target);
        }
        if(player2 != null){
            if(this.distanceTo(player2) < 20){
                this.getLookControl().setLookAt(player2);
                this.getMoveControl().strafe(-10, 0);
            }
        }
        if(!this.getLevel().isClientSide) {
            if (target != null) {
                Entity livingentity = target;
                if (livingentity instanceof BouncingEntity bouncing && player2 != null) {
                    LivingEntity player = bouncing.thrower;

                    double x = livingentity.getX();
                    double y = livingentity.getY();
                    double z = livingentity.getZ();
                    if(player != null) {


                        if(this.distanceTo(player) < 20){
                            x = x + (bouncing.getDeltaMovement().x)/(bouncing.getDeltaMovement().length())*20;
                            z = z + (bouncing.getDeltaMovement().z)/(bouncing.getDeltaMovement().length())*20;
                        }
                        if(bouncing.closerThan(this,this.distanceTo(player))) {
                            this.getMoveControl().setWantedPosition(x, y, z, 0.6);
                            this.getNavigation().createPath(x,y,z,1);
                        }
                        System.out.print("if");

                        if (bouncing.changetime == 0 && bouncing.thrower != null && !this.getLevel().isClientSide) {
                            {
                                if ((this.distanceTo(target) < 5 && player != this)) {
                                    System.out.println("hit");
                                    this.swing(InteractionHand.MAIN_HAND);
                                    bouncing.hurt(DamageSource.mobAttack(this), 1);
                                }
                            }
                        }
                    }
                }
            }
        }
*/
    }


    public Entity findTarget() {
        return this.getNearestEntity(this.getLevel().getEntitiesOfClass(BouncingEntity.class,  this.getTargetSearchArea(64)),TargetingConditions.forCombat(),this,this.getX(),this.getY(),this.getZ());
    }
    @Nullable
    public static Entity getNearestEntity(List<? extends Entity> p_45983_, TargetingConditions p_45984_, @Nullable LivingEntity p_45985_, double p_45986_, double p_45987_, double p_45988_) {
        double d0 = -1.0D;
        Entity t = null;

        for(Entity t1 : p_45983_) {
            double d1 = t1.distanceToSqr(p_45986_, p_45987_, p_45988_);
            if (d0 == -1.0D || d1 < d0) {
                d0 = d1;
                t = t1;
            }

        }

        return t;
    }
    protected AABB getTargetSearchArea(double p_26069_) {
        return this.getBoundingBox().inflate(p_26069_, p_26069_, p_26069_);
    }

    public class MeleeAttackGoal extends Goal {
        protected final VolleyballEntity mob;
        private final double speedModifier;
        private final boolean followingTargetEvenIfNotSeen;
        private Path path;
        private double pathedTargetX;
        private double pathedTargetY;
        private double pathedTargetZ;
        private int ticksUntilNextPathRecalculation;
        private int ticksUntilNextAttack;
        private final int attackInterval = 20;
        private long lastCanUseCheck;
        private static final long COOLDOWN_BETWEEN_CAN_USE_CHECKS = 20L;
        private int failedPathFindingPenalty = 0;
        private boolean canPenalize = false;

        public MeleeAttackGoal(VolleyballEntity p_25552_, double p_25553_, boolean p_25554_) {
            this.mob = p_25552_;
            this.speedModifier = p_25553_;
            this.followingTargetEvenIfNotSeen = p_25554_;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean canUse() {
            return true;
        }

        public boolean canContinueToUse() {
            return true;

        }

        public void start() {

        }

        public void stop() {

        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            this.mob.target = this.mob.findTarget();

        /*this.getLookControl().tick();

        this.getMoveControl().tick();
        this.getJumpControl().tick();*/
            this.mob.setYRot(this.mob.getYHeadRot());
            Player player2 = this.mob.getLevel().getNearestPlayer(this.mob, 64);
            if(player2 != null && target == null) {
                this.mob.getLookControl().setLookAt(player2);
            }
            if(this.mob.target != null){
                this.mob.getLookControl().setLookAt(this.mob.target);
            }
            /*if(player2 != null){
                if(this.mob.distanceTo(player2) < 20){
                    this.mob.getLookControl().setLookAt(player2);
                    this.mob.getMoveControl().strafe(-10, 0);
                }
            }*/
            /*if(this.mob.horizontalCollision){
                this.mob.getJumpControl().jump();
            }*/
            if(!this.mob.getLevel().isClientSide) {
                if (target != null) {
                    Entity livingentity = target;
                    if (livingentity instanceof BouncingEntity bouncing && player2 != null) {
                        LivingEntity player = bouncing.thrower;

                        double x = livingentity.getX();
                        double y = livingentity.getY();
                        double z = livingentity.getZ();
                        if(player != null) {


                            if(this.mob.distanceTo(player) < 20 ){
                                if( bouncing.getDeltaMovement().x == 0){
                                    x = x - Math.sin(Math.PI*player.getYHeadRot()/180)*20;
                                }
                                else {

                                    x = x + (bouncing.getDeltaMovement().x) / Math.sqrt(bouncing.getDeltaMovement().x*bouncing.getDeltaMovement().x + bouncing.getDeltaMovement().z()*bouncing.getDeltaMovement().z) * 20;
                                }
                                if( bouncing.getDeltaMovement().z == 0){
                                    z = z + Math.cos(Math.PI*player.getYHeadRot()/180)*20;
                                }
                                else {

                                    z = z + (bouncing.getDeltaMovement().z) / Math.sqrt(bouncing.getDeltaMovement().x*bouncing.getDeltaMovement().x + bouncing.getDeltaMovement().z()*bouncing.getDeltaMovement().z) * 20;
                                }
                            }

                            if(player != this.mob) {
                                this.mob.getMoveControl().setWantedPosition(x, y, z, 0.6);

                            }

                            if (bouncing.changetime == 0 && bouncing.thrower != null && !this.mob.getLevel().isClientSide) {
                                {
                                    if ((this.mob.distanceTo(target) < 5 && player != this.mob)) {
                                        this.mob.swing(InteractionHand.MAIN_HAND);
                                        bouncing.hurt(DamageSource.mobAttack(this.mob), 1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        protected void checkAndPerformAttack(LivingEntity p_25557_, double p_25558_) {
            double d0 = this.getAttackReachSqr(p_25557_);
            if (p_25558_ <= d0 && this.ticksUntilNextAttack <= 0) {
                this.resetAttackCooldown();
                this.mob.swing(InteractionHand.MAIN_HAND);
                this.mob.doHurtTarget(p_25557_);
            }

        }

        protected void resetAttackCooldown() {
            this.ticksUntilNextAttack = this.adjustedTickDelay(20);
        }

        protected boolean isTimeToAttack() {
            return this.ticksUntilNextAttack <= 0;
        }

        protected int getTicksUntilNextAttack() {
            return this.ticksUntilNextAttack;
        }

        protected int getAttackInterval() {
            return this.adjustedTickDelay(20);
        }

        protected double getAttackReachSqr(LivingEntity p_25556_) {
            return (double) (this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + p_25556_.getBbWidth());
        }
    }
        @Nullable
        protected Predicate<Entity> targetConditions;








    }
