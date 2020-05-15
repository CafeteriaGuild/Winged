package net.adriantodt.winged.item

import net.adriantodt.winged.Winged.data
import net.adriantodt.winged.WingedLoreItems
import net.adriantodt.winged.secondsLeft
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.world.World


class HeartOfTheSkyItem(settings: Settings) : Item(settings) {
    override fun hasEnchantmentGlint(stack: ItemStack?) = true

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, ctx: TooltipContext) {
        tooltip += TranslatableText("$translationKey.description")
        tooltip += TranslatableText("tooltip.winged.heart_of_the_sky")
        if (ctx.isAdvanced) {
            tooltip += TranslatableText(
                "tooltip.winged.time_left",
                secondsLeft(stack, data.heartOfTheSkyItemTicksPerDamage)
            )
        }
    }

    fun damageOnce(user: PlayerEntity, stack: ItemStack): ItemStack {
        if (!user.abilities.flying) {
            return stack
        }
        val ticksLeft = stack.tag?.getInt("TicksLeft") ?: 0
        when {
            user.isCreative -> {
                return stack
            }
            ticksLeft > 0 -> {
                stack.orCreateTag.putInt("TicksLeft", ticksLeft - 1)
                return stack
            }
            stack.damage < stack.maxDamage -> {
                stack.damage++
                stack.orCreateTag.putInt("TicksLeft", data.heartOfTheSkyItemTicksPerDamage)
                return stack
            }
        }

        return ItemStack(WingedLoreItems.brokenCoreOfFlight)
    }
}