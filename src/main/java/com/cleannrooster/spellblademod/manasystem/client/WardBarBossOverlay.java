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
import net.minecraft.network.chat.TextColor;
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
    static float hue = 0;
    static int i = 0;
    static int state = 0;
    static int a = 255;
    static int r = 255;
    static int g = 0;
    static int b = 0;
    int hex = 0;
    static int state2 = 0;
    static int a2 = 255;
    static int r2 = 255;
    static int g2 = 0;
    static int b2 = 0;
    public static int color = TextColor.fromRgb(0x9966cc).getValue();

    public static int color2 = 0x000000;
    public void render(PoseStack p_93705_, int ii) {
            int i = this.minecraft.getWindow().getGuiScaledWidth();
            int j = this.minecraft.getWindow().getGuiScaledHeight() - ii;
            if (Minecraft.getInstance().cameraEntity instanceof Player player) {
                int k = i / 2;
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, new ResourceLocation("spellblademod", "textures/gui/bars.png"));
                this.drawBar(p_93705_, k, j, (int) Math.round(player.getAttributeValue(manatick.WARD)));
                Component component = new TranslatableComponent(String.valueOf(Math.round(ClientManaData.getPlayerMana())));
                Component component2 = new TranslatableComponent(String.valueOf(Math.round(ClientManaData.getPlayerBaseMana())));

                int l = this.minecraft.font.width(component);
                int l2 = this.minecraft.font.width(component2);

                int i1 = i / 2 - l / 2;
                int j1 = j - 9;
                if((int)ClientManaData.getPlayerMana() != 0) {
                    String toDisplay = String.valueOf(Math.round(ClientManaData.getPlayerMana()) + " / " + Math.round(ClientManaData.getPlayerBaseMana()) );
                    if(ClientManaData.getPlayerBaseMana() > 159 || ClientManaData.getPlayerMana() > 159) {
                        if (state == 0) {
                            g++;
                            if (g == 255)
                                state = 1;
                        }
                        if (state == 1) {
                            r--;
                            if (r == 0)
                                state = 2;
                        }
                        if (state == 2) {
                            b++;
                            if (b == 255)
                                state = 3;
                        }
                        if (state == 3) {
                            g--;
                            if (g == 0)
                                state = 4;
                        }
                        if (state == 4) {
                            r++;
                            if (r == 255)
                                state = 5;
                        }
                        if (state == 5) {
                            b--;
                            if (b == 0)
                                state = 0;
                        }
                        hex = (a << 24) + (r << 16) + (g << 8) + (b);
                    }

                    if (ClientManaData.getPlayerMana() < -21)
                    {
                        color = TextColor.fromRgb(0xFF0000).getValue();
                    }
                    if (ClientManaData.getPlayerMana() >= -21 && ClientManaData.getPlayerMana() < 39)
                    {
                        color = TextColor.fromRgb(0x9966cc).getValue();
                    }
                    if (ClientManaData.getPlayerMana() >= 39 && ClientManaData.getPlayerMana() <= 79 )
                    {
                        color = TextColor.fromRgb(0xFF69B4).getValue();
                    }
                    if (ClientManaData.getPlayerMana() >= 79 && ClientManaData.getPlayerMana() <= 119 )
                    {
                        color = TextColor.fromRgb(0x00FFFF).getValue();
                    }
                    if (ClientManaData.getPlayerMana() >= 119 && ClientManaData.getPlayerMana() <= 159 )
                    {
                        color = TextColor.fromRgb(0xFFFFFF).getValue();
                    }
                    if (ClientManaData.getPlayerMana() >= 159) {

                        color = hex;
                    }
                    if (ClientManaData.getPlayerBaseMana() < -21)
                    {
                        color2 = 0xFF0000;
                    }
                    if (ClientManaData.getPlayerBaseMana() >= -21 && ClientManaData.getPlayerBaseMana() < 39)
                    {
                        color2 = 0x9966cc;
                    }
                    if (ClientManaData.getPlayerBaseMana() >= 39 && ClientManaData.getPlayerBaseMana() <= 79 )
                    {
                        color2 = 0xFF69B4;
                    }
                    if (ClientManaData.getPlayerBaseMana() >= 79 && ClientManaData.getPlayerBaseMana() <= 119 )
                    {
                        color2 = 0x00FFFF;
                    }
                    if (ClientManaData.getPlayerBaseMana() >= 119 && ClientManaData.getPlayerBaseMana() <= 159 )
                    {
                        color2 = 0xFFFFFF;
                    }
                    if (ClientManaData.getPlayerBaseMana() >= 159) {

                        color2 = hex;
                    }
                    this.minecraft.font.drawShadow(p_93705_, component, (float) k - 183F / 2 - l, (float) j, color);
                    this.minecraft.font.drawShadow(p_93705_, component2, (float) k + 183F / 2, (float) j, color2);
                }

            }
           // net.minecraftforge.client.ForgeHooksClient.renderBossEventPost(p_93705_, this.minecraft.getWindow());




    }

    private void drawBar(PoseStack p_93707_, int p_93708_, int p_93709_, int ward) {
            //this.blit(p_93707_, p_93708_, p_93709_, 0, 80 + (BossEvent.BossBarOverlay.NOTCHED_6.ordinal() - 1) * 5 * 2, 182, 5);

        int i = Math.min((int)(ward*183/200),183);

        if (i > 2) {
            this.blit(p_93707_, Math.max(p_93708_-ward/2,this.minecraft.getWindow().getGuiScaledWidth()/2-91), p_93709_, 0, 80 + (BossEvent.BossBarOverlay.NOTCHED_20.ordinal() - 1) * 5 * 2, i+1, 5);
            this.blit(p_93707_, Math.max(p_93708_-ward/2,this.minecraft.getWindow().getGuiScaledWidth()/2-91), p_93709_, 0, BossEvent.BossBarColor.BLUE.ordinal() * 5 * 2 + 5, i, 5);
                //this.blit(p_93707_, p_93708_, p_93709_, 0, 80 + (BossEvent.BossBarOverlay.NOTCHED_6.ordinal() - 1) * 5 * 2 + 5, i, 5);

        }


    }
}
