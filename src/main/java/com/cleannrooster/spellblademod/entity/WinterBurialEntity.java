package com.cleannrooster.spellblademod.entity;

import com.cleannrooster.spellblademod.items.FriendshipBracelet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class WinterBurialEntity extends FallingBlockEntity {
    public Player owner;
    public BlockState getBlockState() {
        return Blocks.SNOW_BLOCK.defaultBlockState();
    }
    public BlockPos blockpos;
    public int size;
    public int remaining;
    public ArrayList<BlockPos> list = new ArrayList<>();

    public boolean isTip;
    public WinterBurialEntity(Level p_201972_, double v, double y, double v1, BlockState blockState, Player player, int size, BlockPos blockPos, int remaining) {
        super(ModEntities.WINTERBURIAL.get(),p_201972_);
        this.blocksBuilding = true;
        this.setPos(v, y, v1);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = v;
        this.size = size;
        this.yo = y;
        this.zo = v1;
        this.blockpos = blockPos;
        this.remaining = remaining;
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

    protected WinterBurialEntity(EntityType<? extends WinterBurialEntity> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }
    public WinterBurialEntity(EntityType<? extends WinterBurialEntity> p_36833_, Level p_36834_, Player player, BlockPos blockPos, int remaining) {
        super(p_36833_, p_36834_);
        this.setOwner(player);
        BlockState base = Blocks.SNOW.defaultBlockState();
        WinterBurialEntity.fall(this.level,blockPos,base,player,1, blockPos, remaining);

    }
    public static WinterBurialEntity fall(Level p_201972_, BlockPos p_201973_, BlockState p_201974_, Player player, int size, BlockPos blockPos, int remaining) {

        WinterBurialEntity fallingblockentity = new WinterBurialEntity(p_201972_, (double)p_201973_.getX() + 0.5D, (double)p_201973_.getY(), (double)p_201973_.getZ() + 0.5D, p_201974_.hasProperty(BlockStateProperties.WATERLOGGED) ? p_201974_.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(false)) : p_201974_, player, size, blockPos,remaining);
        if(!p_201972_.isClientSide()) {
            p_201972_.addFreshEntity(fallingblockentity);
        }
        return fallingblockentity;
    }

    @Override
    public boolean canBeCollidedWith() {
        return super.canBeCollidedWith();
    }

    public void positionRider(Entity p_20312_) {
        this.positionRider(p_20312_, Entity::setPos);
    }

    private void positionRider(Entity p_19957_, Entity.MoveFunction p_19958_) {
        if (this.hasPassenger(p_19957_)) {
            double d0 = this.getY();
            p_19958_.accept(p_19957_, this.getX(), d0, this.getZ());
        }
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
        if(this.tickCount < 3 +this.remaining*3 && !this.getLevel().isClientSide()){
            this.setPos(this.position().add(0,1F/5F,0));
            List<LivingEntity> list = this.getLevel().getEntitiesOfClass(LivingEntity.class,this.getBoundingBox());
            if(!list.isEmpty() && this.size == 1){
                for(LivingEntity living : list){
                    if(this.canAddPassenger(living) && !living.isPassenger() && living != this.owner && FriendshipBracelet.PlayerFriendshipPredicate(this.owner,living)){
                        living.startRiding(this);
                    }
                }
            }
        }
        if(this.tickCount > 100 - this.remaining*2 && !this.getLevel().isClientSide()){
            ListIterator<Entity> list2 = this.getPassengers().listIterator();
            list2.forEachRemaining(entity -> {
                if(!(entity instanceof Player)){
                    entity.stopRiding();
                }
            });
            this.setPos(this.position().add(0,-1F/5F,0));
        }
        if(this.tickCount > 110 && !this.getLevel().isClientSide()){
            this.discard();
        }
        if(this.tickCount % 10 == 0 && !this.getLevel().isClientSide()){
            List<LivingEntity> list1 = this.getLevel().getEntitiesOfClass(LivingEntity.class,this.getBoundingBox(),livingEntity -> livingEntity != this.owner && FriendshipBracelet.PlayerFriendshipPredicate(this.owner,livingEntity));
            for(LivingEntity living : list1){
                living.hurt(new EntityDamageSource("freeze",this.owner),2);
            }
        }
        List<WinterBurialEntity> list1 = this.getLevel().getEntitiesOfClass(WinterBurialEntity.class,this.getBoundingBox().inflate(8),winterBurialEntity -> winterBurialEntity.owner == this.owner);
        for(WinterBurialEntity entity : list1){
            if(entity != null){
                if(!entity.list.contains(this.blockpos)){
                    entity.list.add(this.blockpos);
                }
            }
        }
        if(this.tickCount == 3 && this.remaining > 0 && this.size == 1 && !this.getLevel().isClientSide()){



            if(!this.list.contains( this.blockpos.east()) && this.getLevel().getBlockState(this.blockpos.east()).getMaterial().blocksMotion()) {
                WinterBurialEntity entity1 = new WinterBurialEntity(ModEntities.WINTERBURIAL.get(), this.getLevel(), this.owner, this.blockpos.east(), this.remaining - 1);
            }

            if(!this.list.contains( this.blockpos.south()) && this.getLevel().getBlockState(this.blockpos.south()).getMaterial().blocksMotion()) {

                WinterBurialEntity entity3 =new WinterBurialEntity(ModEntities.WINTERBURIAL.get(), this.getLevel(), this.owner, this.blockpos.south(), this.remaining - 1);
            }
            if(!this.list.contains( this.blockpos.west()) && this.getLevel().getBlockState(this.blockpos.west()).getMaterial().blocksMotion()) {

                WinterBurialEntity entity2 =new WinterBurialEntity(ModEntities.WINTERBURIAL.get(), this.getLevel(), this.owner, this.blockpos.west(), this.remaining - 1);

            }
            if(!this.list.contains( this.blockpos.north()) && this.getLevel().getBlockState(this.blockpos.north()).getMaterial().blocksMotion()) {

                WinterBurialEntity entity4 =new WinterBurialEntity(ModEntities.WINTERBURIAL.get(), this.getLevel(), this.owner, this.blockpos.north(), this.remaining - 1);

            }

        }
    }
}
