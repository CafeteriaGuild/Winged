package net.adriantodt.winged.integration.rei

import me.shedaniel.rei.api.EntryStack
import me.shedaniel.rei.api.RecipeHelper
import me.shedaniel.rei.api.plugins.REIPluginV0
import net.adriantodt.winged.Winged
import net.adriantodt.winged.identifier
import net.adriantodt.winged.recipe.WingRecipe
import net.minecraft.util.Identifier

object REIPlugin : REIPluginV0 {

    val WING_RECIPE = identifier("wingrecipe")

    override fun getPluginIdentifier(): Identifier = identifier("winged_rei")

    override fun registerPluginCategories(recipeHelper: RecipeHelper?) {
        recipeHelper?.registerCategory(
            WingRecipeCategory(
                WING_RECIPE,
                EntryStack.create(Winged.wingBenchBlock),
                "winged.category.rei.wingrecipe"
            )
        )
    }

    override fun registerRecipeDisplays(recipeHelper: RecipeHelper?) {
        recipeHelper?.registerRecipes(WING_RECIPE, WingRecipe::class.java) {
            WingRecipeDisplay(it, WING_RECIPE)
        }
    }
}