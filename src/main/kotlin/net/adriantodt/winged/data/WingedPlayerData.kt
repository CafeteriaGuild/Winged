package net.adriantodt.winged.data

import net.adriantodt.winged.WING_REGISTRY
import net.minecraft.entity.data.TrackedDataHandler
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier

data class WingedPlayerData(val wing: Wing? = null) {
    fun writeDataToTag(tag: CompoundTag) {
        wing?.let { tag.putString("Identifier", WING_REGISTRY.getId(it).toString()) }
    }

    companion object {
        @JvmField
        val NO_WING = WingedPlayerData()

        @JvmStatic
        fun readDataFromTag(tag: CompoundTag): WingedPlayerData {
            if (tag.contains("Identifier")) {
                return WingedPlayerData(WING_REGISTRY[Identifier(tag.getString("Identifier"))])
            }
            return NO_WING
        }
    }

    object DataHandler : TrackedDataHandler<WingedPlayerData> {
        override fun write(data: PacketByteBuf, obj: WingedPlayerData) {
            val tag = CompoundTag()
            obj.writeDataToTag(tag)
            data.writeCompoundTag(tag)
        }

        override fun copy(obj: WingedPlayerData): WingedPlayerData {
            return obj.copy()
        }

        override fun read(data: PacketByteBuf): WingedPlayerData {
            return readDataFromTag(data.readCompoundTag() ?: CompoundTag())
        }
    }
}