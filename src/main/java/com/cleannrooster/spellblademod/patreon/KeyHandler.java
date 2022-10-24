package com.cleannrooster.spellblademod.patreon;


import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = "spellblademod", value = Dist.CLIENT)
public class KeyHandler {
    public static final String KEY_CATEGORY = "key.category.spellblademod";
    public static final String KEY = "key.spellblademod.patreon";
    public static final KeyMapping PATREON = new KeyMapping(KEY, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_INSERT, KEY_CATEGORY);

    @SubscribeEvent
        public static void onKeyInput(InputEvent event) {
            if(Minecraft.getInstance().player != null && PATREON.isDown()) {
                Minecraft.getInstance().setScreen(new PatreonMenu(new TranslatableComponent("Spellblade Patreon")));
            }
        }


}


