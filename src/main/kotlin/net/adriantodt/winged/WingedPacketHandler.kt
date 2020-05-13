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
        tag.putDouble("launcherConstantVelocity", configHolder.config.boosters.forwardLauncher.constantVelocity)
        tag.putDouble(
            "launcherInterpolatingVelocity",
            configHolder.config.boosters.forwardLauncher.interpolatingVelocity
        )
        tag.putDouble("launcherFrictionFactor", configHolder.config.boosters.forwardLauncher.frictionFactor)
        tag.putDouble("standardBoosterConstantVelocity", configHolder.config.boosters.standardBooster.constantVelocity)
        tag.putDouble(
            "standardBoosterInterpolatingVelocity",
            configHolder.config.boosters.standardBooster.interpolatingVelocity
        )
        tag.putDouble("standardBoosterFrictionFactor", configHolder.config.boosters.standardBooster.frictionFactor)
        tag.putInt("standardBoosterTicksPerDamage", configHolder.config.boosters.standardBooster.ticksPerDamage)
        tag.putDouble("slowBoosterConstantVelocity", configHolder.config.boosters.slowBooster.constantVelocity)
        tag.putDouble(
            "slowBoosterInterpolatingVelocity",
            configHolder.config.boosters.slowBooster.interpolatingVelocity
        )
        tag.putDouble("slowBoosterFrictionFactor", configHolder.config.boosters.slowBooster.frictionFactor)
        tag.putInt("slowBoosterTicksPerDamage", configHolder.config.boosters.slowBooster.ticksPerDamage)
        tag.putDouble("fastBoosterConstantVelocity", configHolder.config.boosters.fastBooster.constantVelocity)
        tag.putDouble(
            "fastBoosterInterpolatingVelocity",
            configHolder.config.boosters.fastBooster.interpolatingVelocity
        )
        tag.putDouble("fastBoosterFrictionFactor", configHolder.config.boosters.fastBooster.frictionFactor)
        tag.putInt("fastBoosterTicksPerDamage", configHolder.config.boosters.fastBooster.ticksPerDamage)
        ServerSidePacketRegistry.INSTANCE.sendToPlayer(
            player, sync, PacketByteBuf(Unpooled.buffer()).writeCompoundTag(tag)
        )
    }

    fun updateConfigs(tag: CompoundTag) {
        Winged.logger.info("Syncing local configs from the server!")
        Winged.data.removeWingsDamage = tag.getFloat("removeWingsDamage")
        Winged.data.launcherVelocity.constantVelocity = tag.getDouble("launcherConstantVelocity")
        Winged.data.launcherVelocity.interpolatingVelocity = tag.getDouble("launcherInterpolatingVelocity")
        Winged.data.launcherVelocity.frictionFactor = tag.getDouble("launcherFrictionFactor")
        Winged.data.booster(STANDARD).apply {
            constantVelocity = tag.getDouble("standardBoosterConstantVelocity")
            interpolatingVelocity = tag.getDouble("standardBoosterInterpolatingVelocity")
            frictionFactor = tag.getDouble("standardBoosterFrictionFactor")
            ticksPerDamage = tag.getInt("standardBoosterTicksPerDamage")
        }
        Winged.data.booster(SLOW).apply {
            constantVelocity = tag.getDouble("slowBoosterConstantVelocity")
            interpolatingVelocity = tag.getDouble("slowBoosterInterpolatingVelocity")
            frictionFactor = tag.getDouble("slowBoosterFrictionFactor")
            ticksPerDamage = tag.getInt("slowBoosterTicksPerDamage")
        }
        Winged.data.booster(FAST).apply {
            constantVelocity = tag.getDouble("fastBoosterConstantVelocity")
            interpolatingVelocity = tag.getDouble("fastBoosterInterpolatingVelocity")
            frictionFactor = tag.getDouble("fastBoosterFrictionFactor")
            ticksPerDamage = tag.getInt("fastBoosterTicksPerDamage")
        }
        Winged.logger.info("We're now be in sync.")
    }
}