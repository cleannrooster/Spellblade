package com.cleannrooster.spellblademod.entity;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.effects.DamageSourceModded;
import com.cleannrooster.spellblademod.items.ModItems;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class EndersEyeEntity extends Projectile implements ItemSupplier {

    public List<Entity> blacklist;
    public EndersEyeEntity(EntityType<? extends EndersEyeEntity> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick() {
        if(this.getOwner()!=null){
            if(this.getOwner().isAlive()){
                if(((Player)this.getOwner()).hasEffect(StatusEffectsModded.ENDERSGAZE.get())) {
                    this.setPos(this.getOwner().position().add(new Vec3(0, 2.5, 0)));
                    boolean flag1 = false;
                    if (((Player)this.getOwner()).getInventory().contains(ModItems.FRIENDSHIP.get().getDefaultInstance())){
                        flag1 = true;
                    }
                    if (tickCount % 10 == 5) {
                        List entities = this.level.getEntitiesOfClass(LivingEntity.class, new AABB(this.getX() - 6, this.getY() - 6, this.getZ() - 6, this.getX() + 6, this.getY() + 6, this.getZ() + 6));
                        Object[] entitiesarray = entities.toArray();
                        int entityamount = entitiesarray.length;
                        for (int ii = 0; ii < entityamount; ii = ii + 1) {
                            LivingEntity target = (LivingEntity) entities.get(ii);
                            boolean flag2 = false;
                            if (target.getClassification(false).isFriendly() || target instanceof Player || (target instanceof NeutralMob)){
                                flag2 = true;
                            }
                            if (target != this.getOwner() && !Objects.requireNonNullElse(this.blacklist, new ArrayList()).contains(target) && !(flag1 && flag2) && target.hasLineOfSight(this)) {
                                int i = 0;
                                int num_pts = 100;
                                int num_pts_line = 25;
                                for (int iii = 0; iii < num_pts_line; iii++) {
                                    double X = this.getEyePosition().x + (target.getEyePosition().x -this.getEyePosition().x) * ((double)iii / (num_pts_line));
                                    double Y = this.getEyePosition().y + (target.getEyePosition().y - this.getEyePosition().y) * ((double)iii / (num_pts_line));
                                    double Z = this.getEyePosition().z + (target.getEyePosition().z - this.getEyePosition().z) * ((double)iii / (num_pts_line));
                                    this.level.addParticle(DustParticleOptions.REDSTONE, X, Y, Z, 0, 0, 0);
                                }
                                for (i = 0; i <= num_pts; i = i + 1) {
                                    double[] indices = IntStream.rangeClosed(0, (int) ((1000 - 0) / 1))
                                            .mapToDouble(x -> x * 1 + 0).toArray();

                                    double phi = Math.acos(1 - 2 * indices[i] / num_pts);
                                    double theta = Math.PI * (1 + Math.pow(5, 0.5) * indices[i]);
                                    double x = cos(theta) * sin(phi);
                                    double y = Math.sin(theta) * sin(phi);
                                    double z = cos(phi);
                                    this.level.addParticle((ParticleOptions) DustParticleOptions.REDSTONE, target.getEyePosition().x + 1.5 * x, target.getEyePosition().y + 1.5 * y, target.getEyePosition().z + 1.5 * z, 0, 0, 0);
                                }
                                target.invulnerableTime = 0;
                                target.hurt(DamageSourceModded.eyelaser((Player) this.getOwner()), 6);
                                target.invulnerableTime = 0;
                            }
                        }
                    }
                }
                else {
                    this.discard();
                }
            }
            else {
                this.discard();
            }
        }
        else {
            this.discard();
        }

    }
    @Override
    public ItemStack getItem() {
        return ModItems.ENDERSEYE.get().getDefaultInstance();
    }

}
