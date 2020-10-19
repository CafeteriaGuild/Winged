package net.adriantodt.zephyr.init

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import org.apache.logging.log4j.LogManager

@Environment(EnvType.CLIENT)
object ZephyrClient : ClientModInitializer {
    private val log = LogManager.getLogger(javaClass)

    override fun onInitializeClient() {
    }
}