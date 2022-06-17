package com.cleannrooster.spellblademod.effects;

import com.cleannrooster.spellblademod.entity.EndersEyeEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class DamageSourceModded {
    public static  EntityDamageSource eyelaser(Player player) { return new EntityDamageSource("eyelaser",  player);}

    public static  EntityDamageSource fluxed(Player player){ return new EntityDamageSource("fluxed",  player);}
    public static DamageSource FLUXED = new DamageSource("fluxed");
    public static DamageSource EYELASER = new DamageSource("eyelaser");
}
