package net.adriantodt.winged.data

import net.adriantodt.winged.item.ActiveBoosterItem
import net.adriantodt.winged.item.BoosterItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

interface WingedDataObject {
    interface Accessible<T> {
        fun access(): T
    }

    enum class BoosterType {
        STANDARD, FAST, SLOW
    }

    interface BoosterVelocity {
        var instantVelocity: Double
        var maxVelocity: Double
        var speedFactor: Double
    }

    interface BoosterData : BoosterVelocity {
        var ticksPerDamage: Int

        val boosterItem: BoosterItem
        val activeBoosterItem: ActiveBoosterItem

        fun convertTo(stack: ItemStack, item: Item): ItemStack {
            val converted = ItemStack(item)
            converted.damage = stack.damage
            stack.tag?.getInt("TicksLeft")?.let { converted.orCreateTag.putInt("TicksLeft", it) }
            return converted
        }

        fun toActiveBooster(stack: ItemStack) = convertTo(stack, activeBoosterItem)
        fun toBooster(stack: ItemStack) = convertTo(stack, boosterItem)

        fun secondsLeft(stack: ItemStack): Double {
            val damageTicksLeft = (stack.maxDamage - stack.damage) * ticksPerDamage
            val tagTicksLeft = stack.tag?.getInt("TicksLeft") ?: 0
            return (damageTicksLeft + tagTicksLeft) / 20.0
        }
    }

    interface FuelPelletData {
        val standardPellets: Int
        val fastPellets: Int
        val slowPellets: Int
        val resultItem: BoosterItem
    }

    val launcherVelocity: BoosterVelocity
    var removeWingsDamage: Float
    fun booster(type: BoosterType): BoosterData
    fun fuelPellet(type: BoosterType): FuelPelletData
}