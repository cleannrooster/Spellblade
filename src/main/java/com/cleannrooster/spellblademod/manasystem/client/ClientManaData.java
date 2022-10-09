package com.cleannrooster.spellblademod.manasystem.client;

import net.minecraft.nbt.CompoundTag;

/**
 * Class holding the data for mana client-side
 */
public class ClientManaData {

    private static float playerMana;
    private static float playerBaseMana;
    private static CompoundTag playerBasemodifiers;

    private static float chunkMana;

    public static void set(float playerMana, float playerBaseMana) {
        ClientManaData.playerMana = playerMana;
        ClientManaData.playerBaseMana = playerBaseMana;
    }

    public static float getPlayerMana() {
        return playerMana;
    }

    public static float getPlayerBaseMana() {
        return playerBaseMana;
    }
}