package net.adriantodt.winged.data.impl

import net.adriantodt.winged.WingedUtilityItems.fastBooster
import net.adriantodt.winged.WingedUtilityItems.fastBoosterActive
import net.adriantodt.winged.WingedUtilityItems.slowBooster
import net.adriantodt.winged.WingedUtilityItems.slowBoosterActive
import net.adriantodt.winged.WingedUtilityItems.standardBooster
import net.adriantodt.winged.WingedUtilityItems.standardBoosterActive
import net.adriantodt.winged.data.WingedConfig
import net.adriantodt.winged.data.WingedDataObject
import net.adriantodt.winged.data.WingedDataObject.*
import net.adriantodt.winged.data.WingedDataObject.BoosterType.*
import net.adriantodt.winged.item.ActiveBoosterItem
import net.adriantodt.winged.item.BoosterItem

class WingedDataObjectImpl(config: WingedConfig) : WingedDataObject {
    override val launcherVelocity = object : BoosterVelocity {
        override var constantVelocity = config.boosters.forwardLauncher.constantVelocity
        override var interpolatingVelocity: Double = config.boosters.forwardLauncher.interpolatingVelocity
        override var frictionFactor = config.boosters.forwardLauncher.frictionFactor
    }

    override var removeWingsDamage = config.removeWingsDamage

    override fun booster(type: BoosterType): BoosterData = baseBoosterMap[type] ?: error("Impossible.")
    override fun fuelPellet(type: BoosterType): FuelPelletData = fuelPelletMap[type] ?: error("Impossible.")

    // private stuff here
    private val fuelPelletMap = mapOf(
        STANDARD to LazyFuelPelletData(3, 0, 0, fun() = standardBooster),
        FAST to LazyFuelPelletData(2, 1, 0, fun() = fastBooster),
        SLOW to LazyFuelPelletData(2, 0, 1, fun() = slowBooster)
    )

    private val baseBoosterMap = mapOf(
        STANDARD to LazyBoosterData(config, STANDARD, fun() = standardBooster, fun() = standardBoosterActive),
        SLOW to LazyBoosterData(config, SLOW, fun() = slowBooster, fun() = slowBoosterActive),
        FAST to LazyBoosterData(config, FAST, fun() = fastBooster, fun() = fastBoosterActive)
    )

    private class LazyBoosterData(
        config: WingedConfig,
        type: BoosterType,
        lazyBooster: () -> BoosterItem,
        lazyActiveBooster: () -> ActiveBoosterItem
    ) : BoosterData {
        override val boosterItem by lazy(lazyBooster)
        override val activeBoosterItem by lazy(lazyActiveBooster)

        override var ticksPerDamage: Int = type.fromConfig(config.boosters).ticksPerDamage
        override var constantVelocity: Double = type.fromConfig(config.boosters).constantVelocity
        override var interpolatingVelocity: Double = type.fromConfig(config.boosters).interpolatingVelocity
        override var frictionFactor: Double = type.fromConfig(config.boosters).frictionFactor
    }

    private class LazyFuelPelletData(
        override val standardPellets: Int,
        override val fastPellets: Int,
        override val slowPellets: Int,
        block: () -> BoosterItem
    ) : FuelPelletData {
        override val resultItem by lazy(block)
    }
}