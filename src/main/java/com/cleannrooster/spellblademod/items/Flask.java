package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.SpellbladeMod;
import com.cleannrooster.spellblademod.manasystem.client.ClientSetup;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TippedArrowItem;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public interface Flask {
    public String spellname = "air";
    public Item item = Items.AIR;
    public MobEffect effect = null;

    public default String getSpellName(){
        return spellname;
    }
    public default String itemSpell(){
        return item.getRegistryName().getNamespace();
    }

    public static String getSpellItem(ItemStack stack){
        return stack.getOrCreateTag().getString("Spell");
    }
    public static int getColor(ItemStack stack) {
        if(stack.getOrCreateTag().get("Color") != null) {
            return stack.getOrCreateTag().getInt("Color");
        }
        else{
            return 16777182;
        }

    }
    public static ItemStack newFlaskItem(Spell item){
        ItemStack stack = new ItemStack(ModItems.OIL.get());

        stack.getOrCreateTag().putString("Spell", item.getDescriptionId());
        stack.getOrCreateTag().putInt("Color", item.getColor());
        return stack;
    }


    public default MobEffect getFlaskEffect(){
        return effect;
    }
    public static  boolean triggerOnOrUse(String spellname, Level level, Player player, LivingEntity target, float modifier, ItemStack stack){
        if(stack.getOrCreateTag().getCompound("Oils") != null){
            if(stack.getOrCreateTag().getCompound("Oils").getInt(spellname) > 0) {
                for (RegistryObject<Item> spell3 : ModItems.ITEMS.getEntries()) {
                    if (spell3.get() instanceof Spell spell) {
                        if (Objects.equals(spell.getDescriptionId(), spellname)) {
                            if (spell.isTargeted() && spell.isTriggerable()) {
                                ((Spell) spell).triggeron(level, player, target, 1);
                            } else {
                                ((Spell) spell).use(level, player, InteractionHand.MAIN_HAND);

                            }

                        }

                    }
                }
            }

            else{
                stack.getOrCreateTag().getCompound("Oils").remove(spellname);
            }
        }
        return true;
    }

    public static  boolean triggerOrTriggeron(String spellname, Level level, Player player, LivingEntity target, float modifier, ItemStack stack, boolean trigger){
        String triggeroroil = "Oils";
        if(trigger){
            triggeroroil = "Triggers";
        }
        if(stack.getOrCreateTag().getCompound(triggeroroil) != null){
            if(stack.getOrCreateTag().getCompound(triggeroroil).getInt(spellname) > 0) {
                for (RegistryObject<Item> spell3 : ModItems.ITEMS.getEntries()) {
                    if (spell3.get() instanceof Spell spell && spell.isTriggerable()) {
                        if (Objects.equals(spell.getDescriptionId(), spellname)) {
                            if (spell.isTargeted()) {
                                ((Spell) spell).triggeron(level, player, target, 1);
                            } else {
                                ((Spell) spell).trigger(level, player, 1);

                            }
                                               }
                    }
                }
            }
            else{
                stack.getOrCreateTag().getCompound(triggeroroil).remove(spellname);
            }
        }
        return true;
    }
    public static boolean useSpell(String spellname, Level level, Player player, InteractionHand hand, ItemStack stack){
        boolean worked = true;
        if(stack.getOrCreateTag().getCompound("Oils") != null){
            if(stack.getOrCreateTag().getCompound("Oils").getInt(spellname) > 0) {
                for (RegistryObject<Item> spell3 : ModItems.ITEMS.getEntries()) {
                    if (spell3.get() instanceof Spell spell) {
                        if (Objects.equals(spell.getDescriptionId(), spellname)) {
                            if(spell.canFail()){
                                worked = spell.failState(level,player,hand);
                            }
                            else {
                                ((Spell) spell).use(level, player, hand);
                            }

                        }

                    }

                }
            }
            else{
                stack.getOrCreateTag().getCompound("Oils").remove(spellname);
            }
        }
        return worked;
    }
    public default void applyFlask(Player player, @Nullable InteractionHand hand, ItemStack stack, ItemStack sword, boolean trigger){
        CompoundTag nbt = sword.getOrCreateTag();
        CompoundTag nbtadd;
        if(!trigger) {
             nbtadd = nbt.getCompound("Oils");
            if (nbt.getCompound("Oils").getInt(getSpellItem(stack)) > 0) {
                return;
            }

            nbtadd.putInt(getSpellItem(stack),8);
            nbt.put("Oils", nbtadd);
        }
        if(trigger){

            nbtadd = nbt.getCompound("Triggers");
            if (nbt.getCompound("Triggers").getInt(getSpellItem(stack)) > 0) {
                return;
            }
            nbtadd.putInt(getSpellItem(stack),8);
            nbt.put("Triggers", nbtadd);
        }
        stack.hurtAndBreak(1, player, player1 -> {
        });
    }


}
