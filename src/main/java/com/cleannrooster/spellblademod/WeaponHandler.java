package com.cleannrooster.spellblademod;

import com.cleannrooster.spellblademod.items.*;
import com.cleannrooster.spellblademod.manasystem.data.PlayerMana;
import com.cleannrooster.spellblademod.manasystem.data.PlayerManaProvider;
import com.mojang.math.Vector3f;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.stats.Stat;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.EnderChestBlock;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
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
            if (((Player) event.getSource().getEntity()).getMainHandItem().getItem() instanceof Spellblade  && event.getSource().msgId != "eyelaser") {
                Spellblade spellblade = (Spellblade) ((Player) event.getSource().getEntity()).getMainHandItem().getItem();
                event.setAmount((float) (event.getAmount()*Math.pow(1.25, spellblade.tier)));
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
            if (player.getOffhandItem().getItem() instanceof IceEffigy && playerMana.getMana() >= 39 && player.getMainHandItem().getItem() instanceof Spellblade){
                player.addEffect(new MobEffectInstance(StatusEffectsModded.WARD_DRAIN.get(),5,0));
                event.getEntityLiving().addEffect(new MobEffectInstance(StatusEffectsModded.ECHOES.get(),10, (int) Math.floor(event.getAmount())));
            }
        }
    }
    @SubscribeEvent
    public static void EchoesHandler(PotionEvent.PotionExpiryEvent event){
        if (event.getPotionEffect() != null) {
            if (event.getPotionEffect().getEffect() == StatusEffectsModded.ECHOES.get()) {
                event.getEntityLiving().invulnerableTime = 0;
                event.getEntityLiving().hurt(new DamageSource("echoes"), event.getPotionEffect().getAmplifier());
                event.getEntityLiving().invulnerableTime = 0;
            }
        }
    }
    @SubscribeEvent
    public static void FireEffigyHandler(LivingDeathEvent event){
        if(event.getSource().getEntity() instanceof Player){
            if (((Player)event.getSource().getEntity()).getOffhandItem().getItem() instanceof FireEffigy){
                Player player = (Player) event.getSource().getEntity();
                PlayerMana playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
                if (playerMana.getMana() >= 39) {
                    player.addEffect(new MobEffectInstance(StatusEffectsModded.WARD_DRAIN.get(), 5, 0));
                    if (!event.getEntityLiving().getLevel().isClientSide()) {
                        event.getEntityLiving().getLevel().explode(event.getSource().getEntity(), event.getEntityLiving().getX(), event.getEntityLiving().getY(), event.getEntityLiving().getZ(), 3, false, Explosion.BlockInteraction.NONE);
                    }
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
    @SubscribeEvent
    public static void EffigyOfChaining(LivingAttackEvent event){
        if (event.getSource().getDirectEntity() instanceof Player && ((DamageSource)event.getSource()).msgId != "chain" && ((DamageSource)event.getSource()).msgId != "fluxed") {
            Player player = (Player) event.getSource().getDirectEntity();
            LivingEntity living = event.getEntityLiving();
            PlayerMana playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
            if (player.getOffhandItem().getItem() instanceof LightningEffigy && playerMana.getMana() >= 39 && player.getMainHandItem().getItem() instanceof Spellblade) {
                player.addEffect(new MobEffectInstance(StatusEffectsModded.WARD_DRAIN.get(), 5, 0));
                List entities = player.level.getEntitiesOfClass(LivingEntity.class, new AABB(living.getX() - 4, living.getY() + 0.5 - 4, living.getZ() - 4, living.getX() + 4, living.getY() + 4, living.getZ() + 4));
                List<LivingEntity> validentities = new ArrayList<>();
                Object[] entitiesarray = entities.toArray();
                int entityamount = entitiesarray.length;
                boolean flag1 = false;
                if (player.getInventory().contains(ModItems.FRIENDSHIP.get().getDefaultInstance())){
                    flag1 = true;
                }
                for (int ii = 0; ii < entityamount; ii = ii + 1) {
                LivingEntity target = (LivingEntity) entities.get(ii);
                    boolean flag2 = false;
                    if (target.getClassification(false).isFriendly()|| target instanceof Player || (target instanceof NeutralMob)){
                        flag2 = true;
                    }
                    if (target != player && target != living && !(flag1 && flag2)) {
                        validentities.add(target);
                    }

                }
                LivingEntity chained = player.getLevel().getNearestEntity(validentities, TargetingConditions.forNonCombat().ignoreLineOfSight(), living, living.getX(), living.getY(), living.getZ());
                if (chained != null) {
                    int num_pts_line = 50;
                    for (int iii = 0; iii < num_pts_line; iii++) {
                        double X = living.getBoundingBox().getCenter().x + (chained.getBoundingBox().getCenter().x -living.getBoundingBox().getCenter().x) * ((double)iii / (num_pts_line));
                        double Y = living.getBoundingBox().getCenter().y + (chained.getBoundingBox().getCenter().y - living.getBoundingBox().getCenter().y) * ((double)iii / (num_pts_line));
                        double Z = living.getBoundingBox().getCenter().z + (chained.getBoundingBox().getCenter().z - living.getBoundingBox().getCenter().z) * ((double)iii / (num_pts_line));
                        chained.level.addParticle(new DustParticleOptions(new Vector3f(Vec3.fromRGB24(65535)), 1F), X, Y, Z, 0, 0, 0);
                    }
                    List entities2 = player.level.getEntitiesOfClass(LivingEntity.class, new AABB(chained.getX() - 4, chained.getY() + 0.5 - 4, chained.getZ() - 4, chained.getX() + 4, chained.getY() + 4, chained.getZ() + 4));
                    List<LivingEntity> validentities2 = new ArrayList<>();
                    Object[] entitiesarray2 = entities2.toArray();
                    int entityamount2 = entitiesarray2.length;
                    for (int ii = 0; ii < entityamount2; ii = ii + 1) {
                        LivingEntity target = (LivingEntity) entities2.get(ii);
                        boolean flag3 = false;
                        if (target.getClassification(false).isFriendly()|| target instanceof Player || (target instanceof NeutralMob)){
                            flag3 = true;
                        }
                        if (target != player && target != living && target != chained && !(flag1 && flag3)) {
                            validentities2.add(target);
                        }

                    }
                    chained.hurt(new EntityDamageSource("chain", player), event.getAmount());

                    LivingEntity chained2 = player.getLevel().getNearestEntity(validentities2, TargetingConditions.forNonCombat().ignoreLineOfSight(), chained, chained.getX(), chained.getY(), chained.getZ());
                    if (chained2 != null) {
                        int num_pts_line2 = 50;
                        for (int iii = 0; iii < num_pts_line2; iii++) {
                            double X = chained.getBoundingBox().getCenter().x + (chained2.getBoundingBox().getCenter().x -chained.getBoundingBox().getCenter().x) * ((double)iii / (num_pts_line));
                            double Y = chained.getBoundingBox().getCenter().y + (chained2.getBoundingBox().getCenter().y - chained.getBoundingBox().getCenter().y) * ((double)iii / (num_pts_line));
                            double Z = chained.getBoundingBox().getCenter().z + (chained2.getBoundingBox().getCenter().z - chained.getBoundingBox().getCenter().z) * ((double)iii / (num_pts_line));
                            chained2.level.addParticle(new DustParticleOptions(new Vector3f(Vec3.fromRGB24(65535)),1F), X, Y, Z, 0, 0, 0);

                        }
                        chained2.hurt(new EntityDamageSource("chain", player), event.getAmount());

                    }
                }

            }
        }
    }
}
