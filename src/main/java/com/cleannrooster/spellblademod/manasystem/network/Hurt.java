package com.cleannrooster.spellblademod.manasystem.network;


import com.cleannrooster.spellblademod.items.BladeFlurry;
import com.cleannrooster.spellblademod.items.FriendshipBracelet;
import com.cleannrooster.spellblademod.items.Spellblade;
import com.cleannrooster.spellblademod.manasystem.manatick;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class Hurt {

    public static final String MESSAGE_NO_MANA = "message.nomana";
    private double ward;

    public Hurt(FriendlyByteBuf friendlyByteBuf) {
        ward = friendlyByteBuf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(ward);

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();

        ctx.enqueueWork(() -> {
            // Here we are server side
            ServerPlayer player = ctx.getSender();
            Minecraft.getInstance().player.getAttribute(manatick.WARD).setBaseValue(ward);

        });
        return true;
    }
}
