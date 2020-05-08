package net.adriantodt.winged

import net.adriantodt.winged.item.ActiveBoosterItem
import net.adriantodt.winged.item.BoosterItem
import net.adriantodt.winged.item.FuelPelletItem
import net.adriantodt.winged.item.LoreItem
import net.minecraft.item.Item

val EMPTY_BOOSTER = LoreItem(itemSettings())

val LONG_DURATION_BOOSTER = BoosterItem(
    itemSettings().maxDamage(400),
    fun() = LONG_DURATION_BOOSTER_ACTIVE
)
val STANDARD_BOOSTER = BoosterItem(
    itemSettings().maxDamage(200),
    fun() = STANDARD_BOOSTER_ACTIVE
)
val SHORT_BURST_BOOSTER = BoosterItem(
    itemSettings().maxDamage(100),
    fun() = SHORT_BURST_BOOSTER_ACTIVE
)

val STANDARD_FUEL_PELLET = FuelPelletItem(itemSettings(), 3, 0, 0, STANDARD_BOOSTER)
val LONG_DURATION_FUEL_PELLET = FuelPelletItem(itemSettings(), 2, 0, 1, LONG_DURATION_BOOSTER)
val SHORT_BURST_FUEL_PELLET = FuelPelletItem(itemSettings(), 2, 1, 0, SHORT_BURST_BOOSTER)

val LONG_DURATION_BOOSTER_ACTIVE: ActiveBoosterItem = ActiveBoosterItem(
    Item.Settings().maxDamage(400),
    0.1, 0.9, 0.4, 8,
    fun() = LONG_DURATION_BOOSTER
)
val STANDARD_BOOSTER_ACTIVE: ActiveBoosterItem = ActiveBoosterItem(
    Item.Settings().maxDamage(200),
    0.1, 1.5, 0.5, 4,
    fun() = STANDARD_BOOSTER
)
val SHORT_BURST_BOOSTER_ACTIVE: ActiveBoosterItem = ActiveBoosterItem(
    Item.Settings().maxDamage(100),
    0.2, 2.5, 0.6, 2,
    fun() = SHORT_BURST_BOOSTER
)

fun initBoosters() {
    identifier("fuel_pellet_standard").item(STANDARD_FUEL_PELLET)
    identifier("fuel_pellet_fast").item(SHORT_BURST_FUEL_PELLET)
    identifier("fuel_pellet_slow").item(LONG_DURATION_FUEL_PELLET)
    identifier("booster_empty").item(EMPTY_BOOSTER)
    identifier("booster_standard").item(STANDARD_BOOSTER)
    identifier("booster_standard_active").item(STANDARD_BOOSTER_ACTIVE)
    identifier("booster_fast").item(SHORT_BURST_BOOSTER)
    identifier("booster_fast_active").item(SHORT_BURST_BOOSTER_ACTIVE)
    identifier("booster_slow").item(LONG_DURATION_BOOSTER)
    identifier("booster_slow_active").item(LONG_DURATION_BOOSTER_ACTIVE)
}