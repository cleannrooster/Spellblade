package com.cleannrooster.spellblademod.patreon;


import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;


public class KeyHandler {
    public static final String KEY_CATEGORY = "key.category.spellblademod";
    public static final String KEY = "key.spellblademod.patreon";
    public static final KeyMapping PATREON = new KeyMapping(KEY, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_INSERT, KEY_CATEGORY);


    @Mod.EventBusSubscriber(modid = "spellblademod", value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if(Minecraft.getInstance().player != null && PATREON.isDown()) {
                Minecraft.getInstance().setScreen(new PatreonMenu(Component.translatable("Spellblade Patreon")));
            }
        }
    }

    @Mod.EventBusSubscriber(modid = "spellblademod", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(PATREON);
        }
    }
}


