package com.cleannrooster.spellblademod.patreon;

import com.cleannrooster.spellblademod.SpellbladeMod;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Patreon {
    public static List<Player> emeraldbladeflurry = new ArrayList<>();

    public static boolean allowed(Player player){

        if(SpellbladeMod.UUIDS.contains(player.getStringUUID())){
            return true;
        }
        if(player.getGameProfile().getName().equals("Dev")){
            return true;
        }
        return false;
    }
}
