package net.adriantodt.winged.data.components.impl

import nerdhub.cardinal.components.api.ComponentType
import net.adriantodt.winged.Winged
import net.adriantodt.winged.data.Wing
import net.adriantodt.winged.data.components.PlayerComponent
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.Identifier

class DefaultPlayerComponent(private val owner: PlayerEntity) :
    PlayerComponent {
    override var wing: Wing? = null
        set(value) {
            field = value
            this.sync()
        }

    override fun getEntity() = owner

    override fun toTag(tag: CompoundTag): CompoundTag {
        wing?.let { tag.putString(WING_TAG, Winged.wingRegistry.getId(it).toString()) }
        return tag
    }

    override fun fromTag(tag: CompoundTag) {
        if (tag.contains(WING_TAG)) {
            wing = Winged.wingRegistry[Identifier(tag.getString(WING_TAG))]
        }
    }

    override fun getComponentType(): ComponentType<PlayerComponent> {
        return Winged.playerComponentType
    }

    companion object {
        const val WING_TAG = "Wing"
    }
}