package com.cleannrooster.spellblademod.patreon;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class EmeraldPacket {
    UUID entity;
    String string;
    public EmeraldPacket(UUID entity, String name) {
        this.entity =  entity;
    }

    public EmeraldPacket(FriendlyByteBuf buf) {
        this.entity = buf.readUUID();

    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(entity);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();

        ctx.enqueueWork(() -> {
            Patreon.emeraldbladeflurry.add(ctx.getSender().level.getPlayerByUUID(entity));
        });
        return true;

    }
}
