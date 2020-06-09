package net.adriantodt.winged

import net.minecraft.util.Rarity

@Suppress("MemberVisibilityCanBePrivate")
object WingedLoreItems {
    val coreOfFlight = itemSettings().rarity(Rarity.RARE).maxCount(1).loreItem(glint = true)
    val brokenCoreOfFlight = itemSettings().maxCount(1).rarity(Rarity.UNCOMMON).loreItem()

    val batWing = itemSettings().rarity(Rarity.UNCOMMON).loreItem()
    val blackFeather = itemSettings().rarity(Rarity.UNCOMMON).loreItem()
    val angelFeather = itemSettings().rarity(Rarity.UNCOMMON).loreItem()
    val phoenixFeather = itemSettings().rarity(Rarity.UNCOMMON).loreItem()
    val friedChicken = itemSettings().rarity(Rarity.UNCOMMON)
        .food { hunger(6).saturationModifier(1.6f).meat().snack() }.loreItem()
    val irrealityCrystal = itemSettings().rarity(Rarity.UNCOMMON).loreItem()
    val demonicFlesh = itemSettings().rarity(Rarity.UNCOMMON).loreItem()
    val bowlOfDyes = itemSettings().recipeRemainder(net.minecraft.item.Items.BOWL).loreItem()
    val attendeeTicket = itemSettings().rarity(Rarity.UNCOMMON).loreItem()
    val vexEssence = itemSettings().rarity(Rarity.UNCOMMON).loreItem()
    val dragonScale = itemSettings().rarity(Rarity.UNCOMMON).loreItem()

    fun register() {
        identifier("core_of_flight").item(coreOfFlight)
        identifier("broken_core_of_flight").item(brokenCoreOfFlight)

        identifier("bat_wing").item(batWing)
        identifier("black_feather").item(blackFeather)
        identifier("angel_feather").item(angelFeather)
        identifier("phoenix_feather").item(phoenixFeather)
        identifier("fried_chicken").item(friedChicken)
        identifier("irreality_crystal").item(irrealityCrystal)
        identifier("demonic_flesh").item(demonicFlesh)
        identifier("bowl_of_dyes").item(bowlOfDyes)
        identifier("attendee_ticket").item(attendeeTicket)
        identifier("vex_essence").item(vexEssence)
        identifier("dragon_scale").item(dragonScale)
    }
}