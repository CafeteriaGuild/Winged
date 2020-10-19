package net.adriantodt.zephyr.config

import me.sargunvohra.mcmods.autoconfig1u.ConfigData
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry
import net.adriantodt.zephyr.ZephyrDataManager

@Config(name = "zephyr")
class ZephyrConfig : ConfigData {
    @ConfigEntry.Gui.Tooltip(count = 2)
    var heartOfTheSkyItemTicksPerDamage = 40

    override fun validatePostLoad() {
        ZephyrDataManager.updateDataAccessObject(this)
    }
}