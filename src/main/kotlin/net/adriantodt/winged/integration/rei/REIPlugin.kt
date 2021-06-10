package net.adriantodt.winged.integration.rei

import me.shedaniel.rei.api.client.plugins.REIClientPlugin
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry
import me.shedaniel.rei.api.common.util.EntryStacks
import net.adriantodt.winged.Winged
import net.adriantodt.winged.identifier
import net.adriantodt.winged.recipe.WingcraftingRecipe

object REIPlugin : REIClientPlugin {

    private val WINGCRAFTING_RECIPE = identifier("wingcrafting")

    override fun registerCategories(registry: CategoryRegistry) {
        registry.add(
            WingRecipeCategory(
                WINGCRAFTING_RECIPE,
                EntryStacks.of(Winged.wingBenchBlock),
                "winged.category.rei.wingcrafting"
            ))
    }

    override fun registerDisplays(registry: DisplayRegistry) {
        registry.recipeManager.listAllOfType(WingcraftingRecipe.TYPE).forEach { recipe ->
                registry.registerFiller(WingcraftingRecipe::class.java, { r -> recipe is WingcraftingRecipe && recipe.type == r.type }, ::WingRecipeDisplay)
        }
    }
}
