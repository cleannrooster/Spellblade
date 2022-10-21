package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.SpellbladeMod;
import com.cleannrooster.spellblademod.manasystem.network.ClickSpell;
import com.cleannrooster.spellblademod.setup.Messages;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = "spellblademod", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT )
public class SpellRightClickHandler {
    @SubscribeEvent
    public static void onClick(ScreenEvent.MouseClickedEvent event){
        /*if ((event.getScreen() instanceof AbstractContainerScreen screen && event.getButton() == 1)) {
            if (screen.getSlotUnderMouse() != null) {

                int containerId = screen.getMenu().containerId;
                ItemStack itemStack = Objects.requireNonNull(screen.getSlotUnderMouse()).getItem();

                if (itemStack.getItem() instanceof Flask || itemStack.getItem() instanceof FriendshipBracelet || itemStack.getItem() instanceof Spellblade || itemStack.getItem() instanceof Spell || itemStack.getItem() instanceof Guard || EnchantmentHelper.getEnchantments(itemStack).containsKey(SpellbladeMod.spellproxy)) {
                        *//*if (screen.getSlotUnderMouse() != null) {
                            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
                            buf.writeItemStack(itemStack,false);

                            Messages.sendToServer(new ClickSpell(buf));
                            event.setCanceled(true);
                        }*//*
                    CompoundTag nbt;
                    Player player = Minecraft.getInstance().player;
                    if (itemStack.getItem() instanceof BladeFlurry) {
                            if (itemStack.hasTag()) {
                                if (itemStack.getTag().get("Triggerable") != null) {
                                    if (itemStack.getTag().getInt("Mode") < 3) {
                                        nbt = itemStack.getTag();
                                        nbt.putInt("Mode", itemStack.getTag().getInt("Mode") + 1);

                                    } else {
                                        nbt = itemStack.getTag();
                                        nbt.remove("Triggerable");
                                        nbt.remove("Mode");
                                    }
                                } else {
                                    nbt = itemStack.getOrCreateTag();
                                    nbt.putInt("Triggerable", 1);
                                    nbt.putInt("Mode", 1);
                                }

                            } else {
                                nbt = itemStack.getOrCreateTag();
                                nbt.putInt("Triggerable", 1);
                                nbt.putInt("Mode", 1);
                            }
                            return;
                        }
                        if (itemStack.getItem() instanceof FriendshipBracelet) {
                            if (itemStack.hasTag()) {
                                if (itemStack.getTag().get("Friendship") != null) {
                                    nbt = itemStack.getTag();
                                    nbt.remove("Friendship");
                                } else {
                                    nbt = itemStack.getOrCreateTag();
                                    nbt.putInt("Friendship", 1);
                                }

                            } else {
                                nbt = itemStack.getOrCreateTag();
                                nbt.putInt("Friendship", 1);
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


                    }
                Messages.sendToServer(new ClickSpell(itemStack,screen.getMenu().containerId,screen.getMenu().incrementStateId(),screen.getSlotUnderMouse().getContainerSlot()));
                event.setCanceled(true);

            }
        }*/
        /*if ((event.getScreen() instanceof CreativeModeInventoryScreen screen && event.getButton() == 1)) {
            if (screen.getSelectedTab() == CreativeModeTab.TAB_INVENTORY.getId()) {
                if (screen.getSlotUnderMouse() != null) {
                    ItemStack itemStack = Objects.requireNonNull(screen.getSlotUnderMouse()).getItem();
                    FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
                    buf.writeInt(Objects.requireNonNull(screen.getSlotUnderMouse()).getSlotIndex());
                    if (itemStack.getItem() instanceof Flask || itemStack.getItem() instanceof FriendshipBracelet || itemStack.getItem() instanceof Spellblade|| itemStack.getItem() instanceof Spell || itemStack.getItem() instanceof Guard ||  EnchantmentHelper.getEnchantments(itemStack).containsKey(SpellbladeMod.spellproxy)) {
                        Messages.sendToServer(new ClickSpell(buf));
                *//*CompoundTag nbt;
                if (itemStack.hasTag()) {
                    nbt = itemStack.getTag();
                    nbt.remove("Triggerable");

                } else {
                    nbt = itemStack.getOrCreateTag();
                    nbt.putInt("Triggerable", 1);
                }*//*
                        event.setCanceled(true);
                    }
                }
            }
        }*/
    }
}
