package com.cleannrooster.spellblademod.manasystem.data;


import com.cleannrooster.spellblademod.manasystem.ManaConfig;
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

public class ManaManager extends SavedData {

    private final Map<ChunkPos, Mana> manaMap = new HashMap<>();
    private final Random random = new Random();

    private int counter = 0;

    @Nonnull
    public static ManaManager get(Level level) {
        if (level.isClientSide) {
            throw new RuntimeException("Don't access this client-side!");
        }
        DimensionDataStorage storage = ((ServerLevel)level).getDataStorage();
        return storage.computeIfAbsent(ManaManager::new, ManaManager::new, "manamanager");
    }

    @NotNull
    private Mana getManaInternal(BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);
        return manaMap.computeIfAbsent(chunkPos, cp -> new Mana(random.nextInt(ManaConfig.CHUNK_MAX_MANA.get()) + ManaConfig.CHUNK_MIN_MANA.get()));
    }

    public float getMana(BlockPos pos) {
        Mana mana = getManaInternal(pos);
        return mana.getMana();
    }

    public int extractMana(BlockPos pos) {
        Mana mana = getManaInternal(pos);
        float present = mana.getMana();
        if (present > 0) {
            mana.setMana(present-1);
            setDirty();
            return 1;
        } else {
            return 0;
        }
    }

    public void tick(Level level) {
        counter--;
        if (counter <= 0) {
            counter = 1;
            // Synchronize the mana to the players in this world
            // todo expansion: keep the previous data that was sent to the player and only send if changed
            level.players().forEach(player -> {
                if (player instanceof ServerPlayer serverPlayer) {
                    float playerMana = serverPlayer.getCapability(PlayerManaProvider.PLAYER_MANA)
                            .map(PlayerMana::getMana)
                            .orElse((float)0);
                    float playerBaseMana = serverPlayer.getCapability(PlayerManaProvider.PLAYER_MANA)
                            .map(PlayerMana::getBasemana)
                            .orElse((float)0);
                    CompoundTag playerBaseModifiers = serverPlayer.getCapability(PlayerManaProvider.PLAYER_MANA)
                            .map(PlayerMana::getBasemodifiers)
                            .orElse(new CompoundTag());
                    float chunkMana = getMana(serverPlayer.blockPosition());
                    Messages.sendToPlayer(new PacketSyncManaToClient(playerMana, playerBaseMana, playerBaseModifiers, chunkMana), serverPlayer);
                }
            });

            // todo expansion: here it would be possible to slowly regenerate mana in chunks
        }
    }

    public ManaManager() {
    }

    public ManaManager(CompoundTag tag) {
        ListTag list = tag.getList("mana", Tag.TAG_COMPOUND);
        for (Tag t : list) {
            CompoundTag manaTag = (CompoundTag) t;
            Mana mana = new Mana(manaTag.getInt("mana"));
            ChunkPos pos = new ChunkPos(manaTag.getInt("x"), manaTag.getInt("z"));
            manaMap.put(pos, mana);
        }
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag list = new ListTag();
        manaMap.forEach((chunkPos, mana) -> {
            CompoundTag manaTag = new CompoundTag();
            manaTag.putInt("x", chunkPos.x);
            manaTag.putInt("z", chunkPos.z);
            manaTag.putFloat("mana", mana.getMana());
            list.add(manaTag);
        });
        tag.put("mana", list);
        return tag;
    }

}
