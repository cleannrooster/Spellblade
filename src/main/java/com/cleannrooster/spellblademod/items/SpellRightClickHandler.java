package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.manasystem.network.ClickSpell;
import com.cleannrooster.spellblademod.setup.Messages;
import io.netty.buffer.Unpooled;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
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
        if ((event.getScreen() instanceof InventoryScreen screen && event.getButton() == 1)) {

            if (screen.getSlotUnderMouse() != null) {
                ItemStack itemStack = Objects.requireNonNull(screen.getSlotUnderMouse()).getItem();
                if (itemStack.getItem() instanceof FriendshipBracelet || itemStack.getItem() instanceof Spellblade||itemStack.getItem() instanceof Spell || itemStack.getItem() instanceof Guard) {

                    if (screen.getSlotUnderMouse() != null) {
                        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
                        buf.writeInt(Objects.requireNonNull(screen.getSlotUnderMouse()).getSlotIndex());
                        Messages.sendToServer(new ClickSpell(buf));
                /*CompoundTag nbt;
                if (itemStack.hasTag()) {
                    nbt = itemStack.getTag();
                    nbt.remove("Triggerable");

                } else {
                    nbt = itemStack.getOrCreateTag();
                    nbt.putInt("Triggerable", 1);
                }*/
                        event.setCanceled(true);
                    }
                }
            }
        }
        if ((event.getScreen() instanceof CreativeModeInventoryScreen screen && event.getButton() == 1)) {
            if (screen.getSelectedTab() == CreativeModeTab.TAB_INVENTORY.getId()) {
                if (screen.getSlotUnderMouse() != null) {
                    ItemStack itemStack = Objects.requireNonNull(screen.getSlotUnderMouse()).getItem();
                    FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
                    buf.writeInt(Objects.requireNonNull(screen.getSlotUnderMouse()).getSlotIndex());
                    if (itemStack.getItem() instanceof FriendshipBracelet || itemStack.getItem() instanceof Spellblade|| itemStack.getItem() instanceof Spell || itemStack.getItem() instanceof Guard) {
                        Messages.sendToServer(new ClickSpell(buf));
                /*CompoundTag nbt;
                if (itemStack.hasTag()) {
                    nbt = itemStack.getTag();
                    nbt.remove("Triggerable");

                } else {
                    nbt = itemStack.getOrCreateTag();
                    nbt.putInt("Triggerable", 1);
                }*/
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
