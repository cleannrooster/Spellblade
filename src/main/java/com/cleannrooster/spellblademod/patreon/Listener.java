package com.cleannrooster.spellblademod.patreon;

import com.cleannrooster.spellblademod.SpellbladeMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Listener extends SavedData{

    public Listener() {
    }
    private int counter = 0;
    public void tick(Level world){
        counter--;
        URL url = null;
        if(counter <= 0) {
            counter = 1200*60;
            try {
                url = new URL("https://pastebin.com/raw/" + "6ZNv6DDb");

                //Retrieving the contents of the specified page
                Scanner sc = new Scanner(url.openStream());
                //Instantiating the StringBuffer class to hold the result
                StringBuffer sb = new StringBuffer();
                while (sc.hasNext()) {
                    sb.append(sc.next());
                    //System.out.println(sc.next());
                }
                //Retrieving the String from the String Buffer object
                SpellbladeMod.UUIDS = sb.toString();
                //Removing the HTML tags
                SpellbladeMod.UUIDS = SpellbladeMod.UUIDS.replaceAll("<[^>]*>", "");

            } catch (IOException ignored) {
            }
        }
    }

    @Override
    public CompoundTag save(CompoundTag p_77763_) {
        return null;
    }
    public Listener(CompoundTag tag) {

    }
    @Nonnull
    public static Listener get(Level level) {
        if (level.isClientSide) {
            throw new RuntimeException("Don't access this client-side!");
        }
        // Get the vanilla storage manager from the level
        DimensionDataStorage storage = ((ServerLevel)level).getDataStorage();
        // Get the mana manager if it already exists. Otherwise create a new one. Note that both
        // invocations of ManaManager::new actually refer to a different constructor. One without parameters
        // and the other with a CompoundTag parameter
        return storage.computeIfAbsent(Listener::new, Listener::new, "listener");
    }

}
