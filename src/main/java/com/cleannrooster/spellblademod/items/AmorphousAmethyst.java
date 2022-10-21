package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.manasystem.manatick;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Predicate;

public class AmorphousAmethyst extends SwordItem {
    public float speed = 6;
    public float baseward = 0;
    public float ward = 0;

    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public AmorphousAmethyst(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super( Tiers.DIAMOND,  p_43270_,  p_43271_,  p_43272_);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 5, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -2, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    public float getDestroySpeed(ItemStack p_41004_, BlockState p_41005_) {
        return Math.min(9, this.speed);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", 5+ (int)((this.ward+1)/40D), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)-1.6, AttributeModifier.Operation.ADDITION));
        if(slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND) {
            return builder.build();
        }
        else{
            return super.getAttributeModifiers(slot, stack);
        }
    }


    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int p_41407_, boolean p_41408_) {

        if(entity instanceof Player player) {
            if (!p_41408_) {
                stack.hurtAndBreak(getMaxDamage(stack) / (10 * 20), player, (p_40992_) -> {
                    if (player.getMainHandItem() == stack) {
                        p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                        if (player.getOffhandItem() == stack) {
                            p_40992_.broadcastBreakEvent(EquipmentSlot.OFFHAND);
                        }
                    }
                });
            }
            else{
                stack.setDamageValue(0);
            }
            this.speed = (float) player.getAttributeValue(manatick.BASEWARD) * 9 / 40;
            this.baseward = (float) player.getAttributeValue(manatick.BASEWARD);
            this.ward = (float) player.getAttributeValue(manatick.WARD);
            BlockHitResult blockHitResult = getPlayerPOVHitResult(level,player, ClipContext.Fluid.NONE);
            Vec3 vec3 = player.getEyePosition();
            Vec3 vec31 = player.getViewVector(1.0F);
            Vec3 vec32 = vec3.add(vec31.x * 100.0D, vec31.y * 100.0D, vec31.z * 100.0D);
            EntityHitResult entityhitresult = ProjectileUtil.getEntityHitResult(player.level, player, vec3, vec32, (new AABB(vec3, vec32)).inflate(1.0D), (p_156765_) -> {
                return !p_156765_.isSpectator();
            }, 0.0F);
            if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                BlockState state = entity.level.getBlockState(blockHitResult.getBlockPos());
                if ((stack.getItem() == ModItems.AMORPHOUS.get())) {
                    CompoundTag tag = stack.getOrCreateTag();

                    if (state.is(BlockTags.MINEABLE_WITH_HOE)) {
                        tag.putInt("CustomModelData", 5);
                    }
                    if (state.is(BlockTags.MINEABLE_WITH_AXE)) {
                        tag.putInt("CustomModelData", 2);
                    }
                    if (state.is(BlockTags.MINEABLE_WITH_SHOVEL)) {
                        tag.putInt("CustomModelData", 4);
                    }
                    if (state.is(BlockTags.MINEABLE_WITH_PICKAXE)) {
                        tag.putInt("CustomModelData", 3);
                    }
                }
            }
            if (blockHitResult.getType() == HitResult.Type.MISS) {
                if ((stack.getItem() == ModItems.AMORPHOUS.get())) {
                    CompoundTag tag = stack.getOrCreateTag();
                    tag.putInt("CustomModelData", 0);

                }
            }
            if (entityhitresult != null) {
                if ((stack.getItem() == ModItems.AMORPHOUS.get())) {
                    CompoundTag tag = stack.getOrCreateTag();
                    tag.putInt("CustomModelData", 1);

                }
            }
        }
    }


    public boolean mineBlock(ItemStack p_40998_, Level p_40999_, BlockState p_41000_, BlockPos p_41001_, LivingEntity p_41002_) {
        if (!p_40999_.isClientSide && p_41000_.getDestroySpeed(p_40999_, p_41001_) != 0.0F) {
            p_40998_.hurtAndBreak(1, p_41002_, (p_40992_) -> {
                p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot p_40990_) {
        return p_40990_ == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(p_40990_);
    }

    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state)
    {
        return true;
    }


}
