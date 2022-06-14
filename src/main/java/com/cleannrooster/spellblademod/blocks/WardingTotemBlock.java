package com.cleannrooster.spellblademod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class WardingTotemBlock extends BaseEntityBlock {


    public WardingTotemBlock(Properties p_49224_) {
        super(p_49224_);
    }

    private static final VoxelShape SHAPE =  Block.box(0, 0, 0, 16, 16, 16);

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    /* FACING */






    /* BLOCK ENTITY */

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

  /*  @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        return InteractionResult.SUCCESS;
    }*/

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new WardingTotemBlockEntity(pPos, pState);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModTileEntity.WARDING_TOTEM_BLOCK_ENTITY.get(),
                WardingTotemBlockEntity::tick);
    }
    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult ray) {

            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof WardingTotemBlockEntity && entity.getTileData().get("Inscribed") == null && player.getMainHandItem() == ItemStack.EMPTY && player.getOffhandItem() == ItemStack.EMPTY) {
                CompoundTag tag = new CompoundTag();
                String name = String.valueOf(player.getGameProfile().getName());
                tag.putString("Inscribed", name);
                entity.getTileData().putString("Inscribed", name);
                player.displayClientMessage(Component.nullToEmpty("Inscribed " + name), true);
                entity.setChanged();
                return InteractionResult.sidedSuccess(world.isClientSide());
            } else if (entity instanceof WardingTotemBlockEntity && entity.getTileData().get("Inscribed") != null && player.getMainHandItem() == ItemStack.EMPTY && player.getOffhandItem() == ItemStack.EMPTY) {
                String name = entity.getTileData().getString("Inscribed");
                player.displayClientMessage(Component.nullToEmpty("Inscribed by " + name), true);
                return InteractionResult.sidedSuccess(world.isClientSide());
            } else {
                return InteractionResult.FAIL;
            }
    }
}
