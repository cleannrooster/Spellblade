package com.cleannrooster.spellblademod.manasystem.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class RenderCrosshair implements IIngameOverlay {
    GUI gooie;
    public RenderCrosshair(GUI gui) {
        this.gooie = gui;
    }

    @Override
    public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height) {
        if (!Minecraft.getInstance().options.hideGui && gui.shouldDrawSurvivalElements())
        {
            int screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            gui.setupOverlayRenderState(true, false);
            gooie.left_height = gui.left_height;
            gooie.renderWard(screenWidth,screenHeight,poseStack);
            gui.left_height = gooie.left_height;
        }
    }}
