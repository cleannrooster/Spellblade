package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.effects.DamageSourceModded;
import com.cleannrooster.spellblademod.entity.ModEntities;
import com.cleannrooster.spellblademod.entity.VolatileEntity;
import com.cleannrooster.spellblademod.manasystem.data.PlayerMana;
import com.cleannrooster.spellblademod.manasystem.data.PlayerManaProvider;
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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.*;
import java.util.stream.IntStream;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Volatile extends Spell{

    public Volatile(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!level.isClientSide() && player.isShiftKeyDown()) {
            CompoundTag nbt;
            if (itemstack.hasTag())
            {
                nbt = itemstack.getTag();
                nbt.remove("Triggerable");
                return InteractionResultHolder.success(itemstack);

            }
            else
            {
                nbt = itemstack.getOrCreateTag();
                nbt.putInt("Triggerable", 1);
                return InteractionResultHolder.success(itemstack);
            }
        }
        if (!player.getMainHandItem().isEdible()) {
            PlayerMana playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
            if (playerMana.getMana() > 79) {
                VolatileEntity volatile1 = new VolatileEntity(ModEntities.VOLATILE_ENTITY.get(),level);
                VolatileEntity volatile2 = new VolatileEntity(ModEntities.VOLATILE_ENTITY.get(),level);
                VolatileEntity volatile3 = new VolatileEntity(ModEntities.VOLATILE_ENTITY.get(),level);

                player.getCooldowns().addCooldown(this, 10);
                player.addEffect(new MobEffectInstance(StatusEffectsModded.WARD_DRAIN.get(), 5, 1));

                List entities = level.getEntitiesOfClass(LivingEntity.class, new AABB(player.getX() - 6, player.getY() - 6, player.getZ() - 6, player.getX() + 6, player.getY() + 6, player.getZ() + 6));
                Object[] entitiesarray = entities.toArray();
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
                    if (target != player && !(flag1 && flag2)) {
                        target.invulnerableTime = 0;
                        validtargets.add(target);
                        target.invulnerableTime = 0;

                    }
                }
                if (!validtargets.isEmpty()){
                    Random rand = new Random();
                    volatile1.setPos(player.position().add( new Vec3(rand.nextDouble(-1, 1),rand.nextDouble(-1, 1),rand.nextDouble(-1, 1))));
                    volatile2.setPos(player.position().add( new Vec3(rand.nextDouble(-1, 1),rand.nextDouble(-1, 1),rand.nextDouble(-1, 1))));
                    volatile3.setPos(player.position().add( new Vec3(rand.nextDouble(-1, 1),rand.nextDouble(-1, 1),rand.nextDouble(-1, 1))));
                    volatile1.setOwner(player);
                    volatile2.setOwner(player);
                    volatile3.setOwner(player);
                    if(validtargets.toArray().length == 1){
                        volatile1.target = validtargets.get(0);

                        level.addFreshEntity(volatile2);

                    }
                    if(validtargets.toArray().length == 2){
                        volatile1.target = validtargets.get(0);
                        volatile2.target = validtargets.get(1);
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
                            if (iii < 3) {
                                volatilelist.get(iii).target = validtargets.get(list.get(toadd)-1);
                            }
                            System.out.println(list.remove(toadd));
                            iii++;
                        }
                        level.addFreshEntity(volatile1);
                        level.addFreshEntity(volatile2);
                        level.addFreshEntity(volatile3);
                    }

                }

             }

            return InteractionResultHolder.consume(itemstack);
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
    public void trigger(Level level, Player player, float modifier) {
        VolatileEntity volatile1 = new VolatileEntity(ModEntities.VOLATILE_ENTITY.get(),level);
        VolatileEntity volatile2 = new VolatileEntity(ModEntities.VOLATILE_ENTITY.get(),level);
        VolatileEntity volatile3 = new VolatileEntity(ModEntities.VOLATILE_ENTITY.get(),level);
        PlayerMana playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);

        player.getCooldowns().addCooldown(this, 10);
        List entities = level.getEntitiesOfClass(LivingEntity.class, new AABB(player.getX() - 6, player.getY() - 6, player.getZ() - 6, player.getX() + 6, player.getY() + 6, player.getZ() + 6));
        Object[] entitiesarray = entities.toArray();
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
            if (target != player && !(flag1 && flag2)) {
                target.invulnerableTime = 0;
                validtargets.add(target);
                target.invulnerableTime = 0;

            }
        }
        if (!validtargets.isEmpty()){
            Random rand = new Random();
            volatile1.setPos(player.position().add( new Vec3(rand.nextDouble(-1, 1),rand.nextDouble(-1, 1),rand.nextDouble(-1, 1))));
            volatile2.setPos(player.position().add( new Vec3(rand.nextDouble(-1, 1),rand.nextDouble(-1, 1),rand.nextDouble(-1, 1))));
            volatile3.setPos(player.position().add( new Vec3(rand.nextDouble(-1, 1),rand.nextDouble(-1, 1),rand.nextDouble(-1, 1))));
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
                    if (iii < 3) {
                        volatilelist.get(iii).target = validtargets.get(list.get(toadd)-1);
                    }
                    System.out.println(list.remove(toadd));
                    iii++;

                }
                level.addFreshEntity(volatile1);
                level.addFreshEntity(volatile2);
                level.addFreshEntity(volatile3);

            }
        }

    }
}
