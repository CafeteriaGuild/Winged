package net.adriantodt.winged.data

import net.adriantodt.winged.item.ActiveBoosterItem
import net.adriantodt.winged.item.BoosterItem
import net.adriantodt.winged.plus
import net.adriantodt.winged.times
import net.minecraft.entity.LivingEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

interface WingedData {
    enum class BoosterType { STANDARD, FAST, SLOW }

    interface BoosterVelocity {
        // Positive, greater than zero. Default 0.1
        // Velocity being constantly applied, independent of drift factor.
        var constantVelocity: Double

        // Positive, greater than zero. Default 1.5
        // Velocity which is interpolated according to the drift factor.
        var interpolatingVelocity: Double

        // Between 0.0 and 1.0, Default 0.5
        // Increases flying driftness and slows velocity gain.
        var frictionFactor: Double

        fun applyBoost(entity: LivingEntity) {
            val r = entity.rotationVector
            val v = entity.velocity
            val driftFactor = 1.0 - frictionFactor
            entity.velocity = v * driftFactor + r * (constantVelocity + interpolatingVelocity * frictionFactor)
        }
    }

    interface BoosterData : BoosterVelocity {
        var ticksPerDamage: Int

        val boosterItem: BoosterItem
        val activeBoosterItem: ActiveBoosterItem

        fun convertTo(stack: ItemStack, item: Item): ItemStack {
            val converted = ItemStack(item)
            converted.damage = stack.damage
            val tag = stack.tag
            tag?.getInt("TicksLeft")?.let { converted.orCreateTag.putInt("TicksLeft", it) }
            tag?.getBoolean("Unbreakable")?.let { converted.orCreateTag.putBoolean("Unbreakable", it) }
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
    var heartOfTheSkyItemTicksPerDamage: Int
    fun booster(type: BoosterType): BoosterData
    fun fuelPellet(type: BoosterType): FuelPelletData
}