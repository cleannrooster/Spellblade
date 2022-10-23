package com.cleannrooster.spellblademod.entity;

import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EssenceBoltEntity extends ThrowableItemProjectile {
    public EssenceBoltEntity(EntityType<? extends EssenceBoltEntity> p_37442_, Level p_37443_) {
        super(p_37442_, p_37443_);
        this.setNoGravity(true);
    }
    public EssenceBoltEntity(EntityType<? extends EssenceBoltEntity> p_37442_, Level p_37443_, Player owner) {
        super(p_37442_, p_37443_);
        this.setPos(owner.getEyePosition());
        this.setNoGravity(true);
        this.setOwner(owner);
    }

    @Override
    protected void onHitEntity(EntityHitResult p_37259_) {

        if(this.getOwner() != null) {
            if (this.getOwner() instanceof Player player) {
                player.attack(p_37259_.getEntity());
            }
        }
        super.onHitEntity(p_37259_);
    }

    @Override
    public void tick() {
        if(this.tickCount == 1){
            this.playSound( SoundEvents.ILLUSIONER_CAST_SPELL,1,1);
        }
        super.tick();
        float tridentEntity = this.getYRot() % 360;

        float f = this.getXRot();
        float g = (float) (-Math.sin(tridentEntity * ((float) Math.PI / 180)) * Math.cos(f * ((float) Math.PI / 180)));
        float h = (float) -Math.sin(f * ((float) Math.PI / 180));
        float k2 = (float) (Math.cos(tridentEntity * ((float) Math.PI / 180)) * Math.cos(f * ((float) Math.PI / 180)));
        float l2 = (float) Math.sqrt(g * g + h * h + k2 * k2);
        float m2 = 2.0f;
        float f7 = 360 + this.getYRot() % 360;

        int ii = this.tickCount % 20;
        final double theta = Math.toRadians((ii / 10d) * 360d);
        double x = Math.cos(theta);
        double y = Math.sin(theta);
        double z = 0;
        Vec3 vec3d = rotate(x, y, z, -Math.toRadians(f7), Math.toRadians(f), 0);
            /*final double angle = Math.toRadians(((double) ii / 20) * 360d);
                double x = Math.cos(angle);
                double y = Math.sin(angle);
                double z = 0;*/
        double x1 = this.getX() + 0.5 * vec3d.x;
        double y1 = this.getY() + 0.5 * vec3d.y;
        double z1 = this.getZ() + 0.5 * vec3d.z;
        double xx1 = this.getX() - 0.5 * vec3d.x;
        double yy1 = this.getY() - 0.5 * vec3d.y;
        double zz1 = this.getZ() - 0.5 * vec3d.z;
        this.level.addParticle(new DustParticleOptions(new Vector3f(Vec3.fromRGB24(16766720)),1F), true, x1 , y1 , z1, 0,0, 0);
        this.level.addParticle(new DustParticleOptions(new Vector3f(Vec3.fromRGB24(16766720)),1F), true, this.getX(), this.getY(), this.getZ(), 0, 0, 0);

        this.level.addParticle(new DustParticleOptions(new Vector3f(Vec3.fromRGB24(16766720)),1F), true, xx1, yy1, zz1, 0, 0, 0);

    }
    public Vec3 rotate(double x, double y, double z, double pitch, double roll, double yaw) {
        double cosa = Math.cos(yaw);
        double sina = Math.sin(yaw);

        double cosb = Math.cos(pitch);
        double sinb = Math.sin(pitch);
        double cosc = Math.cos(roll);
        double sinc = Math.sin(roll);

        double Axx = cosa * cosb;
        double Axy = cosa * sinb * sinc - sina * cosc;
        double Axz = cosa * sinb * cosc + sina * sinc;

        double Ayx = sina * cosb;
        double Ayy = sina * sinb * sinc + cosa * cosc;
        double Ayz = sina * sinb * cosc - cosa * sinc;

        double Azx = -sinb;
        double Azy = cosb * sinc;
        double Azz = cosb * cosc;

        Vec3 vec3 = new Vec3(Axx * x + Axy * y + Axz * z,Ayx * x + Ayy * y + Ayz * z,Azx * x + Azy * y + Azz * z);
        return vec3;
    }

    @Override
    protected Item getDefaultItem() {
        return Items.AIR;
    }
}
