package com.cleannrooster.spellblademod.patreon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.cleannrooster.spellblademod.patreon.Patreon.allowed;

@OnlyIn(Dist.CLIENT)
public class PatreonMenu extends Screen {
    protected PatreonMenu(Component p_96550_) {
        super(p_96550_);
    }
    Widget emeraldbutton;
    public static final Component EMERALD_BLADE_FLURRY = Component.translatable("Emerald Blade Flurry");
    @Override
    protected void init() {
        if(allowed(Minecraft.getInstance().player)) {
            this.emeraldbutton = this.addRenderableWidget(CycleButton.builder(isEnabled::getDisplayName).withValues(isEnabled.EMERALDENABLED, isEnabled.EMERALDDISABLED).withInitialValue(Patreon.emeraldbladeflurry.contains(Minecraft.getInstance().player) ? isEnabled.EMERALDENABLED : isEnabled.EMERALDDISABLED )
                    .create(this.width / 2 - 102, this.height / 4 + 120 + -16, 204, 20, EMERALD_BLADE_FLURRY, (p_232910_, p_232911_) -> {
                        this.setEnabled(p_232911_);
                    }));
        }
    }


    protected void setEnabled(isEnabled enabled) {
        if(enabled.name.equals("emeraldbladeflurry")){
            if(enabled.enabled) {
                Patreon.emeraldbladeflurry.add(Minecraft.getInstance().player);
            }
            else{
                Patreon.emeraldbladeflurry.remove(Minecraft.getInstance().player);

            }
        }
    }

    static enum isEnabled {
        EMERALDENABLED("emeraldbladeflurry", true),
        EMERALDDISABLED("emeraldbladeflurry", false);

        final String name;
        final boolean enabled;
        private final Component displayName;

        private isEnabled(String p_101035_, boolean p_101036_) {
            this.name = p_101035_;
            this.enabled = p_101036_;
            if(p_101036_) {
                this.displayName = Component.translatable("Enabled");
            }
            else{
                this.displayName = Component.translatable("Disabled");

            }
        }

        public Component getDisplayName() {
            return this.displayName;
        }
    }
}
