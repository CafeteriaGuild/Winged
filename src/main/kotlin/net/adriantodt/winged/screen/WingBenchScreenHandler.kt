package net.adriantodt.winged.screen

import net.adriantodt.winged.Winged
import net.adriantodt.winged.identifier
import net.adriantodt.winged.recipe.WingRecipe
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.CraftingResultInventory
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.Slot
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.world.World

class WingBenchScreenHandler(syncId: Int, private val playerInventory: PlayerInventory) : ScreenHandler(Winged.wingbenchType, syncId) {

    private val input: WingBenchCraftingInventory = WingBenchCraftingInventory(this)
    private val result: CraftingResultInventory = CraftingResultInventory()

    init {

        addSlot(WingBenchCraftingResultSlot(playerInventory.player, input, result, 0, 80, 99- 20))

        addSlot(Slot(input, 0, 55, 24 - 24))
        addSlot(Slot(input, 1, 80, 24 - 24))
        addSlot(Slot(input, 2, 105, 24 - 24))
        addSlot(Slot(input, 3, 42, 60 - 24))
        addSlot(Slot(input, 4, 119, 60 - 24))
        addSlot(Slot(input, 5, 26, 96 - 24))
        addSlot(Slot(input, 6, 134, 96 - 24))

        addPlayerSlots()

    }

    override fun canUse(player: PlayerEntity?): Boolean = true

    private fun updateResult(syncId: Int, world: World, player: PlayerEntity, craftingInventory: WingBenchCraftingInventory, resultInventory: CraftingResultInventory) {
        if (!world.isClient) {
            val serverPlayerEntity = player as ServerPlayerEntity
            var itemStack = ItemStack.EMPTY
            val optional = world.server!!.recipeManager.getFirstMatch(WingRecipe.TYPE, craftingInventory, world)
            if (optional.isPresent) {
                val craftingRecipe = optional.get()
                if (resultInventory.shouldCraftRecipe(world, serverPlayerEntity, craftingRecipe)) {
                    itemStack = craftingRecipe.craft(craftingInventory)
                }
            }
            resultInventory.setStack(0, itemStack)
            serverPlayerEntity.networkHandler.sendPacket(ScreenHandlerSlotUpdateS2CPacket(syncId, 0, itemStack))
        }
    }

    override fun onContentChanged(inventory: Inventory?) {
        updateResult(syncId, playerInventory.player.world, playerInventory.player, input, result)
    }

    override fun close(player: PlayerEntity) {
        super.close(player)
        dropInventory(player, player.world, input)
    }

    private fun addPlayerSlots() {
        for (x in 0 until 9) {
            for (y in 0 until 3) {
                addSlot(Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18+18+13))
            }
        }

        for (x in 0 until 9) {
            addSlot(Slot(playerInventory, x, 8 + x * 18, 142+18+13))
        }
    }

    companion object {
        val ID = identifier("wingbench_screen")
    }
}