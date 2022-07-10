package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "spellblademod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GuardHandler {
    @SubscribeEvent
    public static void guard(LivingHurtEvent event){
        if(event.getEntityLiving() instanceof Player && event.getSource().getEntity() instanceof LivingEntity){
            Player player = (Player) event.getEntityLiving();
            if (event.getEntityLiving().getUseItem().getItem() instanceof Spellblade){
                boolean flag = false;
                int ii = 0;
                for (int i = 0; i <= player.getInventory().getContainerSize(); i++) {
                    if (player.getInventory().getItem(i).getItem() instanceof Guard) {
                        if (player.getInventory().getItem(i).hasTag()) {
                            if (player.getInventory().getItem(i).getTag().getInt("Triggerable") == 1) {
                                if (!player.getCooldowns().isOnCooldown(player.getInventory().getItem(i).getItem())) {

                                    ((Guard) player.getInventory().getItem(i).getItem()).guard(player, player.level, (LivingEntity) event.getSource().getEntity());
                                    ii++;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
