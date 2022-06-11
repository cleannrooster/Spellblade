package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.ModBlocks;
import com.cleannrooster.spellblademod.blocks.WardingTotemBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraftforge.registries.RegistryObject;

public class WardingTotem extends BlockItem {


    public WardingTotem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }
    @Override
    public InteractionResult useOn(UseOnContext p_40581_) {
        BlockPlaceContext context = new BlockPlaceContext(p_40581_);
        BlockPos nearest = BlockPos.findClosestMatch(context.getClickedPos(), 64, 128, (p_186148_) -> {
            return context.getLevel().getBlockState(p_186148_).is(ModBlocks.WARDING_TOTEM_BLOCK.get());}).orElse(null);

            if(context.getLevel().getBlockState(context.getClickedPos().below()).getBlock() instanceof WardingTotemBlock){
                InteractionResult interactionresult = this.place(new BlockPlaceContext(p_40581_));
                return interactionresult;
            }
        else if (nearest != null){
            double HorDis = Math.sqrt(nearest.distSqr(context.getClickedPos()));
            if (HorDis > 64){
                InteractionResult interactionresult = this.place(new BlockPlaceContext(p_40581_));
                return interactionresult;
            }
            else {
                return InteractionResult.FAIL;
            }
        }
        else {
            InteractionResult interactionresult = this.place(new BlockPlaceContext(p_40581_));
            return interactionresult;
        }
    }
}
