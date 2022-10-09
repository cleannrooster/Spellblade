package com.cleannrooster.spellblademod.blocks;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.manasystem.client.ManaOverlay;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WardIronBlock extends Block {
    public WardIronBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void stepOn(Level p_152431_, BlockPos p_152432_, BlockState p_152433_, Entity p_152434_) {
        if(p_152434_ instanceof Player){
            Player player = (Player) p_152434_;
            player.addEffect(new MobEffectInstance(StatusEffectsModded.WARDING.get(),5,2));
        }
        super.stepOn(p_152431_, p_152432_, p_152433_, p_152434_);
    }

}
