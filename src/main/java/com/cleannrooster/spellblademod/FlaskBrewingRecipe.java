package com.cleannrooster.spellblademod;

import com.cleannrooster.spellblademod.items.Flask;
import com.cleannrooster.spellblademod.items.FlaskItem;
import com.cleannrooster.spellblademod.items.Spell;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class FlaskBrewingRecipe implements IBrewingRecipe {
    private final Item input;
    private final Item ingredient;
    private final Spell output;

    public FlaskBrewingRecipe(Item input, Item ingredient, Spell spell) {
        this.input = input;
        this.ingredient = ingredient;
        this.output = spell;
    }

    @Override
    public boolean isInput(ItemStack input) {
        if(!(input.getItem() instanceof FlaskItem)) {
            return input.getItem() == this.input;
        }
        else{
            return Flask.getSpellItem(input).equals(this.input.getDescriptionId());
        }
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return ingredient.getItem() == this.ingredient;
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        if(!this.isInput(input) || !this.isIngredient(ingredient)) {
            return ItemStack.EMPTY;
        }

        return Flask.newFlaskItem(this.output);
    }
}