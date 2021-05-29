package net.adriantodt.winged.integration.rei

import me.shedaniel.rei.api.EntryStack
import me.shedaniel.rei.api.TransferRecipeDisplay
import me.shedaniel.rei.server.ContainerInfo
import net.adriantodt.winged.recipe.WingcraftingRecipe
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.util.Identifier
import java.util.*

class WingRecipeDisplay(val recipe: WingcraftingRecipe, private val category: Identifier) : TransferRecipeDisplay {

    private val outputPreview: MutableList<EntryStack> = mutableListOf()
    private val output: MutableList<EntryStack> = mutableListOf()
    private val input: MutableList<MutableList<EntryStack>> = mutableListOf()

    init {
        input += recipe.ingredients.map {
            it.matchingStacksClient.mapTo(mutableListOf()) { stack ->
                EntryStack.create(ItemStack(stack.item, 1))
            }
        }
        outputPreview.add(EntryStack.create(recipe.output))
        output.addAll(outputPreview)
    }


    override fun getRecipeCategory(): Identifier = category

    override fun getRecipeLocation(): Optional<Identifier> = Optional.ofNullable(recipe).map { it.id }

    override fun getRequiredEntries(): MutableList<MutableList<EntryStack>> = input

    override fun getOrganisedInputEntries(
        p0: ContainerInfo<ScreenHandler>?,
        p1: ScreenHandler?
    ): MutableList<MutableList<EntryStack>> = input

    override fun getInputEntries(): MutableList<MutableList<EntryStack>> = input

    override fun getResultingEntries(): MutableList<MutableList<EntryStack>> =
        output.map { mutableListOf(it) }.toMutableList()

    override fun getWidth(): Int = 3

    override fun getHeight(): Int = 3
}
