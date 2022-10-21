package com.cleannrooster.spellblademod.items;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

public class AmorphousPacket {
    public static final String MESSAGE_NO_MANA = "message.nomana";
    public static UUID uuid;
    public AmorphousPacket(int i) {
    }

    public AmorphousPacket(FriendlyByteBuf buf) {
        uuid = buf.readUUID();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            player.getInventory().setItem(player.getInventory().selected,new ItemStack(ModItems.AMORPHOUS.get()));

        });
        return true;
    }
}
