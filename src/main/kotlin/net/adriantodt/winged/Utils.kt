package net.adriantodt.winged

import net.adriantodt.winged.item.LoreItem
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.FoodComponent
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.random.Random
import net.minecraft.util.registry.Registry

fun mcIdentifier(path: String) = Identifier("minecraft", path)

fun identifier(path: String) = Identifier("winged", path)

inline fun identifier(path: String, block: Identifier.() -> Unit) = identifier(path).run(block)

fun Identifier.item(item: Item) = apply {
    Registry.register(Registry.ITEM, this, item)
}

fun Identifier.item(block: Block) = apply {
    Registry.register(Registry.ITEM, this, BlockItem(block, Item.Settings().group(Winged.mainGroup)))
}

fun itemSettings(): Item.Settings = Item.Settings().group(Winged.mainGroup)

fun Item.Settings.food(block: FoodComponent.Builder.() -> Unit) = apply {
    food(FoodComponent.Builder().also(block).build())
}

fun Item.Settings.loreItem(amount: Int = 2, glint: Boolean = false) = LoreItem(this, amount, glint)

operator fun Vec3d.times(other: Double): Vec3d = multiply(other)

operator fun Vec3d.plus(other: Vec3d): Vec3d = add(other)

public fun <T> Collection<T>.random(random: Random): T {
    if (isEmpty())
        throw NoSuchElementException("Collection is empty.")
    return elementAt(random.nextInt(size))
}
