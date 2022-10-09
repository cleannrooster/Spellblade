package com.cleannrooster.spellblademod.manasystem.data;


import com.cleannrooster.spellblademod.manasystem.ManaConfig;
import com.cleannrooster.spellblademod.manasystem.manatick;
import com.cleannrooster.spellblademod.manasystem.network.PacketSyncManaToClient;
import com.cleannrooster.spellblademod.setup.Messages;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ManaManager {

    private final Random random = new Random();

    private static int counter = 0;




    public static void tick(Level level) {
        counter--;
        if (counter <= 0) {
            counter = 1;
            // Synchronize the mana to the players in this world
            // todo expansion: keep the previous data that was sent to the player and only send if changed
            level.players().forEach(player -> {
                if (player instanceof ServerPlayer serverPlayer) {
                    float playerMana = (float) serverPlayer.getAttribute(manatick.WARD).getValue();
                    float playerBaseMana = (float) serverPlayer.getAttribute(manatick.BASEWARD).getValue();

                    Messages.sendToPlayer(new PacketSyncManaToClient(playerMana, playerBaseMana), serverPlayer);
                }
            });

            // todo expansion: here it would be possible to slowly regenerate mana in chunks
        }
    }




}
