package net.adriantodt.winged.data

import nerdhub.cardinal.components.api.ComponentType
import net.adriantodt.winged.wingRegistry
import net.adriantodt.winged.wingedComponent
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.Identifier


class DefaultWingedComponent(private val owner: PlayerEntity) : WingedComponent {
    override var wing: Wing? = null
        set(value) {
            field = value
            this.sync()
        }

    override fun getEntity() = owner

    override fun toTag(tag: CompoundTag): CompoundTag {
        wing?.let { tag.putString(WING_TAG, wingRegistry.getId(it).toString()) }
        return tag
    }

    override fun fromTag(tag: CompoundTag) {
        if (tag.contains(WING_TAG)) {
            wing = wingRegistry[Identifier(tag.getString(WING_TAG))]
        }
    }

    override fun getComponentType(): ComponentType<WingedComponent> {
        return wingedComponent
    }

    companion object {
        const val WING_TAG = "Wing"
    }
}