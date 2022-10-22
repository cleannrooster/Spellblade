package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.entity.ModEntities;
import com.cleannrooster.spellblademod.entity.SpiderSpark;
import com.cleannrooster.spellblademod.manasystem.manatick;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class SpiderSparkItem extends Spell{
    public SpiderSparkItem(Properties p_41383_) {
        super(p_41383_);
    }
    public Item getIngredient1() {return Items.FERMENTED_SPIDER_EYE;};
    public Item getIngredient2() {return ModItems.FLUXITEM.get();};


    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        ItemStack itemstack = p_41433_.getItemInHand(p_41434_);
        Player player = p_41433_;

        if (((Player) player).isShiftKeyDown()) {
            CompoundTag nbt;
            if (itemstack.hasTag())
            {
                if(itemstack.getTag().get("Triggerable") != null) {
                    nbt = itemstack.getTag();
                    nbt.remove("Triggerable");
                    player.getInventory().setChanged();
                }
                else{
                    nbt = itemstack.getOrCreateTag();
                    nbt.putInt("Triggerable", 1);
                    player.getInventory().setChanged();
                }

            }
            else
            {
                nbt = itemstack.getOrCreateTag();
                nbt.putInt("Triggerable", 1);
                player.getInventory().setChanged();
            }
            return InteractionResultHolder.success(itemstack);

        }
        SpiderSpark spider = new SpiderSpark(ModEntities.SPARK.get(),p_41432_, p_41433_);
        spider.setPos(p_41433_.position());
        p_41432_.addFreshEntity(spider);
        ((Player)p_41433_).getAttribute(manatick.WARD).setBaseValue(((Player)p_41433_).getAttributes().getBaseValue(manatick.WARD)-10);
        if (((Player)p_41433_).getAttributes().getBaseValue(manatick.WARD) < -21) {
            p_41433_.hurt(DamageSource.MAGIC,2);
        }
        return InteractionResultHolder.success(p_41433_.getItemInHand(p_41434_));
    }
    public int getColor() {
        return 4277599;
    }

    @Override
    public int triggerCooldown() {
        return 10;
    }

    @Override
    public boolean trigger(Level level, Player player, float modifier) {
        SpiderSpark spider = new SpiderSpark(ModEntities.SPARK.get(),level, player);
        spider.setPos(player.position());
        if(player.getLastHurtMob() != null){
            if(player.getLastHurtMob().isAlive()){
                spider.setTarget(player.getLastHurtMob());
            }
        }
        ((Player)player).getAttribute(manatick.WARD).setBaseValue(((Player) player).getAttributeBaseValue(manatick.WARD)-10);

        if (((Player)player).getAttributes().getBaseValue(manatick.WARD) < -21) {
            player.hurt(DamageSource.MAGIC,2);
        }
        level.addFreshEntity(spider);
        return false;
    }
}
