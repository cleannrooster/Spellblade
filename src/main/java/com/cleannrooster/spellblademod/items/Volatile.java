package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.effects.DamageSourceModded;
import com.cleannrooster.spellblademod.entity.InvisiVex;
import com.cleannrooster.spellblademod.entity.ModEntities;
import com.cleannrooster.spellblademod.entity.VolatileEntity;
import com.cleannrooster.spellblademod.manasystem.manatick;
import com.mojang.math.Vector3d;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.IntStream;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Volatile extends Spell{

    public Volatile(Properties p_41383_) {
        super(p_41383_);
    }
    public Item getIngredient1() {return Items.FIRE_CHARGE;};
    public Item getIngredient2() {return ModItems.FLUXITEM.get();};

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (player.isShiftKeyDown()) {
            CompoundTag nbt;
            if (itemstack.hasTag())
            {
                if(itemstack.getTag().get("Triggerable") != null) {
                    nbt = itemstack.getTag();
                    nbt.remove("Triggerable");
                }
                else{
                    nbt = itemstack.getOrCreateTag();
                    nbt.putInt("Triggerable", 1);
                }

            }
            else
            {
                nbt = itemstack.getOrCreateTag();
                nbt.putInt("Triggerable", 1);
            }
            return InteractionResultHolder.success(itemstack);

        }
        if (!player.getMainHandItem().isEdible()) {
            ((Player)player).getAttribute(manatick.WARD).setBaseValue(((Player)player).getAttributes().getBaseValue(manatick.WARD)-20);
            if (((Player)player).getAttributes().getBaseValue(manatick.WARD) < -21) {
                player.hurt(DamageSource.MAGIC,2);
            }
            if (!player.isShiftKeyDown()) {

            }
            VolatileEntity volatile1 = new VolatileEntity(ModEntities.VOLATILE_ENTITY.get(),level);
            VolatileEntity volatile2 = new VolatileEntity(ModEntities.VOLATILE_ENTITY.get(),level);
            VolatileEntity volatile3 = new VolatileEntity(ModEntities.VOLATILE_ENTITY.get(),level);
            volatile1.setPos(player.getEyePosition().add( new Vec3(level.random.nextDouble(-1, 1),level.random.nextDouble(0, 1),level.random.nextDouble(-1, 1))));
            volatile2.setPos(player.getEyePosition().add( new Vec3(level.random.nextDouble(-1, 1),level.random.nextDouble(0, 1),level.random.nextDouble(-1, 1))));
            volatile3.setPos(player.getEyePosition().add( new Vec3(level.random.nextDouble(-1, 1),level.random.nextDouble(0, 1),level.random.nextDouble(-1, 1))));
            level.addFreshEntity(volatile1);
            level.addFreshEntity(volatile2);
            level.addFreshEntity(volatile3);


        }

        return InteractionResultHolder.fail(itemstack);

    }
    @Override
    public boolean isFoil(ItemStack p_41453_) {
        if (p_41453_.hasTag()){
            if(p_41453_.getTag().getInt("Triggerable") == 1){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean trigger(Level level, Player player, float modifier) {


        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,player.getBoundingBox().inflate(6D),livingEntity -> {return FriendshipBracelet.PlayerFriendshipPredicate((Player) player,livingEntity);});          Object[] entitiesarray = entities.toArray();
        boolean flag1 = false;
        if (player.getInventory().contains(ModItems.FRIENDSHIP.get().getDefaultInstance())){
            flag1 = true;
        }
        List<LivingEntity> validtargets = new ArrayList<>();
        int entityamount = entitiesarray.length;
        for (int ii = 0; ii < entityamount; ii = ii + 1) {
            LivingEntity target = (LivingEntity) entities.get(ii);
            boolean flag2 = false;
            if (target.getClassification(false).isFriendly() || target instanceof Player || (target instanceof NeutralMob)) {
                flag2 = true;
            }
            if (target != player && target.hasLineOfSight(player)) {
                validtargets.add(target);
            }
        }
        validtargets.removeIf(livingEntity -> {
            if (livingEntity instanceof InvisiVex vex) {
                return vex.owner2 == player;
            } else {
                return false;
            }
        });
        if (!validtargets.isEmpty()){

            if(((Player)player).getAttributes().getBaseValue(manatick.WARD)< -1 && player.getHealth() <= 2)
            {
                return true;
            }
            VolatileEntity volatile1 = new VolatileEntity(ModEntities.VOLATILE_ENTITY.get(),level);
            VolatileEntity volatile2 = new VolatileEntity(ModEntities.VOLATILE_ENTITY.get(),level);
            VolatileEntity volatile3 = new VolatileEntity(ModEntities.VOLATILE_ENTITY.get(),level);
            volatile1.explosionPower = 2;
            volatile3.explosionPower = 2;
            volatile2.explosionPower = 2;
            Random rand = new Random();
            volatile1.setPos(player.position().add( new Vec3(rand.nextDouble(-1, 1),rand.nextDouble(0, 1),rand.nextDouble(-1, 1))));
            volatile2.setPos(player.position().add( new Vec3(rand.nextDouble(-1, 1),rand.nextDouble(0, 1),rand.nextDouble(-1, 1))));
            volatile3.setPos(player.position().add( new Vec3(rand.nextDouble(-1, 1),rand.nextDouble(0, 1),rand.nextDouble(-1, 1))));
            volatile1.setOwner(player);
            volatile2.setOwner(player);
            volatile3.setOwner(player);
            if(validtargets.toArray().length == 1){
                volatile1.target = validtargets.get(0);
                level.addFreshEntity(volatile1);

            }
            if(validtargets.toArray().length == 2){
                volatile1.target = validtargets.get(0);
                volatile2.target = validtargets.get(1);
                if (rand.nextBoolean()){
                    volatile3.target = validtargets.get(0);
                }
                else{
                    volatile3.target = validtargets.get(1);
                }
                level.addFreshEntity(volatile1);
                level.addFreshEntity(volatile2);

            }
            if(validtargets.toArray().length == 3){
                volatile1.target = validtargets.get(0);
                volatile2.target = validtargets.get(1);
                volatile3.target = validtargets.get(2);
                level.addFreshEntity(volatile1);
                level.addFreshEntity(volatile2);
                level.addFreshEntity(volatile3);

            }
            if(validtargets.toArray().length > 3){
                int size = validtargets.toArray().length;
                ArrayList<Integer> list = new ArrayList<Integer>(size);
                for(int i = 1; i <= size; i++) {
                    list.add(i);
                }

                ArrayList<Integer> index = new ArrayList<Integer>();
                ArrayList<VolatileEntity> volatilelist = new ArrayList<VolatileEntity>(3);

                volatilelist.add(volatile1);
                volatilelist.add(volatile2);
                volatilelist.add(volatile3);
                int iii = 0;
                while(list.size() > 0) {
                    int toadd = rand.nextInt(list.size());
                    if (iii < 3 && list.get(toadd) > 0 && iii >= 0) {
                        volatilelist.get(iii).target = validtargets.get(list.get(toadd)-1);
                    }
                    if (iii > 10) break;
                    iii++;

                }
                level.addFreshEntity(volatile1);
                level.addFreshEntity(volatile2);
                level.addFreshEntity(volatile3);

            }
            ((Player)player).getAttribute(manatick.WARD).setBaseValue(((Player) player).getAttributeBaseValue(manatick.WARD)-20);

            if (((Player)player).getAttributes().getBaseValue(manatick.WARD) < -21) {
                player.hurt(DamageSource.MAGIC,2);
            }

        }
        return false;
    }
    public int getColor() {
        return 10944512;
    }

    @Override
    public int triggerCooldown() {
        return 20;
    }
}
