package com.pinzen.sheltercraft.recipe.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ToolDurabilityRecipeSerializer implements RecipeSerializer<ToolDurabilityRecipe> {
    public static final MapCodec<ToolDurabilityRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(r -> r.ingredient),
            Ingredient.CODEC.fieldOf("tool").forGetter(r -> r.tool),
            ItemStack.CODEC.fieldOf("result").forGetter(r -> r.result),
            Codec.INT.optionalFieldOf("damage", 1).forGetter(r -> r.damage)
    ).apply(instance, ToolDurabilityRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ToolDurabilityRecipe> STREAM_CODEC =
            StreamCodec.of((buf, recipe) -> {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.ingredient);
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.tool);
                ItemStack.STREAM_CODEC.encode(buf, recipe.result);
                buf.writeVarInt(recipe.damage);
            }, buf -> {
                Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                Ingredient tool = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                ItemStack result = ItemStack.STREAM_CODEC.decode(buf);
                int damage = buf.readVarInt();
                return new ToolDurabilityRecipe(ingredient, tool, result, damage);
            });

    @Override
    public MapCodec<ToolDurabilityRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, ToolDurabilityRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
