package net.adriantodt.winged

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry

object WingedClient : ClientModInitializer {
    override fun onInitializeClient() {
        ClientSidePacketRegistry.INSTANCE.register(WingedPacketHandler.sync) { _, attachedData ->
            WingedPacketHandler.updateConfigs(attachedData.readCompoundTag()!!)
        }
    }
}