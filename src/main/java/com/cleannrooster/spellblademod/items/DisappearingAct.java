package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.entity.FireworkEntity;
import com.cleannrooster.spellblademod.manasystem.manatick;
import com.cleannrooster.spellblademod.manasystem.network.FireworkHandler;
import com.cleannrooster.spellblademod.setup.Messages;
import com.google.common.collect.Lists;
import io.netty.buffer.Unpooled;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DisappearingAct extends Spell{
    public DisappearingAct(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public boolean canFail() {
        return true;
    }

    @Override
    public Item getIngredient1() {
        return Items.ENDER_PEARL;
    }
    public boolean isFoil(ItemStack p_41453_) {
        if (p_41453_.hasTag()){
            if(p_41453_.getTag().getInt("Triggerable") == 1){
                return true;
            }
        }
        return false;
    }
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        Component text = new TranslatableComponent("Not Triggerable");
        p_41423_.add(text);


        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
    }
    @Override
    public Item getIngredient2() {
        return ModItems.BOMBARD.get();
    }

    @Override
    public boolean isTriggerable() {
        return false;
    }

    @Override
    public boolean isTargeted() {
        return false;
    }

    @Override
    public int getColor() {
        return 12960964;
    }

    @Override
    public boolean failState(Level level, Player player, InteractionHand hand) {
        HitResult hitResult = Impale.getPlayerPOVHitResult(level,player, ClipContext.Fluid.NONE, 4*player.getAttribute(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).getValue());
        if(hitResult.getType() == HitResult.Type.BLOCK ) {
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

            compoundtag1.putByte("Flight", (byte)i);
            if (!listtag.isEmpty()) {
                compoundtag1.put("Explosions", listtag);
            }
            if(level instanceof ServerLevel serverLevel){
                for(ServerPlayer player1 : serverLevel.players()) {
                    FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
                    buf.writeNbt(itemstack.getTagElement("Fireworks"));
                    buf.writeInt(player.getId());

                    Messages.sendToPlayer(new FireworkHandler(buf),player1);
                }

            }
            player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY,20,0, false, false));

            player.teleportTo(hitResult.getLocation().x,hitResult.getLocation().y,hitResult.getLocation().z);
            List<LivingEntity> livingEntities = level.getEntitiesOfClass(LivingEntity.class,player.getBoundingBox().inflate(player.getAttribute(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).getValue()),entity -> FriendshipBracelet.PlayerFriendshipPredicate(player, (LivingEntity) entity));
            livingEntities.removeIf(livingEntity -> livingEntity == player);
            livingEntities.removeIf(livingEntity -> !livingEntity.hasLineOfSight(player));
            LivingEntity entity = level.getNearestEntity(livingEntities, TargetingConditions.forNonCombat(),player,player.getX(),player.getY(),player.getZ());
            if(entity != null){
                player.attack(entity);
                player.swing(InteractionHand.MAIN_HAND,true);
            }
            ((Player)player).getAttribute(manatick.WARD).setBaseValue(((Player) player).getAttributeBaseValue(manatick.WARD)-20);

            if (((Player)player).getAttributes().getBaseValue(manatick.WARD) < -21) {
                player.hurt(DamageSource.MAGIC,2);
            }

            return true;
        }
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        HitResult hitResult = Impale.getPlayerPOVHitResult(level,player, ClipContext.Fluid.NONE, 4*player.getAttribute(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).getValue());
        if(hitResult.getType() == HitResult.Type.BLOCK ) {
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

            compoundtag1.putByte("Flight", (byte)i);
            if (!listtag.isEmpty()) {
                compoundtag1.put("Explosions", listtag);
            }
            if(level instanceof ServerLevel serverLevel){
                for(ServerPlayer player1 : serverLevel.players()) {
                    FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
                    buf.writeNbt(itemstack.getTagElement("Fireworks"));
                    buf.writeInt(player.getId());

                    Messages.sendToPlayer(new FireworkHandler(buf),player1);
                }

            }
            player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY,20,0, false, false));

            player.teleportTo(hitResult.getLocation().x,hitResult.getLocation().y,hitResult.getLocation().z);
            List<LivingEntity> livingEntities = level.getEntitiesOfClass(LivingEntity.class,player.getBoundingBox().inflate(player.getAttribute(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).getValue()),entity -> FriendshipBracelet.PlayerFriendshipPredicate(player, (LivingEntity) entity));
            livingEntities.removeIf(livingEntity -> livingEntity == player);
            livingEntities.removeIf(livingEntity -> !livingEntity.hasLineOfSight(player));
            LivingEntity entity = level.getNearestEntity(livingEntities, TargetingConditions.forNonCombat(),player,player.getX(),player.getY(),player.getZ());
            if(entity != null){
                player.attack(entity);
                player.swing(InteractionHand.MAIN_HAND,true);
            }

        }
        return super.use(level, player, hand);

    }
}
