package com.cleannrooster.spellblademod.manasystem.network;

import com.cleannrooster.spellblademod.manasystem.client.ClientManaData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncManaToClient {

    private final float playerMana;
    private final float chunkMana;

    public PacketSyncManaToClient(float playerMana, float chunkMana) {
        this.playerMana = playerMana;
        this.chunkMana = chunkMana;
    }

    public PacketSyncManaToClient(FriendlyByteBuf buf) {
        playerMana = buf.readFloat();
        chunkMana = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(playerMana);
        buf.writeFloat(chunkMana);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            // Here we are client side.
            // Be very careful not to access client-only classes here! (like Minecraft) because
            // this packet needs to be available server-side too
            ClientManaData.set(playerMana, chunkMana);
        });
        return true;
    }
}
