package com.cleannrooster.spellblademod.entity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class InvisiVex extends Vex {
    public Player owner2;
    public int number = 0;
    public int number2 = 8;

    public InvisiVex(EntityType<? extends InvisiVex> p_33984_, Level p_33985_, Player owner) {
        super(p_33984_, p_33985_);
        this.owner2 = owner;
        this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 190, 0, false, false));
        this.setInvisible(true);
        this.setSilent(true);

    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 14.0D).add(Attributes.ATTACK_DAMAGE, 4.0D);
    }

    public InvisiVex(EntityType<InvisiVex> invisiVexEntityType, Level level) {
        super(invisiVexEntityType, level);
        this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 190, 0));
        this.setInvisible(true);
        this.setSilent(true);

    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(4, new InvisiVex.InvisiVexChargeAttackGoal());
        this.goalSelector.addGoal(5, new FollowPersonGoal(this, 2D, 3, 20));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, new PlayerHurtByGoal(this));
        this.targetSelector.addGoal(2, new PlayerHurtTargetGoal(this));

    }

    public void tick() {
        super.tick();
        this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 190, 0, false, false));
        if (this.owner2 == null){
            this.kill();
            return;
        }
        if (!this.owner2.isAlive()){
            this.kill();
            return;
        }

    }

    class InvisiVexChargeAttackGoal extends Goal {
        public InvisiVexChargeAttackGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            if (InvisiVex.this.getTarget() != null && !InvisiVex.this.getMoveControl().hasWanted() && InvisiVex.this.random.nextInt(reducedTickDelay(7)) == 0) {
                return InvisiVex.this.distanceToSqr(InvisiVex.this.getTarget()) > 4.0D;
            } else {
                return false;
            }
        }

        public boolean canContinueToUse() {
            return InvisiVex.this.getMoveControl().hasWanted() && InvisiVex.this.isCharging() && InvisiVex.this.getTarget() != null && InvisiVex.this.getTarget().isAlive();
        }

        public void start() {
            LivingEntity livingentity = InvisiVex.this.getTarget();
            if (livingentity != null) {
                Vec3 vec3 = livingentity.getEyePosition();
                InvisiVex.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0D);
            }

            InvisiVex.this.setIsCharging(true);
            InvisiVex.this.playSound(SoundEvents.VEX_CHARGE, 1.0F, 1.0F);
        }

        public void stop() {
            InvisiVex.this.setIsCharging(false);
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingentity = InvisiVex.this.getTarget();
            if (livingentity != null) {
                if (InvisiVex.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                    InvisiVex.this.doHurtTarget(livingentity);
                    InvisiVex.this.setIsCharging(false);
                } else {
                    double d0 = InvisiVex.this.distanceToSqr(livingentity);
                    if (d0 < 9.0D) {
                        Vec3 vec3 = livingentity.getEyePosition();
                        InvisiVex.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0D);
                    }
                }

            }
        }
    }

    public class FollowPersonGoal extends Goal {
        private final Mob mob;/*
    private final Predicate<Player> followPredicate;*/
        @Nullable
        private Player followingMob;
        private final double speedModifier;
        private final PathNavigation navigation;
        private int timeToRecalcPath;
        private final float stopDistance;
        private float oldWaterCost;
        private final float areaSize;

        public FollowPersonGoal(Mob p_25271_, double p_25272_, float p_25273_, float p_25274_) {
            this.mob = p_25271_;
/*        this.followPredicate = (p_25278_) -> {
            return p_25278_ != null && p_25271_.getClass() != p_25278_.getClass();
        };*/
            this.speedModifier = p_25272_;
            this.navigation = p_25271_.getNavigation();
            this.stopDistance = p_25273_;
            this.areaSize = p_25274_;
            this.followingMob = InvisiVex.this.owner2;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
/*        if (!(p_25271_.getNavigation() instanceof GroundPathNavigation) && !(p_25271_.getNavigation() instanceof FlyingPathNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowMobGoal");
        }*/
        }

        public boolean canUse() {
            List<Player> list = this.mob.level.getEntitiesOfClass(Player.class, this.mob.getBoundingBox().inflate((double) this.areaSize));
            if (!list.isEmpty()) {
                this.followingMob = InvisiVex.this.owner2;
                return true;
            }

            return false;
        }

        public boolean canContinueToUse() {
            return this.followingMob != null && !this.navigation.isDone() && this.mob.distanceToSqr(this.followingMob) > (double) (this.stopDistance * this.stopDistance);
        }

        public void start() {
            this.timeToRecalcPath = 0;
            this.oldWaterCost = this.mob.getPathfindingMalus(BlockPathTypes.WATER);
            this.mob.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        }

        public void stop() {
            this.followingMob = null;
            this.navigation.stop();
            this.mob.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
        }

        public void tick() {
            if (this.followingMob != null && !this.mob.isLeashed()) {
                this.mob.getLookControl().setLookAt(this.followingMob, 10.0F, (float) this.mob.getMaxHeadXRot());/*
                if (--this.timeToRecalcPath <= 0) {
                    this.timeToRecalcPath = this.adjustedTickDelay(10);*/
                    double d0 = this.mob.getX() - this.followingMob.getX();
                    double d1 = this.mob.getY() - this.followingMob.getY();
                    double d2 = this.mob.getZ() - this.followingMob.getZ();
                    double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                    double tridentEntity = this.followingMob.getYRot();
                    double f = InvisiVex.this.owner2.getXRot();
                    float g = (float) (-sin(tridentEntity * ((float) Math.PI / 180)) * cos(f * ((float) Math.PI / 180)));
                    float h = (float) -sin(f * ((float) Math.PI / 180));
                    float r = (float) (cos(tridentEntity * ((float) Math.PI / 180)) * cos(f * ((float) Math.PI / 180)));
                    if (this.followingMob.isShiftKeyDown()){
                        this.mob.getMoveControl().setWantedPosition(followingMob.getX() - g + 1.5D*cos((((double) (InvisiVex.this.tickCount % 40) / 40D)) * (2D * (double) Math.PI)+ (2*Math.PI * (double) (number % 8) / 8D) ), (double) followingMob.getBoundingBox().getCenter().y(), followingMob.getZ() - r + 1.5D*sin((((double) (InvisiVex.this.tickCount % 40) / 40D)) * (2 * Math.PI)+ (2*Math.PI * (double) (number % 8) / 8D)) , speedModifier);
                    }
                    else {
                        this.mob.getMoveControl().setWantedPosition(followingMob.getX() - g + cos((((double) (InvisiVex.this.tickCount % 40) / 40D)) * (2D * (double) Math.PI) + (2 * Math.PI * (double) (number % 8) / 8D)), (double) followingMob.getEyeY() + 2D, followingMob.getZ() - r + sin((((double) (InvisiVex.this.tickCount % 40) / 40D)) * (2 * Math.PI) + (2 * Math.PI * (double) (number % 8) / 8D)), speedModifier);
                    }
              /*      System.out.println((double) followingMob.getEyeHeight() + 2D);

                    System.out.println(followingMob.getZ() - r + sin((((double) (InvisiVex.this.tickCount % 40) / 40D)) * (2 * Math.PI) * ((double) (number % 8) / 8D)));*/

/*                    LookControl lookcontrol = this.followingMob.getLookControl();
                    if (d3 <= (double)this.stopDistance || lookcontrol.getWantedX() == this.mob.getX() && lookcontrol.getWantedY() == this.mob.getY() && lookcontrol.getWantedZ() == this.mob.getZ()) {
                        double d4 = this.followingMob.getX() - this.mob.getX();
                        double d5 = this.followingMob.getZ() - this.mob.getZ();
                        this.navigation.moveTo(this.mob.getX() - d4, this.mob.getY(), this.mob.getZ() - d5, this.speedModifier);
                    }*/



            }
        }
    }

    public class PlayerHurtByGoal extends TargetGoal {
        private final LivingEntity tameAnimal;
        private LivingEntity ownerLastHurtBy;
        private int timestamp;
        private final Player player = InvisiVex.this.owner2;

        public PlayerHurtByGoal(LivingEntity p_26107_) {
            super((Mob) p_26107_, false);
            this.tameAnimal = p_26107_;
            this.setFlags(EnumSet.of(Flag.TARGET));
        }

        public boolean canUse() {

            LivingEntity livingentity = player;
            if (livingentity == null) {
                return false;
            } else {
                this.ownerLastHurtBy = livingentity.getLastHurtByMob();
                int i = livingentity.getLastHurtByMobTimestamp();
                return i != this.timestamp && this.canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT);
            }
        }

        public void start() {
            this.mob.setTarget(this.ownerLastHurtBy);
            LivingEntity livingentity = this.player;
            if (livingentity != null) {
                this.timestamp = livingentity.getLastHurtByMobTimestamp();
            }

            super.start();
        }
    }

    public class PlayerHurtTargetGoal extends TargetGoal {
        private final LivingEntity tameAnimal;
        private LivingEntity ownerLastHurt;
        private int timestamp;

        public PlayerHurtTargetGoal(LivingEntity p_26114_) {
            super((Mob) p_26114_, false);
            this.tameAnimal = p_26114_;
            this.setFlags(EnumSet.of(Flag.TARGET));
        }

        public boolean canUse() {
            LivingEntity livingentity = InvisiVex.this.owner2;
            if (livingentity == null) {
                return false;
            } else {
                this.ownerLastHurt = livingentity.getLastHurtMob();
                int i = livingentity.getLastHurtMobTimestamp();
                return i != this.timestamp && this.canAttack(this.ownerLastHurt, TargetingConditions.DEFAULT);
            }
        }

        public void start() {
            if (!ownerLastHurt.getTags().toString().contains("Invisible")) {
                this.mob.setTarget(this.ownerLastHurt);
            }
            LivingEntity livingentity = InvisiVex.this.owner2;
            if (livingentity != null) {
                this.timestamp = livingentity.getLastHurtMobTimestamp();
            }

            super.start();
        }
    }
}

