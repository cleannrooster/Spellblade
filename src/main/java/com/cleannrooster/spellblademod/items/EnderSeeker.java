package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.manasystem.manatick;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;

public class EnderSeeker extends Item {
    private static final Component CONTAINER_TITLE = new TranslatableComponent("container.enderchest");

    public EnderSeeker(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        Player player = p_41433_;
        PlayerEnderChestContainer playerenderchestcontainer = p_41433_.getEnderChestInventory();
        ItemStack itemStack = p_41433_.getItemInHand(p_41434_);
        if (playerenderchestcontainer != null) {
            ((Player)player).getAttribute(manatick.WARD).setBaseValue(((Player) player).getAttributeBaseValue(manatick.WARD)-80);
            if (((Player)player).getAttributes().getBaseValue(manatick.WARD)< -21) {
                player.hurt(DamageSource.MAGIC,2);
            }
            if (p_41432_.isClientSide) {
                return InteractionResultHolder.success(itemStack);
            } else {
                p_41433_.openMenu(new SimpleMenuProvider((p_53124_, p_53125_, p_53126_) -> {
                    return ChestMenu.threeRows(p_53124_, p_53125_, playerenderchestcontainer);
                }, CONTAINER_TITLE));
                p_41432_.playSound((Player)null, (double)player.getX() + 0.5D, (double)player.getY() + 0.5D, (double)player.getZ() + 0.5D, SoundEvents.ENDER_CHEST_OPEN, SoundSource.BLOCKS, 0.5F, p_41432_.random.nextFloat() * 0.1F + 0.9F);
                p_41433_.awardStat(Stats.OPEN_ENDERCHEST);
                PiglinAi.angerNearbyPiglins(p_41433_, true);
                return InteractionResultHolder.consume(itemStack);
            }
        } else {
            return InteractionResultHolder.sidedSuccess(itemStack, p_41432_.isClientSide);
        }
    }
}
