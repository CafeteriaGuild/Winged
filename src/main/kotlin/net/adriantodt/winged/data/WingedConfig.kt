package net.adriantodt.winged.data

import me.sargunvohra.mcmods.autoconfig1u.ConfigData
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry

@Config(name = "winged")
class WingedConfig : ConfigData {

    @ConfigEntry.Gui.Tooltip
    var removeWingsDamage = 12f

    @ConfigEntry.Gui.Tooltip
    var heartOfTheSkyItemTicksPerDamage = 40

    @ConfigEntry.Category("lootTables")
    @ConfigEntry.Gui.TransitiveObject
    var lootTables = LootTablesConfig()

    class LootTablesConfig {
        @ConfigEntry.Gui.CollapsibleObject
        var abandonedMineshaft = CoreOfFlightLootTable()

        @ConfigEntry.Gui.CollapsibleObject
        var buriedTreasure = CoreOfFlightLootTable()

        @ConfigEntry.Gui.CollapsibleObject
        var simpleDungeon = CoreOfFlightLootTable()

        @ConfigEntry.Gui.CollapsibleObject
        var woodlandMansion = CoreOfFlightLootTable()

        @ConfigEntry.Gui.CollapsibleObject
        var endCityTreasure = CoreOfFlightLootTable(broken = false)

        @ConfigEntry.Gui.CollapsibleObject
        var blackFeather = DropLootTable()

        @ConfigEntry.Gui.CollapsibleObject
        var friedChicken = DropLootTable()

        @ConfigEntry.Gui.CollapsibleObject
        var demonicFlesh = DropLootTable()

        @ConfigEntry.Gui.CollapsibleObject
        var endermanIrrealityCrystal = DropLootTable(chance = 0.1f, lootingMultiplier = 0.04f)

        @ConfigEntry.Gui.CollapsibleObject
        var endermiteIrrealityCrystal = DropLootTable(chance = 0.4f, lootingMultiplier = 0.16f)

        @ConfigEntry.Gui.CollapsibleObject
        var batWing = DropLootTable()

        @ConfigEntry.Gui.CollapsibleObject
        var vexEssence = DropLootTable(chance = 0.1f, lootingMultiplier = 0.04f)

        @ConfigEntry.Gui.CollapsibleObject
        var holidayDrops = DropLootTable(chance = 0.01f, lootingMultiplier = 0.005f)
    }

    @ConfigEntry.Category("boosters")
    @ConfigEntry.Gui.TransitiveObject
    var boosters = BoostersConfig()

    class BoostersConfig {
        @ConfigEntry.Gui.CollapsibleObject
        var forwardLauncher = ForwardLauncherValues()

        @ConfigEntry.Gui.CollapsibleObject
        var standardBooster = BoosterValues(0.1, 1.5, 0.5, 4)

        @ConfigEntry.Gui.CollapsibleObject
        var fastBooster = BoosterValues(0.2, 2.5, 0.6, 2)

        @ConfigEntry.Gui.CollapsibleObject
        var slowBooster = BoosterValues(0.1, 0.9, 0.4, 8)
    }

    class CoreOfFlightLootTable(var generate: Boolean = true, var broken: Boolean = true, var chance: Float = 0.1f)

    class DropLootTable(
        var drop: Boolean = true, var requirePlayer: Boolean = true,
        var chance: Float = 0.05f, var lootingMultiplier: Float = 0.02f
    )

    class ForwardLauncherValues(
        @ConfigEntry.Gui.Tooltip(count = 2)
        var constantVelocity: Double = 0.9,
        @ConfigEntry.Gui.Tooltip(count = 2)
        var interpolatingVelocity: Double = 0.1,
        @ConfigEntry.Gui.Tooltip(count = 2)
        var frictionFactor: Double = 0.4
    )

    class BoosterValues(
        @ConfigEntry.Gui.Tooltip(count = 2)
        var constantVelocity: Double = 0.1,
        @ConfigEntry.Gui.Tooltip(count = 2)
        var interpolatingVelocity: Double = 1.5,
        @ConfigEntry.Gui.Tooltip(count = 2)
        var frictionFactor: Double = 0.5,
        @ConfigEntry.Gui.Tooltip(count = 2)
        var ticksPerDamage: Int = 4
    )
}