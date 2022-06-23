package com.cleannrooster.spellblademod.manasystem.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

import java.sql.Array;
import java.util.Arrays;

public class basemana {

    public static float baseadditional = 0;
    static CompoundTag compound = new CompoundTag();

    public static void addBaseadditional(String identifier, Float amount){
            compound.putFloat(identifier, amount);
    }
    public static Float sum(){
        int length = compound.getAllKeys().toArray().length;
        Object[] keys = compound.getAllKeys().toArray();
        float baseList[] = new float[length];
        float sum = 0;
        for (int i = 0; i < length; i++){
            String key = (String) Arrays.stream(compound.getAllKeys().toArray()).toList().get(i);
            baseList[i] = compound.getFloat(key);
            sum = sum + baseList[i];
        }
        return sum;
    }
}
