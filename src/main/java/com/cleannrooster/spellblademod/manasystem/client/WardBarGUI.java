package com.cleannrooster.spellblademod.manasystem.client;

import com.cleannrooster.spellblademod.items.Spell;
import com.cleannrooster.spellblademod.items.Spellblade;
import com.cleannrooster.spellblademod.manasystem.manatick;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.ForgeIngameGui;

import java.util.Map;
import java.util.UUID;

public class WardBarGUI extends ForgeIngameGui {
    public Minecraft minecraft;
    public WardBarBossOverlay overlay;
    public WardBarGUI(Minecraft mc) {
        super(mc);
        this.minecraft = mc;
        this.overlay = new WardBarBossOverlay(mc);
    }
    final Map<UUID, LerpingBossEvent> events = Maps.newLinkedHashMap();


    protected void renderBossHealth(PoseStack poseStack, int ii)
    {
        bind(GuiComponent.GUI_ICONS_LOCATION);
        RenderSystem.defaultBlendFunc();
        minecraft.getProfiler().push("wardBar");
        this.overlay.render(poseStack, ii);
        minecraft.getProfiler().pop();
    }
    private void bind(ResourceLocation res)
    {
        RenderSystem.setShaderTexture(0, res);
    }

}
