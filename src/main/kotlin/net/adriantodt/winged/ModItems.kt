package net.adriantodt.winged

import net.adriantodt.winged.item.ActiveBoosterItem
import net.adriantodt.winged.item.BoosterItem
import net.adriantodt.winged.item.LoreItem
import net.adriantodt.winged.item.RemovalKnifeItem
import net.minecraft.item.FoodComponent
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.util.Rarity

val CORE_OF_FLIGHT = LoreItem(itemSettings().rarity(Rarity.RARE).maxCount(1), 2, true)
val BROKEN_CORE_OF_FLIGHT = LoreItem(itemSettings().maxCount(1).rarity(Rarity.UNCOMMON), 2)
val BLACK_FEATHER = LoreItem(itemSettings().rarity(Rarity.UNCOMMON), 2)
val ATTENDEE_TICKET = LoreItem(itemSettings().rarity(Rarity.UNCOMMON), 2)
val DEMONIC_FLESH = LoreItem(itemSettings().rarity(Rarity.UNCOMMON), 2)
val FRIED_CHICKEN = LoreItem(
    itemSettings().rarity(Rarity.UNCOMMON).food(
        FoodComponent.Builder().hunger(6).saturationModifier(1.6f).meat().snack().build()
    ), 2
)
val PHOENIX_FEATHER = LoreItem(itemSettings().rarity(Rarity.UNCOMMON), 2)
val IRREALITY_CRYSTAL = LoreItem(itemSettings().rarity(Rarity.UNCOMMON), 2)
val ITEM_BAT_WING = LoreItem(itemSettings().rarity(Rarity.UNCOMMON), 2)
val BOWL_OF_DYES = LoreItem(itemSettings().recipeRemainder(Items.BOWL), 2)
val CEREMONIAL_KNIFE = LoreItem(itemSettings().maxCount(1), 2)
val DIPPED_CEREMONIAL_KNIFE = RemovalKnifeItem(itemSettings().rarity(Rarity.RARE).maxCount(1))

val LONG_DURATION_BOOSTER = BoosterItem(itemSettings().maxDamage(800)) { LONG_DURATION_BOOSTER_ACTIVE }
val STANDARD_BOOSTER = BoosterItem(itemSettings().maxDamage(400)) { SHORT_BURST_BOOSTER_ACTIVE }
val SHORT_BURST_BOOSTER = BoosterItem(itemSettings().maxDamage(100)) { SHORT_BURST_BOOSTER_ACTIVE }

val LONG_DURATION_BOOSTER_ACTIVE: Item = ActiveBoosterItem(
    Item.Settings().maxDamage(800),
    0.05, 0.9, 0.4, 20,
    fun() = LONG_DURATION_BOOSTER
)
val STANDARD_BOOSTER_ACTIVE: Item = ActiveBoosterItem(
    Item.Settings().maxDamage(400),
    0.1, 1.5, 0.5, 10,
    fun() = STANDARD_BOOSTER
)
val SHORT_BURST_BOOSTER_ACTIVE: Item = ActiveBoosterItem(
    Item.Settings().maxDamage(100),
    0.2, 2.5, 0.6, 2,
    fun() = SHORT_BURST_BOOSTER
)

fun initItems() {
    identifier("core_of_flight").item(CORE_OF_FLIGHT)
    identifier("broken_core_of_flight").item(BROKEN_CORE_OF_FLIGHT)
    identifier("attendee_ticket").item(ATTENDEE_TICKET)
    identifier("black_feather").item(BLACK_FEATHER)
    identifier("demonic_flesh").item(DEMONIC_FLESH)
    identifier("fried_chicken").item(FRIED_CHICKEN)
    identifier("phoenix_feather").item(PHOENIX_FEATHER)
    identifier("irreality_crystal").item(IRREALITY_CRYSTAL)
    identifier("bat_wing").item(ITEM_BAT_WING)
    identifier("bowl_of_dyes").item(BOWL_OF_DYES)
    identifier("ceremonial_knife").item(CEREMONIAL_KNIFE)
    identifier("dipped_ceremonial_knife").item(DIPPED_CEREMONIAL_KNIFE)

    identifier("booster_standard").item(STANDARD_BOOSTER)
    identifier("booster_standard_active").item(STANDARD_BOOSTER_ACTIVE)
    identifier("booster_fast").item(SHORT_BURST_BOOSTER)
    identifier("booster_fast_active").item(SHORT_BURST_BOOSTER_ACTIVE)
    identifier("booster_slow").item(LONG_DURATION_BOOSTER)
    identifier("booster_slow_active").item(LONG_DURATION_BOOSTER_ACTIVE)
}