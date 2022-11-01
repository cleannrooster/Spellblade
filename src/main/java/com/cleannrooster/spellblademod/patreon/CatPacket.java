package com.cleannrooster.spellblademod.patreon;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class CatPacket {
    boolean bool;
    UUID entity;
    String string;
    public CatPacket(UUID entity, boolean bool) {
        this.entity =  entity;
        this.bool = bool;
    }

    public CatPacket(FriendlyByteBuf buf) {
        this.entity = buf.readUUID();

    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(entity);

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();

        ctx.enqueueWork(() -> {
            if(Patreon.cat.contains(ctx.getSender().level.getPlayerByUUID(entity))) {
                Patreon.cat.remove(ctx.getSender().level.getPlayerByUUID(entity));
            }
            else{
                Patreon.cat.add(ctx.getSender().level.getPlayerByUUID(entity));
            }
        });
        return true;

    }
}
