package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.entity.ImpaleEntity;
import com.cleannrooster.spellblademod.entity.ModEntities;
import com.cleannrooster.spellblademod.entity.WinterBurialEntity;
import com.cleannrooster.spellblademod.manasystem.manatick;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import static java.lang.Math.sqrt;

public class WinterBurial extends Spell{

    public WinterBurial(Properties p_41383_) {
        super(p_41383_);
    }

    public Item getIngredient1() {return Items.PACKED_ICE;};
    public Item getIngredient2() {return ModItems.FLUXITEM.get();};

    @Override
    public int triggerCooldown() {
        return 20;
    }
    public int getColor() {
        return 13434879;
    }

    @Override
    public boolean isTargeted() {
        return true;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(Impale.getPlayerPOVHitResult(level,player, ClipContext.Fluid.NONE).getType() == HitResult.Type.BLOCK) {
            new WinterBurialEntity(ModEntities.WINTERBURIAL.get(), level, player,Impale.getPlayerPOVHitResult(level,player, ClipContext.Fluid.NONE).getBlockPos() , 3);
            ((Player)player).getAttribute(manatick.WARD).setBaseValue(((Player) player).getAttributeBaseValue(manatick.WARD)-20);

            if (((Player)player).getAttributes().getBaseValue(manatick.WARD) < -21) {
                player.hurt(DamageSource.MAGIC,2);
            }
        }
        return super.use(level, player, hand);
    }
    @Override
    public boolean trigger(Level level, Player player, float modifier) {
        boolean activated = false;
        for(int ii = 0; ii < 3; ii++){
            double xRand = level.random.nextDouble(-1,1);
            double zRand = level.random.nextDouble(-1,1);
            double d0 = sqrt(xRand*xRand+zRand*zRand);
            BlockHitResult result = level.clip(new ClipContext(player.getEyePosition(),new Vec3(player.getX()+20*xRand/d0,player.getY()-20*.1666666,player.getZ()+20*zRand/d0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,player));
            if(result.getType() == HitResult.Type.BLOCK && result.distanceTo(player) > 3) {
                new WinterBurialEntity(ModEntities.WINTERBURIAL.get(), level, player, result.getBlockPos(), 3);
                activated = true;
            }
        }
        if (activated){
            ((Player)player).getAttribute(manatick.WARD).setBaseValue(((Player) player).getAttributeBaseValue(manatick.WARD)-20);

            if (((Player)player).getAttributes().getBaseValue(manatick.WARD) < -21) {
                player.hurt(DamageSource.MAGIC,2);
            }
        }
        return false;
    }
    @Override
    public boolean triggeron(Level level, Player player, LivingEntity target, float modifier) {
        if(level.getBlockState(target.getOnPos()).isSuffocating(level,target.getOnPos())) {

            new WinterBurialEntity(ModEntities.WINTERBURIAL.get(), level, player, target.getOnPos(), 3);
            ((Player) player).getAttribute(manatick.WARD).setBaseValue(((Player) player).getAttributeBaseValue(manatick.WARD) - 20);

            if (((Player) player).getAttributes().getBaseValue(manatick.WARD) < -21) {
                player.hurt(DamageSource.MAGIC, 2);
            }
        }
/*
        for(int ii = 0; ii < 3; ii++){
            double xRand = level.random.nextDouble(-1,1);
            double zRand = level.random.nextDouble(-1,1);
            double d0 = sqrt(xRand*xRand+zRand*zRand);
            BlockHitResult result = level.clip(new ClipContext(player.getEyePosition(),new Vec3(player.getX()+30*xRand/d0,player.getY()-30*.1666666,player.getZ()+30*zRand/d0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,player));
            if(result.getType() == HitResult.Type.BLOCK && result.distanceTo(player) > 3) {
                new WinterBurialEntity(ModEntities.WINTERBURIAL.get(), level, player, result.getBlockPos(), 3);
            }
        }*/
        return false;
    }
    @Override
    public InteractionResult interactLivingEntity(ItemStack p_41398_, Player p_41399_, LivingEntity p_41400_, InteractionHand p_41401_) {
        triggeron(p_41399_.getLevel(),p_41399_,p_41400_,1);
        return InteractionResult.sidedSuccess(false);
    }
}
