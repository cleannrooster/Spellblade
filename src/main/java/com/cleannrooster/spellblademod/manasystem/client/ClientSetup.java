package com.cleannrooster.spellblademod.manasystem.client;

import com.cleannrooster.spellblademod.manasystem.client.KeyBindings;
import com.cleannrooster.spellblademod.manasystem.client.KeyInputHandler;
import com.cleannrooster.spellblademod.manasystem.client.ManaOverlay;

import com.cleannrooster.spellblademod.manasystem.manatick;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.system.CallbackI;

import static net.minecraft.client.gui.GuiComponent.GUI_ICONS_LOCATION;
import static net.minecraftforge.client.gui.ForgeIngameGui.*;

@Mod.EventBusSubscriber(modid = "spellblademod", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static int left_height = 39;
    public int right_height = 39;
    static GUI gui = new GUI(Minecraft.getInstance());
    private static int blitOffset;


    public static void init(FMLClientSetupEvent event){
        //OverlayRegistry.registerOverlayAbove(HOTBAR_ELEMENT, "ward", ManaOverlay.HUD_MANA);
        OverlayRegistry.registerOverlayAbove(PLAYER_HEALTH_ELEMENT,"wardhearts", new OverlayWard(new GUI(Minecraft.getInstance())));
        OverlayRegistry.registerOverlayBelow(PLAYER_HEALTH_ELEMENT, "wardbar", new WardBar(new WardBarGUI(Minecraft.getInstance())));

    }

}