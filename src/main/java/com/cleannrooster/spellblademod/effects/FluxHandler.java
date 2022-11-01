package com.cleannrooster.spellblademod.effects;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.entity.FluxEntity;
import com.cleannrooster.spellblademod.entity.ModEntities;
import com.cleannrooster.spellblademod.items.FriendshipBracelet;
import com.cleannrooster.spellblademod.items.ModItems;
import com.cleannrooster.spellblademod.items.Spell;
import com.cleannrooster.spellblademod.items.Spellblade;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.IntStream;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

@Mod.EventBusSubscriber(modid = "spellblademod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FluxHandler {

    static Random random = new Random();
    /*@SubscribeEvent
    public static void fluxHandler(LivingHurtEvent event){
        LivingEntity living = event.getEntityLiving();
        if (living instanceof ServerPlayer){
            if (!(((ServerPlayer)living).gameMode.getGameModeForPlayer() == GameType.SURVIVAL)){
                return;
            }
        }
        if(event.getSource().getDirectEntity() instanceof Player) {
            Player player = (Player) event.getSource().getEntity();

                int i;
                float amount = (float) (event.getAmount());
                List<LivingEntity> entitieshit = new ArrayList<>();
                List<LivingEntity> entities = player.level.getEntitiesOfClass(LivingEntity.class,living.getBoundingBox().inflate(3D),livingEntity -> {return FriendshipBracelet.PlayerFriendshipPredicate(player,livingEntity);});
                Object[] entitiesarray = entities.toArray();
                float mult = 1;
                int ii;
                int entityamount = entitiesarray.length;

                if (player == living || event.getSource().getDirectEntity() == living) {
                    return;
                }
                if (living.hasEffect(StatusEffectsModded.FLUXED.get())) {
                    if (living.hasEffect(MobEffects.GLOWING)) {
                        living.removeEffect(MobEffects.GLOWING);
                    }
                    living.removeEffect(StatusEffectsModded.FLUXED.get());

                    living.invulnerableTime = 0;

                    living.hurt(DamageSourceModded.fluxed(player),1.5F*event.getAmount());
                    living.invulnerableTime = 0;

                    player.addEffect(new MobEffectInstance(StatusEffectsModded.WARDING.get(), 80, 1+ EnchantmentHelper.getItemEnchantmentLevel(ModItems.WARDTEMPERED.get(),player.getMainHandItem())));
                    int num_pts = 100;
                    *//*for (i = 0; i <= num_pts; i = i + 1) {
                        double[] indices = IntStream.rangeClosed(0, (int) ((num_pts - 0) / 1))
                                .mapToDouble(x -> x * 1 + 0).toArray();

                        double phi = Math.acos(1 - 2 * indices[i] / num_pts);
                        double theta = Math.PI * (1 + Math.pow(5, 0.5) * indices[i]);
                        double x = cos(theta) * sin(phi);
                        double y = Math.sin(theta) * sin(phi);
                        double z = cos(phi);

                        living.level.addParticle(ParticleTypes.GLOW_SQUID_INK.getType(), true, living.getX() + random.nextDouble(-0.1, 0.1), living.getY() + random.nextDouble(-0.1, 0.1), living.getZ() + random.nextDouble(-0.1, 0.1), x * 0.5, y * 0.5, z * 0.5);
                    }*//*

                    for (ii = 0; ii < entityamount; ii = ii + 1) {

                        LivingEntity living2 = (LivingEntity) entities.get(ii);
                        if (living2.hasEffect(StatusEffectsModded.FLUXED.get()) && living != living2) {
                            FluxEntity flux = new FluxEntity(ModEntities.FLUX_ENTITY.get(), living.getLevel());
                            flux.target = living2;
                            flux.setOwner(player);
                            flux.list = entitieshit;
                            flux.setPos(living.getBoundingBox().getCenter());
                            flux.overload = true;
                            flux.setInvisible(true);
                            flux.setCustomName(new TranslatableComponent("overload"));
                            flux.amount = amount;
                            living.level.addFreshEntity(flux);
                        }
                    }


            }
        }
    }*/
    public static void fluxHandler2(LivingEntity living, Player player, float Amount, Level level, List<LivingEntity> list, @Nullable List<Spell> spells, UUID fluxid) {
        if (living instanceof ServerPlayer){
            if (!(((ServerPlayer)living).gameMode.getGameModeForPlayer() == GameType.SURVIVAL)){
                return;
            }
        }


        if (living.hasEffect(MobEffects.GLOWING)) {
            living.removeEffect(MobEffects.GLOWING);
        }
        /*int num_pts = 100;
            for (int i = 0; i <= num_pts; i = i + 1) {
                double[] indices = IntStream.rangeClosed(0, (int) ((num_pts - 0) / 1))
                        .mapToDouble(x -> x * 1 + 0).toArray();

                double phi = Math.acos(1 - 2 * indices[i] / num_pts);
                double theta = Math.PI * (1 + Math.pow(5, 0.5) * indices[i]);
                double x = cos(theta) * sin(phi);
                double y = Math.sin(theta) * sin(phi);
                double z = cos(phi);
                level.addParticle(ParticleTypes.GLOW_SQUID_INK.getType(), true, living.getX() + random.nextDouble(-0.1, 0.1), living.getY() + random.nextDouble(-0.1, 0.1), living.getZ() + random.nextDouble(-0.1, 0.1), x * 0.5, y * 0.5, z * 0.5);
            }*/
        list.add(living);


        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,living.getBoundingBox().inflate(3D),livingEntity -> {return FriendshipBracelet.PlayerFriendshipPredicate(player,livingEntity);});
        entities.removeIf(Entity::isInvulnerable);
        entities.removeIf(entity -> !entity.hasEffect(StatusEffectsModded.FLUXED.get()));
        entities.removeIf(entity2 -> !entity2.hasEffect(StatusEffectsModded.FLUXED.get()) || entity2 == living || list.contains(entity2) || entity2 == player);

        Object[] entitiesarray = entities.toArray();
        float mult = 1;

        int entityamount = entitiesarray.length;

        for (int ii = 0; ii < 8; ii++) {
            if(entities.isEmpty()){
                break;
            }
            LivingEntity living2 = level.getNearestEntity(entities, TargetingConditions.forNonCombat(),null, living.getX(),living.getY(),living.getZ());
            if(living2 != null) {
                FluxEntity flux = new FluxEntity(ModEntities.FLUX_ENTITY.get(), living.getLevel());
                flux.target = living2;
                flux.setOwner(player);
                flux.list = list;
                flux.setPos(living.getBoundingBox().getCenter());
                flux.overload = true;
                flux.setInvisible(true);
                flux.setCustomName(new TranslatableComponent("overload"));
                flux.bool2 = true;
                flux.amount = Amount;
                flux.spells = spells;
                flux.fluxid = fluxid;

                living.level.addFreshEntity(flux);
            }
            entities.remove(living2);
        }
    }
    @SubscribeEvent
    public static void PreventTeleport(EntityTeleportEvent.EnderEntity event){
        if (event.getEntityLiving().hasEffect(StatusEffectsModded.FLUXED.get())){
            event.setCanceled(true);
        }
    }
}
