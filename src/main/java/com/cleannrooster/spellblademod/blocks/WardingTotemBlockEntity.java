package com.cleannrooster.spellblademod.blocks;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class WardingTotemBlockEntity extends BlockEntity {

    public WardingTotemBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModTileEntity.WARDING_TOTEM_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, WardingTotemBlockEntity pBlockEntity) {
        /*for (int i = 1; i < pLevel.getMaxBuildHeight() - pPos.getY(); i++){
            boolean airblock = pLevel.getBlockState(pPos.above(i)).getBlock() instanceof AirBlock;
            boolean totemblock = pLevel.getBlockState(pPos.above(i)).getBlock() instanceof WardingTotemBlock;
            if(!(airblock) && !(totemblock)){
                pLevel.destroyBlock(pPos, true);
                return;
            }
        }*/
        List entities = pLevel.getEntitiesOfClass(Player.class,new AABB(pPos.getX()+0.5-32, pPos.getY()+0.5-32,pPos.getZ()+0.5-32,pPos.getX()+0.5+32,pPos.getY()+0.5+32,pPos.getZ()+0.5+32 ));
        Object[] entitiesarray = entities.toArray();
        int entityamount = entitiesarray.length;
        int ceiling = 0;
        if (pBlockEntity.getTileData().get("Inscribed") == null){
            return;
        }
        for (int ii = 0; ii < entityamount; ii = ii + 1) {
            Player target = (Player) entities.get(ii);
            if (Objects.equals(String.valueOf(target.getGameProfile().getName()), pBlockEntity.getTileData().getString("Inscribed")) && !target.hasEffect(StatusEffectsModded.TOTEMIC_ZEAL.get())) {
                target.addEffect(new MobEffectInstance(StatusEffectsModded.TOTEMIC_ZEAL.get(),20,0));
            }
        }
        for (int ii = 0; ii < entityamount; ii = ii + 1) {
            Player target = (Player) entities.get(ii);
            if (target.hasEffect(StatusEffectsModded.TOTEMIC_ZEAL.get())){
                ceiling = ceiling + 1;
            }
        }
        for (int ii = 0; ii < entityamount; ii = ii + 1) {
            Player target = (Player) entities.get(ii);
            if (Objects.equals(String.valueOf(target.getGameProfile().getName()), pBlockEntity.getTileData().getString("Inscribed")) && target.hasEffect(StatusEffectsModded.TOTEMIC_ZEAL.get())) {
                target.addEffect(new MobEffectInstance(StatusEffectsModded.TOTEMIC_ZEAL.get(),20,Math.min(ceiling-1,entityamount-1)));
            }
        }
    }

}
