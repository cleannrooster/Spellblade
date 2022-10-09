package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.entity.*;
import com.cleannrooster.spellblademod.manasystem.manatick;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Animate extends Item{
    int timestamp;
    public Animate(Item.Properties p_41383_) {
        super(p_41383_);
    }
    ItemEntity target = null;
    LivingEntity playerLastHurtBy = null;
    LivingEntity ownerLastHurt = null;
    public InteractionResultHolder<ItemStack> use(Level p_40672_, Player p_40673_, InteractionHand p_40674_) {
        ItemStack itemstack = p_40673_.getItemInHand(p_40674_);
        Player player = p_40673_;
        if(((Player)player).getAttributes().getBaseValue(manatick.WARD) < 80){
            return InteractionResultHolder.fail(itemstack);
        }
        double tridentEntity = player.getYRot();
        double f = player.getXRot();
        float g = (float) (-sin(tridentEntity * ((float) Math.PI / 180)) * cos(f * ((float) Math.PI / 180)));
        float h = (float) -sin(f * ((float) Math.PI / 180));
        float r = (float) (cos(tridentEntity * ((float) Math.PI / 180)) * cos(f * ((float) Math.PI / 180)));
        AABB box = player.getBoundingBox();
/*        float minx = (float) box.minX;
        float miny = (float) box.minY;
        float minz = (float) box.minZ;*/
        AABB box3 = box.expandTowards(5 * g, 5 * h, 5 * r);
        List entities = player.level.getEntitiesOfClass(ItemEntity.class, box3);
        Object entitiesarray[] = entities.toArray();
        int entityamount = entitiesarray.length;


        for (int ii = 0; ii < entityamount; ii = ii + 1) {
            target = (ItemEntity) entities.get(ii);
            if (target.getItem().getItem() instanceof SwordItem && player.hasLineOfSight(target)) {
                target.remove(Entity.RemovalReason.DISCARDED);
                InvisiVex animated = new InvisiVex(ModEntities.INVISIVEX.get(), p_40672_);
                animated.setPos(target.position());

                animated.owner2 = player;
                animated.number = ii + 1;
                p_40672_.addFreshEntity(animated);
                animated.equipItemIfPossible(target.getItem());
                /*animated.equipItemIfPossible(target.getItemBySlot(EquipmentSlot.CHEST));
                animated.equipItemIfPossible(target.getItemBySlot(EquipmentSlot.LEGS));
                animated.equipItemIfPossible(target.getItemBySlot(EquipmentSlot.FEET));*/
                animated.setInvisible(true);
                animated.setSilent(true);
            /*{
                    @Override
                    public boolean canUse() {
                        LivingEntity livingentity = player;
                        if (livingentity == null) {
                            return false;
                        }
                        if (playerLastHurtBy == player) {
                            return false;
                        }
                        else {
                            LivingEntity playerLastHurtBy = livingentity.getLastHurtByMob();
                            animated.setTarget(playerLastHurtBy);
                            timestamp = livingentity.getLastHurtMobTimestamp();
                            return this.canAttack(playerLastHurtBy, TargetingConditions.DEFAULT);
                        }
                    }
                });*/
                /*animated.targetSelector.addGoal(2, new TargetGoal(animated, true, false) {
                    @Override
                    public boolean canUse() {
                        LivingEntity livingentity = player;
                        if (livingentity == null) {
                            return false;
                        }
                        if (ownerLastHurt == player) {
                            return false;
                        }
                        else {
                            LivingEntity ownerLastHurt = livingentity.getLastHurtMob();
                            return this.canAttack(ownerLastHurt, TargetingConditions.DEFAULT);
                        }
                    }
                });*/


            }
        }
        return InteractionResultHolder.consume(itemstack);

    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(((Player)context.getPlayer()).getAttributes().getBaseValue(manatick.WARD) < 80){
            return InteractionResult.FAIL;
        }
        if(context.getLevel().getBlockState(context.getClickedPos()).getBlock() == Blocks.IRON_BLOCK){
            context.getLevel().removeBlock(context.getClickedPos(),false);
            SentinelEntity sentinel = new SentinelEntity(ModEntities.SENTINEL.get(),context.getLevel());
            sentinel.setPos(new Vec3(context.getClickedPos().getX(),context.getClickedPos().getY(),context.getClickedPos().getZ()));
            context.getLevel().addFreshEntity(sentinel);
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);

    }
}
