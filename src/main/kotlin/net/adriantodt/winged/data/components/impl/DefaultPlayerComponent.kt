package net.adriantodt.winged.data.components.impl

import net.adriantodt.winged.Winged
import net.adriantodt.winged.data.Wing
import net.adriantodt.winged.data.components.PlayerComponent
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.Identifier

class DefaultPlayerComponent(private val owner: PlayerEntity, override var creativeFlight: Boolean) : PlayerComponent {
    override var wing: Wing? = null
        set(value) {
            field = value
            Winged.playerComponentType.sync(owner)
        }

    override fun writeToNbt(tag: CompoundTag) {
        wing?.let { tag.putString(WING_TAG, Winged.wingRegistry.getId(it).toString()) }
        tag.putBoolean("creativeFlight", creativeFlight)
    }
    override fun readFromNbt(tag: CompoundTag) {
        wing = if (tag.contains(WING_TAG)) Winged.wingRegistry[Identifier(tag.getString(WING_TAG))] else null
        creativeFlight = tag.getBoolean("creativeFlight")
    }
    override fun copyFrom(other: PlayerComponent) {
        val tag = CompoundTag()
        other.writeToNbt(tag)
        readFromNbt(tag)
    }


    companion object {
        const val WING_TAG = "Wing"
    }
}