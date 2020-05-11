package net.adriantodt.winged

import net.adriantodt.winged.item.LoreItem
import net.adriantodt.winged.item.RemovalKnifeItem
import net.minecraft.item.FoodComponent
import net.minecraft.item.Items
import net.minecraft.util.Rarity

val CORE_OF_FLIGHT = LoreItem(itemSettings().rarity(Rarity.RARE).maxCount(1), 2, true)
val BROKEN_CORE_OF_FLIGHT = LoreItem(itemSettings().maxCount(1).rarity(Rarity.UNCOMMON), 2)
val BLACK_FEATHER = LoreItem(itemSettings().rarity(Rarity.UNCOMMON), 2)
val ANGEL_FEATHER = LoreItem(itemSettings().rarity(Rarity.UNCOMMON), 2)
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

fun initItems() {
    identifier("core_of_flight").item(CORE_OF_FLIGHT)
    identifier("broken_core_of_flight").item(BROKEN_CORE_OF_FLIGHT)
    identifier("attendee_ticket").item(ATTENDEE_TICKET)
    identifier("black_feather").item(BLACK_FEATHER)
    identifier("angel_feather").item(ANGEL_FEATHER)
    identifier("demonic_flesh").item(DEMONIC_FLESH)
    identifier("fried_chicken").item(FRIED_CHICKEN)
    identifier("phoenix_feather").item(PHOENIX_FEATHER)
    identifier("irreality_crystal").item(IRREALITY_CRYSTAL)
    identifier("bat_wing").item(ITEM_BAT_WING)
    identifier("bowl_of_dyes").item(BOWL_OF_DYES)
    identifier("ceremonial_knife").item(CEREMONIAL_KNIFE)
    identifier("dipped_ceremonial_knife").item(DIPPED_CEREMONIAL_KNIFE)
}