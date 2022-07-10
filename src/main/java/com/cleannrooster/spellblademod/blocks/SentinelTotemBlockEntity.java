package com.cleannrooster.spellblademod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SentinelTotemBlockEntity extends BlockEntity {
    public SentinelTotemBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModTileEntity.SENTINEL_TOTEM_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
    }
}
