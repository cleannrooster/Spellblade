package com.cleannrooster.spellblademod.manasystem.client;

import com.cleannrooster.spellblademod.manasystem.ManaConfig;
import com.cleannrooster.spellblademod.manasystem.data.basemana;
import net.minecraft.network.chat.TextColor;
import net.minecraftforge.client.gui.IIngameOverlay;

import java.awt.*;

public class ManaOverlay {

    public static int color = TextColor.fromRgb(0x9966cc).getValue();

    static float hue = 0;
    static int i = 0;
    static int state = 0;
    static int a = 255;
    static int r = 255;
    static int g = 0;
    static int b = 0;

    public static int color2 = 0x000000;
    public static final IIngameOverlay HUD_MANA = (gui, poseStack, partialTicks, width, height) -> {
        String toDisplay = String.valueOf(Math.round(ClientManaData.getPlayerMana()) + " / " + Math.round(ClientManaData.getPlayerBaseMana()) );
        int x = 10;
        int y = height-20;
        if (ClientManaData.getPlayerMana() < -21)
        {
            color = TextColor.fromRgb(0xFF0000).getValue();
            color2 = 0xFF0000;
        }
        if (ClientManaData.getPlayerMana() >= -21 && ClientManaData.getPlayerMana() < 39)
        {
            color = TextColor.fromRgb(0x9966cc).getValue();
            color2 = 0x9966cc;
        }
        if (ClientManaData.getPlayerMana() >= 39 && ClientManaData.getPlayerMana() <= 79 )
        {
            color = TextColor.fromRgb(0xFF69B4).getValue();
            color2 = 0xFF69B4;
        }
        if (ClientManaData.getPlayerMana() >= 79 && ClientManaData.getPlayerMana() <= 119 )
        {
            color = TextColor.fromRgb(0x00FFFF).getValue();
            color2 = 0x00FFFF;
        }
        if (ClientManaData.getPlayerMana() >= 119 && ClientManaData.getPlayerMana() <= 159 )
        {
            color = TextColor.fromRgb(0xFFFFFF).getValue();
            color2 = 0xFFFFFF;
        }
        if (ClientManaData.getPlayerMana() >= 159) {

            if(state == 0){
                g++;
                if(g == 255)
                    state = 1;
            }
            if(state == 1){
                r--;
                if(r == 0)
                    state = 2;
            }
            if(state == 2){
                b++;
                if(b == 255)
                    state = 3;
            }
            if(state == 3){
                g--;
                if(g == 0)
                    state = 4;
            }
            if(state == 4){
                r++;
                if(r == 255)
                    state = 5;
            }
            if(state == 5){
                b--;
                if(b == 0)
                    state = 0;
            }
            int hex = (a << 24) + (r << 16) + (g << 8) + (b);
            color = hex;
            color2 = hex;
        }
        if (x >= 0 && y >= 0 && Math.abs(ClientManaData.getPlayerMana()) >= 0.5) {
            gui.getFont().draw(poseStack, toDisplay, x, y, color);
        }
    };
}
