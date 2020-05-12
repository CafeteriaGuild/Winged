package net.adriantodt.winged

import io.netty.buffer.Unpooled
import net.adriantodt.winged.Winged.configHolder
import net.adriantodt.winged.data.WingedDataObject.BoosterType.*
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.network.ServerPlayerEntity


object WingedPacketHandler {
    val sync = identifier("sync")
    fun sendServerConfig(player: ServerPlayerEntity) {
        val tag = CompoundTag()
        tag.putFloat("removeWingsDamage", configHolder.config.removeWingsDamage)
        tag.putDouble("launcherInstantVelocity", configHolder.config.boosters.forwardLauncher.instantVelocity)
        tag.putDouble("launcherMaxVelocity", configHolder.config.boosters.forwardLauncher.maxVelocity)
        tag.putDouble("launcherSpeedFactor", configHolder.config.boosters.forwardLauncher.speedFactor)
        tag.putDouble("standardBoosterInstantVelocity", configHolder.config.boosters.standardBooster.instantVelocity)
        tag.putDouble("standardBoosterMaxVelocity", configHolder.config.boosters.standardBooster.maxVelocity)
        tag.putDouble("standardBoosterSpeedFactor", configHolder.config.boosters.standardBooster.speedFactor)
        tag.putInt("standardBoosterTicksPerDamage", configHolder.config.boosters.standardBooster.ticksPerDamage)
        tag.putDouble("slowBoosterInstantVelocity", configHolder.config.boosters.slowBooster.instantVelocity)
        tag.putDouble("slowBoosterMaxVelocity", configHolder.config.boosters.slowBooster.maxVelocity)
        tag.putDouble("slowBoosterSpeedFactor", configHolder.config.boosters.slowBooster.speedFactor)
        tag.putInt("slowBoosterTicksPerDamage", configHolder.config.boosters.slowBooster.ticksPerDamage)
        tag.putDouble("fastBoosterInstantVelocity", configHolder.config.boosters.fastBooster.instantVelocity)
        tag.putDouble("fastBoosterMaxVelocity", configHolder.config.boosters.fastBooster.maxVelocity)
        tag.putDouble("fastBoosterSpeedFactor", configHolder.config.boosters.fastBooster.speedFactor)
        tag.putInt("fastBoosterTicksPerDamage", configHolder.config.boosters.fastBooster.ticksPerDamage)
        ServerSidePacketRegistry.INSTANCE.sendToPlayer(
            player, sync, PacketByteBuf(Unpooled.buffer()).writeCompoundTag(tag)
        )
    }

    fun updateConfigs(tag: CompoundTag) {
        Winged.data.removeWingsDamage = tag.getFloat("removeWingsDamage")
        Winged.data.launcherVelocity.instantVelocity = tag.getDouble("launcherInstantVelocity")
        Winged.data.launcherVelocity.maxVelocity = tag.getDouble("launcherMaxVelocity")
        Winged.data.launcherVelocity.speedFactor = tag.getDouble("launcherSpeedFactor")
        Winged.data.booster(STANDARD).apply {
            instantVelocity = tag.getDouble("standardBoosterInstantVelocity")
            maxVelocity = tag.getDouble("standardBoosterMaxVelocity")
            speedFactor = tag.getDouble("standardBoosterSpeedFactor")
            ticksPerDamage = tag.getInt("standardBoosterTicksPerDamage")
        }
        Winged.data.booster(SLOW).apply {
            instantVelocity = tag.getDouble("slowBoosterInstantVelocity")
            maxVelocity = tag.getDouble("slowBoosterMaxVelocity")
            speedFactor = tag.getDouble("slowBoosterSpeedFactor")
            ticksPerDamage = tag.getInt("slowBoosterTicksPerDamage")
        }
        Winged.data.booster(FAST).apply {
            instantVelocity = tag.getDouble("fastBoosterInstantVelocity")
            maxVelocity = tag.getDouble("fastBoosterMaxVelocity")
            speedFactor = tag.getDouble("fastBoosterSpeedFactor")
            ticksPerDamage = tag.getInt("fastBoosterTicksPerDamage")
        }
    }
}