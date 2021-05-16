package net.adriantodt.winged

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import net.adriantodt.winged.WingItems.spooky
import net.adriantodt.winged.WingItems.xmasStar
import net.adriantodt.winged.WingItems.xmasTree
import net.adriantodt.winged.WingedLoreItems.batWing
import net.adriantodt.winged.WingedLoreItems.blackFeather
import net.adriantodt.winged.WingedLoreItems.brokenCoreOfFlight75
import net.adriantodt.winged.WingedLoreItems.coreOfFlight
import net.adriantodt.winged.WingedLoreItems.demonicFlesh
import net.adriantodt.winged.WingedLoreItems.friedChicken
import net.adriantodt.winged.WingedLoreItems.irrealityCrystal
import net.adriantodt.winged.WingedLoreItems.shardOfZephyr
import net.adriantodt.winged.WingedLoreItems.vexEssence
import net.adriantodt.winged.data.WingedConfig
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplierBuilder
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback
import net.minecraft.item.Item
import net.minecraft.loot.ConstantLootTableRange
import net.minecraft.loot.LootManager
import net.minecraft.loot.condition.*
import net.minecraft.loot.context.LootContext
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.predicate.entity.EntityFlagsPredicate
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.resource.ResourceManager
import net.minecraft.util.Identifier
import net.minecraft.util.JsonSerializer
import net.minecraft.util.registry.Registry
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.MONTH
import java.util.Calendar.getInstance as calendarNow

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
    private val skeleton = mcIdentifier("entities/skeleton")

    data class DropValues(val identifier: Identifier, val item: Item, val poolConfig: WingedConfig.DropLootTable)

    private val holidayDropMobs = listOf(zombie, zombieVillager, husk, drowned, skeleton, enderman, endermite)

    fun register(config: WingedConfig) {
        registerCoreAndShards(
            listOf(
                abandonedMineshaft to config.lootTables.abandonedMineshaft,
                buriedTreasure to config.lootTables.buriedTreasure,
                endCityTreasure to config.lootTables.endCityTreasure,
                simpleDungeon to config.lootTables.simpleDungeon,
                woodlandMansion to config.lootTables.woodlandMansion
            )
        )
        registerDrops(
            listOf(
                DropValues(chicken, blackFeather, config.lootTables.blackFeather),
                DropValues(zombie, demonicFlesh, config.lootTables.demonicFlesh),
                DropValues(zombieVillager, demonicFlesh, config.lootTables.demonicFlesh),
                DropValues(husk, demonicFlesh, config.lootTables.demonicFlesh),
                DropValues(drowned, demonicFlesh, config.lootTables.demonicFlesh),
                DropValues(enderman, irrealityCrystal, config.lootTables.endermanIrrealityCrystal),
                DropValues(endermite, irrealityCrystal, config.lootTables.endermiteIrrealityCrystal),
                DropValues(bat, batWing, config.lootTables.batWing),
                DropValues(vex, vexEssence, config.lootTables.vexEssence)
            )
        )
        registerFireDrops(
            listOf(
                DropValues(chicken, friedChicken, config.lootTables.friedChicken)
            )
        )
        registerHolidayDrops(config.lootTables.holidayDrops)

        LootTableLoadingCallback.EVENT.register(
            LootTableLoadingCallback { resourceManager, lootManager, id, supplier, setter ->
                val ctx by lazy { Context(resourceManager, lootManager, id, supplier, setter) }
                configurators[id]?.forEach { ctx.it() }
            }
        )
    }

    private fun registerCoreAndShards(pools: List<Pair<Identifier, WingedConfig.CoreAndShardLootTables>>) {
        for ((identifier, pool) in pools) {
            if (pool.core.generate) lootTable(identifier) {
                standardPool(if (pool.core.broken) brokenCoreOfFlight75 else coreOfFlight) {
                    randomChanceCondition(pool.core.chance)
                }
            }
            if (pool.shard.generate) lootTable(identifier) {
                standardPool(shardOfZephyr) {
                    randomChanceCondition(pool.shard.chance)
                }
            }
        }
    }

    private fun registerDrops(pools: List<DropValues>) {
        for ((identifier, item, pool) in pools) {
            if (pool.drop) lootTable(identifier) {
                standardPool(item, pool.requirePlayer) {
                    randomChanceWithLootingCondition(pool.chance, pool.lootingMultiplier)
                }
            }
        }
    }

    private fun registerFireDrops(pools: List<DropValues>) {
        for ((identifier, item, pool) in pools) {
            if (pool.drop) lootTable(identifier) {
                standardPool(item, pool.requirePlayer) {
                    randomChanceWithLootingCondition(pool.chance, pool.lootingMultiplier)
                    onFireCondition()
                }
            }
        }
    }

    private fun registerHolidayDrops(pool: WingedConfig.WingDropLootTable) {
        Registry.register(Registry.LOOT_CONDITION_TYPE, identifier("is_christmas"), IsChristmas.type)
        Registry.register(Registry.LOOT_CONDITION_TYPE, identifier("is_halloween"), IsHalloween.type)

        if (pool.drop) for (identifier in holidayDropMobs) lootTable(identifier) {
            for (variations in listOf(xmasStar, xmasTree)) {
                standardPool(variations.standard, pool.requirePlayer) {
                    randomChanceWithLootingCondition(
                        pool.standardChance,
                        pool.lootingMultiplier
                    )
                    isChristmasCondition()
                }
                standardPool(variations.creativeFlight, pool.requirePlayer) {
                    randomChanceWithLootingCondition(
                        pool.creativeFlightChance,
                        pool.lootingMultiplier
                    )
                    isChristmasCondition()
                }
            }
            for (variations in listOf(spooky)) {
                standardPool(variations.standard, pool.requirePlayer) {
                    randomChanceWithLootingCondition(
                        pool.standardChance,
                        pool.lootingMultiplier
                    )
                    isHalloweenCondition()
                }
                standardPool(variations.creativeFlight, pool.requirePlayer) {
                    randomChanceWithLootingCondition(
                        pool.creativeFlightChance,
                        pool.lootingMultiplier
                    )
                    isHalloweenCondition()
                }
            }
        }
    }

    private data class Context(
        val resourceManager: ResourceManager,
        val lootManager: LootManager,
        val id: Identifier,
        val supplier: FabricLootSupplierBuilder,
        val setter: LootTableLoadingCallback.LootTableSetter
    ) {
        fun addPool(block: FabricLootPoolBuilder.() -> Unit) {
            supplier.pool(FabricLootPoolBuilder.builder().also(block))
        }

        fun standardPool(item: Item, requirePlayer: Boolean = false, block: FabricLootPoolBuilder.() -> Unit) {
            addPool {
                rolls(ConstantLootTableRange.create(1))
                with(ItemEntry.builder(item))
                block()
                if (requirePlayer) killedByPlayerCondition()
            }
        }
    }

    private val configurators = LinkedHashMap<Identifier, ArrayList<Context.() -> Unit>>()

    private fun lootTables(vararg identifiers: Identifier, block: Context.() -> Unit) {
        identifiers.forEach { configurators.getOrPut(it, ::ArrayList).add(block) }
    }

    private fun lootTable(identifier: Identifier, block: Context.() -> Unit) {
        configurators.getOrPut(identifier, ::ArrayList).add(block)
    }

    object IsChristmas : LootCondition {
        @JvmField
        val type = LootConditionType(Serializer)

        override fun getType() = type

        override fun test(_1: LootContext): Boolean {
            // Extracted from ChestBlockEntityRenderer
            return calendarNow().let { it[MONTH] + 1 == 12 && it[DAY_OF_MONTH] in 24..26 }
        }

        object Serializer : JsonSerializer<IsChristmas> {
            override fun toJson(_1: JsonObject, _2: IsChristmas, _3: JsonSerializationContext) = Unit
            override fun fromJson(_1: JsonObject, _2: JsonDeserializationContext) = IsChristmas
        }

        fun builder() = LootCondition.Builder { IsChristmas }
    }

    object IsHalloween : LootCondition {
        @JvmField
        val type = LootConditionType(Serializer)

        override fun getType() = type

        override fun test(_1: LootContext): Boolean {
            // Extracted from ChestBlockEntityRenderer
            return calendarNow().let {
                it[MONTH] + 1 == 10 && it[DAY_OF_MONTH] in 30..31 || it[MONTH] + 1 == 11 && it[DAY_OF_MONTH] == 1
            }
        }

        object Serializer : JsonSerializer<IsHalloween> {
            override fun toJson(_1: JsonObject, _2: IsHalloween, _3: JsonSerializationContext) = Unit
            override fun fromJson(_1: JsonObject, _2: JsonDeserializationContext) = IsHalloween
        }

        fun builder() = LootCondition.Builder { IsHalloween }
    }

    private fun FabricLootPoolBuilder.killedByPlayerCondition() {
        conditionally(KilledByPlayerLootCondition.builder())
    }

    private fun FabricLootPoolBuilder.randomChanceCondition(chance: Float) {
        conditionally(RandomChanceLootCondition.builder(chance.coerceIn(0f, 1f)))
    }

    private fun FabricLootPoolBuilder.randomChanceWithLootingCondition(chance: Float, lootingMultiplier: Float) {
        conditionally(
            RandomChanceWithLootingLootCondition.builder(chance.coerceIn(0f, 1f), lootingMultiplier.coerceIn(0f, 1f))
        )
    }

    private fun FabricLootPoolBuilder.onFireCondition() {
        conditionally(
            EntityPropertiesLootCondition.builder(
                LootContext.EntityTarget.THIS,
                EntityPredicate.Builder.create().flags(EntityFlagsPredicate.Builder.create().onFire(true).build())
            )
        )
    }

    private fun FabricLootPoolBuilder.isChristmasCondition() {
        conditionally(IsChristmas.builder())
    }

    private fun FabricLootPoolBuilder.isHalloweenCondition() {
        conditionally(IsHalloween.builder())
    }
}
