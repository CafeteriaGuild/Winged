package net.adriantodt.winged.integration.rei

import me.shedaniel.rei.api.EntryStack
import me.shedaniel.rei.api.RecipeHelper
import me.shedaniel.rei.api.plugins.REIPluginV0
import net.adriantodt.winged.Winged
import net.adriantodt.winged.identifier
import net.adriantodt.winged.recipe.WingcraftingRecipe
import net.minecraft.util.Identifier

object REIPlugin : REIPluginV0 {

    private val WINGCRAFTING_RECIPE = identifier("wingcrafting")

    override fun getPluginIdentifier(): Identifier = identifier("winged_rei")

    override fun registerPluginCategories(recipeHelper: RecipeHelper) {
        recipeHelper.registerCategory(
            WingRecipeCategory(
                WINGCRAFTING_RECIPE,
                EntryStack.create(Winged.wingBenchBlock),
                "winged.category.rei.wingcrafting"
            )
        )
    }

    override fun registerRecipeDisplays(recipeHelper: RecipeHelper?) {
        recipeHelper?.registerRecipes(WINGCRAFTING_RECIPE, WingcraftingRecipe::class.java) {
            WingRecipeDisplay(it, WINGCRAFTING_RECIPE)
        }
    }
}
