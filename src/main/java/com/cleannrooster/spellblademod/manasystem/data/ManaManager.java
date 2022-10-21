package com.cleannrooster.spellblademod.manasystem.data;


import com.cleannrooster.spellblademod.manasystem.ManaConfig;
import com.cleannrooster.spellblademod.manasystem.client.GUI;
import com.cleannrooster.spellblademod.manasystem.client.ManaOverlay;
import com.cleannrooster.spellblademod.manasystem.manatick;
import com.cleannrooster.spellblademod.manasystem.network.PacketSyncManaToClient;
import com.cleannrooster.spellblademod.setup.Messages;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static net.minecraftforge.client.gui.ForgeIngameGui.PLAYER_HEALTH_ELEMENT;

public class ManaManager {

    private final Random random = new Random();

    public static int counter = 0;


    public static void tick(Level level) {
        counter++;
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
