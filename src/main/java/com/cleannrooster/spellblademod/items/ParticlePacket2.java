package com.cleannrooster.spellblademod.items;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class ParticlePacket2 {
    public static final String MESSAGE_NO_MANA = "message.nomana";
    public static int[] bytearray;
    public static double x = 0;
    public static double y = 0;
    public static double z = 0;
    public static double xx = 0;
    public static double yy = 0;
    public static double zz = 0;


    public ParticlePacket2(double x1, double y1, double z1, double x2, double y2, double z2) {
        x = x1;
        y = y1;
        z = z1;
        xx = x2;
        yy = y2;
        zz = z2;
    }

    public ParticlePacket2(FriendlyByteBuf buf) {
        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();
        xx = buf.readDouble();
        yy = buf.readDouble();
        zz = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeDouble(xx);
        buf.writeDouble(yy);
        buf.writeDouble(zz);


    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            int i = 0;
            int num_pts = 100;
            int num_pts_line = 25;
            for (int iii = 0; iii < num_pts_line; iii++) {
                double X = x + (xx - x) * ((double) iii / (num_pts_line));
                double Y = y + (yy - y) * ((double) iii / (num_pts_line));
                double Z = z + (zz - z) * ((double) iii / (num_pts_line));
                Minecraft.getInstance().level.addParticle(DustParticleOptions.REDSTONE, X, Y, Z, 0, 0, 0);
            }
            for (i = 0; i <= num_pts; i = i + 1) {
                double[] indices = IntStream.rangeClosed(0, (int) ((1000)))
                        .mapToDouble(xxx -> xxx).toArray();

                double phi = Math.acos(1 - 2 * indices[i] / num_pts);
                double theta = Math.PI * (1 + Math.pow(5, 0.5) * indices[i]);
                double x2 = cos(theta) * sin(phi);
                double y2 = Math.sin(theta) * sin(phi);
                double z2 = cos(phi);
                Minecraft.getInstance().level.addParticle((ParticleOptions) DustParticleOptions.REDSTONE, xx + 1.5 * x2, yy + 1.5 * y2, zz + 1.5 * z2, 0, 0, 0);
            }
        });
        return true;
    }
}
