package com.cleannrooster.spellblademod.setup;


import com.cleannrooster.spellblademod.items.ParticlePacket;
import com.cleannrooster.spellblademod.items.ParticlePacket2;
import com.cleannrooster.spellblademod.manasystem.network.ClickSpell;
import com.cleannrooster.spellblademod.manasystem.network.Hurt;
import com.cleannrooster.spellblademod.manasystem.network.PacketSyncManaToClient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class Messages {

    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation("spellblademod", "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ClickSpell.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ClickSpell::new)
                .encoder(ClickSpell::toBytes)
                .consumer(ClickSpell::handle)
                .add();
        net.messageBuilder(PacketSyncManaToClient.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketSyncManaToClient::new)
                .encoder(PacketSyncManaToClient::toBytes)
                .consumer(PacketSyncManaToClient::handle)
                .add();
        net.messageBuilder(ParticlePacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ParticlePacket::new)
                .encoder(ParticlePacket::toBytes)
                .consumer(ParticlePacket::handle)
                .add();
        net.messageBuilder(ParticlePacket2.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ParticlePacket2::new)
                .encoder(ParticlePacket2::toBytes)
                .consumer(ParticlePacket2::handle)
                .add();
        net.messageBuilder(Hurt.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(Hurt::new)
                .encoder(Hurt::toBytes)
                .consumer(Hurt::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

}
