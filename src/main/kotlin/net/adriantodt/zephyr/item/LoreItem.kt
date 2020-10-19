package net.adriantodt.zephyr.item

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.world.World

/**
 * Represents a simple item with a lore description
 */
class ZephyrItem(settings: Settings, private val amount: Int = 2, private val glint: Boolean = false) : Item(settings) {
    override fun hasGlint(stack: ItemStack?): Boolean = glint || super.hasGlint(stack)

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack?, world: World?, tooltip: MutableList<Text>, ctx: TooltipContext?) {
        repeat(amount) { tooltip += TranslatableText("$translationKey.lore${it + 1}") }
    }
}