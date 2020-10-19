package net.adriantodt.zephyr

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack

object ZephyrItemGroups {
    val main: ItemGroup = FabricItemGroupBuilder.create(identifier("main"))
        .icon { ItemStack(ZephyrBaseItems.zephyrJewel) }
        .build()

    val showcase: ItemGroup = FabricItemGroupBuilder.create(identifier("showcase"))
        .icon { ItemStack(ZephyrBaseItems.zephyrJewel) } // TODO change this to a Elytra
        .build()
}