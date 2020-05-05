package net.adriantodt.winged.loottables

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplierBuilder
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback
import net.minecraft.loot.LootManager
import net.minecraft.resource.ResourceManager
import net.minecraft.util.Identifier

class WingedLootTables : LootTableLoadingCallback {
    private val extra = LinkedHashMap<Identifier, ArrayList<FabricLootPoolBuilder.() -> Unit>>()

    override fun onLootTableLoading(
        resourceManager: ResourceManager,
        lootManager: LootManager,
        id: Identifier,
        supplier: FabricLootSupplierBuilder,
        setter: LootTableLoadingCallback.LootTableSetter
    ) {
        extra[id]?.let { list -> list.forEach { supplier.withPool(FabricLootPoolBuilder.builder().also(it).build()) } }
    }

    fun addPoolToTables(vararg identifiers: Identifier, block: FabricLootPoolBuilder.() -> Unit) {
        identifiers.forEach { extra.getOrPut(it, ::ArrayList).add(block) }
    }

    fun register() {
        LootTableLoadingCallback.EVENT.register(this)
    }
}