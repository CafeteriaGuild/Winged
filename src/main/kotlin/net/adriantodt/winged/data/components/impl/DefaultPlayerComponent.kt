package net.adriantodt.winged.data.components.impl

import net.adriantodt.winged.Winged
import net.adriantodt.winged.data.Wing
import net.adriantodt.winged.data.components.WingedPlayerComponent
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier

class DefaultPlayerComponent(private val owner: PlayerEntity) : WingedPlayerComponent {
    override var wing: Wing? = null
        set(value) {
            field = value
            Winged.playerComponentType.sync(owner)
        }

    override var creativeFlight: Boolean = false
        set(value) {
            field = value
            Winged.playerComponentType.sync(owner)
        }

    override fun writeToNbt(tag: NbtCompound) {
        wing?.let { tag.putString(WING_TAG, Winged.wingRegistry.getId(it).toString()) }
        tag.putBoolean("creativeFlight", creativeFlight)
    }
    override fun readFromNbt(tag: NbtCompound) {
        wing = if (tag.contains(WING_TAG)) Winged.wingRegistry[Identifier(tag.getString(WING_TAG))] else null
        creativeFlight = tag.getBoolean("creativeFlight")
    }

    override fun copyFrom(other: WingedPlayerComponent) {
        wing = other.wing
        creativeFlight = other.creativeFlight
        Winged.playerComponentType.sync(owner)
    }

    companion object {
        const val WING_TAG = "Wing"
    }
}
