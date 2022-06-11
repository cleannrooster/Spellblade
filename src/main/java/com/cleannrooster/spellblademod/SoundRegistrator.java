package com.cleannrooster.spellblademod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fml.common.Mod;


public class SoundRegistrator {
    public static final SoundEvent HIT_1;
    public static final SoundEvent SHATTER_1;
    public static final SoundEvent READY_1;
    static {
        HIT_1 = addSoundsToRegistry("hit1");
        SHATTER_1 = addSoundsToRegistry("shatter1");
        READY_1 = addSoundsToRegistry("ready1");
    }

    private static SoundEvent addSoundsToRegistry(String soundId) {
        ResourceLocation shotSoundLocation = new ResourceLocation("spellblademod", soundId);
        SoundEvent soundEvent = new SoundEvent(shotSoundLocation);
        soundEvent.setRegistryName(shotSoundLocation);
        return soundEvent;
    }
}