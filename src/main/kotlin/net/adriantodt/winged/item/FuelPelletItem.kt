package net.adriantodt.winged.item

import net.adriantodt.winged.WingedPlayerInventory
import net.adriantodt.winged.WingedUtilityItems.emptyBooster
import net.adriantodt.winged.WingedUtilityItems.fastFuelPellet
import net.adriantodt.winged.WingedUtilityItems.slowFuelPellet
import net.adriantodt.winged.WingedUtilityItems.standardFuelPellet
import net.adriantodt.winged.data.WingedData
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

class FuelPelletItem(settings: Settings, private val data: WingedData.FuelPelletData) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        if (user.isSneaking && user.inventory is WingedPlayerInventory) {
            val inv = user.inventory as WingedPlayerInventory
            if (
                inv.count(standardFuelPellet) >= data.standardPellets &&
                inv.count(fastFuelPellet) >= data.fastPellets &&
                inv.count(slowFuelPellet) >= data.slowPellets &&
                inv.takeOneAndReplace(emptyBooster, data.resultItem)
            ) {
                inv.takeFromInventory(standardFuelPellet, data.standardPellets)
                inv.takeFromInventory(fastFuelPellet, data.fastPellets)
                inv.takeFromInventory(slowFuelPellet, data.slowPellets)
            }
        }
        return TypedActionResult.pass(user.getStackInHand(hand))
    }

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack?, world: World?, tooltip: MutableList<Text?>, ctx: TooltipContext?) {
        tooltip.add(TranslatableText("$translationKey.description"))
        tooltip.add(TranslatableText("tooltip.winged.craft_booster1"))
        tooltip.add(TranslatableText("tooltip.winged.craft_booster2", TranslatableText(data.resultItem.translationKey)))
    }
}