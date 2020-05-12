package net.adriantodt.winged.item

import net.adriantodt.winged.WingedPlayerInventory
import net.adriantodt.winged.data.WingedDataObject
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


class BoosterItem(settings: Settings, private val data: WingedDataObject.BoosterData) : Item(settings) {
    override fun use(world: World?, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)

        if (user.isFallFlying) {
            (user.inventory as WingedPlayerInventory).ensureOnlyActiveBooster(null)
            return TypedActionResult.success(data.toActiveBooster(stack))
        }
        return TypedActionResult.fail(stack)
    }

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, ctx: TooltipContext) {
        tooltip += TranslatableText("$translationKey.description")
        tooltip += TranslatableText("tooltip.winged.activate_booster")
        if (ctx.isAdvanced) {
            tooltip += TranslatableText("tooltip.winged.time_left", data.secondsLeft(stack))
        }
    }
}