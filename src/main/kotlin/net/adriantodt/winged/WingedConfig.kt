package net.adriantodt.winged

import me.sargunvohra.mcmods.autoconfig1u.ConfigData
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry

@Config(name = "winged")
class WingedConfig : ConfigData {
    class LootTableItem(var generate: Boolean = true, var broken: Boolean = true, var chance: Float = 0.1f)

    class LootTablesConfig {
        @ConfigEntry.Gui.CollapsibleObject
        var abandonedMineshaft = LootTableItem()

        @ConfigEntry.Gui.CollapsibleObject
        var buriedTreasure = LootTableItem()

        @ConfigEntry.Gui.CollapsibleObject
        var simpleDungeon = LootTableItem()

        @ConfigEntry.Gui.CollapsibleObject
        var woodlandMansion = LootTableItem()

        @ConfigEntry.Gui.CollapsibleObject
        var endCityTreasure = LootTableItem(broken = false)
    }

    @ConfigEntry.Gui.CollapsibleObject
    var coreOfFlightLootTables = LootTablesConfig()

    @ConfigEntry.Gui.Tooltip
    var removeWingsDamage = 12f
}