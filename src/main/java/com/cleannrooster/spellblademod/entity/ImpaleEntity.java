package com.cleannrooster.spellblademod.entity;

import com.cleannrooster.spellblademod.items.FriendshipBracelet;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.List;

public class ImpaleEntity extends FallingBlockEntity {
    public Player owner;
    public BlockState getBlockState() {
        if(this.getCustomName().getString().equals("0")) return Blocks.POINTED_DRIPSTONE.defaultBlockState().setValue(BlockStateProperties.VERTICAL_DIRECTION, Direction.UP).setValue(BlockStateProperties.DRIPSTONE_THICKNESS, DripstoneThickness.TIP);
        else if (this.getCustomName().getString().equals("1")) return Blocks.POINTED_DRIPSTONE.defaultBlockState().setValue(BlockStateProperties.VERTICAL_DIRECTION, Direction.UP).setValue(BlockStateProperties.DRIPSTONE_THICKNESS, DripstoneThickness.MIDDLE);
        else return Blocks.POINTED_DRIPSTONE.defaultBlockState().setValue(BlockStateProperties.VERTICAL_DIRECTION, Direction.UP).setValue(BlockStateProperties.DRIPSTONE_THICKNESS, DripstoneThickness.BASE);
    }

    public boolean isTip;
    public ImpaleEntity(Level p_201972_, double v, double y, double v1, BlockState blockState, Player player,int size) {
        super(ModEntities.IMPALE.get(),p_201972_);
        this.blocksBuilding = true;
        this.setPos(v, y, v1);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = v;
        this.yo = y;
        this.zo = v1;
        this.setStartPos(this.blockPosition());
        this.setOwner(player);
        this.setCustomName(new TranslatableComponent(String.valueOf(size)));
    }

    @Override
    public boolean shouldShowName() {
        return false;
    }

    public void setOwner(Player player){
        this.owner = player;
    }

    protected ImpaleEntity(EntityType<? extends ImpaleEntity> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }
    public ImpaleEntity(EntityType<? extends ImpaleEntity> p_36833_, Level p_36834_, Player player, BlockPos blockPos) {
        super(p_36833_, p_36834_);
        this.setOwner(player);
        this.setRot(0,-90);
        BlockState tip = Blocks.POINTED_DRIPSTONE.defaultBlockState().setValue(BlockStateProperties.VERTICAL_DIRECTION, Direction.UP).setValue(BlockStateProperties.DRIPSTONE_THICKNESS, DripstoneThickness.TIP);
        BlockState base = Blocks.POINTED_DRIPSTONE.defaultBlockState().setValue(BlockStateProperties.VERTICAL_DIRECTION, Direction.UP).setValue(BlockStateProperties.DRIPSTONE_THICKNESS, DripstoneThickness.BASE);
        ImpaleEntity.fall(level,blockPos.above().above(),tip,player,0);
        ImpaleEntity.fall(level,blockPos.above(),base,player,1);
        ImpaleEntity.fall(level,blockPos,base,player,2);

    }
    public static ImpaleEntity fall(Level p_201972_, BlockPos p_201973_, BlockState p_201974_, Player player, int size) {
        ImpaleEntity fallingblockentity = new ImpaleEntity(p_201972_, (double)p_201973_.getX() + 0.5D, (double)p_201973_.getY(), (double)p_201973_.getZ() + 0.5D, p_201974_.hasProperty(BlockStateProperties.WATERLOGGED) ? p_201974_.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(false)) : p_201974_, player, size);
        if(!p_201972_.isClientSide()) {
            p_201972_.addFreshEntity(fallingblockentity);
        }
        return fallingblockentity;
    }


    @Override
    public void tick() {
        if(this.owner == null){
            if(!this.getLevel().isClientSide()) {
                this.discard();
                return;
            }
        }
        this.setNoGravity(true);
        this.noPhysics = true;
        if(this.tickCount < 3 && !this.getLevel().isClientSide()){
            this.setPos(this.position().add(0,2F/5F,0));
            List<LivingEntity> list = this.getLevel().getEntitiesOfClass(LivingEntity.class,this.getBoundingBox().expandTowards(0,0.5,0).inflate(0.5));
            if(this.getCustomName().getString().equals("2") && !list.isEmpty()){
                for(LivingEntity living : list){
                    if(this.canAddPassenger(living) && living != this.owner && FriendshipBracelet.PlayerFriendshipPredicate(this.owner,living)){
                        living.hurt( new EntityDamageSource("impale",this.owner),8);
                        living.startRiding(this);
                    }
                }
            }
        }
        if(this.tickCount > 100  && !this.getLevel().isClientSide()){
            if(this.getCustomName().getString().equals("1")) {
                this.getLevel().explode(this.owner, this.getX(), this.getY(), this.getZ(), 1.5F, Explosion.BlockInteraction.NONE);


            }
            this.discard();
        }
    }
}
