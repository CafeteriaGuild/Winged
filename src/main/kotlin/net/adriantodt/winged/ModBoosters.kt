package net.adriantodt.winged

import net.adriantodt.winged.item.ActiveBoosterItem
import net.adriantodt.winged.item.BoosterItem
import net.minecraft.item.Item

val LONG_DURATION_BOOSTER = BoosterItem(
    itemSettings().maxDamage(401),
    fun() = LONG_DURATION_BOOSTER_ACTIVE
)
val STANDARD_BOOSTER = BoosterItem(
    itemSettings().maxDamage(201),
    fun() = STANDARD_BOOSTER_ACTIVE
)
val SHORT_BURST_BOOSTER = BoosterItem(
    itemSettings().maxDamage(101),
    fun() = SHORT_BURST_BOOSTER_ACTIVE
)

val LONG_DURATION_BOOSTER_ACTIVE: ActiveBoosterItem = ActiveBoosterItem(
    Item.Settings().maxDamage(401),
    0.1, 0.9, 0.4, 8,
    fun() = LONG_DURATION_BOOSTER
)
val STANDARD_BOOSTER_ACTIVE: ActiveBoosterItem = ActiveBoosterItem(
    Item.Settings().maxDamage(201),
    0.1, 1.5, 0.5, 4,
    fun() = STANDARD_BOOSTER
)
val SHORT_BURST_BOOSTER_ACTIVE: ActiveBoosterItem = ActiveBoosterItem(
    Item.Settings().maxDamage(101),
    0.2, 2.5, 0.6, 2,
    fun() = SHORT_BURST_BOOSTER
)

fun initBoosters() {
    identifier("booster_standard").item(STANDARD_BOOSTER)
    identifier("booster_standard_active").item(STANDARD_BOOSTER_ACTIVE)
    identifier("booster_fast").item(SHORT_BURST_BOOSTER)
    identifier("booster_fast_active").item(SHORT_BURST_BOOSTER_ACTIVE)
    identifier("booster_slow").item(LONG_DURATION_BOOSTER)
    identifier("booster_slow_active").item(LONG_DURATION_BOOSTER_ACTIVE)
}