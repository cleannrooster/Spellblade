package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.entity.FireworkEntity;
import com.cleannrooster.spellblademod.entity.ReverberatingRay;
import com.cleannrooster.spellblademod.manasystem.manatick;
import com.google.common.collect.Lists;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

import static java.lang.Float.NaN;
import static java.lang.Math.*;

public class Bombard extends Spell{
    public Item getIngredient1() {return Items.FIREWORK_STAR;};
    public Item getIngredient2() {return ModItems.FLUXITEM.get();};

    @Override
    public boolean isTargeted() {
        return true;
    }
    public Bombard(Properties p_41383_) {
        super(p_41383_);
    }
    public boolean isFoil(ItemStack p_41453_) {
        if (p_41453_.hasTag()){
            if(p_41453_.getTag().getInt("Triggerable") == 1){
                return true;
            }
        }
        return false;
    }
    public int getColor() {
        return 16756176;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack2 = player.getItemInHand(hand);
        if (((Player) player).isShiftKeyDown() && itemstack2.getItem() instanceof Spell) {
            CompoundTag nbt;
            if (itemstack2.hasTag())
            {
                if(itemstack2.getTag().get("Triggerable") != null) {
                    nbt = itemstack2.getTag();
                    nbt.remove("Triggerable");
                    player.getInventory().setChanged();
                }
                else{
                    nbt = itemstack2.getOrCreateTag();
                    nbt.putInt("Triggerable", 1);
                    player.getInventory().setChanged();
                }

            }
            else
            {
                nbt = itemstack2.getOrCreateTag();
                nbt.putInt("Triggerable", 1);
                player.getInventory().setChanged();
            }
            return InteractionResultHolder.success(itemstack2);

        }
        HitResult hitResult = Impale.getPlayerPOVHitResult(level,player, ClipContext.Fluid.NONE, 16*player.getAttribute(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).getValue());
        if(hitResult.getType() == HitResult.Type.BLOCK ){
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            double xdist = hitResult.getLocation().x - player.getX();
            double ydist = blockHitResult.getBlockPos().above().getY() - player.getEyeY();
            double zdist = hitResult.getLocation().z - player.getZ();

            double hordis = 0;
            double theta = 90;
                 hordis = sqrt(blockHitResult.getBlockPos().above().distToCenterSqr(player.getEyePosition()) - ydist * ydist);
            double Vy = ydist + 24;
            double Vxz = (hordis*24)/(Vy + sqrt(Vy*Vy+2*24*-ydist));
            if(Float.isNaN((float) Vxz)){
                Vxz = 0;
            }
            if(!(abs(ydist) < 16)){
                return InteractionResultHolder.fail(itemstack2);
            }
            double Vxx = Vxz*player.getLookAngle().x;
            double Vzz = Vxz*player.getLookAngle().z;

            for(int ii = 0; ii < 18; ii++) {
                if (!level.isClientSide()) {
                    DyeColor dyecolor = Util.getRandom(DyeColor.values(), level.getRandom());
                    int i = level.getRandom().nextInt(3);
                    ItemStack itemstack = new ItemStack(Items.FIREWORK_ROCKET, 1);
                    ItemStack itemstack1 = new ItemStack(Items.FIREWORK_STAR);
                    CompoundTag compoundtag = itemstack1.getOrCreateTagElement("Explosion");
                    List<Integer> list = Lists.newArrayList();
                    list.add(dyecolor.getFireworkColor());
                    compoundtag.putIntArray("Colors", list);
                    compoundtag.putByte("Type", (byte)FireworkRocketItem.Shape.BURST.getId());
                    CompoundTag compoundtag1 = itemstack.getOrCreateTagElement("Fireworks");
                    ListTag listtag = new ListTag();
                    CompoundTag compoundtag2 = itemstack1.getTagElement("Explosion");
                    if (compoundtag2 != null) {
                        listtag.add(compoundtag2);
                    }

                    compoundtag1.putByte("Flight", (byte)i);
                    if (!listtag.isEmpty()) {
                        compoundtag1.put("Explosions", listtag);
                    }

                    FireworkEntity fireworkrocketentity = new FireworkEntity(level, player, player.getX(), player.getEyeY(), player.getZ(), itemstack, itemstack1);
                    fireworkrocketentity.setDeltaMovement(Vxx/20  + level.getRandom().nextDouble(-0.1,0.1), Vy/20+ level.getRandom().nextDouble(-0.1,0.1), Vzz/20 + level.getRandom().nextDouble(-0.1,0.1));
                    fireworkrocketentity.setPos(player.getEyePosition());
                    fireworkrocketentity.damage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);

                    level.addFreshEntity(fireworkrocketentity);


                }
            }
            ((Player)player).getAttribute(manatick.WARD).setBaseValue(((Player) player).getAttributeBaseValue(manatick.WARD)-20);

            if (((Player)player).getAttributes().getBaseValue(manatick.WARD) < -21) {
                player.hurt(DamageSource.MAGIC,2);
            }

        }
        return super.use(level, player, hand);
    }

    @Override
    public boolean triggeron(Level level, Player player, LivingEntity target, float modifier) {
        for(int ii = 0; ii < 3; ii++){
            DyeColor dyecolor = Util.getRandom(DyeColor.values(), level.getRandom());
            int i = level.getRandom().nextInt(3);
            ItemStack itemstack = new ItemStack(Items.FIREWORK_ROCKET, 1);
            ItemStack itemstack1 = new ItemStack(Items.FIREWORK_STAR);
            CompoundTag compoundtag = itemstack1.getOrCreateTagElement("Explosion");
            List<Integer> list = Lists.newArrayList();
            list.add(dyecolor.getFireworkColor());
            compoundtag.putIntArray("Colors", list);
            compoundtag.putByte("Type", (byte) FireworkRocketItem.Shape.BURST.getId());
            CompoundTag compoundtag1 = itemstack.getOrCreateTagElement("Fireworks");
            ListTag listtag = new ListTag();
            CompoundTag compoundtag2 = itemstack1.getTagElement("Explosion");
            if (compoundtag2 != null) {
                listtag.add(compoundtag2);
            }

            compoundtag1.putByte("Flight", (byte) i);
            if (!listtag.isEmpty()) {
                compoundtag1.put("Explosions", listtag);
            }

            Vec3 vec3 = target.position().add(player.position().multiply(-1,-1,-1)).multiply(1,0,1).normalize();

            FireworkEntity fireworkrocketentity = new FireworkEntity(level, player, player.getX(), player.getEyeY(), player.getZ(), itemstack, itemstack1);
            fireworkrocketentity.damage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);

            fireworkrocketentity.setDeltaMovement(level.getRandom().nextDouble(-0.2, 0.2)+0.2*vec3.x(), 0.3+level.getRandom().nextDouble(-0.1, 0.1), level.getRandom().nextDouble(-0.2, 0.2)+0.2*vec3.z());
            fireworkrocketentity.setPos(target.getEyePosition());
            if (!level.isClientSide()) {
                level.addFreshEntity(fireworkrocketentity);
            }
        }

        ((Player)player).getAttribute(manatick.WARD).setBaseValue(((Player) player).getAttributeBaseValue(manatick.WARD)-20);

        if (((Player)player).getAttributes().getBaseValue(manatick.WARD) < -21) {
            player.hurt(DamageSource.MAGIC,2);
        }
        return super.triggeron(level, player, target, modifier);
    }

    @Override
    public boolean trigger(Level level, Player player, float modifier) {

        List<LivingEntity> livingEntities = level.getEntitiesOfClass(LivingEntity.class,player.getBoundingBox().inflate(32),entity -> FriendshipBracelet.PlayerFriendshipPredicate(player, (LivingEntity) entity));
       livingEntities.removeIf(livingEntity -> livingEntity == player);
       livingEntities.removeIf(livingEntity -> !livingEntity.hasLineOfSight(player));
       LivingEntity living = level.getNearestEntity(livingEntities, TargetingConditions.forNonCombat(),player,player.getX(),player.getY(),player.getZ());
        if(living != null){
            double xdist = living.getX() - player.getX();
            double ydist = living.getY() - player.getEyeY();
            double zdist = living.getZ() - player.getZ();
            double hordis = 0;
            double theta = 90;
            hordis = sqrt(living.distanceToSqr(player.getEyePosition()) - ydist * ydist);
            double Vy = ydist + 24;
            double Vxz = (hordis*24)/(Vy + sqrt(Vy*Vy+2*24*-ydist));
            if(Float.isNaN((float) Vxz)){
                Vxz = 0;
            }
            if(!(abs(ydist) < 16)){
                return false;
            }
            double Vxx = Vxz*sin(-toRadians(ReverberatingRay.getYRotD(player,living)));
            double Vzz = Vxz*cos(toRadians(ReverberatingRay.getYRotD(player,living)));

            for(int ii = 0; ii < 18; ii++) {
                if (!level.isClientSide()) {
                    DyeColor dyecolor = Util.getRandom(DyeColor.values(), level.getRandom());
                    int i = level.getRandom().nextInt(3);
                    ItemStack itemstack = new ItemStack(Items.FIREWORK_ROCKET, 1);
                    ItemStack itemstack1 = new ItemStack(Items.FIREWORK_STAR);
                    CompoundTag compoundtag = itemstack1.getOrCreateTagElement("Explosion");
                    List<Integer> list = Lists.newArrayList();
                    list.add(dyecolor.getFireworkColor());
                    compoundtag.putIntArray("Colors", list);
                    compoundtag.putByte("Type", (byte)FireworkRocketItem.Shape.BURST.getId());
                    CompoundTag compoundtag1 = itemstack.getOrCreateTagElement("Fireworks");
                    ListTag listtag = new ListTag();
                    CompoundTag compoundtag2 = itemstack1.getTagElement("Explosion");
                    if (compoundtag2 != null) {
                        listtag.add(compoundtag2);
                    }

                    compoundtag1.putByte("Flight", (byte)i);
                    if (!listtag.isEmpty()) {
                        compoundtag1.put("Explosions", listtag);
                    }

                    FireworkEntity fireworkrocketentity = new FireworkEntity(level, player, player.getX(), player.getEyeY(), player.getZ(), itemstack, itemstack1);
                    fireworkrocketentity.setDeltaMovement(Vxx / 20 + level.getRandom().nextDouble(-0.2,0.2), Vy/20+ level.getRandom().nextDouble(-0.2,0.), Vzz / 20+ level.getRandom().nextDouble(-0.2,0.2));
                    fireworkrocketentity.damage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                    fireworkrocketentity.setPos(player.getEyePosition());
                    level.addFreshEntity(fireworkrocketentity);
                }


            }
            ((Player)player).getAttribute(manatick.WARD).setBaseValue(((Player) player).getAttributeBaseValue(manatick.WARD)-20);

            if (((Player)player).getAttributes().getBaseValue(manatick.WARD) < -21) {
                player.hurt(DamageSource.MAGIC,2);
            }
        }
        return super.trigger(level, player, modifier);

    }

    @Override
    public int triggerCooldown() {
        return 20;
    }
}
