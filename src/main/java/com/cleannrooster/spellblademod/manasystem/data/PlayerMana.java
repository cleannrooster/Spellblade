package com.cleannrooster.spellblademod.manasystem.data;

import net.minecraft.nbt.CompoundTag;

public class PlayerMana {

    private float mana;

    public float getMana() {
        return mana;
    }

    public void setMana(float mana) {
        this.mana = mana;
    }

    public  void addMana(float mana) {
        this.mana += mana;
    }

    public void copyFrom(PlayerMana source) {
        mana = source.mana;
    }


    public void saveNBTData(CompoundTag compound) {
        compound.putFloat("mana", mana);
    }

    public void loadNBTData(CompoundTag compound) {
        mana = compound.getFloat("mana");
    }
}
