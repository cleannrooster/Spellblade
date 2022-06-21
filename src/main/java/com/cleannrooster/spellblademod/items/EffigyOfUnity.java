package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.blocks.WardingTotemBlock;
import com.cleannrooster.spellblademod.blocks.WardingTotemBlockEntity;
import com.cleannrooster.spellblademod.manasystem.data.PlayerMana;
import com.cleannrooster.spellblademod.manasystem.data.PlayerManaProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class EffigyOfUnity extends Item {
    public EffigyOfUnity(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public UseAnim getUseAnimation(ItemStack p_43417_) {
        return UseAnim.BOW;
    }
    @Override
    public int getUseDuration(ItemStack p_43419_) {
        return 200;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        Player player = p_41433_;
        PlayerMana playerMana = player.getCapability(PlayerManaProvider.PLAYER_MANA).orElse(null);
        ItemStack itemstack = p_41433_.getItemInHand(p_41434_);

        if(playerMana.getMana()< 159 ){
            return InteractionResultHolder.fail(itemstack);
        }
        else {
            player.getCooldowns().addCooldown(this,20);
            player.addEffect(new MobEffectInstance(StatusEffectsModded.WARD_DRAIN.get(),5, 3));
            p_41433_.startUsingItem(p_41434_);
            return InteractionResultHolder.consume(itemstack);
        }

    }
    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count)
    {
        List players = player.getLevel().players().stream().toList();
        if(count%20 == 0 && count >= 120){
            ((Player)(player)).displayClientMessage(Component.nullToEmpty("Teleporting in " + (count-120)/20), true);
        }
        for(int i = 0; i < players.toArray().length; i++){
            Player teleportee = (Player)players.get(i);
            if (Objects.equals(((Player) players.get(i)).getGameProfile().getName(), stack.getOrCreateTag().getString("UnitedWith")) &&  count == 120 && player.level == teleportee.level){
                player.teleportTo(teleportee.getX(),teleportee.getY(), teleportee.getZ());
            }
        }
    }
    @Override
    public InteractionResult useOn(UseOnContext p_42885_) {
        int intarray[];
        intarray = new int[128];
        boolean startdown = false;
        boolean startup = false;
        if (p_42885_.getLevel().getBlockEntity(p_42885_.getClickedPos()) instanceof WardingTotemBlockEntity && p_42885_.getLevel().getBlockEntity(p_42885_.getClickedPos()).getTileData().getString("Inscribed") != null) {
             startdown = true;
             startup = true;
        }
        else{
            return super.useOn(p_42885_);
        }
        int index = 0;
        BlockEntity entity[] = new BlockEntity[393];
        entity[index] = p_42885_.getLevel().getBlockEntity(p_42885_.getClickedPos());
        while(startdown == true) {
            if (p_42885_.getLevel().getBlockEntity(entity[0].getBlockPos().below())== null) {
                startdown = false;
                break;
            }
            entity[index+1] = p_42885_.getLevel().getBlockEntity(entity[index].getBlockPos().below());
            if (entity[index+1]!= null){
                if (entity[index+1] instanceof WardingTotemBlockEntity){

                    if(entity[index + 1].getTileData().getString("Inscribed").equals(p_42885_.getPlayer().getGameProfile().getName()))
                    {
                        p_42885_.getItemInHand().getOrCreateTag().putString("UnitedWith",p_42885_.getLevel().getBlockEntity(p_42885_.getClickedPos()).getTileData().getString("Inscribed"));
                        String name = p_42885_.getLevel().getBlockEntity(p_42885_.getClickedPos()).getTileData().getString("Inscribed");
                        p_42885_.getPlayer().displayClientMessage(Component.nullToEmpty("Spellbook attuned to " + name), true);

                        startup = false;
                        startdown = false;
                        break;
                    }
                    index++;
                }

            }
            else{
                startdown = false;
            }
        }
        while (startup == true){
            if (p_42885_.getLevel().getBlockEntity(entity[0].getBlockPos().above())== null) {
                startup = false;
                break;
            }
            entity[index+1] = p_42885_.getLevel().getBlockEntity(entity[index].getBlockPos().above());
            if (entity[index+1]!= null){
                if (entity[index+1] instanceof WardingTotemBlockEntity){
                    if(entity[index + 1].getTileData().getString("Inscribed").equals(p_42885_.getPlayer().getGameProfile().getName()))
                    {
                        String name = p_42885_.getLevel().getBlockEntity(p_42885_.getClickedPos()).getTileData().getString("Inscribed");
                        p_42885_.getItemInHand().getOrCreateTag().putString("UnitedWith",name);
                        p_42885_.getPlayer().displayClientMessage(Component.nullToEmpty("Spellbook attuned to " + name), true);

                        startup = false;
                        startdown = false;
                        break;
                    }
                    index++;
                }

            }
            else{
                startup = false;
            }
        }
        if (p_42885_.getLevel().getBlockEntity(p_42885_.getClickedPos()) instanceof WardingTotemBlockEntity) {
            return InteractionResult.SUCCESS;
        }
        else{
            return super.useOn(p_42885_);
        }
    }

}
