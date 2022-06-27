package com.cleannrooster.spellblademod.manasystem.network;


import com.cleannrooster.spellblademod.items.Spell;
import com.cleannrooster.spellblademod.manasystem.data.ManaManager;
import com.cleannrooster.spellblademod.manasystem.data.PlayerManaProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClickSpell {

    public static final String MESSAGE_NO_MANA = "message.nomana";
    private static int slot;
    public ClickSpell(int i) {
        slot = i;
    }

    public ClickSpell(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            // Here we are server side
            ServerPlayer player = ctx.getSender();
            ItemStack itemStack = player.getInventory().getItem(slot);
            CompoundTag nbt;
            if (itemStack.hasTag())
            {
                if(itemStack.getTag().get("Triggerable") != null) {
                    nbt = itemStack.getTag();
                    nbt.remove("Triggerable");
                    player.getInventory().setChanged();
                }
                else{
                    nbt = itemStack.getOrCreateTag();
                    nbt.putInt("Triggerable", 1);
                    player.getInventory().setChanged();
                }

            }
            else
            {
                nbt = itemStack.getOrCreateTag();
                nbt.putInt("Triggerable", 1);
                player.getInventory().setChanged();
            }
        });
        return true;
    }
}
