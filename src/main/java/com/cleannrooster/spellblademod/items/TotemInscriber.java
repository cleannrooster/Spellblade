package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.ModBlocks;
import com.cleannrooster.spellblademod.blocks.WardingTotemBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class TotemInscriber extends Item{
    public TotemInscriber(Properties p_41383_) {
        super(p_41383_);
    }
    public InteractionResult useOn(UseOnContext p_42885_) {
        BlockEntity entity = p_42885_.getLevel().getBlockEntity(p_42885_.getClickedPos());
        if (entity instanceof WardingTotemBlockEntity && entity.getTileData().get("Inscribed") == null && !p_42885_.getLevel().isClientSide) {
            CompoundTag tag = new CompoundTag();
            String name = String.valueOf(p_42885_.getPlayer().getGameProfile().getName());
            tag.putString("Inscribed", name);
            entity.getTileData().putString("Inscribed", name);
            p_42885_.getPlayer().displayClientMessage(Component.nullToEmpty("Inscribed " + name), true);
            entity.setChanged();
            return InteractionResult.SUCCESS;
        }
        else if (entity instanceof WardingTotemBlockEntity && entity.getTileData().get("Inscribed") != null && !p_42885_.getLevel().isClientSide) {
            String name = entity.getTileData().getString("Inscribed");
            p_42885_.getPlayer().displayClientMessage(Component.nullToEmpty("Already inscribed by " + name), true);
            return super.useOn(p_42885_);
        } else {
            return super.useOn(p_42885_);
        }
    }
}
