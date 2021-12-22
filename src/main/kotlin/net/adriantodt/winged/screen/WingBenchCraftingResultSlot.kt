package net.adriantodt.winged.screen

import net.adriantodt.winged.recipe.WingcraftingRecipe
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.RecipeUnlocker
import net.minecraft.screen.slot.Slot

class WingBenchCraftingResultSlot(
    private val player: PlayerEntity,
    private val input: WingBenchCraftingInventory,
    inventory: Inventory?,
    index: Int,
    x: Int,
    y: Int
) : Slot(inventory, index, x, y) {
    private var amount = 0

    override fun canInsert(stack: ItemStack): Boolean = false

    override fun takeStack(amount: Int): ItemStack {
        if (hasStack())
            this.amount += amount.coerceAtMost(this.stack.count)
        return super.takeStack(amount)
    }

    override fun onCrafted(stack: ItemStack, amount: Int) {
        this.amount += amount
        this.onCrafted(stack)
    }

    override fun onTake(amount: Int) {
        this.amount += amount
    }

    override fun onCrafted(stack: ItemStack) {
        if (amount > 0) {
            stack.onCraft(player.world, player, amount)
        }
        if (inventory is RecipeUnlocker) {
            (inventory as RecipeUnlocker).unlockLastRecipe(player)
        }
        amount = 0
    }

    override fun onTakeItem(player: PlayerEntity, stack: ItemStack): Unit {
        this.onCrafted(stack)
        val remainders = player.world.recipeManager.getRemainingStacks(WingcraftingRecipe.TYPE, input, player.world)
        for (slot in remainders.indices) {
            var itemStack = input.getStack(slot)!!
            val itemStack2 = remainders[slot]
            if (!itemStack.isEmpty) {
                input.removeStack(slot, 1)
                itemStack = input.getStack(slot)!!
            }
            if (!itemStack2.isEmpty) {
                if (itemStack.isEmpty) {
                    input.setStack(slot, itemStack2)
                } else if (ItemStack.areItemsEqualIgnoreDamage(itemStack, itemStack2) && ItemStack.areNbtEqual(itemStack, itemStack2)) {
                    itemStack2.increment(itemStack.count)
                    input.setStack(slot, itemStack2)
                } else if (!this.player.inventory.insertStack(itemStack2)) {
                    this.player.dropItem(itemStack2, false)
                }
            }
        }
    }
}
