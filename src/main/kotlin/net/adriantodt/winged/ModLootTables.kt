package net.adriantodt.winged

import net.adriantodt.winged.loottables.WingedLootTables
import net.minecraft.loot.ConstantLootTableRange
import net.minecraft.loot.condition.EntityPropertiesLootCondition
import net.minecraft.loot.condition.KilledByPlayerLootCondition
import net.minecraft.loot.condition.RandomChanceLootCondition
import net.minecraft.loot.condition.RandomChanceWithLootingLootCondition
import net.minecraft.loot.context.LootContext
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.predicate.entity.EntityFlagsPredicate
import net.minecraft.predicate.entity.EntityPredicate

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

fun initLootTables() {
    WingedLootTables().apply {
        addPoolToTables(buriedTreasure, simpleDungeon, woodlandMansion, abandonedMineshaft) {
            withRolls(ConstantLootTableRange.create(1))
            withEntry(ItemEntry.builder(BROKEN_CORE_OF_FLIGHT))
            withCondition(RandomChanceLootCondition.builder(0.1f))
        }
        addPoolToTables(endCityTreasure) {
            withRolls(ConstantLootTableRange.create(1))
            withEntry(ItemEntry.builder(CORE_OF_FLIGHT))
            withCondition(RandomChanceLootCondition.builder(0.1f))
        }
        addPoolToTables(chicken) {
            withRolls(ConstantLootTableRange.create(1))
            withEntry(ItemEntry.builder(BLACK_FEATHER))
            withCondition(RandomChanceWithLootingLootCondition.builder(0.05f, 0.02f))
            withCondition(KilledByPlayerLootCondition.builder())
        }
        addPoolToTables(chicken) {
            withRolls(ConstantLootTableRange.create(1))
            withEntry(ItemEntry.builder(FRIED_CHICKEN))
            withCondition(RandomChanceWithLootingLootCondition.builder(0.05f, 0.02f))
            withCondition(KilledByPlayerLootCondition.builder())
            withCondition(
                EntityPropertiesLootCondition.builder(
                    LootContext.EntityTarget.THIS,
                    EntityPredicate.Builder.create()
                        .flags(EntityFlagsPredicate.Builder.create().onFire(true).build())
                )
            )
        }
        addPoolToTables(zombie, zombieVillager, husk, drowned) {
            withRolls(ConstantLootTableRange.create(1))
            withEntry(ItemEntry.builder(DEMONIC_FLESH))
            withCondition(RandomChanceWithLootingLootCondition.builder(0.05f, 0.02f))
            withCondition(KilledByPlayerLootCondition.builder())
            withCondition(
                EntityPropertiesLootCondition.builder(
                    LootContext.EntityTarget.THIS,
                    EntityPredicate.Builder.create()
                        .flags(EntityFlagsPredicate.Builder.create().onFire(true).build())
                )
            )
        }
        addPoolToTables(enderman) {
            withRolls(ConstantLootTableRange.create(1))
            withEntry(ItemEntry.builder(IRREALITY_CRYSTAL))
            withCondition(RandomChanceWithLootingLootCondition.builder(0.1f, 0.04f))
            withCondition(KilledByPlayerLootCondition.builder())
        }
        addPoolToTables(endermite) {
            withRolls(ConstantLootTableRange.create(1))
            withEntry(ItemEntry.builder(IRREALITY_CRYSTAL))
            withCondition(RandomChanceWithLootingLootCondition.builder(0.4f, 0.16f))
            withCondition(KilledByPlayerLootCondition.builder())
        }
        addPoolToTables(bat) {
            withRolls(ConstantLootTableRange.create(1))
            withEntry(ItemEntry.builder(ITEM_BAT_WING))
            withCondition(RandomChanceWithLootingLootCondition.builder(0.1f, 0.04f))
            withCondition(KilledByPlayerLootCondition.builder())
        }
    }.register()
}