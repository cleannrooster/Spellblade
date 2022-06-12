package com.cleannrooster.spellblademod.effects;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.items.Spellblade;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = "spellblademod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FluxHandler {
    @SubscribeEvent
    public static void fluxHandler(LivingAttackEvent event){
        LivingEntity living = event.getEntityLiving();
        if (living instanceof ServerPlayer){
            if (!(((ServerPlayer)living).gameMode.getGameModeForPlayer() == GameType.SURVIVAL)){
                return;
            }
        }
        if (event.getSource().getEntity() instanceof Player) {

            Player player = (Player)event.getSource().getEntity();
            if (living.hasEffect(StatusEffectsModded.FLUXED.get()) && player.getMainHandItem().getItem() instanceof Spellblade) {
                if (living.hasEffect(MobEffects.GLOWING)) {
                    living.removeEffect(MobEffects.GLOWING);

                }
                float amount = (float) (event.getAmount()*Math.pow(1.25,((Spellblade) player.getMainHandItem().getItem()).tier));
                living.hurt(new DamageSource("fluxed"), (float) (amount*2.5));
                player.addEffect(new MobEffectInstance(StatusEffectsModded.WARDING.get(),40,1));

                living.removeEffect(StatusEffectsModded.FLUXED.get());
                living.addEffect(new MobEffectInstance(StatusEffectsModded.OVERLOAD.get(), 5, 0));
                List entities = living.level.getEntitiesOfClass(LivingEntity.class, new AABB(living.getX() - 4, living.getY() + 0.5 - 4, living.getZ() - 4, living.getX() + 4, living.getY() + 4, living.getZ() + 4));
                Object[] entitiesarray = entities.toArray();
                float mult = 1;

                int entityamount = entitiesarray.length;
                for (int ii = 0; ii < entityamount; ii = ii + 1) {

                    LivingEntity living2 = (LivingEntity) entities.get(ii);
                    if (living2.hasEffect(StatusEffectsModded.FLUXED.get()) && living != living2) {
                        if (!living2.hasEffect(StatusEffectsModded.OVERLOAD.get())) {
                            fluxHandler2(living2, player,amount);
                        }
                    }
                }
            }
        }

    }
    public static void fluxHandler2(LivingEntity living, Player player, float Amount) {
        if (living instanceof ServerPlayer){
            if (!(((ServerPlayer)living).gameMode.getGameModeForPlayer() == GameType.SURVIVAL)){
                return;
            }
        }
        if (living.hasEffect(StatusEffectsModded.FLUXED.get()) && player.getMainHandItem().getItem() instanceof Spellblade) {
            if (living.hasEffect(MobEffects.GLOWING)) {
                living.removeEffect(MobEffects.GLOWING);
            }
            living.hurt(new DamageSource("fluxed"), (float) (Amount*2.5));
            living.removeEffect(StatusEffectsModded.FLUXED.get());
            living.addEffect(new MobEffectInstance(StatusEffectsModded.OVERLOAD.get(), 5, 0));
            List entities = living.level.getEntitiesOfClass(LivingEntity.class, new AABB(living.getX() - 4, living.getY() + 0.5 - 4, living.getZ() - 4, living.getX() + 4, living.getY() + 4, living.getZ() + 4));
            Object[] entitiesarray = entities.toArray();
            float mult = 1;

            int entityamount = entitiesarray.length;
            for (int ii = 0; ii < entityamount; ii = ii + 1) {

                LivingEntity living2 = (LivingEntity) entities.get(ii);
                if (living2.hasEffect(StatusEffectsModded.FLUXED.get()) && living != living2) {
                    if (!living2.hasEffect(StatusEffectsModded.OVERLOAD.get())) {
                        fluxHandler2(living2, player,Amount);
                    }
                }
            }
        }
    }
}
