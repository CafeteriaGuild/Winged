package net.adriantodt.winged

import net.adriantodt.winged.data.Wing
import net.adriantodt.winged.data.WingedPlayerData
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.registry.DefaultedRegistry

val MAIN_GROUP: ItemGroup = FabricItemGroupBuilder.create(identifier("main"))
    .icon { ItemStack(CORE_OF_FLIGHT) }
    .build()

val SHOWCASE_GROUP: ItemGroup = FabricItemGroupBuilder.create(identifier("showcase"))
    .icon { ItemStack(ITEM_WING_ELYTRA) }
    .build()

val WING_REGISTRY = DefaultedRegistry<Wing>("minecraft:elytra")

val REMOVE_WINGS = RemoveWingsDamageSource()

@Suppress("unused")
fun init() {
    TrackedDataHandlerRegistry.register(WingedPlayerData.DataHandler)
    initItems()
    initWings()
}

