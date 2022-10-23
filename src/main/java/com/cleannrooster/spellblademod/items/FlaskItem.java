package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.SpellbladeMod;
import com.cleannrooster.spellblademod.manasystem.network.RetrieveItem;
import com.cleannrooster.spellblademod.setup.Messages;
import com.google.common.collect.Maps;
import io.netty.buffer.Unpooled;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FlaskItem extends Item implements Flask{
    public int color;

    public FlaskItem( Item.Properties p_43210_) {
        super(p_43210_);

    }

    @Override
    public ItemStack getDefaultInstance() {
        return Flask.newFlaskItem((Spell) ModItems.FLUXITEM.get());
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        if(p_41433_.getMainHandItem().getItem() instanceof Spellblade || p_41433_.getOffhandItem().getItem() instanceof Spellblade ) {
            if(p_41434_ == InteractionHand.MAIN_HAND) {
                applyFlask(p_41433_, p_41434_, p_41433_.getItemInHand(p_41434_), p_41433_.getItemInHand(InteractionHand.OFF_HAND), false);
            }
            else{
                applyFlask(p_41433_, p_41434_, p_41433_.getItemInHand(p_41434_), p_41433_.getItemInHand(InteractionHand.MAIN_HAND), false);

            }
            return InteractionResultHolder.success(p_41433_.getItemInHand(p_41434_));
        }
        if(EnchantmentHelper.getEnchantmentLevel(SpellbladeMod.spellproxy,p_41433_)>0){
            ItemStack itemstack = new ItemStack(ModItems.SPELLBLADE.get());
            if(p_41433_.getPersistentData().get("spellproxy") != null) {
                if (!p_41433_.getPersistentData().getCompound("spellproxy").equals(new CompoundTag())) {
                    itemstack = ItemStack.of(p_41433_.getPersistentData().getCompound("spellproxy"));
                }
            }
            CompoundTag compoundtag = new CompoundTag();
            itemstack.save(compoundtag);
            applyFlask(p_41433_, p_41434_, p_41433_.getItemInHand(p_41434_), itemstack, false);
            itemstack.save(compoundtag);
            p_41433_.getPersistentData().put("spellproxy", compoundtag);
            return InteractionResultHolder.success(p_41433_.getItemInHand(p_41434_));

        }
        return InteractionResultHolder.fail(p_41433_.getItemInHand(p_41434_));
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
    /*@Override
    public boolean overrideOtherStackedOnMe(ItemStack thisStack, ItemStack onStack, Slot slot, ClickAction clickAction, Player player, SlotAccess slotAccess) {
        if(clickAction == ClickAction.SECONDARY){
            if (thisStack.hasTag()) {
                if (thisStack.getTag().get("Triggerable") != null) {
                    CompoundTag nbt = thisStack.getTag();
                    nbt.remove("Triggerable");
                    return true;
                } else {
                    CompoundTag nbt = thisStack.getOrCreateTag();
                    nbt.putInt("Triggerable", 1);
                    return true;

                }

            } else {
                CompoundTag nbt = thisStack.getOrCreateTag();
                nbt.putInt("Triggerable", 1);
                return true;

            }
        }
        return false;
    }*/
    @Override
    public boolean overrideStackedOnOther(ItemStack thisStack, Slot slot, ClickAction clickAction, Player player) {
        if (  thisStack.getItem() instanceof FlaskItem flaskItem && clickAction == ClickAction.PRIMARY && ( EnchantmentHelper.getEnchantments(slot.getItem()).containsKey(SpellbladeMod.spellproxy))) {
            ItemStack slotItem = slot.getItem();
            CompoundTag compoundtag = new CompoundTag();
            for (RegistryObject<Item> spell3 : ModItems.ITEMS.getEntries()) {
                if (spell3.get() instanceof Spell spell && !spell.isTriggerable()) {
                    if (Objects.equals(spell.getDescriptionId(), Flask.getSpellItem(thisStack))) {
                        return false;
                    }
                }
            }
            /*ItemStack itemStack = ItemStack.of(player.getPersistentData().getCompound("spellproxy"));
            itemStack.save(compoundtag);

            ((FlaskItem)thisStack.getItem()).applyFlask(player,null,thisStack,itemStack);

            CompoundTag nbt = itemStack.getOrCreateTag();
            System.out.println(nbt);
            CompoundTag autoUse = nbt.getCompound("AutoUse");

            if (nbt.getCompound("AutoUse").contains(thisStack.getOrCreateTag().getString("Spell"))) {
                nbt.getCompound("AutoUse").remove(thisStack.getOrCreateTag().getString("Spell"));

            } else {
                nbt.getCompound("AutoUse").putBoolean(thisStack.getOrCreateTag().getString("Spell"), true);

            }
             if(!nbt.contains("AutoUse")){
                 autoUse.putBoolean(thisStack.getOrCreateTag().getString("Spell"), true);
                nbt.put("AutoUse",autoUse);
            }
            itemStack.save(compoundtag);
            CompoundTag tag = new CompoundTag();
            tag.put("spellproxy", compoundtag);
            player.getPersistentData().put("spellproxy",compoundtag);
            slotItem.getOrCreateTag().put("AutoUse",itemStack.getOrCreateTag().getCompound("AutoUse"));
            slotItem.getOrCreateTag().put("Oils",itemStack.getOrCreateTag().getCompound("Oils"));
*/
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeItem(thisStack);
            buf.writeItem(slotItem);
            buf.writeBoolean(true);

            Messages.sendToServer(new RetrieveItem(buf));

            return true;
        }
            return false;
    }
    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        Component text = new TranslatableComponent("Spellblade or Spell Proxy to use");
        MutableComponent text2 = new TranslatableComponent(p_41421_.getOrCreateTag().getString("Spell"));
        Component text3 = new TranslatableComponent("Drag to and Left/Right Click on a ");
        Component text4 =  new TranslatableComponent("Left: Trigger, Right: Cast");
        p_41423_.add(text2);
        p_41423_.add(text3);
        p_41423_.add(text);
        p_41423_.add(text4);
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
    }

    public int getUseDuration(ItemStack p_43001_) {
        return 32;
    }

    public UseAnim getUseAnimation(ItemStack p_42997_) {
        return UseAnim.DRINK;
    }


    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }


    public void fillItemCategory(CreativeModeTab p_42981_, NonNullList<ItemStack> p_42982_) {
        if (this.allowdedIn(p_42981_)) {
            for(RegistryObject<Item> itemRegistryObject : ModItems.ITEMS.getEntries())
                if(itemRegistryObject.get() instanceof Spell spell) {
                    p_42982_.add(Flask.newFlaskItem(spell));
                }
        }
    }
}
