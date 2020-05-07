package net.adriantodt.winged.loottables

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplierBuilder
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback
import net.minecraft.loot.LootManager
import net.minecraft.resource.ResourceManager
import net.minecraft.util.Identifier

class LootTableManager {
    private inner class Callback : LootTableLoadingCallback {
        override fun onLootTableLoading(
            resourceManager: ResourceManager,
            lootManager: LootManager,
            id: Identifier,
            supplier: FabricLootSupplierBuilder,
            setter: LootTableLoadingCallback.LootTableSetter
        ) {
            val ctx by lazy { Context(resourceManager, lootManager, id, supplier, setter) }
            configurators[id]?.forEach { ctx.it() }
        }
    }

    companion object {
        fun register(block: LootTableManager.() -> Unit) {
            LootTableLoadingCallback.EVENT.register(LootTableManager().also(block).Callback())
        }
    }

    data class Context(
        val resourceManager: ResourceManager,
        val lootManager: LootManager,
        val id: Identifier,
        val supplier: FabricLootSupplierBuilder,
        val setter: LootTableLoadingCallback.LootTableSetter
    ) {
        fun addPool(block: FabricLootPoolBuilder.() -> Unit) {
            supplier.withPool(FabricLootPoolBuilder.builder().also(block).build())
        }
    }

    private val configurators = LinkedHashMap<Identifier, ArrayList<Context.() -> Unit>>()

    fun lootTables(vararg identifiers: Identifier, block: Context.() -> Unit) {
        identifiers.forEach { configurators.getOrPut(it, ::ArrayList).add(block) }
    }
}