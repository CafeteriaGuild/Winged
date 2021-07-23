package net.adriantodt.winged.integration.rei

import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.display.basic.BasicDisplay
import me.shedaniel.rei.api.common.entry.EntryIngredient
import me.shedaniel.rei.api.common.util.EntryIngredients
import net.adriantodt.winged.identifier
import net.adriantodt.winged.recipe.WingcraftingRecipe
import net.minecraft.item.ItemStack

class WingRecipeDisplay(val recipe: WingcraftingRecipe)
    : BasicDisplay(getInputs(recipe), getOutput(recipe)) {

    override fun getCategoryIdentifier(): CategoryIdentifier<*> {
        return CategoryIdentifier.of<WingRecipeDisplay>(identifier("wingcrafting"))
    }
    companion object {
        fun getInputs(recipe: WingcraftingRecipe): List<EntryIngredient> =
            recipe.ingredients.map { EntryIngredients.ofIngredient(it) }

        fun getOutput(recipe: WingcraftingRecipe): List<EntryIngredient> = listOf(EntryIngredients.of(recipe.output))
    }
}
