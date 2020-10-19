package net.adriantodt.zephyr

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer
import net.adriantodt.zephyr.config.ZephyrConfig
import net.adriantodt.zephyr.data.ZephyrDataAccess

object ZephyrDataManager {
    private val configHolder = AutoConfig.register(ZephyrConfig::class.java, ::JanksonConfigSerializer)!!

    val data: ZephyrDataAccess = object : ZephyrDataAccess {
        override val heartOfTheSkyItemTicksPerDamage: Int
            get() = 1

    }

    fun register() {
        // TODO WingedDataAccess
        // TODO Cardinal Components
    }

    fun updateDataAccessObject(config: ZephyrConfig) {
        // TODO make this callback send updates to clients
    }
}