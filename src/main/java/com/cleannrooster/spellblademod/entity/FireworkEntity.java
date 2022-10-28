package com.cleannrooster.spellblademod.entity;

import com.cleannrooster.spellblademod.manasystem.network.FireworkHandler;
import com.cleannrooster.spellblademod.setup.Messages;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;
import java.util.OptionalInt;

public class FireworkEntity extends Projectile implements ItemSupplier {
    public static final EntityDataAccessor<ItemStack> DATA_ID_FIREWORKS_ITEM = SynchedEntityData.defineId(FireworkEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<OptionalInt> DATA_ATTACHED_TO_TARGET = SynchedEntityData.defineId(FireworkEntity.class, EntityDataSerializers.OPTIONAL_UNSIGNED_INT);
    private static final EntityDataAccessor<Boolean> DATA_SHOT_AT_ANGLE = SynchedEntityData.defineId(FireworkEntity.class, EntityDataSerializers.BOOLEAN);
    private int life;
    private int lifetime;
    public float damage = 6;
    @Nullable
    private LivingEntity attachedToEntity;
    private ItemStack item = new ItemStack(Items.FIREWORK_STAR);

    public FireworkEntity(EntityType<? extends FireworkEntity> p_37027_, Level p_37028_) {
        super(p_37027_, p_37028_);
    }
    public FireworkEntity(Level p_37030_, double p_37031_, double p_37032_, double p_37033_, ItemStack p_37034_) {
        super(ModEntities.FIREWORK.get(), p_37030_);
        this.life = 0;
        this.setPos(p_37031_, p_37032_, p_37033_);
        int i = 1;
        if (!p_37034_.isEmpty() && p_37034_.hasTag()) {
            this.entityData.set(DATA_ID_FIREWORKS_ITEM, p_37034_.copy());
            i += p_37034_.getOrCreateTagElement("Fireworks").getByte("Flight");
        }

        //this.setDeltaMovement(this.random.nextGaussian() * 0.001D, 0.05D, this.random.nextGaussian() * 0.001D);
        this.lifetime = 10 * i + this.random.nextInt(6) + this.random.nextInt(7);
    }
    public FireworkEntity(Level p_37043_, ItemStack p_37044_, double p_37045_, double p_37046_, double p_37047_, boolean p_37048_) {
        this(p_37043_, p_37045_, p_37046_, p_37047_, p_37044_);
        this.entityData.set(DATA_SHOT_AT_ANGLE, p_37048_);
    }
    public FireworkEntity(Level p_37036_, @Nullable Entity p_37037_, double p_37038_, double p_37039_, double p_37040_, ItemStack p_37041_, ItemStack star) {
        this(p_37036_, p_37038_, p_37039_, p_37040_, p_37041_);
        this.setOwner(p_37037_);
        this.item = star;
    }
    protected void defineSynchedData() {
        this.entityData.define(DATA_ID_FIREWORKS_ITEM, ItemStack.EMPTY);
        this.entityData.define(DATA_ATTACHED_TO_TARGET, OptionalInt.empty());
        this.entityData.define(DATA_SHOT_AT_ANGLE, false);
    }
    @Override
    public void tick() {
        this.baseTick();
        HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
        if (!this.noPhysics) {
            if(hitresult.getType() != HitResult.Type.MISS) {
                this.onHit(hitresult);
            }
        }

        if (this.life == 0 && !this.isSilent()) {
            this.level.playSound((Player)null, this.getX(), this.getY(), this.getZ(), SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.AMBIENT, 3.0F, 1.0F);
        }

        ++this.life;
        if (this.level.isClientSide && this.life % 2 < 2) {
            this.level.addParticle(ParticleTypes.FIREWORK, this.getX(), this.getY(), this.getZ(), this.random.nextGaussian() * 0.05D, -this.getDeltaMovement().y * 0.5D, this.random.nextGaussian() * 0.05D);
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
            f = 1;
        }
        if(!this.getLevel().isClientSide()) {
            if (!this.isNoGravity()) {
                Vec3 vec31 = this.getDeltaMovement();
                this.setDeltaMovement(vec31.x, vec31.y - (double) 0.06, vec31.z);
            }
        }

        this.setPos(d2, d0, d1);
    }
    public boolean isShotAtAngle() {
        return false;
    }

    public void handleEntityEvent(byte p_37063_) {

        ItemStack itemstack = this.entityData.get(DATA_ID_FIREWORKS_ITEM);
        if(this.level instanceof ServerLevel serverLevel){
            for(ServerPlayer player : serverLevel.players()) {
                FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
                buf.writeNbt(itemstack.getTagElement("Fireworks"));
                buf.writeInt(this.getId());

                Messages.sendToPlayer(new FireworkHandler(buf),player);
            }

        }

    }

    public void addAdditionalSaveData(CompoundTag p_37075_) {
        super.addAdditionalSaveData(p_37075_);

        p_37075_.putInt("Life", this.life);
        p_37075_.putInt("LifeTime", this.lifetime);
        ItemStack itemstack = this.entityData.get(DATA_ID_FIREWORKS_ITEM);
        if (!itemstack.isEmpty()) {
            p_37075_.put("FireworksItem", itemstack.save(new CompoundTag()));
        }

        p_37075_.putBoolean("ShotAtAngle", this.entityData.get(DATA_SHOT_AT_ANGLE));

    }

    public void readAdditionalSaveData(CompoundTag p_37073_) {
        super.readAdditionalSaveData(p_37073_);
        this.life = p_37073_.getInt("Life");
        this.lifetime = p_37073_.getInt("LifeTime");
        ItemStack itemstack = ItemStack.of(p_37073_.getCompound("FireworksItem"));
        if (!itemstack.isEmpty()) {
            this.entityData.set(DATA_ID_FIREWORKS_ITEM, itemstack);
        }

        if (p_37073_.contains("ShotAtAngle")) {
            this.entityData.set(DATA_SHOT_AT_ANGLE, p_37073_.getBoolean("ShotAtAngle"));
        }
    }
    @Override
    public ItemStack getItem() {
        ItemStack star = new ItemStack(Items.FIREWORK_STAR);
        if(this.entityData.get(DATA_ID_FIREWORKS_ITEM).getTagElement("Fireworks") != null) {
            CompoundTag tag = this.entityData.get(DATA_ID_FIREWORKS_ITEM).getTagElement("Fireworks");
            star.setTag(tag);
        }
        return star;
    }
    private boolean hasExplosion() {
        ItemStack itemstack = this.entityData.get(DATA_ID_FIREWORKS_ITEM);
        CompoundTag compoundtag = itemstack.isEmpty() ? null : itemstack.getTagElement("Fireworks");

        ListTag listtag = compoundtag != null ? compoundtag.getList("Explosions", 10) : null;
        return listtag != null && !listtag.isEmpty();
    }



    private void dealExplosionDamage() {
        float f = 0.0F;
        ItemStack itemstack = this.entityData.get(DATA_ID_FIREWORKS_ITEM);
        CompoundTag compoundtag = itemstack.isEmpty() ? null : itemstack.getTagElement("Fireworks");

        ListTag listtag = compoundtag != null ? compoundtag.getList("Explosions", 10) : null;
        if (listtag != null && !listtag.isEmpty()) {
            f = 5.0F + (float)(listtag.size() * 2);
        }

        if (f > 0.0F && this.getOwner() != null) {
            if (this.attachedToEntity != null) {
                this.attachedToEntity.hurt(DamageSource.explosion((LivingEntity) this.getOwner()), (float)Math.max(6,(this.damage)));
            }

            double d0 = 5.0D;
            Vec3 vec3 = this.position();

            for(LivingEntity livingentity : this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.0D))) {
                if (livingentity != this.attachedToEntity && !(this.distanceToSqr(livingentity) > 25.0D)) {
                    boolean flag = false;

                    for(int i = 0; i < 2; ++i) {
                        Vec3 vec31 = new Vec3(livingentity.getX(), livingentity.getY(0.5D * (double)i), livingentity.getZ());
                        HitResult hitresult = this.level.clip(new ClipContext(vec3, vec31, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
                        if (hitresult.getType() == HitResult.Type.MISS) {
                            flag = true;
                            break;
                        }
                    }

                    if (flag) {
                        float f1 = f * (float)Math.sqrt((5.0D - (double)this.distanceTo(livingentity)) / 5.0D);
                        livingentity.hurt(DamageSource.explosion((LivingEntity) this.getOwner()), (float)Math.max(6,(this.damage)));
                    }
                }
            }
        }

    }
    public boolean shouldRenderAtSqrDistance(double p_37065_) {
        return p_37065_ < 4096.0D && !this.isAttachedToEntity();
    }

    public boolean shouldRender(double p_37083_, double p_37084_, double p_37085_) {
        return super.shouldRender(p_37083_, p_37084_, p_37085_) && !this.isAttachedToEntity();
    }

    @Override
    protected void onHit(HitResult result) {
        if (!this.level.isClientSide && hasExplosion()) {
            this.explode();
        }
    }

    private void explode() {

        this.handleEntityEvent( (byte)17);
        this.gameEvent(GameEvent.EXPLODE, this);
        this.dealExplosionDamage();
        this.discard();
    }

    protected void onHitEntity(EntityHitResult p_37071_) {
        if (!this.level.isClientSide && hasExplosion()) {
            this.explode();
        }
    }

    protected void onHitBlock(BlockHitResult p_37069_) {
        BlockPos blockpos = new BlockPos(p_37069_.getBlockPos());
        this.level.getBlockState(blockpos).entityInside(this.level, blockpos, this);
        if (!this.level.isClientSide() && this.hasExplosion()) {
            this.explode();
        }
    }
    private boolean isAttachedToEntity() {
        return false;
    }

    public boolean isAttackable() {
        return false;
    }

}
