package com.cleannrooster.spellblademod.manasystem;

import com.cleannrooster.spellblademod.SoundRegistrator;
import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.blocks.WardIronBlock;
import com.cleannrooster.spellblademod.items.ModArmorMaterials;
import com.cleannrooster.spellblademod.items.ModItems;
import com.cleannrooster.spellblademod.items.WardArmorItem;
import com.cleannrooster.spellblademod.manasystem.client.ManaOverlay;
import com.cleannrooster.spellblademod.manasystem.data.*;
import com.cleannrooster.spellblademod.manasystem.network.Hurt;
import com.cleannrooster.spellblademod.setup.Messages;
import io.netty.buffer.Unpooled;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.w3c.dom.Attr;

import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = "spellblademod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class manatick {
    public static final Attribute WARD = new RangedAttribute("generic.ward",0.0d,-Double.MAX_VALUE, Double.MAX_VALUE);
    public static final Attribute BASEWARD = new RangedAttribute("generic.baseward",0.0d,-Double.MAX_VALUE, Double.MAX_VALUE);
    public static final Attribute SMOTE = new RangedAttribute("smote",0.0d,0.0d, Double.MAX_VALUE);

    @SubscribeEvent
    public static void manatickevent(LivingEvent.LivingUpdateEvent event) {
        if(event.getEntityLiving().getAttribute(SMOTE) != null) {
            if (event.getEntityLiving().getAttribute(SMOTE).getValue() > 0) {
                event.getEntityLiving().getAttribute(SMOTE).setBaseValue(event.getEntityLiving().getAttribute(SMOTE).getValue() - 1);
            }
        }
    }
    @SubscribeEvent
    public static void manatickevent(TickEvent.PlayerTickEvent event){
          float basestep;
          float basewaving;
         float basearmor;
         float basewarding;
         float basetotem;
        if(event.phase != TickEvent.Phase.START) {
            return;
        }
        if(event.player.getAttribute(manatick.BASEWARD) != null && event.player.getAttribute(manatick.WARD) != null) {
            basemana.baseadditional = basemana.sum();

            int wardeff = 0;
            float flagd = 0;
            int minWard = 0;
            int warddrain = 0;


            if (event.player.getFeetBlockState().getBlock() instanceof WardIronBlock) {
                basestep = 3;
            } else {
                basestep = 0;
            }
            UUID uuid = UUID.fromString("bf87bfce-4668-11ed-b878-0242ac120002");
            if (event.player.hasEffect(StatusEffectsModded.WARD_DRAIN.get())) {
                warddrain = event.player.getEffect(StatusEffectsModded.WARD_DRAIN.get()).getAmplifier()+1;

            } else {
                warddrain = 0;

            }


            if (event.player.getInventory().getArmor(0).getItem() instanceof WardArmorItem) {
                wardeff = wardeff + 1;
                if (event.player.getInventory().getArmor(0).getItem() == ModItems.DIAMOND_WARDING_BOOTS.get()) {
                    flagd = flagd + 1;
                }
            }

            if (event.player.hasEffect(StatusEffectsModded.WARDING.get()) && !event.player.hasEffect(StatusEffectsModded.WARDLOCKED.get())) {
                //playerMana.addMana((1 + event.player.getEffect(StatusEffectsModded.WARDING.get()).getAmplifier()));
                basewarding = (1 + event.player.getEffect(StatusEffectsModded.WARDING.get()).getAmplifier());
            } else {
                basewarding = 0;
            }
            if (event.player.hasEffect(StatusEffectsModded.WARD_DRAIN.get()) && !event.player.hasEffect(StatusEffectsModded.WARDLOCKED.get())) {
                //playerMana.addMana(-8*(1+event.player.getEffect(StatusEffectsModded.WARD_DRAIN.get()).getAmplifier()));
            }
            if (event.player.hasEffect(StatusEffectsModded.SPELLWEAVING.get())) {
                //playerMana.addMana(-1*(1+event.player.getEffect(StatusEffectsModded.SPELLWEAVING.get()).getAmplifier()));
                basewaving = -1 * (1 + event.player.getEffect(StatusEffectsModded.SPELLWEAVING.get()).getAmplifier());
            } else {
                basewaving = 0;
            }
            if (event.player.hasEffect(StatusEffectsModded.TOTEMIC_ZEAL.get()) && !event.player.hasEffect(StatusEffectsModded.WARDLOCKED.get())) {
                //playerMana.addMana((float) (1+0.25*event.player.getEffect(StatusEffectsModded.TOTEMIC_ZEAL.get()).getAmplifier()));
                basetotem = (float) (1 + 0.25 * event.player.getEffect(StatusEffectsModded.TOTEMIC_ZEAL.get()).getAmplifier());
            } else {
                basetotem = 0;

            }
            if (event.player.getInventory().getArmor(1).getItem() instanceof WardArmorItem) {
                wardeff = wardeff + 1;
                if (event.player.getInventory().getArmor(1).getItem() == ModItems.DIAMOND_WARDING_LEGGINGS.get()) {
                    flagd = flagd + 1;
                }
            }
            if (event.player.getInventory().getArmor(2).getItem() instanceof WardArmorItem) {
                wardeff = wardeff + 1;
                if (event.player.getInventory().getArmor(2).getItem() == ModItems.DIAMOND_WARDING_CHEST.get()) {
                    flagd = flagd + 1;
                }
            }
            if (event.player.getInventory().getArmor(3).getItem() instanceof WardArmorItem) {
                wardeff = wardeff + 1;
                if (event.player.getInventory().getArmor(3).getItem() == ModItems.DIAMOND_WARDING_HELMET.get()) {
                    flagd = flagd + 1;
                }
            }
            if (event.player.getInventory().getArmor(0).isEnchanted()) {
                if (event.player.getInventory().getArmor(0).getEnchantmentTags().toString().contains("lesserwarding")) {
                    flagd = flagd + 1F;
                }
            }
            if (event.player.getInventory().getArmor(1).isEnchanted()) {
                if (event.player.getInventory().getArmor(1).getEnchantmentTags().toString().contains("lesserwarding")) {
                    flagd = flagd + 1F;
                }
            }
            if (event.player.getInventory().getArmor(2).isEnchanted()) {
                if (event.player.getInventory().getArmor(2).getEnchantmentTags().toString().contains("lesserwarding")) {
                    flagd = flagd + 1F;
                }
            }
            if (event.player.getInventory().getArmor(3).isEnchanted()) {
                if (event.player.getInventory().getArmor(3).getEnchantmentTags().toString().contains("lesserwarding")) {
                    flagd = flagd + 1F;
                }
            }
            if (event.player.getInventory().getArmor(0).isEnchanted()) {
                if (event.player.getInventory().getArmor(0).getEnchantmentTags().toString().contains("greaterwarding")) {
                    flagd = flagd + 2F;
                }
            }
            if (event.player.getInventory().getArmor(1).isEnchanted()) {
                if (event.player.getInventory().getArmor(1).getEnchantmentTags().toString().contains("greaterwarding")) {
                    flagd = flagd + 2F;
                }
            }
            if (event.player.getInventory().getArmor(2).isEnchanted()) {
                if (event.player.getInventory().getArmor(2).getEnchantmentTags().toString().contains("greaterwarding")) {
                    flagd = flagd + 2F;
                }
            }
            if (event.player.getInventory().getArmor(3).isEnchanted()) {
                if (event.player.getInventory().getArmor(3).getEnchantmentTags().toString().contains("greaterwarding")) {
                    flagd = flagd + 2F;
                }
            }


            if (!event.player.hasEffect(StatusEffectsModded.WARDLOCKED.get())) {
                //playerMana.addMana( ((float)flagd*(float)0.25));
            }
            basearmor = (float) (0.25 * flagd);
            float multiplier = 1;
            float base = (float) (basestep + basewaving + basearmor + basewarding + basetotem -warddrain);

            if(base > 0 && (event.player.getInventory().hasAnyOf(Set.of(ModItems.AMORPHOUS.get())))){
                multiplier = 0.5F;
            }
            float baseWard = multiplier*base / (0.025F);


            if (!event.player.hasEffect(StatusEffectsModded.WARDLOCKED.get())) {
                event.player.getAttribute(manatick.WARD).setBaseValue((float) (event.player.getAttribute(manatick.WARD).getValue() + (baseWard - event.player.getAttribute(manatick.WARD).getValue()) * 0.01856026932));
            }
            event.player.getAttribute(manatick.BASEWARD).setBaseValue(baseWard);


        }
        /*if (!event.player.hasEffect(StatusEffectsModded.WARDLOCKED.get())){
            playerMana.setMana(minWard);
        }*/
    }


}

