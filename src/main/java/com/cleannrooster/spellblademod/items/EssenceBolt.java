package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.entity.EssenceBoltEntity;
import com.cleannrooster.spellblademod.entity.ModEntities;
import com.cleannrooster.spellblademod.manasystem.manatick;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class EssenceBolt extends Spell{
    public EssenceBolt(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public boolean isTargeted() {
        return false;
    }
    @Override
    public int getColor() {
        return 16766720;
    }

    public Item getIngredient1() {return Items.GLOWSTONE_DUST;};
    public Item getIngredient2() {return ModItems.FLUXITEM.get();};

    @Override
    public boolean isTriggerable() {
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        EssenceBoltEntity bolt = new EssenceBoltEntity(ModEntities.ESSENCEBOLT.get(),level,player);
        bolt.shootFromRotation(player,player.getXRot(),player.getYRot(),0,4,1);
        if(!level.isClientSide()) {
            level.addFreshEntity(bolt);
        }
        ((Player)player).getAttribute(manatick.WARD).setBaseValue(((Player) player).getAttributeBaseValue(manatick.WARD)-20);

        if (((Player)player).getAttributes().getBaseValue(manatick.WARD) < -21) {
            player.hurt(DamageSource.MAGIC,2);
        }
        return super.use(level, player, hand);
    }
}
