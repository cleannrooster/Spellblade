package com.cleannrooster.spellblademod.manasystem.network;

import com.cleannrooster.spellblademod.items.FlaskItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import org.checkerframework.checker.units.qual.C;

import java.util.function.Supplier;

public class RetrieveItem {
    public static final String MESSAGE_NO_MANA = "message.nomana";
    private ItemStack flaskItem ;
    private ItemStack slotItem;
    private boolean trigger;

    public RetrieveItem(ItemStack flaskItem, ItemStack slotItem) {
        this.flaskItem = flaskItem;
        this.slotItem = slotItem;


    }

    public RetrieveItem(FriendlyByteBuf buf) {
        flaskItem = buf.readItem();
        slotItem = buf.readItem();
        trigger = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeItemStack(flaskItem,false);
        buf.writeItemStack(slotItem,false);
        buf.writeBoolean(trigger);

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            CompoundTag compoundtag = new CompoundTag();
            ItemStack itemStack = ItemStack.of(player.getPersistentData().getCompound("spellproxy"));
            itemStack.save(compoundtag);

            ((FlaskItem)flaskItem.getItem()).applyFlask(player,null,flaskItem,itemStack,trigger);

            CompoundTag nbt = itemStack.getOrCreateTag();
            if(trigger) {
                CompoundTag autoUse = nbt.getCompound("AutoTrigger");

                if (nbt.getCompound("AutoTrigger").contains(flaskItem.getOrCreateTag().getString("Spell"))) {
                    nbt.getCompound("AutoTrigger").remove(flaskItem.getOrCreateTag().getString("Spell"));

                } else {
                    autoUse.putBoolean(flaskItem.getOrCreateTag().getString("Spell"), true);

                }
                if (!nbt.contains("AutoTrigger")) {
                    autoUse.putBoolean(flaskItem.getOrCreateTag().getString("Spell"), true);
                }
                nbt.put("AutoTrigger", autoUse);
            }
            else {
                CompoundTag autoUse = nbt.getCompound("AutoUse");

                if (nbt.getCompound("AutoUse").contains(flaskItem.getOrCreateTag().getString("Spell"))) {
                    nbt.getCompound("AutoUse").remove(flaskItem.getOrCreateTag().getString("Spell"));

                } else {
                    autoUse.putBoolean(flaskItem.getOrCreateTag().getString("Spell"), true);

                }
                if (!nbt.contains("AutoUse")) {
                    autoUse.putBoolean(flaskItem.getOrCreateTag().getString("Spell"), false);
                }
                nbt.put("AutoUse", autoUse);
            }
            itemStack.save(compoundtag);
            CompoundTag tag = new CompoundTag();
            tag.put("spellproxy", compoundtag);
            player.getPersistentData().put("spellproxy",compoundtag);
        });
        return true;

    }
}
