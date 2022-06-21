package com.cleannrooster.spellblademod.manasystem.client;

import com.cleannrooster.spellblademod.manasystem.ManaConfig;
import net.minecraft.network.chat.TextColor;
import net.minecraftforge.client.gui.IIngameOverlay;

import java.awt.*;

public class ManaOverlay {
    public static float basestep;
    public static float basewaving;
    static int color = TextColor.fromRgb(0x9966cc).getValue();
    public static float basearmor;
    public static float basewarding;
    public static float basetotem;

    static float hue = 0;
    static int i = 0;
    static int state = 0;
    static int a = 255;
    static int r = 255;
    static int g = 0;
    static int b = 0;

    static int color2 = TextColor.fromRgb(0x000000).getValue();
    public static final IIngameOverlay HUD_MANA = (gui, poseStack, partialTicks, width, height) -> {
        float base = basestep + basewaving + basearmor + basewarding + basetotem;
        String toDisplay = (String.valueOf((((int)ClientManaData.getPlayerMana()))*100/160) + " / " + (int) (33.3333333333F*(base)) );
        int x = 10;
        int y = height-20;
        if (ClientManaData.getPlayerMana() < 39)
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
        }
        if (x >= 0 && y >= 0 && Math.abs(ClientManaData.getPlayerMana()) >= 1.6) {
            gui.getFont().draw(poseStack, toDisplay, x, y, color);
        }
    };
}
