package com.cleannrooster.spellblademod.manasystem.network;


import com.cleannrooster.spellblademod.SpellbladeMod;
import com.cleannrooster.spellblademod.items.BladeFlurry;
import com.cleannrooster.spellblademod.items.FriendshipBracelet;
import com.cleannrooster.spellblademod.items.Spell;
import com.cleannrooster.spellblademod.items.Spellblade;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClickSpell {

    public static final String MESSAGE_NO_MANA = "message.nomana";
    private ItemStack itemStack;
    private int containerId;
    private int increment;
    private int slot;




    public ClickSpell(ItemStack itemStack, int containerid, int increment, int slot) {
        this.itemStack = itemStack;
        this.containerId = containerid;
        this.increment = increment;
        this.slot = slot;

    }

    public ClickSpell(FriendlyByteBuf buf) {
        itemStack = buf.readItem();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeItemStack(itemStack,false);
        buf.writeInt(containerId);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            // Here we are server side
            ServerPlayer player = ctx.getSender();
                CompoundTag nbt;
                player.connection.send(new ClientboundContainerSetSlotPacket(containerId, increment, slot, itemStack));
                /*if (itemStack.getItem() instanceof BladeFlurry) {
                    if (itemStack.hasTag()) {
                        if (itemStack.getTag().get("Triggerable") != null) {
                            if (itemStack.getTag().getInt("Mode") < 3) {
                                nbt = itemStack.getTag();
                                nbt.putInt("Mode", itemStack.getTag().getInt("Mode") + 1);

                            } else {
                                nbt = itemStack.getTag();
                                nbt.remove("Triggerable");
                                nbt.remove("Mode");
                                player.getInventory().setChanged();
                            }
                        } else {
                            nbt = itemStack.getOrCreateTag();
                            nbt.putInt("Triggerable", 1);
                            nbt.putInt("Mode", 1);
                            player.getInventory().setChanged();
                        }

                    } else {
                        nbt = itemStack.getOrCreateTag();
                        nbt.putInt("Triggerable", 1);
                        nbt.putInt("Mode", 1);
                        player.getInventory().setChanged();
                    }
                    return;
                }
                if (itemStack.getItem() instanceof FriendshipBracelet) {
                    if (itemStack.hasTag()) {
                        if (itemStack.getTag().get("Friendship") != null) {
                            nbt = itemStack.getTag();
                            nbt.remove("Friendship");
                            player.getInventory().setChanged();
                        } else {
                            nbt = itemStack.getOrCreateTag();
                            nbt.putInt("Friendship", 1);
                            player.getInventory().setChanged();
                        }

                    } else {
                        nbt = itemStack.getOrCreateTag();
                        nbt.putInt("Friendship", 1);
                        player.getInventory().setChanged();
                    }
                    return;
                } else if (itemStack.getItem() instanceof Spellblade || EnchantmentHelper.getEnchantments(itemStack).containsKey(SpellbladeMod.spellproxy)) {
                    ItemStack itemStack1 = ItemStack.of(player.getPersistentData().getCompound("spellproxy"));

                    if (itemStack.hasTag()) {
                        if (itemStack.getTag().get("OnHit") != null) {
                            nbt = itemStack.getTag();
                            nbt.remove("OnHit");
                            player.getInventory().setChanged();
                        } else {
                            nbt = itemStack.getOrCreateTag();
                            nbt.putInt("OnHit", 1);
                            player.getInventory().setChanged();
                        }

                    } else {
                        nbt = itemStack.getOrCreateTag();
                        nbt.putInt("OnHit", 1);
                        player.getInventory().setChanged();
                    }
                    return;
                } else if (itemStack.hasTag()) {
                    if (itemStack.getTag().get("Triggerable") != null) {
                        nbt = itemStack.getTag();
                        nbt.remove("Triggerable");
                        player.getInventory().setChanged();
                    } else {
                        nbt = itemStack.getOrCreateTag();
                        nbt.putInt("Triggerable", 1);
                        player.getInventory().setChanged();
                    }
                    System.out.println(itemStack);

                } else {
                    nbt = itemStack.getOrCreateTag();
                    nbt.putInt("Triggerable", 1);
                    player.getInventory().setChanged();
                }

*/
        });
        return true;
    }
}
