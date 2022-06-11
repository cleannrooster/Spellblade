package com.cleannrooster.spellblademod.entity;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FireworkParticles;
import net.minecraft.commands.arguments.NbtTagArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.compress.utils.Lists;
import org.lwjgl.system.CallbackI;

import java.sql.Array;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class HammerEntity extends ThrownTrident {

public boolean secondary = false;
    public HammerEntity(EntityType<? extends ThrownTrident> p_37561_, Level p_37562_) {
        super(p_37561_, p_37562_);
    }

    @Override
    protected void onHit(HitResult p_36913_) {
            Random random = new Random();
            SoundEvent event = SoundEvents.GLASS_BREAK;
            this.level.playSound((Player) null, new BlockPos((double)this.getX(),(double)this.getY(),(double)this.getZ()), event, SoundSource.PLAYERS, 1.0F, 1.0F);
            int num_pts = 1000;
            LivingEntity entity = (LivingEntity) this.getOwner();
            if (entity.getEffect(StatusEffectsModded.WARDLOCKED.get()) != null){
                num_pts = 250;
            }
            /*if (this.getLevel().isClientSide)
            {
                ClientLevel level = (ClientLevel) this.getLevel();*/
            int i = 0;
                for ( i = 0; i <= num_pts; i = i + 1) {
                    double[] indices = IntStream.rangeClosed(0, (int) ((1000 - 0) / 1))
                            .mapToDouble(x -> x * 1 + 0).toArray();

                    double phi = Math.acos(1 - 2 * indices[i] / num_pts);
                    double theta = Math.PI * (1 + Math.pow(5, 0.5) * indices[i]);
                    double x = cos(theta) * sin(phi);
                    double y = Math.sin(theta) * sin(phi);
                    double z = cos(phi);

                            this.level.addParticle(ParticleTypes.FIREWORK, this.getX() + 4 * x, this.getY() + 4 * y, this.getZ() + 4 * z, 0, 0, 0);
                        }

            //}
        if (this.getOwner() != null) {

            List entities = this.level.getEntitiesOfClass(LivingEntity.class, new AABB(this.getX() - 4, this.getY() + 0.5 - 4, this.getZ() - 4, this.getX() + 4, this.getY() + 4, this.getZ() + 4));
            Object[] entitiesarray = entities.toArray();
            int entityamount = entitiesarray.length;
            for (int ii = 0; ii < entityamount; ii = ii + 1) {
                LivingEntity target = (LivingEntity) entities.get(ii);
                if (target != this.getOwner()) {
                    target.hurt(DamageSource.trident(this, this.getOwner()), 8);
                    target.invulnerableTime = 0;
                }

            }
        }
        super.onHit(p_36913_);


        this.remove(RemovalReason.DISCARDED);

    }
    public void tick() {
        Entity entity = this.getOwner();
        if (this.tickCount > 80){
            this.discard();
        }
        super.tick();
        if (tickCount%5 == 0 && secondary == false) {
            if (this.getOwner() != null) {
                /*if (this.secondary == false){
                    System.out.println(this.getXRot());
                    System.out.println(this.getYRot());
                }*/
                HammerEntity lance = new HammerEntity(ModEntities.TRIDENT.get(), this.level);

                lance.shootFromRotation(entity, -this.xRotO+10, -this.yRotO+10, 0.0F, 1.6F, 1.0F);
                lance.setPos(this.getEyePosition());
                lance.setOwner(this.getOwner());
                lance.secondary = true;
                lance.pickup = Pickup.DISALLOWED;
                HammerEntity lance2 = new HammerEntity(ModEntities.TRIDENT.get(), this.level);

                lance2.setOwner(this.getOwner());
                lance2.shootFromRotation(entity, -this.xRotO+10, -this.yRotO-10, 0.0F, 1.6F, 1.0F);
                lance2.setPos(this.getEyePosition());

                lance2.secondary = true;
                lance2.pickup = Pickup.DISALLOWED;
                entity.getLevel().addFreshEntity(lance);
                entity.getLevel().addFreshEntity(lance2);
            }
        }
    }
}
