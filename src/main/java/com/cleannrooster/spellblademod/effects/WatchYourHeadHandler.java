package com.cleannrooster.spellblademod.effects;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraft.world.level.block.PointedDripstoneBlock.TIP_DIRECTION;
@Mod.EventBusSubscriber(modid = "spellblademod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WatchYourHeadHandler {
    @SubscribeEvent
    public static void handler(LivingEvent.LivingUpdateEvent event) {
        if(event.getEntityLiving().hasEffect(StatusEffectsModded.WATCHYOURHEAD.get())) {
            if(event.getEntityLiving().getEffect(StatusEffectsModded.WATCHYOURHEAD.get()).getDuration()%5 == 0) {
                BlockState blockstate = Blocks.POINTED_DRIPSTONE.defaultBlockState().setValue(TIP_DIRECTION, Direction.DOWN);
                BlockPos pos = event.getEntityLiving().blockPosition().above(6);
                FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(event.getEntityLiving().getLevel(), pos, blockstate);
                fallingblockentity.dropItem = false;
                fallingblockentity.setHurtsEntities(1, 40);
            }
        }
    }
}
