package net.adriantodt.zephyr.init

import dev.emi.trinkets.api.SlotGroups
import dev.emi.trinkets.api.TrinketSlots
import net.adriantodt.zephyr.ZephyrBaseItems
import net.adriantodt.zephyr.ZephyrDataManager
import net.adriantodt.zephyr.identifier
import net.fabricmc.api.ModInitializer
import org.apache.logging.log4j.LogManager


object Zephyr : ModInitializer {
    private val log = LogManager.getLogger(javaClass)

    override fun onInitialize() {
        ZephyrDataManager.register()
        ZephyrBaseItems.register()

        TrinketSlots.addSlot(
            SlotGroups.CHEST,
            "heart",
            identifier("textures/item/empty_trinket_slot_heart.png")
        )

    }
}