package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.ModBlocks;
import com.cleannrooster.spellblademod.blocks.WardingTotemBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;
import java.util.Optional;

public class WardingTotem extends BlockItem {


    public WardingTotem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }
    /*public InteractionResult useOn(UseOnContext p_40581_) {
        BlockPlaceContext context = new BlockPlaceContext(p_40581_);

        BlockPos nearest = BlockPos.findClosestMatch(context.getClickedPos(), 91, 128, (p_186148_) -> {
            return context.getLevel().getBlockState(p_186148_).is(ModBlocks.WARDING_TOTEM_BLOCK.get());
        }).orElse(null);
            if (context.getLevel().getBlockState(context.getClickedPos().below()).getBlock() instanceof WardingTotemBlock) {

                return super.useOn(p_40581_);
            }
            if (nearest != null) {
                double HorDis = Math.sqrt(nearest.distSqr(context.getClickedPos()));
                if (Math.abs(nearest.getX() - context.getClickedPos().getX()) >= 64 || Math.abs(nearest.getZ() - context.getClickedPos().getZ()) >= 64) {

                    return super.useOn(p_40581_);
                }
                else if (Math.abs(nearest.getX() - context.getClickedPos().getX()) == 0 && Math.abs(nearest.getZ() - context.getClickedPos().getZ()) == 0) {
                    return InteractionResult.FAIL;
                }
                else {
                    context.getPlayer().displayClientMessage(Component.nullToEmpty("A totem already exists " + Math.floor(Math.sqrt(nearest.distSqr(context.getClickedPos()))) + " (" + (nearest.getX() - context.getClickedPos().getX()) +
                            ", " + (nearest.getZ() - context.getClickedPos().getZ()) + ") blocks away."), true);
                    return InteractionResult.FAIL;
                }
            }
            else{
                return super.useOn(p_40581_);
            }
    }*/
    @Nullable
    public BlockPlaceContext updatePlacementContext(BlockPlaceContext p_43063_) {
        BlockPlaceContext context = p_43063_;

        BlockPos nearest = BlockPos.findClosestMatch(context.getClickedPos(), 64, 64, (p_186148_) -> {
            return context.getLevel().getBlockState(p_186148_).is(ModBlocks.WARDING_TOTEM_BLOCK.get());
        }).orElse(null);
        if (nearest == null){
            return BlockPlaceContext.at(context,context.getClickedPos(),Direction.UP);
        }
        if (context.getLevel().getBlockState(context.getClickedPos().below()).getBlock() instanceof WardingTotemBlock) {

            return BlockPlaceContext.at(context,context.getClickedPos(),Direction.UP);
        }
            double HorDis = Math.sqrt(nearest.distSqr(context.getClickedPos()));
            if (Math.abs(nearest.getX() - context.getClickedPos().getX()) >= 64 || Math.abs(nearest.getZ() - context.getClickedPos().getZ()) >= 64) {

                return BlockPlaceContext.at(context,context.getClickedPos(),Direction.UP);
            }
            else if (Math.abs(nearest.getX() - context.getClickedPos().getX()) == 0 && Math.abs(nearest.getZ() - context.getClickedPos().getZ()) == 0) {
                return null;
            }
            else {
                context.getPlayer().displayClientMessage(Component.nullToEmpty("A totem already exists " + Math.floor(Math.sqrt(nearest.distSqr(context.getClickedPos()))) + " (" + (nearest.getX() - context.getClickedPos().getX()) +
                        ", " + (nearest.getZ() - context.getClickedPos().getZ()) + ") blocks away."), true);
                return null;
            }





       /* BlockPos blockpos = p_43063_.getClickedPos();
        Level level = p_43063_.getLevel();
        BlockState blockstate = level.getBlockState(blockpos);
        Block block = this.getBlock();
        if (!blockstate.is(block)) {
            return ScaffoldingBlock.getDistance(level, blockpos) == 7 ? null : p_43063_;
        } else {
            Direction direction;
            if (p_43063_.isSecondaryUseActive()) {
                direction = p_43063_.isInside() ? p_43063_.getClickedFace().getOpposite() : p_43063_.getClickedFace();
            } else {
                direction = p_43063_.getClickedFace() == Direction.UP ? p_43063_.getHorizontalDirection() : Direction.UP;
            }

            int i = 0;
            BlockPos.MutableBlockPos blockpos$mutableblockpos = blockpos.mutable().move(direction);

            while(i < 7) {
                if (!level.isClientSide && !level.isInWorldBounds(blockpos$mutableblockpos)) {
                    Player player = p_43063_.getPlayer();
                    int j = level.getMaxBuildHeight();
                    if (player instanceof ServerPlayer && blockpos$mutableblockpos.getY() >= j) {
                        ((ServerPlayer)player).sendMessage((new TranslatableComponent("build.tooHigh", j - 1)).withStyle(ChatFormatting.RED), ChatType.GAME_INFO, Util.NIL_UUID);
                    }
                    break;
                }

                blockstate = level.getBlockState(blockpos$mutableblockpos);
                if (!blockstate.is(this.getBlock())) {
                    if (blockstate.canBeReplaced(p_43063_)) {
                        return BlockPlaceContext.at(p_43063_, blockpos$mutableblockpos, direction);
                    }
                    break;
                }

                blockpos$mutableblockpos.move(direction);
                if (direction.getAxis().isHorizontal()) {
                    ++i;
                }
            }

            return null;
        }*/
    }
}
