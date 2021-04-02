package net.adriantodt.winged.mixin;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(ShapedRecipe.class)
public interface ShapedRecipeAccessor {
    @Invoker("getComponents")
    static Map<String, Ingredient> winged_getComponents(JsonObject json) {
        throw new AssertionError();
    }

    @Invoker("combinePattern")
    static String[] winged_combinePattern(String... lines) {
        throw new AssertionError();
    }

    @Invoker("getPattern")
    static String[] winged_getPattern(JsonArray json) {
        throw new AssertionError();
    }

    @Invoker("getIngredients")
    static DefaultedList<Ingredient> winged_getIngredients(String[] pattern, Map<String, Ingredient> key, int width, int height) {
        throw new AssertionError();
    }
}
