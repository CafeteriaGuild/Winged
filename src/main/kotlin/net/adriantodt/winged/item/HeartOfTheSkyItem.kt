package net.adriantodt.winged.item

import net.adriantodt.winged.Winged
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.ModifyEntries
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.world.World


class HeartOfTheSkyItem(settings: Settings) : Item(settings) {
    init {
        ItemGroupEvents.modifyEntriesEvent(Winged.mainGroupKey).register(ModifyEntries { content -> content.add(this) })
    }

    override fun hasGlint(stack: ItemStack?) = true

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, ctx: TooltipContext) {
        tooltip += Text.translatable("$translationKey.description")
    }
}