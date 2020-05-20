package net.adriantodt.winged.item

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.world.World


class LoreItem(settings: Settings, private val amount: Int = 2, private val glint: Boolean = false) : Item(settings) {
    override fun hasEnchantmentGlint(stack: ItemStack?): Boolean {
        return glint || super.hasEnchantmentGlint(stack)
    }

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack?, world: World?, tooltip: MutableList<Text?>, ctx: TooltipContext?) {
        repeat(amount) {
            tooltip.add(TranslatableText("$translationKey.lore${it + 1}"))
        }
    }
}