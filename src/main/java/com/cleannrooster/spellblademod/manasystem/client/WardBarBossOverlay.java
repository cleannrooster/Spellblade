package com.cleannrooster.spellblademod.manasystem.client;

import com.cleannrooster.spellblademod.manasystem.manatick;
import com.google.common.collect.Maps;
import com.google.common.io.ByteSource;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.UUID;

public class WardBarBossOverlay extends BossHealthOverlay {
    final Map<UUID, LerpingBossEvent> events = Maps.newLinkedHashMap();

    public Minecraft minecraft;
    public BossHealthOverlay overlay;

    public WardBarBossOverlay(Minecraft mc) {
        super(mc);
        this.minecraft = mc;
    }

    public void render(PoseStack p_93705_, int ii) {
            int i = this.minecraft.getWindow().getGuiScaledWidth();
            int j = this.minecraft.getWindow().getGuiScaledHeight() - ii;
            if (Minecraft.getInstance().cameraEntity instanceof Player player) {
                int k = i / 2;
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, new ResourceLocation("textures/gui/bars.png"));
                this.drawBar(p_93705_, k, j, (int) Math.round(player.getAttributeValue(manatick.WARD)));
                Component component = new TranslatableComponent(String.valueOf(Math.round(ClientManaData.getPlayerMana())));
                Component component2 = new TranslatableComponent(String.valueOf(Math.round(ClientManaData.getPlayerBaseMana())));

                int l = this.minecraft.font.width(component);
                int l2 = this.minecraft.font.width(component2);

                int i1 = i / 2 - l / 2;
                int j1 = j - 9;
                if((int)ClientManaData.getPlayerMana() != 0) {
                    this.minecraft.font.drawShadow(p_93705_, component, (float) k - 183F / 2 - l, (float) j, 35533);
                    this.minecraft.font.drawShadow(p_93705_, component2, (float) k + 183F / 2, (float) j, 35533);
                }

            }
           // net.minecraftforge.client.ForgeHooksClient.renderBossEventPost(p_93705_, this.minecraft.getWindow());




    }

    private void drawBar(PoseStack p_93707_, int p_93708_, int p_93709_, int ward) {
            //this.blit(p_93707_, p_93708_, p_93709_, 0, 80 + (BossEvent.BossBarOverlay.NOTCHED_6.ordinal() - 1) * 5 * 2, 182, 5);

        int i = Math.min((int)(ward*183/200),183);

        if (i > 2) {
            this.blit(p_93707_, Math.max(p_93708_-ward/2,this.minecraft.getWindow().getGuiScaledWidth()/2-91), p_93709_, 0, BossEvent.BossBarColor.BLUE.ordinal() * 5 * 2 + 5, i, 5);
            this.blit(p_93707_, Math.max(p_93708_-ward/2,this.minecraft.getWindow().getGuiScaledWidth()/2-91), p_93709_, 0, 80 + (BossEvent.BossBarOverlay.NOTCHED_20.ordinal() - 1) * 5 * 2, i, 5);
                //this.blit(p_93707_, p_93708_, p_93709_, 0, 80 + (BossEvent.BossBarOverlay.NOTCHED_6.ordinal() - 1) * 5 * 2 + 5, i, 5);

        }


    }
}
