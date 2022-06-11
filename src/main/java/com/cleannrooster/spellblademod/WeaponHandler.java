package com.cleannrooster.spellblademod;

import com.cleannrooster.spellblademod.items.*;
import com.cleannrooster.spellblademod.manasystem.data.PlayerMana;
import com.cleannrooster.spellblademod.manasystem.data.PlayerManaProvider;
import net.minecraft.stats.Stat;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = "spellblademod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WeaponHandler {

    @SubscribeEvent
    public static void lightningwhirlhandler(PotionEvent.PotionExpiryEvent event){
        if (event.getEntityLiving() instanceof Zombie)
        {
            if (event.getPotionEffect().getEffect() == StatusEffectsModded.LIGHTNING_WHIRL_EFFECT.get()){
                event.getEntityLiving().remove(Entity.RemovalReason.DISCARDED);
            }
        }
    }
    @SubscribeEvent
    public static void lightningwhirlhandler2(LivingEvent.LivingUpdateEvent event){

        if (event.getEntityLiving() instanceof Zombie)
        {
            AABB aabb1 = event.getEntityLiving().getBoundingBox();
            if (event.getEntityLiving().getEffect( StatusEffectsModded.LIGHTNING_WHIRL_EFFECT.get()) != null){
                AABB aabb2 = aabb1.minmax(aabb1);
                List<Entity> list = event.getEntityLiving().level.getEntities(event.getEntityLiving(), aabb2);
                if (!list.isEmpty()) {
                    for(int i = 0; i < list.size(); ++i) {
                        Entity entity = list.get(i);
                        if (entity instanceof LivingEntity) {
                            (entity).hurt(DamageSource.GENERIC,10);
                            event.getEntityLiving().setDeltaMovement(event.getEntityLiving().getDeltaMovement().scale(-0.2D));
                            break;
                        }
                    }
                } else if (event.getEntityLiving().horizontalCollision) {
                    event.getEntityLiving().setPose(Pose.FALL_FLYING);
                }

            }
        }
    }

    @SubscribeEvent
    public static void SpellbladeHandler(LivingHurtEvent event){
        if (event.getSource().getEntity() instanceof Player) {
            Player player = (Player) event.getSource().getEntity();
            PlayerMana playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
            if (((Player) event.getSource().getEntity()).getMainHandItem().getItem() instanceof Spellblade) {
                Spellblade spellblade = (Spellblade) ((Player) event.getSource().getEntity()).getMainHandItem().getItem();
                event.setAmount((float) (event.getAmount()*Math.pow(1.25, spellblade.tier)));
            }
            if (player.getOffhandItem().getItem() instanceof LightningEffigy && playerMana.getMana() >= 39 && player.getMainHandItem().getItem() instanceof Spellblade && !player.hasEffect(StatusEffectsModded.WARD_DRAIN.get())){
                player.addEffect(new MobEffectInstance(StatusEffectsModded.WARD_DRAIN.get(),5,0));
                if (event.getEntityLiving().hasEffect(StatusEffectsModded.SHOCKED.get())){
                    int amplifier = event.getEntityLiving().getEffect(StatusEffectsModded.SHOCKED.get()).getAmplifier();
                    if (amplifier >= 2){
                        event.getEntityLiving().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 9));
                        event.getEntityLiving().addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 0));
                    }
                    else{
                        event.getEntityLiving().addEffect(new MobEffectInstance(StatusEffectsModded.SHOCKED.get(),80, amplifier+1));
                    }
                }
                else {
                    event.getEntityLiving().addEffect(new MobEffectInstance(StatusEffectsModded.SHOCKED.get(), 80, 0));
                }
            }
            if (player.getOffhandItem().getItem() instanceof LightningWhirl && player.isAutoSpinAttack() && player.getMainHandItem().getItem() instanceof Spellblade){
                if (event.getEntityLiving().hasEffect(StatusEffectsModded.SHOCKED.get())){
                    int amplifier = event.getEntityLiving().getEffect(StatusEffectsModded.SHOCKED.get()).getAmplifier();
                    if (amplifier >= 2){
                        event.getEntityLiving().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 9));
                        event.getEntityLiving().addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20, 0));
                    }
                    else{
                        event.getEntityLiving().addEffect(new MobEffectInstance(StatusEffectsModded.SHOCKED.get(),80, amplifier+1));
                    }
                }
                else {
                    event.getEntityLiving().addEffect(new MobEffectInstance(StatusEffectsModded.SHOCKED.get(), 80, 0));
                }
            }
            if (player.getOffhandItem().getItem() instanceof IceEffigy && playerMana.getMana() >= 39 && player.getMainHandItem().getItem() instanceof Spellblade && !player.hasEffect(StatusEffectsModded.WARD_DRAIN.get())){
                player.addEffect(new MobEffectInstance(StatusEffectsModded.WARD_DRAIN.get(),5,0));
                if (event.getEntityLiving().hasEffect(MobEffects.MOVEMENT_SLOWDOWN)){
                    int amplifier = event.getEntityLiving().getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier();
                    if (amplifier >= 1){
                        event.setAmount(event.getAmount()*2);
                        event.getEntityLiving().removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
                    }
                    else{
                        event.getEntityLiving().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,80, amplifier+1));
                    }
                }
                else {
                    event.getEntityLiving().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 0));
                }
            }
            if (player.getOffhandItem().getItem() instanceof FireEffigy && playerMana.getMana()>=39 && player.getMainHandItem().getItem() instanceof Spellblade&& !player.hasEffect(StatusEffectsModded.WARD_DRAIN.get())){
                player.addEffect(new MobEffectInstance(StatusEffectsModded.WARD_DRAIN.get(),5,0));
                if (event.getEntityLiving().isOnFire()){
                    AABB box = event.getEntityLiving().getBoundingBox();
                    AABB playerarea = box.inflate(4);
                    List entities = player.level.getEntitiesOfClass(LivingEntity.class, playerarea);
                    Object[] entitiesarray = entities.toArray();
                    int entityamount = entitiesarray.length;

                        for (int ii = 0; ii < entityamount; ii = ii + 1) {
                            LivingEntity target = (LivingEntity) entities.get(ii);
                            if (event.getEntityLiving().hasLineOfSight(target) && !(target == player)) {
                                target.setSecondsOnFire(4);
                                target.hurt(DamageSource.IN_FIRE,event.getAmount());
                            }
                        }
                }
                else{
                    event.getEntityLiving().setSecondsOnFire(4);
                }

            }


        }
    }
    @SubscribeEvent
    public static void VengefulHandler(LivingHurtEvent event) {
        if (event.getEntityLiving() instanceof Player) {
            Player vengeant = (Player) event.getEntityLiving();
            if (vengeant.hasEffect(StatusEffectsModded.VENGEFUL_STANCE.get())) {
                if (event.getSource().getEntity() instanceof LivingEntity) {
                    event.getSource().getEntity().hurt(DamageSource.playerAttack(vengeant), (float) vengeant.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
                }
            }
        }
    }
}
