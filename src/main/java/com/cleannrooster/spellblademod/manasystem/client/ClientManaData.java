package com.cleannrooster.spellblademod.manasystem.client;

/**
 * Class holding the data for mana client-side
 */
public class ClientManaData {

    private static float playerMana;
    private static float chunkMana;

    public static void set(float playerMana, float chunkMana) {
        ClientManaData.playerMana = playerMana;
        ClientManaData.chunkMana = chunkMana;
    }

    public static float getPlayerMana() {
        return playerMana;
    }

    public static float getChunkMana() {
        return chunkMana;
    }
}
