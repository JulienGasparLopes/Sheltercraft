package com.pinzen.sheltercraft.recipe;

import com.pinzen.sheltercraft.Sheltercraft;
import com.pinzen.sheltercraft.recipe.custom.ToolDurabilityRecipe;
import com.pinzen.sheltercraft.recipe.custom.ToolDurabilityRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> REGISTER_RECIPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Sheltercraft.MOD_ID);

    public static final RegistryObject<RecipeSerializer<ToolDurabilityRecipe>> TOOL_DURABILITY_SERIALIZER =
            REGISTER_RECIPES.register("tool_durability", ToolDurabilityRecipeSerializer::new);

    public static void register(BusGroup busGroup) {
        REGISTER_RECIPES.register(busGroup);
    }
}
