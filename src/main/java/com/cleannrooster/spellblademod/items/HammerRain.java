package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.entity.HammerEntity;
import com.cleannrooster.spellblademod.entity.ModEntities;
import com.cleannrooster.spellblademod.manasystem.manatick;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class HammerRain extends Spell {
    public HammerRain(Properties p_41383_) {
        super(p_41383_);
    }
    public int getColor() {
        return 8307711;
    }

    @Override
    public boolean isTargeted() {
        return true;
    }

    public Item getIngredient1() {return Items.PRISMARINE;};
    public Item getIngredient2() {return ModItems.FLUXITEM.get();};

    public InteractionResultHolder<ItemStack> use(Level p_43405_, Player p_43406_, InteractionHand p_43407_) {
        ItemStack itemstack = p_43406_.getItemInHand(p_43407_);

        if (p_43406_.isShiftKeyDown()) {
            CompoundTag nbt;
            if (itemstack.hasTag())
            {
                if(itemstack.getTag().get("Triggerable") != null) {
                    nbt = itemstack.getTag();
                    nbt.remove("Triggerable");
                }
                else{
                    nbt = itemstack.getOrCreateTag();
                    nbt.putInt("Triggerable", 1);
                }

            }
            else
            {
                nbt = itemstack.getOrCreateTag();
                nbt.putInt("Triggerable", 1);
            }
            return InteractionResultHolder.success(itemstack);

        }
        ((Player)p_43406_).getAttribute(manatick.WARD).setBaseValue(((Player) p_43406_).getAttributeBaseValue(manatick.WARD)-20);

        if (((Player)p_43406_).getAttributes().getBaseValue(manatick.WARD) < -21) {
            p_43406_.hurt(DamageSource.MAGIC,2);
        }
        if (!p_43406_.isShiftKeyDown()) {
        }
            HammerEntity hammer1 = new HammerEntity(ModEntities.TRIDENT.get(),p_43406_.getLevel());
            hammer1.setPos(p_43406_.getEyePosition());
            hammer1.setOwner(p_43406_);
            hammer1.pickup = AbstractArrow.Pickup.DISALLOWED;
            hammer1.shootFromRotation(p_43406_, p_43406_.getXRot(), p_43406_.getYRot(), 0.0F, 1.6F, 1.0F);
            p_43405_.addFreshEntity(hammer1);
            return InteractionResultHolder.success(itemstack);
    }
    @Override
    public boolean triggeron(Level level, Player player, LivingEntity target, float modifier){
        if(((Player)player).getAttributes().getBaseValue(manatick.WARD) < -1 && player.getHealth() <= 2)
        {
            return true;
        }

        Random rand = new Random();

        HammerEntity hammer1 = new HammerEntity(ModEntities.TRIDENT.get(),player.getLevel());
        hammer1.triggered = true;
        hammer1.setPos(target.position().add(rand.nextDouble(-4,-1), rand.nextDouble(3,6), rand.nextDouble(-4,-1)));
        hammer1.setOwner(player);
        hammer1.pickup = AbstractArrow.Pickup.DISALLOWED;
        hammer1.secondary = true;
        HammerEntity hammer2 = new HammerEntity(ModEntities.TRIDENT.get(),player.getLevel());
        hammer2.triggered = true;
        hammer2.setPos(target.position().add(rand.nextDouble(-4,-1), rand.nextDouble(3,6), rand.nextDouble(1,4)));
        hammer2.setOwner(player);
        hammer2.pickup = AbstractArrow.Pickup.DISALLOWED;
        hammer2.secondary = true;
        HammerEntity hammer3 = new HammerEntity(ModEntities.TRIDENT.get(),player.getLevel());
        hammer3.triggered = true;
        hammer3.setPos(target.position().add(rand.nextDouble(1,4), rand.nextDouble(3,6), rand.nextDouble(1,4)));
        hammer3.setOwner(player);
        hammer3.pickup = AbstractArrow.Pickup.DISALLOWED;
        hammer3.secondary = true;
        HammerEntity hammer4 = new HammerEntity(ModEntities.TRIDENT.get(),player.getLevel());
        hammer4.triggered = true;
        hammer4.setPos(target.position().add(rand.nextDouble(1,4), rand.nextDouble(3,6), rand.nextDouble(-4,-1)));
        hammer4.setOwner(player);
        hammer4.pickup = AbstractArrow.Pickup.DISALLOWED;
        hammer4.secondary = true;
        hammer1.setXRot(90);
        hammer1.setYRot(0);
        hammer2.setXRot(90);
        hammer2.setYRot(0);
        hammer3.setXRot(90);
        hammer3.setYRot(0);
        hammer4.setXRot(90);
        hammer4.setYRot(0);
        Vec3 vec3 = player.getViewVector(1F);
        hammer1.shoot(0, -1, 0, 1.6F, 0);
        hammer2.shoot(0,-1,0, 1.6F, 0);
        hammer3.shoot(0,-1,0, 1.6F, 0);
        hammer4.shoot(0,-1,0, 1.6F, 0);

        level.addFreshEntity(hammer1);
        level.addFreshEntity(hammer2);
        level.addFreshEntity(hammer3);
        level.addFreshEntity(hammer4);
        ((Player)player).getAttribute(manatick.WARD).setBaseValue(((Player) player).getAttributeBaseValue(manatick.WARD)-20);

        if (((Player)player).getAttributes().getBaseValue(manatick.WARD) < -21) {
            player.hurt(DamageSource.MAGIC,2);
        }
        return false;
    }
    @Override
    public boolean trigger(Level level, Player player, float modifier){
        if(((Player)player).getAttributes().getBaseValue(manatick.WARD) < -1 && player.getHealth() <= 2)
        {
            return true;
        }

        Random rand = new Random();

        HammerEntity hammer1 = new HammerEntity(ModEntities.TRIDENT.get(),player.getLevel());
        hammer1.triggered = true;
        hammer1.setPos(player.position().add(rand.nextDouble(-4,-1), rand.nextDouble(3,6), rand.nextDouble(-4,-1)));
        hammer1.setOwner(player);
        hammer1.pickup = AbstractArrow.Pickup.DISALLOWED;
        hammer1.secondary = true;
        HammerEntity hammer2 = new HammerEntity(ModEntities.TRIDENT.get(),player.getLevel());
        hammer2.triggered = true;
        hammer2.setPos(player.position().add(rand.nextDouble(-4,-1), rand.nextDouble(3,6), rand.nextDouble(1,4)));
        hammer2.setOwner(player);
        hammer2.pickup = AbstractArrow.Pickup.DISALLOWED;
        hammer2.secondary = true;
        HammerEntity hammer3 = new HammerEntity(ModEntities.TRIDENT.get(),player.getLevel());
        hammer3.triggered = true;
        hammer3.setPos(player.position().add(rand.nextDouble(1,4), rand.nextDouble(3,6), rand.nextDouble(1,4)));
        hammer3.setOwner(player);
        hammer3.pickup = AbstractArrow.Pickup.DISALLOWED;
        hammer3.secondary = true;
        HammerEntity hammer4 = new HammerEntity(ModEntities.TRIDENT.get(),player.getLevel());
        hammer4.triggered = true;
        hammer4.setPos(player.position().add(rand.nextDouble(1,4), rand.nextDouble(3,6), rand.nextDouble(-4,-1)));
        hammer4.setOwner(player);
        hammer4.pickup = AbstractArrow.Pickup.DISALLOWED;
        hammer4.secondary = true;
        hammer1.setXRot(90);
        hammer1.setYRot(0);
        hammer2.setXRot(90);
        hammer2.setYRot(0);
        hammer3.setXRot(90);
        hammer3.setYRot(0);
        hammer4.setXRot(90);
        hammer4.setYRot(0);
        Vec3 vec3 = player.getViewVector(1F);
        hammer1.shoot(0, -1, 0, 1.6F, 0);
        hammer2.shoot(0,-1,0, 1.6F, 0);
        hammer3.shoot(0,-1,0, 1.6F, 0);
        hammer4.shoot(0,-1,0, 1.6F, 0);

        level.addFreshEntity(hammer1);
        level.addFreshEntity(hammer2);
        level.addFreshEntity(hammer3);
        level.addFreshEntity(hammer4);
        ((Player)player).getAttribute(manatick.WARD).setBaseValue(((Player) player).getAttributeBaseValue(manatick.WARD)-20);

        if (((Player)player).getAttributes().getBaseValue(manatick.WARD) < -21) {
            player.hurt(DamageSource.MAGIC,2);
        }
        return false;
    }
    @Override
    public boolean isFoil(ItemStack p_41453_) {
        if (p_41453_.hasTag()){
            if(p_41453_.getTag().getInt("Triggerable") == 1){
                return true;
            }
        }
        return false;
    }

    @Override
    public int triggerCooldown() {
        return 10;
    }
}
