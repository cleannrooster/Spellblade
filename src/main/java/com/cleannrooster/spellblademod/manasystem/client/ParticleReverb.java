package com.cleannrooster.spellblademod.manasystem.client;

import com.cleannrooster.spellblademod.items.FlaskItem;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;
import java.util.stream.IntStream;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class ParticleReverb {
    public static final String MESSAGE_NO_MANA = "message.nomana";
    private ItemStack flaskItem ;
    private ItemStack slotItem;
    public double selfy;
    public double selfz;
    public double selfx;
    public double x1;
    public double x2;
    public double x3;
    public double y1;
    public double y2;
    public double y3;
    public double z1;
    public double z2;
    public double z3;

    public ParticleReverb(ItemStack flaskItem, ItemStack slotItem) {


    }

    public ParticleReverb(FriendlyByteBuf buf) {
        selfx = buf.readDouble();
        selfy = buf.readDouble();
        selfz = buf.readDouble();
        x1 = buf.readDouble();
        y1 = buf.readDouble();
        z1 = buf.readDouble();
        x2 = buf.readDouble();
        y2 = buf.readDouble();
        z2 = buf.readDouble();
        x3 = buf.readDouble();
        y3 = buf.readDouble();
        z3 = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(selfx);
        buf.writeDouble(selfy);
        buf.writeDouble(selfz);
        buf.writeDouble(x1);
        buf.writeDouble(y1);
        buf.writeDouble(z1);
        buf.writeDouble(x2);
        buf.writeDouble(y2);
        buf.writeDouble(z2);
        buf.writeDouble(x3);
        buf.writeDouble(y3);
        buf.writeDouble(z3);


    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            CompoundTag compoundtag = new CompoundTag();
            int num_pts_line = 30;
            for (int iii = 0; iii < num_pts_line; iii++) {
                double X = this.selfx + (x1 - this.selfx ) * ((double)iii / (num_pts_line));
                double Y = this.selfy + (y1 - this.selfy ) * ((double)iii / (num_pts_line));
                double Z = this.selfz + (z1  - this.selfz ) * ((double)iii / (num_pts_line));
                double X2 = this.selfx + (x2 -this.selfx) * ((double)iii / (num_pts_line));
                double Y2 = this.selfy +(y2 -  this.selfy) * ((double)iii / (num_pts_line));
                double Z2 = this.selfz + (z2 -this.selfz) * ((double)iii / (num_pts_line));
                double X3 = this.selfx + (x3 -this.selfx) * ((double)iii / (num_pts_line));
                double Y3 = this.selfy + (y3 -this.selfy) * ((double)iii / (num_pts_line));
                double Z3 = this.selfz + (z3 -this.selfz) * ((double)iii / (num_pts_line));

                int num_pts = 10;
                Vec3 targetcenter = new Vec3(X,Y,Z);
                Vec3 targetcenter2 = new Vec3(X2,Y2,Z2);
                Vec3 targetcenter3 = new Vec3(X3,Y3,Z3);

                for (int i = 1; i < num_pts; i = i + 1) {
                    double[] indices = IntStream.rangeClosed(0, (int) ((num_pts - 0) / 1))
                            .mapToDouble(x -> x * 1 + 0).toArray();

                    double phi = Math.acos(1 - 2 * indices[i] / num_pts);
                    double theta = Math.PI * (1 + Math.pow(5, 0.5) * indices[i]);
                    double x = cos(theta) * sin(phi);
                    double y = Math.sin(theta) * sin(phi);
                    double z = cos(phi);
                    Minecraft.getInstance().level.addParticle(new DustParticleOptions(new Vector3f(Vec3.fromRGB24(65436)),1F), true, targetcenter.x + x/4D , targetcenter.y + y/4D , targetcenter.z + z/4D, x * 0.05, y * 0.05, z * 0.05);
                    Minecraft.getInstance().level.addParticle(new DustParticleOptions(new Vector3f(Vec3.fromRGB24(65436)),1F), true, targetcenter2.x + x/4D , targetcenter2.y  + y/4D , targetcenter2.z  + z/4D, x * 0.05, y * 0.05, z * 0.05);
                    Minecraft.getInstance().level.addParticle(new DustParticleOptions(new Vector3f(Vec3.fromRGB24(65436)),1F), true, targetcenter3.x +  x/4D , targetcenter3.y + y/4D , targetcenter3.z  + z/4D, x * 0.05, y * 0.05, z * 0.05);

                }
            }
        });
        return true;

    }

}
