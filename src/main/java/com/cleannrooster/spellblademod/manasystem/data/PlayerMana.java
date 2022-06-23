package com.cleannrooster.spellblademod.manasystem.data;

import net.minecraft.nbt.CompoundTag;

import java.util.Arrays;

public class PlayerMana {

    private float mana;

    private  float basemana;

    private CompoundTag basemodifiers = new CompoundTag();

    public float getMana() {
        return mana;
    }

    public void setMana(float mana) {
        this.mana = mana;
    }

    public  void addMana(float mana) {
        this.mana += mana;
    }

    public float getBasemana() {
        return basemana;
    }

    public void setBasemana(float mana) {
        this.basemana = mana;
    }

    public  void addBasemana(float mana) {
        this.basemana += mana;
    }

    public CompoundTag getBasemodifiers() {
        return basemodifiers;
    }

    public float sumBasemodifiers() {
        int length = basemodifiers.getAllKeys().toArray().length;
        Object[] keys = basemodifiers.getAllKeys().toArray();
        float baseList[] = new float[length];
        float sum = 0;
        for (int i = 0; i < length; i++){
            String key = (String) Arrays.stream(basemodifiers.getAllKeys().toArray()).toList().get(i);
            baseList[i] = basemodifiers.getFloat(key);
            sum = sum + baseList[i];
        }
        return sum;
    }

    public  void addBasemodifiers(String identifier, Float amount) {
        basemodifiers.putFloat(identifier, amount);
    }

    public void copyFrom(PlayerMana source) {
        mana = source.mana;
    }


    public void saveNBTData(CompoundTag compound) {
        compound.putFloat("mana", mana);
        compound.putFloat("basemana", basemana);
    }

    public void loadNBTData(CompoundTag compound) {
        mana = compound.getFloat("mana");
        mana = compound.getFloat("basemana");
    }
}
