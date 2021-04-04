package net.adriantodt.winged.data

import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry

@Config(name = "winged")
class WingedConfig : ConfigData {

    @ConfigEntry.Gui.Tooltip
    var removeWingsDamage = 12f

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
        var abandonedMineshaftShardOfFlight = ShardOfFlightLootTable()

        @ConfigEntry.Gui.CollapsibleObject
        var buriedTreasureShardOfFlight = ShardOfFlightLootTable()

        @ConfigEntry.Gui.CollapsibleObject
        var simpleDungeonShardOfFlight = ShardOfFlightLootTable()

        @ConfigEntry.Gui.CollapsibleObject
        var woodlandMansionShardOfFlight = ShardOfFlightLootTable()

        @ConfigEntry.Gui.CollapsibleObject
        var endCityTreasureShardOfFlight = ShardOfFlightLootTable(chance = 0.3f)

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

        var enderdragonDropsHeartOfTheSky = true
    }

    class CoreOfFlightLootTable(var generate: Boolean = true, var broken: Boolean = true, var chance: Float = 0.1f)
    
    class ShardOfFlightLootTable(var generate: Boolean = true, var chance: Float = 0.2f)

    class DropLootTable(
        var drop: Boolean = true, var requirePlayer: Boolean = true,
        var chance: Float = 0.05f, var lootingMultiplier: Float = 0.02f
    )
}