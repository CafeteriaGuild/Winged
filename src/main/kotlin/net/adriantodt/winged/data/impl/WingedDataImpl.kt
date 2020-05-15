package net.adriantodt.winged.data.impl

import net.adriantodt.winged.WingedUtilityItems.fastBooster
import net.adriantodt.winged.WingedUtilityItems.fastBoosterActive
import net.adriantodt.winged.WingedUtilityItems.slowBooster
import net.adriantodt.winged.WingedUtilityItems.slowBoosterActive
import net.adriantodt.winged.WingedUtilityItems.standardBooster
import net.adriantodt.winged.WingedUtilityItems.standardBoosterActive
import net.adriantodt.winged.data.WingedConfig
import net.adriantodt.winged.data.WingedData
import net.adriantodt.winged.data.WingedData.BoosterType
import net.adriantodt.winged.data.WingedData.BoosterType.*

class WingedDataImpl(config: WingedConfig) : WingedData {
    override val launcherVelocity = BoosterVelocityImpl(config.boosters.forwardLauncher)
    override var removeWingsDamage = config.removeWingsDamage
    override var heartOfTheSkyItemTicksPerDamage = config.heartOfTheSkyItemTicksPerDamage

    private val fuelPelletMap = mapOf(
        STANDARD to FuelPelletDataImpl(3, 0, 0, fun() = standardBooster),
        FAST to FuelPelletDataImpl(2, 1, 0, fun() = fastBooster),
        SLOW to FuelPelletDataImpl(2, 0, 1, fun() = slowBooster)
    )

    private val boosterMap = mapOf(
        STANDARD to BoosterDataImpl(STANDARD.booster(config), fun() = standardBooster, fun() = standardBoosterActive),
        SLOW to BoosterDataImpl(SLOW.booster(config), fun() = slowBooster, fun() = slowBoosterActive),
        FAST to BoosterDataImpl(FAST.booster(config), fun() = fastBooster, fun() = fastBoosterActive)
    )

    override fun booster(type: BoosterType) = boosterMap[type] ?: error("Impossible.")
    override fun fuelPellet(type: BoosterType) = fuelPelletMap[type] ?: error("Impossible.")

    private fun BoosterType.booster(config: WingedConfig) = when (this) {
        STANDARD -> config.boosters.standardBooster
        FAST -> config.boosters.fastBooster
        SLOW -> config.boosters.slowBooster
    }
}

