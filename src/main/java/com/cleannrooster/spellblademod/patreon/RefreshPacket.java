package com.cleannrooster.spellblademod.patreon;

import com.cleannrooster.spellblademod.SpellbladeMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.UUID;
import java.util.function.Supplier;

public class RefreshPacket {
    boolean bool;
    UUID entity;
    String string;
    public RefreshPacket() {
    }

    public RefreshPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();

        ctx.enqueueWork(() -> {
            if(ctx.getSender().getServer().getPlayerList().isOp(ctx.getSender().getGameProfile())) {
                try {
                    URL url = new URL("https://pastebin.com/raw/" + "6ZNv6DDb");

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
                    System.out.println("Refreshing Spellblade Patron List");

                } catch (IOException ignored) {
                }
                try {
                    URL url = new URL("https://pastebin.com/raw/" + "PsMyMwe3");

                    //Retrieving the contents of the specified page
                    Scanner sc = new Scanner(url.openStream());
                    //Instantiating the StringBuffer class to hold the result
                    StringBuffer sb = new StringBuffer();
                    while (sc.hasNext()) {
                        sb.append(sc.next());
                        //System.out.println(sc.next());
                    }
                    //Retrieving the String from the String Buffer object
                    SpellbladeMod.CATUUIDS = sb.toString();
                    //Removing the HTML tags
                    SpellbladeMod.CATUUIDS = SpellbladeMod.CATUUIDS.replaceAll("<[^>]*>", "");
                    System.out.println("Refreshing Spellblade Patron List");

                } catch (IOException ignored) {
                }
            }
        });
        return true;

    }
}
