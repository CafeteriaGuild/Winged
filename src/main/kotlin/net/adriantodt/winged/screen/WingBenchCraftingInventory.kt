package net.adriantodt.winged.screen

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.RecipeFinder
import net.minecraft.recipe.RecipeInputProvider
import net.minecraft.screen.ScreenHandler
import net.minecraft.util.collection.DefaultedList

class WingBenchCraftingInventory(private val handler: ScreenHandler) : Inventory, RecipeInputProvider {
    private val stacks: DefaultedList<ItemStack> = DefaultedList.ofSize(8, ItemStack.EMPTY)

    override fun size(): Int = this.stacks.size

    override fun isEmpty(): Boolean = stacks.all { it.isEmpty }

    override fun getStack(slot: Int): ItemStack? = if (slot >= this.size()) ItemStack.EMPTY else this.stacks[slot]

    override fun removeStack(slot: Int): ItemStack? = Inventories.removeStack(this.stacks, slot)

    override fun removeStack(slot: Int, amount: Int): ItemStack? {
        val itemStack = Inventories.splitStack(this.stacks, slot, amount)
        if (!itemStack.isEmpty) {
            this.handler.onContentChanged(this)
        }
        return itemStack
    }

    override fun setStack(slot: Int, stack: ItemStack?) {
        this.stacks[slot] = stack
        this.handler.onContentChanged(this)
    }

    override fun markDirty() {}

    override fun canPlayerUse(player: PlayerEntity?): Boolean {
        return true
    }

    override fun clear() {
        this.stacks.clear()
    }

    override fun provideRecipeInputs(finder: RecipeFinder) {
        stacks.forEach { stack -> finder.addNormalItem(stack) }
    }
}
