package com.cleannrooster.spellblademod.manasystem.client;

import com.cleannrooster.spellblademod.SpellbladeMod;
import com.cleannrooster.spellblademod.manasystem.network.RetrieveItem;
import com.cleannrooster.spellblademod.manasystem.network.SyncAutouse;
import com.cleannrooster.spellblademod.setup.Messages;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketDecoder;
import net.minecraft.network.PacketEncoder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.CallbackI;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Mod.EventBusSubscriber(modid = "spellblademod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientTickEvent {
    @SubscribeEvent
    public static void tickevent(TickEvent.PlayerTickEvent event) {
        //event.player.getPersistentData().putBoolean("cast",false);
        for(ItemStack stack : event.player.getInventory().items) {
            if (EnchantmentHelper.getEnchantments(stack).containsKey(SpellbladeMod.spellproxy)) {
                ItemStack stack2 = ItemStack.of(event.player.getPersistentData().getCompound("spellproxy"));
                //System.out.println(event.player.level+", "+stack2.getOrCreateTag());
                if (!event.player.level.isClientSide()) {
                    FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
                    buf.writeNbt(stack2.getOrCreateTag());
                    buf.writeUUID(event.player.getUUID());
                    Messages.sendToPlayer(new SyncAutouse(buf), (ServerPlayer) event.player);


                }
                stack2 = ItemStack.of(event.player.getPersistentData().getCompound("spellproxy"));
                stack.getOrCreateTag().put("AutoTrigger", stack2.getOrCreateTag().getCompound("AutoTrigger"));
                stack.getOrCreateTag().put("Triggers", stack2.getOrCreateTag().getCompound("Triggers"));

            }
        }
    }
}
