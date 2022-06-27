package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.manasystem.network.ClickSpell;
import com.cleannrooster.spellblademod.setup.Messages;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = "spellblademod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpellRightClickHandler {
    @SubscribeEvent
    public static void onClick(ScreenEvent.MouseClickedEvent event){
        if ((event.getScreen() instanceof InventoryScreen screen && event.getButton() == 1)) {
            ItemStack itemStack = Objects.requireNonNull(screen.getSlotUnderMouse()).getItem();
            if(itemStack.getItem() instanceof Spell) {

                Messages.sendToServer(new ClickSpell(Objects.requireNonNull(screen.getSlotUnderMouse()).getSlotIndex()));
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
        if ((event.getScreen() instanceof CreativeModeInventoryScreen screen && event.getButton() == 1)) {
            if (screen.getSelectedTab() == CreativeModeTab.TAB_INVENTORY.getId()) {
                ItemStack itemStack = Objects.requireNonNull(screen.getSlotUnderMouse()).getItem();
                if (itemStack.getItem() instanceof Spell) {
                    Messages.sendToServer(new ClickSpell(Objects.requireNonNull(screen.getSlotUnderMouse()).getSlotIndex()));
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
