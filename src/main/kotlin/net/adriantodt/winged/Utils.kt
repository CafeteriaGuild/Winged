package net.adriantodt.winged

import net.adriantodt.winged.data.Wing
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry

fun mcIdentifier(path: String) = Identifier("minecraft", path)

fun identifier(path: String) = Identifier("winged", path)

inline fun identifier(path: String, block: Identifier.() -> Unit) = identifier(path).run(block)

fun Identifier.item(item: Item) = apply {
    Registry.register(Registry.ITEM, this, item)
}

fun Identifier.item(block: Block) = apply {
    Registry.register(Registry.ITEM, this, BlockItem(block, Item.Settings().group(mainGroup)))
}

fun Identifier.wing(wing: Wing) = apply {
    Registry.register(wingRegistry, this, wing)
}

fun itemSettings(): Item.Settings = Item.Settings().group(mainGroup)

fun wingItemSettings(): Item.Settings = Item.Settings().group(showcaseGroup).rarity(Rarity.EPIC).maxCount(1)