package com.cleannrooster.spellblademod.manasystem.client;

import com.cleannrooster.spellblademod.SpellbladeMod;
import com.cleannrooster.spellblademod.items.AmorphousAmethyst;
import com.cleannrooster.spellblademod.items.ModItems;
import com.cleannrooster.spellblademod.manasystem.manatick;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Random;

import static net.minecraftforge.client.gui.ForgeIngameGui.PLAYER_HEALTH_ELEMENT;


@Mod.EventBusSubscriber(modid = "spellblademod", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RenderEvent {

        @SubscribeEvent
    public static void renderEvent(RenderHandEvent event) {
        if (Minecraft.getInstance().player.isAutoSpinAttack() && (EnchantmentHelper.getEnchantments(event.getItemStack()).containsKey(SpellbladeMod.spellproxy) || event.getItemStack().getItem() == ModItems.SPELLBLADE.get() || event.getItemStack().getItem() == ModItems.LIGHTNING_WHIRL.get())) {
            event.setCanceled(true);
            boolean flag = event.getHand() == InteractionHand.MAIN_HAND;
            HumanoidArm humanoidarm = flag ? Minecraft.getInstance().player.getMainArm() : Minecraft.getInstance().player.getMainArm().getOpposite();
            boolean flag3 = humanoidarm == HumanoidArm.RIGHT;

            int i = humanoidarm == HumanoidArm.RIGHT ? 1 : -1;
            event.getPoseStack().translate((double) ((float) i * 0.56F), (double) (-0.52F + 0 * -0.6F), (double) -0.72F);
            int j = flag3 ? 1 : -1;
            event.getPoseStack().translate((double) ((float) j * -0.4F), (double) 0.8F, (double) 0.3F);
            event.getPoseStack().mulPose(Vector3f.YP.rotationDegrees((float) j * 65.0F));
            event.getPoseStack().mulPose(Vector3f.ZP.rotationDegrees((float) j * -85.0F));
            ItemTransforms.TransformType transformType = flag3 ? ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND;
            ItemStack itemStack = new ItemStack(Items.TRIDENT);
            itemStack.enchant(Enchantments.CHANNELING, 1);
            Minecraft.getInstance().getItemRenderer().renderStatic(Minecraft.getInstance().player, itemStack, flag3 ? ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !flag3, event.getPoseStack(), event.getMultiBufferSource(), Minecraft.getInstance().level, event.getPackedLight(), OverlayTexture.NO_OVERLAY, Minecraft.getInstance().player.getId() + transformType.ordinal());
        }
    }
    @SubscribeEvent
    public static void rendertick(RenderGameOverlayEvent.PostLayer event) {
        if (event.getOverlay() == PLAYER_HEALTH_ELEMENT) {
            if (!Minecraft.getInstance().options.hideGui && Minecraft.getInstance().gameMode.canHurtPlayer() && Minecraft.getInstance().getCameraEntity() instanceof Player) {
                double ward = Minecraft.getInstance().player.getAttributeValue(manatick.WARD);
                if (Math.round(ward / 40) > 0) {
                    PoseStack poseStack = new PoseStack();
                    //new GUI(Minecraft.getInstance()).renderWard(Minecraft.getInstance().getWindow().getGuiScaledWidth(), Minecraft.getInstance().getWindow().getGuiScaledHeight(), poseStack);
                }
            }
        }
    }
    @SubscribeEvent
    public static void tickevent(ItemTossEvent event) {
        if(event.getEntityItem().getItem().getItem() instanceof AmorphousAmethyst){
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void tickevent(EntityEvent.CanUpdate event) {
        if(event.getEntity() instanceof ItemEntity itemEntity){
            if(itemEntity.getItem().getItem() instanceof AmorphousAmethyst) {
                event.getEntity().discard();
            }
        }
    }
}
