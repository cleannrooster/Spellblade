package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.manasystem.data.PlayerMana;
import com.cleannrooster.spellblademod.manasystem.data.PlayerManaProvider;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Objects;

public class FluxItem extends Item {
    public FluxItem(Properties p_41383_) {
        super(p_41383_);
    }
    public net.minecraft.world.InteractionResult interactLivingEntity(ItemStack stack, net.minecraft.world.entity.player.Player playerIn, LivingEntity entity, net.minecraft.world.InteractionHand hand) {
        Player player = playerIn;
        PlayerMana playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
        if(playerMana.getMana() > 39 && !player.hasEffect(StatusEffectsModded.WARD_DRAIN.get())) {
            FluxFlux(player, entity);
            player.addEffect(new MobEffectInstance(StatusEffectsModded.WARD_DRAIN.get(),5,0));
            return net.minecraft.world.InteractionResult.SUCCESS;
        }
        else{
            return InteractionResult.FAIL;
        }

    }
    public void FluxFlux(LivingEntity entity, LivingEntity target){
        if (target == null){
            return;
        }
        if (target == entity){
            return;
        }
        if (target.getLevel().isClientSide || target.invulnerableTime > 10){
            return;
        }
        target.hurt(DamageSource.MAGIC,1);
        if (target.hasEffect(StatusEffectsModded.FLUXED.get()) ) {
            List entities = target.level.getEntitiesOfClass(LivingEntity.class, new AABB(target.getX() - 4, target.getY() + 0.5 - 4, target.getZ() - 4, target.getX() + 4, target.getY() + 4, target.getZ() + 4));
            Object[] entitiesarray = entities.toArray();

            int entityamount = entitiesarray.length;
            for (int ii = 0; ii < entityamount; ii = ii + 1) {

                LivingEntity target2 = (LivingEntity) entities.get(ii);
                if(target2.invulnerableTime <= 10) {
                    FluxFlux(entity, target2);
                }
            }
        }
            target.addEffect(new MobEffectInstance(StatusEffectsModded.FLUXED.get(), 120, 0, true, true));
            target.addEffect(new MobEffectInstance(MobEffects.GLOWING,120,0));

    }
}

