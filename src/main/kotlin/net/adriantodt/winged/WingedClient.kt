package net.adriantodt.winged

import net.adriantodt.winged.item.SpeedometerModelPredicateProvider
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.client.model.FabricModelPredicateProviderRegistry
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry

object WingedClient : ClientModInitializer {
    override fun onInitializeClient() {
        ClientSidePacketRegistry.INSTANCE.register(WingedPacketHandler.sync) { _, attachedData ->
            WingedPacketHandler.updateConfigs(attachedData.readCompoundTag()!!)
        }
        FabricModelPredicateProviderRegistry.register(
            WingedUtilityItems.speedometer, identifier("speed"), SpeedometerModelPredicateProvider
        )
    }
}