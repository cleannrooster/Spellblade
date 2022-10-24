package com.cleannrooster.spellblademod.patreon;

import com.cleannrooster.spellblademod.setup.Messages;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.cleannrooster.spellblademod.patreon.Patreon.allowed;

@OnlyIn(Dist.CLIENT)
public class PatreonMenu extends Screen {
    protected PatreonMenu(Component p_96550_) {
        super(p_96550_);
    }
    Widget emeraldbutton;
    public static final Component EMERALD_BLADE_FLURRY = new TranslatableComponent("Emerald Blade Flurry Toggle");
    public static final Component EMERALD_BLADE_FLURRY_Enabled = new TranslatableComponent("Emerald Blade Flurry: Enabled");

    @Override
    protected void init() {
        if(allowed(Minecraft.getInstance().player)) {
            this.emeraldbutton = this.addRenderableWidget(new Button(this.width / 2 - 102, this.height / 4 + 120 + -16, 204, 20,EMERALD_BLADE_FLURRY,(p_210872_) -> {
                setEnabled("emeraldbladeflurry");
            }));
        }
    }


    protected void setEnabled(String string) {
        if (string.equals("emeraldbladeflurry")) {

            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeUUID(Minecraft.getInstance().player.getUUID());
            Messages.sendToServer(new EmeraldPacket(buf));
        }
    }

}
