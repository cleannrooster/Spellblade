package com.cleannrooster.spellblademod.manasystem.network;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.function.Supplier;

public class SyncAutouse {
    public static final String MESSAGE_NO_MANA = "message.nomana";
    private CompoundTag tag;
    private boolean bool;
    private String spellname;
    private UUID player;


    public SyncAutouse(boolean bool1, String spellname) {
        this.bool = bool1;
        this.spellname = spellname;
    }

    public SyncAutouse(FriendlyByteBuf buf) {
        tag = buf.readNbt();
        player = buf.readUUID();

    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(tag);
        buf.writeUUID(player);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();

        ctx.enqueueWork(() -> {
            CompoundTag compoundtag = new CompoundTag();
            ItemStack itemStack = ItemStack.of(Minecraft.getInstance().player.getPersistentData().getCompound("spellproxy"));
            itemStack.save(compoundtag);
            itemStack.getOrCreateTag().put("AutoTrigger",tag.getCompound("AutoTrigger"));
            itemStack.getOrCreateTag().put("Triggers",tag.getCompound("Triggers"));
            itemStack.save(compoundtag);
            CompoundTag tag = new CompoundTag();
            tag.put("spellproxy", compoundtag);
            Minecraft.getInstance().player.getPersistentData().put("spellproxy", compoundtag);

        });
        return true;

    }
}
