package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.SpellbladeMod;
import com.cleannrooster.spellblademod.entity.CatSpark;
import com.cleannrooster.spellblademod.entity.ModEntities;
import com.cleannrooster.spellblademod.entity.SpiderSpark;
import com.cleannrooster.spellblademod.manasystem.manatick;
import com.cleannrooster.spellblademod.patreon.Patreon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Cat;
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
    public boolean isTargeted() {
        return true;
    }

    @Override
    public boolean triggeron(Level level, Player player, LivingEntity target, float modifier) {
        if (Patreon.allowed(player, SpellbladeMod.CATUUIDS) && Patreon.cat.contains(player)) {

            CatSpark spider = new CatSpark(ModEntities.CATSPARK.get(), level);
            spider.setPos(target.position());
            if (player.getLastHurtMob() != null) {
                if (player.getLastHurtMob().isAlive()) {
                    spider.setTarget(player.getLastHurtMob());
                }
            }


            ((Player) player).getAttribute(manatick.WARD).setBaseValue(((Player) player).getAttributeBaseValue(manatick.WARD) - 10);
            spider.setOwnerUUID(player.getUUID());

            if (((Player) player).getAttributes().getBaseValue(manatick.WARD) < -21) {
                player.hurt(DamageSource.MAGIC, 2);
            }
            spider.setCatType(level.random.nextInt(11));
            level.addFreshEntity(spider);
            return false;

        }
        SpiderSpark spider = new SpiderSpark(ModEntities.SPARK.get(),level, player);
        spider.setPos(target.position());
        if(player.getLastHurtMob() != null){
            if(player.getLastHurtMob().isAlive()){
                spider.setTarget(target);
            }
        }
        ((Player)player).getAttribute(manatick.WARD).setBaseValue(((Player) player).getAttributeBaseValue(manatick.WARD)-10);

        if (((Player)player).getAttributes().getBaseValue(manatick.WARD) < -21) {
            player.hurt(DamageSource.MAGIC,2);
        }
        level.addFreshEntity(spider);
        return false;
    }

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
        if(Patreon.allowed(player, SpellbladeMod.CATUUIDS) && Patreon.cat.contains(player)) {
            CatSpark spider = new CatSpark(ModEntities.CATSPARK.get(),p_41432_);
            spider.setPos(p_41433_.position());
            spider.setCatType(p_41432_.random.nextInt(11));
            spider.setOwnerUUID(player.getUUID());
            p_41432_.addFreshEntity(spider);
            ((Player)p_41433_).getAttribute(manatick.WARD).setBaseValue(((Player)p_41433_).getAttributes().getBaseValue(manatick.WARD)-10);
            if (((Player)p_41433_).getAttributes().getBaseValue(manatick.WARD) < -21) {
                p_41433_.hurt(DamageSource.MAGIC,2);
            }
            return InteractionResultHolder.success(p_41433_.getItemInHand(p_41434_));
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
        if (Patreon.allowed(player, SpellbladeMod.CATUUIDS) && Patreon.cat.contains(player)) {

            CatSpark spider = new CatSpark(ModEntities.CATSPARK.get(), level);
            spider.setPos(player.position());
            if (player.getLastHurtMob() != null) {
                if (player.getLastHurtMob().isAlive()) {
                    spider.setTarget(player.getLastHurtMob());
                }
            }


        ((Player) player).getAttribute(manatick.WARD).setBaseValue(((Player) player).getAttributeBaseValue(manatick.WARD) - 10);

        if (((Player) player).getAttributes().getBaseValue(manatick.WARD) < -21) {
            player.hurt(DamageSource.MAGIC, 2);
        }
            spider.setCatType(level.random.nextInt(11));
            spider.setOwnerUUID(player.getUUID());

            level.addFreshEntity(spider);
            return false;

        }
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
