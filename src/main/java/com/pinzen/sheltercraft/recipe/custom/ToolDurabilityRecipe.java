package com.pinzen.sheltercraft.recipe.custom;

import com.pinzen.sheltercraft.recipe.ModRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class ToolDurabilityRecipe implements CraftingRecipe {
    protected final Ingredient ingredient;
    protected final Ingredient tool;
    protected final ItemStack result;
    protected final int damage;

    public ToolDurabilityRecipe(Ingredient ingredient, Ingredient tool, ItemStack result, int damage) {
        this.ingredient = ingredient;
        this.tool = tool;
        this.result = result;
        this.damage = damage;
    }

    @Override
    public boolean matches(CraftingInput input, Level pLevel) {
        boolean foundIngredient = false;
        boolean foundTool = false;

        for (ItemStack itemStack : input.items()) {
            if (!itemStack.isEmpty()) {
                if (ingredient.test(itemStack)) {
                    if (foundIngredient) return false; // only one allowed
                    foundIngredient = true;
                } else if (tool.test(itemStack)) {
                    if (foundTool) return false; // only one allowed
                    foundTool = true;
                } else {
                    return false;
                }
            }
        }
        return foundIngredient && foundTool;
    }

    @Override
    public ItemStack assemble(CraftingInput pInput, HolderLookup.Provider pRegistries) {
        return result.copy();
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput pInput) {
        NonNullList<ItemStack> remain = NonNullList.withSize(pInput.size(), ItemStack.EMPTY);

        for (int i = 0; i < pInput.size(); i++) {
            ItemStack itemStack = pInput.getItem(i);

            if (tool.test(itemStack)) {
                ItemStack damagedTool = itemStack.copyWithCount(1);
                damagedTool.setDamageValue(damagedTool.getDamageValue() + damage);
                if (damagedTool.getDamageValue() < damagedTool.getMaxDamage()) {
                    remain.set(i, damagedTool);
                }
            } else if (ingredient.test(itemStack)) {
                // Respect crafting remaining item (e.g. bucket → empty bucket)
                if (itemStack.getCraftingRemainder() != null) {
                    remain.set(i, itemStack.getCraftingRemainder());
                }
            }
        }
        return remain;
    }


    @Override
    public RecipeSerializer<? extends CraftingRecipe> getSerializer() {
        return ModRecipes.TOOL_DURABILITY_SERIALIZER.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public CraftingBookCategory category() {
        return CraftingBookCategory.MISC;
    }
}
