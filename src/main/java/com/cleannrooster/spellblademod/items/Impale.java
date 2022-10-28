package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.entity.ImpaleEntity;
import com.cleannrooster.spellblademod.entity.ModEntities;
import com.cleannrooster.spellblademod.entity.WinterBurialEntity;
import com.cleannrooster.spellblademod.items.Spell;
import com.cleannrooster.spellblademod.manasystem.manatick;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import static java.lang.Math.sqrt;

public class Impale extends Spell {
    public Impale(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public boolean isTargeted() {
        return true;
    }
    public int getColor() {
        return 8152920;
    }

    @Override
    public int triggerCooldown() {
        return 20;
    }

    public Item getIngredient1() {return Items.POINTED_DRIPSTONE;};
    public Item getIngredient2() {return ModItems.FLUXITEM.get();};

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (((Player) player).isShiftKeyDown()) {
            CompoundTag nbt;
            if (itemstack.hasTag())
            {
                if(itemstack.getTag().get("Triggerable") != null) {
                    nbt = itemstack.getTag();
                    nbt.remove("Triggerable");
                    player.getInventory().setChanged();
                }
                else{
                    nbt = itemstack.getOrCreateTag();
                    nbt.putInt("Triggerable", 1);
                    player.getInventory().setChanged();
                }

            }
            else
            {
                nbt = itemstack.getOrCreateTag();
                nbt.putInt("Triggerable", 1);
                player.getInventory().setChanged();
            }
            return InteractionResultHolder.success(itemstack);

        }
        BlockHitResult hitResult = getPlayerPOVHitResult(level,player, ClipContext.Fluid.NONE,player.getAttribute(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).getValue());
        if(hitResult.getType() == HitResult.Type.BLOCK) {
            ImpaleEntity impale = new ImpaleEntity(ModEntities.IMPALE.get(), level, player, hitResult.getBlockPos(),         (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE));
            ((Player)player).getAttribute(manatick.WARD).setBaseValue(((Player) player).getAttributeBaseValue(manatick.WARD)-20);

            if (((Player)player).getAttributes().getBaseValue(manatick.WARD) < -21) {
                player.hurt(DamageSource.MAGIC,2);
            }
        }

        return super.use(level, player, hand);
    }
    protected static BlockHitResult getPlayerPOVHitResult(Level p_41436_, Player p_41437_, ClipContext.Fluid p_41438_, double distance) {
        float f = p_41437_.getXRot();
        float f1 = p_41437_.getYRot();
        Vec3 vec3 = p_41437_.getEyePosition();
        float f2 = Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = distance;;
        Vec3 vec31 = vec3.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
        return p_41436_.clip(new ClipContext(vec3, vec31, ClipContext.Block.COLLIDER, p_41438_, p_41437_));
    }

    @Override
    public boolean trigger(Level level, Player player, float modifier) {
        boolean activated = false;
        for(int ii = 0; ii < 5; ii++){
            double xRand = level.random.nextDouble(-1,1);
            double zRand = level.random.nextDouble(-1,1);
            double d0 = sqrt(xRand*xRand+zRand*zRand);
            BlockHitResult result = level.clip(new ClipContext(player.getEyePosition(),new Vec3(player.getX()+20*xRand/d0,player.getY()-20*.2,player.getZ()+20*zRand/d0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,player));
            if(result.getType() == HitResult.Type.BLOCK && result.distanceTo(player) > 3) {
                new ImpaleEntity(ModEntities.IMPALE.get(), level, player, result.getBlockPos(),         (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE));
                activated = true;
            }
        }
        if(activated){
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
            new ImpaleEntity(ModEntities.IMPALE.get(), level, player, target.getOnPos(),(float) player.getAttributeValue(Attributes.ATTACK_DAMAGE));
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
            BlockHitResult result = level.clip(new ClipContext(player.getEyePosition(),new Vec3(player.getX()+20*xRand/d0,player.getY()-20*.1666666,player.getZ()+20*zRand/d0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,player));
            if(result.getType() == HitResult.Type.BLOCK && result.distanceTo(player) > 3) {
                new ImpaleEntity(ModEntities.IMPALE.get(), level, player, result.getBlockPos());
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
