package net.adriantodt.winged

import net.adriantodt.winged.WingedLoreItems.batWing
import net.adriantodt.winged.WingedLoreItems.blackFeather
import net.adriantodt.winged.WingedLoreItems.brokenCoreOfFlight
import net.adriantodt.winged.WingedLoreItems.coreOfFlight
import net.adriantodt.winged.WingedLoreItems.demonicFlesh
import net.adriantodt.winged.WingedLoreItems.friedChicken
import net.adriantodt.winged.WingedLoreItems.irrealityCrystal
import net.adriantodt.winged.WingedLoreItems.vexEssence
import net.adriantodt.winged.data.WingedConfig
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplierBuilder
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback
import net.minecraft.item.Item
import net.minecraft.loot.ConstantLootTableRange
import net.minecraft.loot.LootManager
import net.minecraft.loot.condition.EntityPropertiesLootCondition
import net.minecraft.loot.condition.KilledByPlayerLootCondition
import net.minecraft.loot.condition.RandomChanceLootCondition
import net.minecraft.loot.condition.RandomChanceWithLootingLootCondition
import net.minecraft.loot.context.LootContext
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.predicate.entity.EntityFlagsPredicate
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.resource.ResourceManager
import net.minecraft.util.Identifier

object WingedLootTables {
    private val abandonedMineshaft = mcIdentifier("chests/abandoned_mineshaft")
    private val buriedTreasure = mcIdentifier("chests/buried_treasure")
    private val simpleDungeon = mcIdentifier("chests/simple_dungeon")
    private val woodlandMansion = mcIdentifier("chests/woodland_mansion")
    private val endCityTreasure = mcIdentifier("chests/end_city_treasure")
    private val chicken = mcIdentifier("entities/chicken")
    private val zombie = mcIdentifier("entities/zombie")
    private val zombieVillager = mcIdentifier("entities/zombie_villager")
    private val husk = mcIdentifier("entities/husk")
    private val drowned = mcIdentifier("entities/drowned")
    private val enderman = mcIdentifier("entities/enderman")
    private val endermite = mcIdentifier("entities/endermite")
    private val bat = mcIdentifier("entities/bat")
    private val vex = mcIdentifier("entities/vex")

    data class DropValues(val identifier: Identifier, val item: Item, val poolConfig: WingedConfig.DropLootTable)

    private fun coreOfFlightLootTables(config: WingedConfig) = listOf(
        abandonedMineshaft to config.lootTables.abandonedMineshaft,
        buriedTreasure to config.lootTables.buriedTreasure,
        endCityTreasure to config.lootTables.endCityTreasure,
        simpleDungeon to config.lootTables.simpleDungeon,
        woodlandMansion to config.lootTables.woodlandMansion
    )

    private fun dropLootTales(config: WingedConfig) = listOf(
        DropValues(chicken, blackFeather, config.lootTables.blackFeather)
    )

    private fun fireDropLootTales(config: WingedConfig) = listOf(
        DropValues(chicken, friedChicken, config.lootTables.friedChicken),
        DropValues(zombie, demonicFlesh, config.lootTables.demonicFlesh),
        DropValues(zombieVillager, demonicFlesh, config.lootTables.demonicFlesh),
        DropValues(husk, demonicFlesh, config.lootTables.demonicFlesh),
        DropValues(drowned, demonicFlesh, config.lootTables.demonicFlesh),
        DropValues(enderman, irrealityCrystal, config.lootTables.endermanIrrealityCrystal),
        DropValues(endermite, irrealityCrystal, config.lootTables.endermiteIrrealityCrystal),
        DropValues(bat, batWing, config.lootTables.batWing),
        DropValues(vex, vexEssence, config.lootTables.vexEssence)
    )

    fun register(config: WingedConfig) {
        for ((identifier, poolConfig) in coreOfFlightLootTables(config)) {
            lootTable(identifier) {
                if (poolConfig.generate) {
                    addPool {
                        withRolls(ConstantLootTableRange.create(1))
                        withEntry(ItemEntry.builder(if (poolConfig.broken) brokenCoreOfFlight else coreOfFlight))
                        withCondition(RandomChanceLootCondition.builder(poolConfig.chance.coerceIn(0f, 1f)))
                    }
                }
            }
        }
        for ((identifier, item, poolConfig) in dropLootTales(config)) {
            lootTable(identifier) {
                if (poolConfig.drop) {
                    addPool {
                        withRolls(ConstantLootTableRange.create(1))
                        withEntry(ItemEntry.builder(item))
                        withCondition(
                            RandomChanceWithLootingLootCondition.builder(
                                poolConfig.chance.coerceIn(0f, 1f),
                                poolConfig.lootingMultiplier.coerceIn(0f, 1f)
                            )
                        )
                        if (poolConfig.requirePlayer) {
                            withCondition(KilledByPlayerLootCondition.builder())
                        }
                    }
                }
            }
        }
        for ((identifier, item, poolConfig) in fireDropLootTales(config)) {
            lootTable(identifier) {
                if (poolConfig.drop) {
                    addPool {
                        withRolls(ConstantLootTableRange.create(1))
                        withEntry(ItemEntry.builder(item))
                        withCondition(
                            RandomChanceWithLootingLootCondition.builder(
                                poolConfig.chance.coerceIn(0f, 1f),
                                poolConfig.lootingMultiplier.coerceIn(0f, 1f)
                            )
                        )
                        if (poolConfig.requirePlayer) {
                            withCondition(KilledByPlayerLootCondition.builder())
                        }
                        withCondition(
                            EntityPropertiesLootCondition.builder(
                                LootContext.EntityTarget.THIS,
                                EntityPredicate.Builder.create()
                                    .flags(EntityFlagsPredicate.Builder.create().onFire(true).build())
                            )
                        )
                    }
                }
            }
        }

        LootTableLoadingCallback.EVENT.register(
            LootTableLoadingCallback { resourceManager, lootManager, id, supplier, setter ->
                val ctx by lazy { Context(resourceManager, lootManager, id, supplier, setter) }
                configurators[id]?.forEach { ctx.it() }
            }
        )
    }

    private data class Context(
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

    private fun lootTables(vararg identifiers: Identifier, block: Context.() -> Unit) {
        identifiers.forEach { configurators.getOrPut(it, ::ArrayList).add(block) }
    }

    private fun lootTable(identifier: Identifier, block: Context.() -> Unit) {
        configurators.getOrPut(identifier, ::ArrayList).add(block)
    }
}