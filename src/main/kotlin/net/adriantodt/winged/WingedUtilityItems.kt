package net.adriantodt.winged

import net.adriantodt.winged.item.GiftWingItem
import net.adriantodt.winged.item.HeartOfTheSkyItem
import net.adriantodt.winged.item.RemovalKnifeItem
import net.minecraft.util.Rarity

@Suppress("MemberVisibilityCanBePrivate")
object WingedUtilityItems {
    val ceremonialKnife = itemSettings().maxCount(1).loreItem()
    val dippedCeremonialKnife = RemovalKnifeItem(itemSettings().rarity(Rarity.RARE).maxCount(1))

    val heartOfTheSky = HeartOfTheSkyItem(itemSettings().rarity(Rarity.EPIC))
    val heartOfTheSky25 = itemSettings().rarity(Rarity.UNCOMMON).loreItem()
    val heartOfTheSky50 = itemSettings().rarity(Rarity.UNCOMMON).loreItem()
    val heartOfTheSky75 = itemSettings().rarity(Rarity.UNCOMMON).loreItem()
    val randomWing = GiftWingItem(itemSettings().rarity(Rarity.EPIC).maxCount(1))

    fun register() {
        identifier("ceremonial_knife").item(ceremonialKnife)
        identifier("dipped_ceremonial_knife").item(dippedCeremonialKnife)

        identifier("heart_of_the_sky").item(heartOfTheSky)
        identifier("heart_of_the_sky_25").item(heartOfTheSky25)
        identifier("heart_of_the_sky_50").item(heartOfTheSky50)
        identifier("heart_of_the_sky_75").item(heartOfTheSky75)
        identifier("wing_random").item(randomWing)
    }
}