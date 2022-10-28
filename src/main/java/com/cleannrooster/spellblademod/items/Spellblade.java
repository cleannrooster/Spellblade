package com.cleannrooster.spellblademod.items;

import com.cleannrooster.spellblademod.SpellbladeMod;
import com.cleannrooster.spellblademod.StatusEffectsModded;
import com.cleannrooster.spellblademod.enchants.WardTempered;
import com.cleannrooster.spellblademod.manasystem.client.ManaOverlay;
import com.cleannrooster.spellblademod.manasystem.manatick;
import com.cleannrooster.spellblademod.setup.ModSetup;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Stream;

public class Spellblade extends SwordItem{
    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;
    private final float attackSpeed;
    public int tier = 0;
    public int tier1 = 0;
    public Spellblade(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
        this.attackDamage = Math.max(0,(float) (((float)p_43270_ + p_43269_.getAttackDamageBonus())));
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        this.attackSpeed = p_43271_;
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)p_43271_, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }
    public ArrayList<Item> items = new ArrayList<>(0);
    Stream<RegistryObject<Item>> oils;
    Stream<RegistryObject<Item>> items2;

    @Override
    public boolean isValidRepairItem(ItemStack p_43311_, ItemStack p_43312_) {
        if(p_43312_.getItem() instanceof WardingMail){
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public boolean overrideOtherStackedOnMe(ItemStack thisStack, ItemStack onStack, Slot slot, ClickAction clickAction, Player player, SlotAccess slotAccess) {
        if(clickAction == ClickAction.SECONDARY){
            if (onStack.getItem() instanceof Flask flask && (thisStack.getItem() instanceof Spellblade)) {
                ItemStack itemStack = thisStack;
                CompoundTag compoundtag = new CompoundTag();

                flask.applyFlask(player,null,onStack,itemStack, false);

                CompoundTag nbt = itemStack.getOrCreateTag();
                CompoundTag autoUse = nbt.getCompound("AutoUse");

                if (nbt.getCompound("AutoUse").contains(onStack.getOrCreateTag().getString("Spell"))) {
                    nbt.getCompound("AutoUse").remove(onStack.getOrCreateTag().getString("Spell"));

                } else {
                    nbt.getCompound("AutoUse").putBoolean(onStack.getOrCreateTag().getString("Spell"), true);

                }
                if(!nbt.contains("AutoUse")){
                    autoUse.putBoolean(onStack.getOrCreateTag().getString("Spell"), true);
                    nbt.put("AutoUse",autoUse);
                }
                return true;
            }
            else {
                CompoundTag nbt = thisStack.getOrCreateTag();
                if (thisStack.hasTag()) {
                    if (thisStack.getTag().get("OnHit") != null) {
                        nbt = thisStack.getTag();
                        nbt.remove("OnHit");
                        return true;
                    } else {
                        nbt = thisStack.getOrCreateTag();
                        nbt.putInt("OnHit", 1);
                        return true;
                    }

                } else {
                    nbt = thisStack.getOrCreateTag();
                    nbt.putInt("OnHit", 1);
                    return true;

                }
            }


        }
        if(clickAction == ClickAction.PRIMARY) {
            if (onStack.getItem() instanceof Flask flask && (thisStack.getItem() instanceof Spellblade)) {
                ItemStack itemStack = thisStack;
                CompoundTag compoundtag = new CompoundTag();
                for (RegistryObject<Item> spell3 : ModItems.ITEMS.getEntries()) {
                    if (spell3.get() instanceof Spell spell && !spell.isTriggerable()) {
                        if (Objects.equals(spell.getDescriptionId(), Flask.getSpellItem(onStack))) {
                            return false;
                        }
                    }
                }
                flask.applyFlask(player,null,onStack,itemStack,true);

                CompoundTag nbt = itemStack.getOrCreateTag();
                CompoundTag autoUse = nbt.getCompound("AutoTrigger");


                    if (nbt.getCompound("AutoTrigger").contains(onStack.getOrCreateTag().getString("Spell"))) {
                        nbt.getCompound("AutoTrigger").remove(onStack.getOrCreateTag().getString("Spell"));

                    } else {
                        nbt.getCompound("AutoTrigger").putBoolean(onStack.getOrCreateTag().getString("Spell"), true);

                    }
                    if (!nbt.contains("AutoTrigger")) {
                        autoUse.putBoolean(onStack.getOrCreateTag().getString("Spell"), true);
                        nbt.put("AutoTrigger", autoUse);
                    }
                    nbt.put("AutoTrigger", autoUse);

                return true;
            }
        }
                return false;
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", Math.max(0,(double)this.attackDamage+ (int)((stack.getOrCreateTag().getInt("ward")))), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)this.attackSpeed, AttributeModifier.Operation.ADDITION));
        if(slot == EquipmentSlot.MAINHAND) {
            return builder.build();
        }
        else{
            return super.getAttributeModifiers(slot, stack);
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        Player playa = (Player) player;
        if (player.getEffect((StatusEffectsModded.WARDLOCKED.get())) == null) {

            player.addEffect(new MobEffectInstance(StatusEffectsModded.WARDING.get(), 30, 1 + EnchantmentHelper.getItemEnchantmentLevel(ModItems.WARDTEMPERED.get(), stack)));
        }

        boolean flag = false;
        int ii = 0;
        for (int i = 0; i <= playa.getInventory().getContainerSize(); i++) {
            if (playa.getInventory().getItem(i).getItem() instanceof Guard) {
                if (playa.getInventory().getItem(i).hasTag()) {
                    if (playa.getInventory().getItem(i).getTag().getInt("Triggerable") == 1) {
                        ((Guard) playa.getInventory().getItem(i).getItem()).guardtick(playa, player.level,i , count);
                        ii++;
                    }
                }
            }
            if (ii > 0) {
                player.addEffect(new MobEffectInstance(StatusEffectsModded.SPELLWEAVING.get(), 30, ii - 1));
            }
        }
        if (player.hasEffect(StatusEffectsModded.SPELLWEAVING.get()) && ((Player)player).getAttributes().getBaseValue(manatick.WARD) < 0 && count % 10 ==5){
            player.hurt(DamageSource.MAGIC,player.getEffect(StatusEffectsModded.SPELLWEAVING.get()).getAmplifier());
        }
        player.addEffect(new MobEffectInstance(StatusEffectsModded.WARDING.get(), 80, 1+EnchantmentHelper.getEnchantmentLevel(SpellbladeMod.wardTempered, player)));

    }
    /*public void releaseUsing(ItemStack p_43394_, Level p_43395_, LivingEntity p_43396_, int p_43397_) {
        if ( p_43396_.getEffect((StatusEffectsModded.WARDLOCKED.get())) == null) {
            p_43396_.addEffect(new MobEffectInstance(StatusEffectsModded.WARDLOCKED.get(), 30, 0));
        }
    }*/

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemstack, Player player, LivingEntity p_41400_, InteractionHand p_41401_) {
        if(!player.getCooldowns().isOnCooldown(this)) {

            if (player.isShiftKeyDown()) {
                super.interactLivingEntity(itemstack, player, p_41400_, p_41401_);
            }
            this.items = new ArrayList<>(0);

            for (RegistryObject<Item> item : ModItems.ITEMS.getEntries().stream().toList()) {
                if (item.get() instanceof Flask) {
                    this.items.add(item.get());
                }
            }
            if (!player.getMainHandItem().isEdible()) {

                CompoundTag tag = itemstack.getOrCreateTag();
                if (tag.contains("Oils")) {
                    for (int ii = 0; ii < tag.getCompound("Oils").getAllKeys().size(); ii++) {
                        String spell = tag.getCompound("Oils").getAllKeys().stream().toList().get(ii);
                        if (tag.getCompound("Oils").getInt(spell) > 0) {

                            Flask.triggerOnOrUse(spell, player.getLevel(), player, p_41400_, 1, itemstack);
                            tag.getCompound("Oils").putInt(spell, tag.getCompound("Oils").getInt(spell) - 1);
                        } else if (!tag.getCompound("AutoUse").contains(spell)) {
                            tag.getCompound("Oils").remove(spell);
                        }

                        player.getCooldowns().addCooldown(this, 20);
                    }
                }
                for (ItemStack item : player.getInventory().items) {
                    if (item.getItem() instanceof Flask flask && tag.contains("AutoUse") && tag.getCompound("AutoUse").contains(Flask.getSpellItem(item))) {
                        for (RegistryObject<Item> spell3 : ModItems.ITEMS.getEntries()) {
                            if (spell3.get() instanceof Spell spell) {
                                if (Objects.equals(spell.getDescriptionId(), Flask.getSpellItem(item))) {
                                    flask.applyFlask(player, null, item, itemstack, false);

                                }
                            }
                        }
                    }
                }
            }
        }
        return InteractionResult.sidedSuccess(false);
    }

    public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) {
        if (p_41406_ instanceof Player){
            Player player = (Player) p_41406_;
            CompoundTag nbt = new CompoundTag();
            ItemStack stack = p_41404_;
            double ward = ((Player)player).getAttributes().getBaseValue(manatick.WARD);
            if (ward >= 39 && ward < 79){
                nbt = stack.getOrCreateTag();
                tier1 = 1;

            }
            if (ward >= 79 && ward < 119){
                nbt = stack.getOrCreateTag();
                tier1 = 2;
            }
             if (ward >= 119 && ward < 159){
                 nbt = stack.getOrCreateTag();
                 tier1 = 3;
            }
            if (ward >= 159){
                nbt = stack.getOrCreateTag();
                tier1 = 4;
            }
            if (ward < 39)
            {
                nbt = stack.getOrCreateTag();
                tier1 = 0;
            }
            tier = tier1;
            nbt.putInt("CustomModelData", tier1);
            nbt.putInt("ward", (int) ((ward+1)/40));
        }

    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {

        return super.onLeftClickEntity(stack, player, entity);
    }

    public boolean hurtEnemy(ItemStack itemstack, LivingEntity living, LivingEntity p_41433_) {
        if (itemstack.hasTag()) {
            if (itemstack.getTag().get("OnHit") != null) {
                if (p_41433_ instanceof Player player) {
                    CompoundTag tag = itemstack.getOrCreateTag();

                    if(tag.contains("Triggers")){
                        for(int ii  = 0; ii < tag.getCompound("Triggers").getAllKeys().size(); ii++){
                            String spell = tag.getCompound("Triggers").getAllKeys().stream().toList().get(ii);
                            if (tag.getCompound("Triggers").getInt(spell) > 0) {

                                Flask.triggerOrTriggeron(spell, player.getLevel(), player, living, 1, itemstack,true);
                                tag.getCompound("Triggers").putInt(spell, tag.getCompound("Triggers").getInt(spell) - 1);
                            }
                            else if(!tag.getCompound("AutoTrigger").contains(spell)){
                                tag.getCompound("Triggers").remove(spell);
                            }

                        }
                    }
                    for(ItemStack item : player.getInventory().items){
                        if(item.getItem() instanceof Flask flask && tag.contains("AutoTrigger") && tag.getCompound("AutoTrigger").contains(Flask.getSpellItem(item))){
                            for (RegistryObject<Item> spell3 : ModItems.ITEMS.getEntries()) {
                                if (spell3.get() instanceof Spell spell) {
                                    if (Objects.equals(spell.getDescriptionId(), Flask.getSpellItem(item))) {
                                        if(spell.isTriggerable()){
                                            flask.applyFlask(player, null, item, itemstack, true);
                                        }
                                    }
                                }
                            }                        }
                    }
                }
            }
        }
        p_41433_.addEffect(new MobEffectInstance(StatusEffectsModded.WARDING.get(), 80, 1 + EnchantmentHelper.getEnchantmentLevel(SpellbladeMod.wardTempered, p_41433_)));

        super.hurtEnemy(itemstack,living,p_41433_);
        return true;
    }
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.BOW;
    }
    public int getUseDuration(ItemStack p_43419_) {
        return 72000;
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        TextComponent text = new TextComponent("Trigger Spells On Hit");
        TextComponent text2 = new TextComponent("Right click to toggle On Hit Mode");
            if (p_41421_.getOrCreateTag().get("OnHit") != null) {
                p_41423_.add(text);
            }
            else{
                p_41423_.add(text2);
            }
            if(p_41421_.getOrCreateTag().contains("Oils")){
                Set<String> keys = p_41421_.getOrCreateTag().getCompound("Oils").getAllKeys();
                for(String key : keys){
                    if(p_41421_.getOrCreateTag().getCompound("Oils").contains(key)) {
                        MutableComponent mutablecomponent1 = new TranslatableComponent("Casting");
                        mutablecomponent1.append(": ");

                        MutableComponent mutablecomponent = new TranslatableComponent(key);


                        if (p_41421_.getOrCreateTag().contains("AutoUse") && p_41421_.getOrCreateTag().getCompound("AutoUse").contains(key)) {
                            mutablecomponent.append(" ").append(new TranslatableComponent("Auto"));
                        } else {
                            mutablecomponent.append(" ").append(new TranslatableComponent(/*"enchantment.level." +*/ String.valueOf(p_41421_.getOrCreateTag().getCompound("Oils").getInt(key))));
                        }
                        mutablecomponent1.append(mutablecomponent);
                        p_41423_.add(mutablecomponent1);
                    }
                }


            }
        if(p_41421_.getOrCreateTag().contains("Triggers")){
            Set<String> keys = p_41421_.getOrCreateTag().getCompound("Triggers").getAllKeys();
            for(String key : keys){
                if(p_41421_.getOrCreateTag().getCompound("Triggers").contains(key)) {
                    MutableComponent mutablecomponent1 = new TranslatableComponent("Triggering");
                    mutablecomponent1.append(": ");
                    MutableComponent mutablecomponent = new TranslatableComponent(key);


                    if (p_41421_.getOrCreateTag().contains("AutoTrigger") && p_41421_.getOrCreateTag().getCompound("AutoTrigger").contains(key)) {
                        mutablecomponent.append(" ").append(new TranslatableComponent("Auto"));
                    } else {
                        mutablecomponent.append(" ").append(new TranslatableComponent(/*"enchantment.level." +*/ String.valueOf(p_41421_.getOrCreateTag().getCompound("Triggers").getInt(key))));
                    }
                    mutablecomponent1.append(mutablecomponent);
                    p_41423_.add(mutablecomponent1);
                }
            }


        }

        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
    }


    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        if(p_41433_.getMainHandItem().getItem() instanceof Flask flask ) {
            flask.applyFlask(p_41433_,InteractionHand.MAIN_HAND,p_41433_.getMainHandItem(),p_41433_.getItemInHand(p_41434_),false);
            return InteractionResultHolder.success(p_41433_.getItemInHand(p_41434_));

        }
        if(p_41433_.getOffhandItem().getItem() instanceof Flask flask ) {
            flask.applyFlask(p_41433_,InteractionHand.OFF_HAND,p_41433_.getOffhandItem(),p_41433_.getItemInHand(p_41434_),false);
            return InteractionResultHolder.success(p_41433_.getItemInHand(p_41434_));

        }
        ItemStack itemstack = p_41433_.getItemInHand(p_41434_);
        if (p_41433_.isShiftKeyDown() /*&& p_41433_.getOffhandItem().getItem() instanceof Flask flask*/) {
            //flask.applyFlask(p_41433_, InteractionHand.OFF_HAND, p_41433_.getOffhandItem());
            p_41433_.startUsingItem(p_41434_);
            int ii = 0;
            for (int i = 0; i <= p_41433_.getInventory().getContainerSize(); i++) {
                if (p_41433_.getInventory().getItem(i).getItem() instanceof Guard) {
                    if (p_41433_.getInventory().getItem(i).hasTag()) {
                        if (p_41433_.getInventory().getItem(i).getTag().getInt("Triggerable") == 1) {
                            ((Guard) p_41433_.getInventory().getItem(i).getItem()).guardstart(p_41433_, p_41433_.level, i);
                            ii++;
                        }
                    }
                }
                if (ii > 0) {
                    p_41433_.addEffect(new MobEffectInstance(StatusEffectsModded.SPELLWEAVING.get(), 30, ii - 1));
                }
            }
            return InteractionResultHolder.sidedSuccess(itemstack,false);

        }
        else {
            if (!p_41433_.getMainHandItem().isEdible()) {
                CompoundTag tag = itemstack.getOrCreateTag();
                for (ItemStack item : p_41433_.getInventory().items) {
                    if (item.getItem() instanceof Flask flask && tag.contains("AutoUse") && tag.getCompound("AutoUse").contains(Flask.getSpellItem(item))) {
                        flask.applyFlask(p_41433_, null, item, itemstack, false);
                    }
                }
                if (tag.contains("Oils")) {
                    for (int ii = 0; ii < tag.getCompound("Oils").getAllKeys().size(); ii++) {
                        boolean worked = true;
                        String spell = tag.getCompound("Oils").getAllKeys().stream().toList().get(ii);
                        if (tag.getCompound("Oils").getInt(spell) > 0) {

                            worked = Flask.useSpell(spell, p_41432_, p_41433_, p_41434_, itemstack);
                            if (worked) {
                                tag.getCompound("Oils").putInt(spell, tag.getCompound("Oils").getInt(spell) - 1);
                            }
                        } else if (!tag.getCompound("AutoUse").contains(spell)) {
                            tag.getCompound("Oils").remove(spell);
                        }
                        if (worked) {
                            p_41433_.getCooldowns().addCooldown(this, 20);
                        }
                    }
                }

                for (ItemStack item : p_41433_.getInventory().items) {
                    if (item.getItem() instanceof Flask flask && tag.contains("AutoUse") && tag.getCompound("AutoUse").contains(Flask.getSpellItem(item))) {
                        flask.applyFlask(p_41433_, null, item, itemstack, false);
                    }
                }


            }
        }
        return super.use(p_41432_,p_41433_,p_41434_);
    }



    public static boolean isSpellblade(Item item) {
        if (item instanceof Spellblade){
            return true;
        }
        else{
            return false;
        }
    }
}
