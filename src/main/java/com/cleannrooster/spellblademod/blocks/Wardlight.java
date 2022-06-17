package com.cleannrooster.spellblademod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class Wardlight extends LightBlock{
    Random rand = new Random();
    public Wardlight(Properties p_153662_) {
        super(p_153662_);
    }

}
