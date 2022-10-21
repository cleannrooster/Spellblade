package com.cleannrooster.spellblademod.manasystem.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

public class OverlayWard implements IIngameOverlay {
    GUI gooie;
    public OverlayWard(GUI gui) {
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
    }

}