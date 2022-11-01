package com.cleannrooster.spellblademod.setup;


import com.cleannrooster.spellblademod.items.AmorphousPacket;
import com.cleannrooster.spellblademod.items.ParticlePacket;
import com.cleannrooster.spellblademod.items.ParticlePacket2;
import com.cleannrooster.spellblademod.manasystem.client.ParticleReverb;
import com.cleannrooster.spellblademod.manasystem.network.*;
import com.cleannrooster.spellblademod.patreon.CatPacket;
import com.cleannrooster.spellblademod.patreon.EmeraldPacket;
import com.cleannrooster.spellblademod.patreon.RefreshPacket;
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
        net.messageBuilder(CatPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(CatPacket::new)
                .encoder(CatPacket::toBytes)
                .consumer(CatPacket::handle)
                .add();
        net.messageBuilder(RetrieveItem.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RetrieveItem::new)
                .encoder(RetrieveItem::toBytes)
                .consumer(RetrieveItem::handle)
                .add();
        net.messageBuilder(EmeraldPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(EmeraldPacket::new)
                .encoder(EmeraldPacket::toBytes)
                .consumer(EmeraldPacket::handle)
                .add();
        net.messageBuilder(RefreshPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RefreshPacket::new)
                .encoder(RefreshPacket::toBytes)
                .consumer(RefreshPacket::handle)
                .add();
        net.messageBuilder(ParticleReverb.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ParticleReverb::new)
                .encoder(ParticleReverb::toBytes)
                .consumer(ParticleReverb::handle)
                .add();
        net.messageBuilder(FireworkHandler.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FireworkHandler::new)
                .encoder(FireworkHandler::toBytes)
                .consumer(FireworkHandler::handle)
                .add();
        net.messageBuilder(SyncAutouse.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncAutouse::new)
                .encoder(SyncAutouse::toBytes)
                .consumer(SyncAutouse::handle)
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

        net.messageBuilder(AmorphousPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(AmorphousPacket::new)
                .encoder(AmorphousPacket::toBytes)
                .consumer(AmorphousPacket::handle)
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
