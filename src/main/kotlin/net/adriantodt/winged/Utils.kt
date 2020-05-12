package net.adriantodt.winged

import net.adriantodt.winged.data.WingedDataObject
import net.adriantodt.winged.item.LoreItem
import net.minecraft.block.Block
import net.minecraft.entity.LivingEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.FoodComponent
import net.minecraft.item.Item
import net.minecraft.util.Identifier
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

fun LivingEntity.boost(config: WingedDataObject.BoosterVelocity) {
    boost(config.maxVelocity, config.instantVelocity, config.speedFactor)
}

fun LivingEntity.boost(maxVelocity: Double = 1.5, instantVelocity: Double = 0.1, speedFactor: Double = 0.5) {
    val rotation = rotationVector
    val velocity = velocity

    this.velocity = velocity.add(
        rotation.x * instantVelocity + (rotation.x * maxVelocity - velocity.x) * speedFactor,
        rotation.y * instantVelocity + (rotation.y * maxVelocity - velocity.y) * speedFactor,
        rotation.z * instantVelocity + (rotation.z * maxVelocity - velocity.z) * speedFactor
    )
}
