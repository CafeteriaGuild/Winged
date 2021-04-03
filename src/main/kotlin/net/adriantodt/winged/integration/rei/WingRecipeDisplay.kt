package net.adriantodt.winged.integration.rei

import me.shedaniel.rei.api.EntryStack
import me.shedaniel.rei.api.TransferRecipeDisplay
import me.shedaniel.rei.server.ContainerInfo
import net.adriantodt.winged.recipe.WingRecipe
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.util.Identifier
import java.util.*

class WingRecipeDisplay(val recipe: WingRecipe, private val category: Identifier) : TransferRecipeDisplay {

    private val outputPreview: MutableList<EntryStack> = mutableListOf()
    private val output: MutableList<EntryStack> = mutableListOf()
    private val input: MutableList<MutableList<EntryStack>> = mutableListOf()

    init {
        input.addAll(recipe.inputs.map { ingredient ->
            ingredient.matchingStacksClient.map { stack -> EntryStack.create(ItemStack(stack.item, 1)) }.toMutableList()
        })
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

    override fun getResultingEntries(): MutableList<MutableList<EntryStack>> = output.map { mutableListOf(it) }.toMutableList()

    override fun getWidth(): Int = 3

    override fun getHeight(): Int = 3
}