package net.adriantodt.winged.data

import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry

@Config(name = "winged")
class WingedConfig : ConfigData {

    @ConfigEntry.Gui.Tooltip
    var removeWingsDamage = 12f

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.RequiresRestart
    var keepWingsAfterDeath = true

    @ConfigEntry.Gui.Tooltip
    var wingRemovalBrokenCore = true

    @ConfigEntry.Category("lootTables")
    @ConfigEntry.Gui.TransitiveObject
    var lootTables = LootTablesConfig()

    class LootTablesConfig {
        @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
        var abandonedMineshaft = CoreAndShardLootTables()

        @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
        var buriedTreasure = CoreAndShardLootTables()

        @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
        var simpleDungeon = CoreAndShardLootTables()

        @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
        var woodlandMansion = CoreAndShardLootTables()

        @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
        var endCityTreasure = CoreAndShardLootTables(CoreLootTable(broken = false), ShardLootTable(chance = 0.3f))

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
        var holidayDrops = WingDropLootTable(
            standardChance = 0.01f, creativeFlightChance = 0.005f, lootingMultiplier = 0.005f
        )

        var enderdragonDropsHeartOfTheSky = true
    }

    class CoreAndShardLootTables(
        @ConfigEntry.Gui.CollapsibleObject
        var core: CoreLootTable = CoreLootTable(),
        @ConfigEntry.Gui.CollapsibleObject
        var shard: ShardLootTable = ShardLootTable()
    )

    class CoreLootTable(
        @ConfigEntry.Gui.RequiresRestart
        var generate: Boolean = true,
        @ConfigEntry.Gui.RequiresRestart
        var broken: Boolean = true,
        @ConfigEntry.Gui.RequiresRestart
        var chance: Float = 0.1f
    )

    class ShardLootTable(
        @ConfigEntry.Gui.RequiresRestart
        var generate: Boolean = true,
        @ConfigEntry.Gui.RequiresRestart
        var chance: Float = 0.2f
    )

    class DropLootTable(
        @ConfigEntry.Gui.RequiresRestart
        var drop: Boolean = true,
        @ConfigEntry.Gui.RequiresRestart
        var requirePlayer: Boolean = true,
        @ConfigEntry.Gui.RequiresRestart
        var chance: Float = 0.05f,
        @ConfigEntry.Gui.RequiresRestart
        var lootingMultiplier: Float = 0.02f
    )

    class WingDropLootTable(
        @ConfigEntry.Gui.RequiresRestart
        var drop: Boolean = true,
        @ConfigEntry.Gui.RequiresRestart
        var requirePlayer: Boolean = true,
        @ConfigEntry.Gui.RequiresRestart
        var standardChance: Float = 0.001f,
        @ConfigEntry.Gui.RequiresRestart
        var creativeFlightChance: Float = 0.0005f,
        @ConfigEntry.Gui.RequiresRestart
        var lootingMultiplier: Float = 0.02f
    )
}
