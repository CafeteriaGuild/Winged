package net.adriantodt.winged.item

import net.adriantodt.winged.*
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class FuelPelletItem(
    settings: Settings,
    private val std: Int,
    private val fast: Int,
    private val slow: Int,
    private val result: Item
) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        if (user.isSneaking && user.inventory is WingedPlayerInventory) {
            val inv = user.inventory as WingedPlayerInventory
            if (
                inv.hasAtLeast(STANDARD_FUEL_PELLET, std) &&
                inv.hasAtLeast(SHORT_BURST_FUEL_PELLET, fast) &&
                inv.hasAtLeast(LONG_DURATION_FUEL_PELLET, slow) &&
                inv.takeOneAndReplace(EMPTY_BOOSTER, result)
            ) {
                inv.takeFromInventory(STANDARD_FUEL_PELLET, std)
                inv.takeFromInventory(SHORT_BURST_FUEL_PELLET, fast)
                inv.takeFromInventory(LONG_DURATION_FUEL_PELLET, slow)
            }
        }
        return TypedActionResult.pass(user.getStackInHand(hand))
    }

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack?, world: World?, tooltip: MutableList<Text?>, ctx: TooltipContext?) {
        tooltip += TranslatableText("$translationKey.description")
        tooltip += TranslatableText("tooltip.winged.craft_booster1")
        tooltip += TranslatableText("tooltip.winged.craft_booster2", TranslatableText(result.translationKey))
    }
}