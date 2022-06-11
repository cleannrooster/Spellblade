package com.cleannrooster.spellblademod.effects;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.items.Spellblade;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = "spellblademod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FluxHandler {
    @SubscribeEvent
    public static void fluxHandler(LivingHurtEvent event){
        LivingEntity living = event.getEntityLiving();
        if (event.getSource().getEntity() instanceof Player) {

            Player player = (Player)event.getSource().getEntity();
            if (living.hasEffect(StatusEffectsModded.FLUXED.get()) && player.getMainHandItem().getItem() instanceof Spellblade) {
                if (living.hasEffect(MobEffects.GLOWING)){
                    living.removeEffect(MobEffects.GLOWING);

                }
                float amount = event.getAmount();
                event.setAmount((float) (amount * 3));
                living.removeEffect(StatusEffectsModded.FLUXED.get());
                List entities = living.level.getEntitiesOfClass(LivingEntity.class, new AABB(living.getX() - 4, living.getY() + 0.5 - 4, living.getZ() - 4, living.getX() + 4, living.getY() + 4, living.getZ() + 4));
                Object[] entitiesarray = entities.toArray();

                int entityamount = entitiesarray.length;
                for (int ii = 0; ii < entityamount; ii = ii + 1) {

                    LivingEntity living2 = (LivingEntity) entities.get(ii);
                    if (living2.hasEffect(StatusEffectsModded.FLUXED.get())) {
                        living2.hurt(DamageSource.playerAttack(player), amount);
                    }
                }
            }
        }
    }
}
