package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.effects.DamageSourceModded;
import com.cleannrooster.spellblademod.entity.EndersEyeEntity;
import com.cleannrooster.spellblademod.entity.HammerEntity;
import com.cleannrooster.spellblademod.entity.ModEntities;
import com.cleannrooster.spellblademod.manasystem.data.PlayerMana;
import com.cleannrooster.spellblademod.manasystem.data.PlayerManaProvider;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class EndersEye extends Spell {
    public EndersEye(Properties p_41383_) {
        super(p_41383_);
    }
    public InteractionResultHolder<ItemStack> use(Level p_43405_, Player p_43406_, InteractionHand p_43407_) {

        ItemStack itemstack = p_43406_.getItemInHand(p_43407_);

        Player player = (Player) p_43406_;
        PlayerMana playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
        if (playerMana.getMana() > 79) {
            player.addEffect(new MobEffectInstance(StatusEffectsModded.WARD_DRAIN.get(), 5, 1));
            player.addEffect(new MobEffectInstance(StatusEffectsModded.ENDERSGAZE.get(), 120, 0));
            player.getCooldowns().addCooldown(this, 160);

            EndersEyeEntity eye = new EndersEyeEntity(ModEntities.ENDERS_EYE.get(),p_43405_);
            eye.setPos(player.getEyePosition());
            eye.setOwner(player);
            p_43405_.addFreshEntity(eye);
            return InteractionResultHolder.success(itemstack);

        }
        else{
            return InteractionResultHolder.fail(itemstack);
        }
    }
    public void trigger(Level level, Player player, float modifier){
        PlayerMana playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
        playerMana.addMana(-80*modifier);
        boolean flag1 = false;
        if (player.getInventory().contains(ModItems.FRIENDSHIP.get().getDefaultInstance())){
            flag1 = true;
        }
        List entities = level.getEntitiesOfClass(LivingEntity.class, new AABB(player.getX() - 6, player.getY() - 6, player.getZ() - 6, player.getX() + 6, player.getY() + 6, player.getZ() + 6));
        Object[] entitiesarray = entities.toArray();
        int entityamount = entitiesarray.length;
        for (int ii = 0; ii < entityamount; ii = ii + 1) {
            LivingEntity target = (LivingEntity) entities.get(ii);
            boolean flag2 = false;
            if (target.getClassification(false).isFriendly()|| target instanceof Player || (target instanceof NeutralMob)){
                flag2 = true;
            }
            if (target != player && !(flag1 && flag2)/*&& !Objects.requireNonNullElse(this.blacklist, new ArrayList()).contains(target)*/) {
                int i = 0;
                int num_pts = 100;
                int num_pts_line = 25;
                for (int iii = 0; iii < num_pts_line; iii++) {
                    double X = player.getEyePosition().x + (target.getEyePosition().x -player.getEyePosition().x) * ((double)iii / (num_pts_line));
                    double Y = player.getEyePosition().y + (target.getEyePosition().y - player.getEyePosition().y) * ((double)iii / (num_pts_line));
                    double Z = player.getEyePosition().z + (target.getEyePosition().z - player.getEyePosition().z) * ((double)iii / (num_pts_line));
                    level.addParticle(DustParticleOptions.REDSTONE, X, Y, Z, 0, 0, 0);
                }
                for (i = 0; i <= num_pts; i = i + 1) {
                    double[] indices = IntStream.rangeClosed(0, (int) ((1000 - 0) / 1))
                            .mapToDouble(x -> x * 1 + 0).toArray();

                    double phi = Math.acos(1 - 2 * indices[i] / num_pts);
                    double theta = Math.PI * (1 + Math.pow(5, 0.5) * indices[i]);
                    double x = cos(theta) * sin(phi);
                    double y = Math.sin(theta) * sin(phi);
                    double z = cos(phi);
                    level.addParticle((ParticleOptions) DustParticleOptions.REDSTONE, target.getEyePosition().x + 1.5 * x, target.getEyePosition().y + 1.5 * y, target.getEyePosition().z + 1.5 * z, 0, 0, 0);
                }
                target.invulnerableTime = 0;
                target.hurt(DamageSourceModded.eyelaser((Player) player), 4);
                target.invulnerableTime = 0;
            }
        }
    }

}
