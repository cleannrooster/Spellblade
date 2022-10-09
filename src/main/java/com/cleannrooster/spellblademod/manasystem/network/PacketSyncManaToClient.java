package com.cleannrooster.spellblademod.manasystem.network;

import com.cleannrooster.spellblademod.manasystem.client.ClientManaData;
import com.cleannrooster.spellblademod.manasystem.manatick;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncManaToClient {

    private final float playerMana;
    private final float playerBaseMana;

    public PacketSyncManaToClient(float playerMana, float playerBaseMana) {
        this.playerMana = playerMana;
        this.playerBaseMana = playerBaseMana;

    }

    public PacketSyncManaToClient(FriendlyByteBuf buf) {
        playerMana = buf.readFloat();
        playerBaseMana = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(playerMana);
        buf.writeFloat(playerBaseMana);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            Minecraft.getInstance().player.getAttribute(manatick.WARD).setBaseValue(playerMana);
            Minecraft.getInstance().player.getAttribute(manatick.BASEWARD).setBaseValue(playerBaseMana);

            // Here we are client side.
            // Be very careful not to access client-only classes here! (like Minecraft) because
            // this packet needs to be available server-side too
            ClientManaData.set(playerMana, playerBaseMana);
        });
        return true;
    }
}
