package net.adriantodt.zephyr.item

import dev.emi.trinkets.api.SlotGroups
import dev.emi.trinkets.api.TrinketItem
import net.adriantodt.zephyr.ZephyrBaseItems
import net.adriantodt.zephyr.ZephyrDataManager.data
import net.adriantodt.zephyr.secondsLeft
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.world.World


class HeartOfTheSkyItem(settings: Settings) : TrinketItem(settings) {
    override fun hasGlint(stack: ItemStack?) = true

    override fun canWearInSlot(group: String?, slot: String?): Boolean {
        return group == SlotGroups.CHEST && slot == "heart"
    }

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

        return ItemStack(ZephyrBaseItems.zephyrJewelBroken)
    }
}