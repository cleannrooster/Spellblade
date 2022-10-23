package com.cleannrooster.spellblademod.manasystem.client;

import com.cleannrooster.spellblademod.SpellbladeMod;
import com.cleannrooster.spellblademod.items.*;
import com.cleannrooster.spellblademod.manasystem.manatick;
import com.cleannrooster.spellblademod.setup.Messages;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.data.ForgeItemTagsProvider;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Mod.EventBusSubscriber(modid = "spellblademod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TooltipEvent {
    @SubscribeEvent
    public static void tooltipEvent(ItemTooltipEvent event){
        if (event.getItemStack().hasTag() && !(event.getItemStack().getItem() instanceof Spellblade) && EnchantmentHelper.getEnchantments(event.getItemStack()).containsKey(SpellbladeMod.spellproxy)) {
            if(event.getItemStack().getOrCreateTag().contains("Triggers")){
                ItemStack stack = event.getItemStack();
                Set<String> keys = stack.getOrCreateTag().getCompound("Triggers").getAllKeys();
                for(String key : keys){
                    if(stack.getOrCreateTag().getCompound("Triggers").getInt(key) > 0) {
                        MutableComponent mutablecomponent1 = new TranslatableComponent("Triggering");
                        mutablecomponent1.append(": ");

                        MutableComponent mutablecomponent = new TranslatableComponent(key);


                        if (stack.getOrCreateTag().contains("AutoTrigger") && stack.getOrCreateTag().getCompound("AutoTrigger").contains(key)) {
                            mutablecomponent.append(" ").append(new TranslatableComponent("Auto"));
                        } else {
                            mutablecomponent.append(" ").append(new TranslatableComponent(/*"enchantment.level." +*/ String.valueOf(stack.getOrCreateTag().getCompound("Triggers").getInt(key))));
                        }
                        mutablecomponent1.append(mutablecomponent);
                        event.getToolTip().add(1,mutablecomponent1);
                    }
                }

            }
        }
    }


        @SubscribeEvent
    public static void rightclickevent(PlayerInteractEvent.RightClickEmpty event){
        if(event.getPlayer().getInventory().getSelected().getItem() == Items.AIR && event.getPlayer().isShiftKeyDown() && event.getPlayer().getAttributeValue(manatick.BASEWARD) >=39){
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeUUID(event.getPlayer().getUUID());
            Messages.sendToServer(new AmorphousPacket(buf));

        }
    }

}
