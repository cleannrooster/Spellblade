package com.cleannrooster.spellblademod.manasystem.network;

import com.cleannrooster.spellblademod.entity.FireworkEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class FireworkHandler {
    public static final String MESSAGE_NO_MANA = "message.nomana";
    private CompoundTag tag;
    private boolean bool;
    private String spellname;
    private int player;


    public FireworkHandler(boolean bool1, String spellname) {
        this.bool = bool1;
        this.spellname = spellname;
    }

    public FireworkHandler(FriendlyByteBuf buf) {
        tag = buf.readNbt();
        player = buf.readInt();

    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(tag);
        buf.writeInt(player);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();

        ctx.enqueueWork(() -> {
            Entity fireworkEntity =  Minecraft.getInstance().level.getEntity(player);

            if( fireworkEntity != null ) {
                Vec3 vec3 = fireworkEntity.getDeltaMovement();


                Minecraft.getInstance().level.createFireworks(fireworkEntity.getX(), fireworkEntity.getY(), fireworkEntity.getZ(), 0, 0, 0, tag);
            }

        });
        return true;

    }
}
