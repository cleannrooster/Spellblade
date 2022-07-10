package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.manasystem.client.ClientManaData;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class ParticlePacket {
    public static final String MESSAGE_NO_MANA = "message.nomana";
    private static int slot;
    private BlockPos blockPos;
    private int type;
    public ParticlePacket(int i) {
        slot = i;
    }

    public ParticlePacket(FriendlyByteBuf buf) {
        blockPos = buf.readBlockPos();
        type = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(blockPos);
        buf.writeInt(type);

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            // Here we are server side
            if (type == 1){
                Random rand = new Random();
                Vec3 vec = new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                Vec3 vec3 = vec.add(new Vec3(rand.nextDouble(-2, 2), rand.nextDouble(-2, 2), rand.nextDouble(-2, 2)));
                Vec3 vec31 = vec.subtract(vec3).normalize();
                Minecraft.getInstance().particleEngine.createParticle(ParticleTypes.SWEEP_ATTACK, vec3.x(), vec3.y(), vec3.z(),vec31.x()*0.5, vec31.y()*0.5, vec31.z()*0.5);
            }
            else {
                Minecraft.getInstance().particleEngine.createParticle(ParticleTypes.SWEEP_ATTACK, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0, 0, 0);
            }
            });
        return true;
    }
}
