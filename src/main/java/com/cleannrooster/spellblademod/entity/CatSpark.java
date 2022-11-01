package com.cleannrooster.spellblademod.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.UUID;

public class CatSpark extends Cat{

    public CatSpark(EntityType<? extends CatSpark> p_28114_, Level p_28115_) {
        super(p_28114_, p_28115_);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(3, new PanicAlwaysGoal(this,1));
        this.goalSelector.addGoal(1, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(2, new CatSpark.SpiderAttackGoal(this));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 16.0D).add(Attributes.MOVEMENT_SPEED, (double)1F);
    }
    @Override
    protected void playStepSound(BlockPos p_33804_, BlockState p_33805_) {
        if (!p_33805_.getMaterial().isLiquid()) {
            BlockState blockstate = this.level.getBlockState(p_33804_.above());
            SoundType soundtype = blockstate.is(Blocks.SNOW) ? blockstate.getSoundType(level, p_33804_, this) : p_33805_.getSoundType(level, p_33804_, this);
            this.playSound(soundtype.getStepSound(), soundtype.getVolume() * 0.15F, soundtype.getPitch());
        }
    }
    @Override
    public void tick() {
        if(this.tickCount > 160){
            this.discard();
        }
        super.tick();
    }

    @Override
    public boolean onClimbable() {
        return this.horizontalCollision;
    }

    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) {
        return false;
    }

    @Override
    public boolean skipAttackInteraction(Entity p_20357_) {
        return true;
    }

    @Override
    protected void dropAllDeathLoot(DamageSource p_21192_) {
        return;
    }

    @Override
    protected boolean shouldDropLoot() {
        return false;
    }
    @Override
    protected void dropExperience() {
        return;
    }

    @Override
    protected void dropFromLootTable(DamageSource p_21389_, boolean p_21390_) {
        return;
    }

    @Override
    protected boolean shouldDropExperience() {
        return false;
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }
    @Override
    public boolean isAttackable() {
        return false;
    }

    public class FloatGoal extends Goal {
        private final Mob mob;

        public FloatGoal(Mob p_25230_) {
            this.mob = p_25230_;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP));
            p_25230_.getNavigation().setCanFloat(true);
        }

        public boolean canUse() {
            return this.mob.isInWater() && this.mob.getFluidHeight(FluidTags.WATER) > this.mob.getFluidJumpThreshold() || this.mob.isInLava();
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            if (this.mob.getRandom().nextFloat() < 0.8F) {
                this.mob.getJumpControl().jump();
            }
        }
    }
    static class SpiderAttackGoal extends MeleeAttackGoal {
        public SpiderAttackGoal(CatSpark p_33822_) {
            super(p_33822_, 1.0D, true);
        }

        public boolean canUse() {
            return super.canUse() && !this.mob.isVehicle();
        }

        public boolean canContinueToUse() {
            return true;
        }

        protected double getAttackReachSqr(LivingEntity p_33825_) {
            return (double)(4.0F + p_33825_.getBbWidth());
        }
    }
}
