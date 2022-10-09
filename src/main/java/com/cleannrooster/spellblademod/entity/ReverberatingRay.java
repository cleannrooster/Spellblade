package com.cleannrooster.spellblademod.entity;

import com.cleannrooster.spellblademod.items.ReverberatingRayItem;
import com.cleannrooster.spellblademod.manasystem.manatick;
import com.google.common.collect.ImmutableMultimap;
import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class ReverberatingRay extends AbstractArrow implements ItemSupplier {

    public  boolean primary = false;
    private boolean secondary = false;
    public boolean triggered = false;

    @Override
    protected void onHit(HitResult p_37218_) {
    }

    @Override
    protected void onHitBlock(BlockHitResult p_37258_) {

    }




    @Override
    protected void onHitEntity(EntityHitResult p_37259_) {

    }
    public ReverberatingRay(EntityType<? extends AbstractArrow> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
        this.pickup = Pickup.DISALLOWED;
    }

    @Override
    protected boolean tryPickup(Player p_150121_) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.triggered && tickCount > 18){
            this.discard();
        }
        if (this.getOwner() != null) {
            Random rand = new Random();

            this.pickup = Pickup.DISALLOWED;
            if(!this.triggered) {
                if (!(((Player) this.getOwner()).isUsingItem() && ((Player) this.getOwner()).getMainHandItem().getItem() instanceof ReverberatingRayItem)) {
                    if (!this.getLevel().isClientSide()) {
                        this.discard();
                    }
                }
            }

            float f7 = this.getOwner().getYRot();
            float f8 = this.getOwner().getYRot()+60;
            float f9 = this.getOwner().getYRot()-60;
            float f = this.getOwner().getXRot();
            float f1 = -Mth.sin(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
            float f2 = -Mth.sin(f * ((float) Math.PI / 180F));
            float f3 = Mth.cos(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));

            ReverberatingRay orb1 = new ReverberatingRay(ModEntities.REVERBERATING_RAY_ORB.get(), this.level);
            orb1.setOwner(this.getOwner());
            orb1.secondary = true;
            orb1.noPhysics = true;
                this.setPos(this.getOwner().getEyePosition().add(f1, f2, f3));
            Position pos1 = (this.getOwner().getEyePosition().add(f1 * 40, f2 * 40, f3 * 40)).add(rand.nextDouble(-10/Math.sqrt(tickCount),10/Math.sqrt(tickCount)),rand.nextDouble(-10/Math.sqrt(tickCount),10/Math.sqrt(tickCount)),rand.nextDouble(-10/Math.sqrt(tickCount),10/Math.sqrt(tickCount)));
            Position pos2 = (this.getOwner().getEyePosition().add(f1 * 40, f2 * 40, f3 * 40)).add(rand.nextDouble(-10/Math.sqrt(tickCount),10/Math.sqrt(tickCount)),rand.nextDouble(-10/Math.sqrt(tickCount),10/Math.sqrt(tickCount)),rand.nextDouble(-10/Math.sqrt(tickCount),10/Math.sqrt(tickCount)));
            Position pos3 = (this.getOwner().getEyePosition().add(f1 * 40, f2 * 40, f3 * 40)).add(rand.nextDouble(-10/Math.sqrt(tickCount),10/Math.sqrt(tickCount)),rand.nextDouble(-10/Math.sqrt(tickCount),10/Math.sqrt(tickCount)),rand.nextDouble(-10/Math.sqrt(tickCount),10/Math.sqrt(tickCount)));
            if (this.triggered && tickCount > 10){
                 pos1 = (this.getOwner().getEyePosition().add(f1 * 40, f2 * 40, f3 * 40));
                 pos2 = (this.getOwner().getEyePosition().add(f1 * 40, f2 * 40, f3 * 40));
                 pos3 = (this.getOwner().getEyePosition().add(f1 * 40, f2 * 40, f3 * 40));

            }

            if (tickCount % 10 == 1) {

                if (!this.triggered) {

                    ((Player)this.getOwner()).getAttribute(manatick.WARD).setBaseValue(((Player) this.getOwner()).getAttributeBaseValue(manatick.WARD)-10);

                    if (((Player)this.getOwner()).getAttributes().getBaseValue(manatick.WARD) < -21) {
                        this.getOwner().hurt(DamageSource.MAGIC, 2);
                    }
                }
                SoundEvent soundEvent = SoundEvents.ILLUSIONER_CAST_SPELL;
                level.playSound((Player)null, this.getOnPos(), soundEvent, SoundSource.PLAYERS, 1F, 1F);

                    int num_pts_line = 30;
                    for (int iii = 0; iii < num_pts_line; iii++) {
                        double X = this.getEyePosition().x + (pos1.x() -this.getEyePosition().x) * ((double)iii / (num_pts_line));
                        double Y = this.getEyePosition().y + (pos1.y() - this.getEyePosition().y) * ((double)iii / (num_pts_line));
                        double Z = this.getEyePosition().z + (pos1.z() - this.getEyePosition().z) * ((double)iii / (num_pts_line));
                        double X2 = this.getEyePosition().x + (pos2.x() -this.getEyePosition().x) * ((double)iii / (num_pts_line));
                        double Y2 = this.getEyePosition().y + (pos2.y() - this.getEyePosition().y) * ((double)iii / (num_pts_line));
                        double Z2 = this.getEyePosition().z + (pos2.z() - this.getEyePosition().z) * ((double)iii / (num_pts_line));
                        double X3 = this.getEyePosition().x + (pos3.x() -this.getEyePosition().x) * ((double)iii / (num_pts_line));
                        double Y3 = this.getEyePosition().y + (pos3.y() - this.getEyePosition().y) * ((double)iii / (num_pts_line));
                        double Z3 = this.getEyePosition().z + (pos3.z() - this.getEyePosition().z) * ((double)iii / (num_pts_line));

                        int num_pts = 10;
                        Vec3 targetcenter = new Vec3(X,Y,Z);
                        Vec3 targetcenter2 = new Vec3(X2,Y2,Z2);
                        Vec3 targetcenter3 = new Vec3(X3,Y3,Z3);

                        for (int i = 1; i < num_pts; i = i + 1) {
                            double[] indices = IntStream.rangeClosed(0, (int) ((num_pts - 0) / 1))
                                    .mapToDouble(x -> x * 1 + 0).toArray();

                            double phi = Math.acos(1 - 2 * indices[i] / num_pts);
                            double theta = Math.PI * (1 + Math.pow(5, 0.5) * indices[i]);
                            double x = cos(theta) * sin(phi);
                            double y = Math.sin(theta) * sin(phi);
                            double z = cos(phi);
                            level.addParticle(new DustParticleOptions(new Vector3f(Vec3.fromRGB24(65436)),1F), true, targetcenter.x + x/4D , targetcenter.y + y/4D , targetcenter.z + z/4D, x * 0.05, y * 0.05, z * 0.05);
                            level.addParticle(new DustParticleOptions(new Vector3f(Vec3.fromRGB24(65436)),1F), true, targetcenter2.x + x/4D , targetcenter2.y  + y/4D , targetcenter2.z  + z/4D, x * 0.05, y * 0.05, z * 0.05);
                            level.addParticle(new DustParticleOptions(new Vector3f(Vec3.fromRGB24(65436)),1F), true, targetcenter3.x +  x/4D , targetcenter3.y + y/4D , targetcenter3.z  + z/4D, x * 0.05, y * 0.05, z * 0.05);

                        }
                    }
                List<Entity> list = this.level.getEntities(this, new AABB((double)this.getOwner().getEyePosition().x(), (double)this.getOwner().getEyePosition().y(), (double)this.getOwner().getEyePosition().z(), (double)pos1.x(), (double)pos1.y(), (double)pos1.z()));
                List<Entity> list2 = this.level.getEntities(this, new AABB((double)this.getOwner().getEyePosition().x(), (double)this.getOwner().getEyePosition().y(), (double)this.getOwner().getEyePosition().z(), (double)pos2.x(), (double)pos2.y(), (double)pos2.z()));
                List<Entity> list3 = this.level.getEntities(this, new AABB((double)this.getOwner().getEyePosition().x(), (double)this.getOwner().getEyePosition().y(), (double)this.getOwner().getEyePosition().z(), (double)pos3.x(), (double)pos3.y(), (double)pos3.z()));

                for (int ii = 0; ii < list.toArray().length; ii++) {
                    Optional<Vec3> vec1 = list.get(ii).getBoundingBox().inflate(0.5).clip(this.getOwner().getEyePosition(), (Vec3) pos1);
                    if (list.get(ii) instanceof LivingEntity target && vec1.isPresent() && ((LivingEntity)this.getOwner()).hasLineOfSight(list.get(ii))&& list.get(ii) != this.getOwner()) {
                        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(),"knockbackresist",1, AttributeModifier.Operation.ADDITION);

                        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
                        builder.put(Attributes.KNOCKBACK_RESISTANCE, modifier);
                        target.getAttributes().addTransientAttributeModifiers(builder.build());
                        target.invulnerableTime = 0;
                        target.hurt(new EntityDamageSource("spell", this.getOwner()), 4);

                        target.getAttributes().removeAttributeModifiers(builder.build());
                        target.invulnerableTime = 0;
                    }
                }
                for (int ii = 0; ii < list2.toArray().length; ii++) {
                    Optional<Vec3> vec1 = list2.get(ii).getBoundingBox().inflate(0.5).clip(this.getOwner().getEyePosition(), (Vec3) pos2);
                    if (list2.get(ii) instanceof LivingEntity target && vec1.isPresent() && ((LivingEntity)this.getOwner()).hasLineOfSight(list2.get(ii))&& list2.get(ii) != this.getOwner()) {
                        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(),"knockbackresist",1, AttributeModifier.Operation.ADDITION);

                        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
                        builder.put(Attributes.KNOCKBACK_RESISTANCE, modifier);
                        target.getAttributes().addTransientAttributeModifiers(builder.build());
                        target.invulnerableTime = 0;
                        target.hurt(new EntityDamageSource("spell", this.getOwner()), 4);

                        target.getAttributes().removeAttributeModifiers(builder.build());
                        target.invulnerableTime = 0;
                    }
                }
                for (int ii = 0; ii < list3.toArray().length; ii++) {
                    Optional<Vec3> vec1 = list3.get(ii).getBoundingBox().inflate(0.5).clip(this.getOwner().getEyePosition(), (Vec3) pos3);
                    if (list3.get(ii) instanceof LivingEntity target && vec1.isPresent() && ((LivingEntity)this.getOwner()).hasLineOfSight(list3.get(ii))&& list3.get(ii) != this.getOwner()) {
                        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(),"knockbackresist",1, AttributeModifier.Operation.ADDITION);

                        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
                        builder.put(Attributes.KNOCKBACK_RESISTANCE, modifier);
                        target.getAttributes().addTransientAttributeModifiers(builder.build());
                        target.invulnerableTime = 0;
                        target.hurt(new EntityDamageSource("spell", this.getOwner()), 4);

                        target.getAttributes().removeAttributeModifiers(builder.build());
                        target.invulnerableTime = 0;
                    }
                }
            }
        }

    }

    @Override
    protected ItemStack getPickupItem() {
        return null;
    }

    @Override
    public boolean hurt(DamageSource p_36839_, float p_36840_) {
        return false;
    }
    @Override
    public boolean mayInteract(Level p_150167_, BlockPos p_150168_) {
        return false;
    }
    @Override
    public boolean isAttackable() {
        return false;
    }
    @Override
    public boolean skipAttackInteraction(Entity p_20357_) {
        return true;
    }


    @Override
    public ItemStack getItem() {
        return new ItemStack(Items.AIR);
    }

}
