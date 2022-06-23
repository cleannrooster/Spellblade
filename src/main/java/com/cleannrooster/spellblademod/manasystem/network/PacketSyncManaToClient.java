package com.cleannrooster.spellblademod.manasystem.network;

import com.cleannrooster.spellblademod.manasystem.client.ClientManaData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncManaToClient {

    private final float playerMana;
    private final float playerBaseMana;
    private final float chunkMana;
    private final CompoundTag playerBasemodifiers;

    public PacketSyncManaToClient(float playerMana, float playerBaseMana, CompoundTag playerBasemodifiers, float chunkMana) {
        this.playerMana = playerMana;
        this.chunkMana = chunkMana;
        this.playerBaseMana = playerBaseMana;
        this.playerBasemodifiers = playerBasemodifiers;

    }

    public PacketSyncManaToClient(FriendlyByteBuf buf) {
        playerMana = buf.readFloat();
        playerBaseMana = buf.readFloat();
        playerBasemodifiers = buf.readNbt();
        chunkMana = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(playerMana);
        buf.writeFloat(playerBaseMana);
        buf.writeNbt(playerBasemodifiers);
        buf.writeFloat(chunkMana);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            // Here we are client side.
            // Be very careful not to access client-only classes here! (like Minecraft) because
            // this packet needs to be available server-side too
            ClientManaData.set(playerMana, playerBaseMana, playerBasemodifiers, chunkMana);
        });
        return true;
    }
}
