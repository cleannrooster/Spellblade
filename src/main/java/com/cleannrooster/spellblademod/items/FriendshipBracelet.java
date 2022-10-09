package com.cleannrooster.spellblademod.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FriendshipBracelet extends Item {
    public FriendshipBracelet(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        if (p_41453_.hasTag()) {
            if (p_41453_.getTag().getInt("Friendship") == 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        ItemStack itemstack = p_41433_.getItemInHand(p_41434_);
        if (p_41433_.isShiftKeyDown()) {
            CompoundTag nbt;
            if (itemstack.hasTag())
            {
                if(itemstack.getTag().get("Friendship") != null) {
                    nbt = itemstack.getTag();
                    nbt.remove("Friendship");
                }
                else{
                    nbt = itemstack.getOrCreateTag();
                    nbt.putInt("Friendship", 1);
                }

            }
            else
            {
                nbt = itemstack.getOrCreateTag();
                nbt.putInt("Friendship", 1);
            }
            return InteractionResultHolder.success(itemstack);

        }
        return InteractionResultHolder.fail(itemstack);
    }

    public static boolean PlayerFriendshipPredicate(Player player, LivingEntity livingEntity){
        boolean flag1 = false;
        boolean flag2 = false;
        for (int i = 0; i <= player.getInventory().getContainerSize(); i++) {
            if (player.getInventory().getItem(i).getItem() instanceof FriendshipBracelet) {
                if (player.getInventory().getItem(i).getTag().get("Friendship") != null) {
                    flag1 = true;
                }
            }
        }
        if(livingEntity instanceof NeutralMob neutralMob){
            if(neutralMob.isAngryAt(player)){
                flag2 = true;
            }
        }
        return !(flag1 && (livingEntity.getClassification(false).isFriendly() || livingEntity instanceof Player || livingEntity instanceof NeutralMob))  || flag2 || livingEntity.getLastHurtMob() == player || player.getLastHurtMob() == livingEntity;

    }
    public static ItemStack FriendshipEnabled() {
    ItemStack stack  = new ItemStack(ModItems.FRIENDSHIP.get());
    CompoundTag tag = stack.getOrCreateTag();
    tag.putInt("Friendship",1);
    stack.setTag(tag);
    return stack;
    }
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        TextComponent text = new TextComponent("Enabled");
        if (p_41421_.hasTag()) {
            if (p_41421_.getTag().get("Friendship") != null) {
                p_41423_.add(text);
            }
        }
        else{
            if (p_41423_.contains(text)){
                p_41423_.remove(text);

            }
        }
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
    }
}
