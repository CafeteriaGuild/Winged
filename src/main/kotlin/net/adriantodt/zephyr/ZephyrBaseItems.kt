package net.adriantodt.zephyr

import net.adriantodt.zephyr.item.HeartOfTheSkyItem
import net.minecraft.util.Rarity

object ZephyrBaseItems {
    val zephyrJewel = itemSettings().rarity(Rarity.RARE).maxDamage(1200).loreItem(glint = true)
    val zephyrJewelBroken = itemSettings().maxCount(1).loreItem()
    val zephyrCrystal = itemSettings().loreItem(glint = true)
    val heartOfTheSky = HeartOfTheSkyItem(itemSettings().rarity(Rarity.EPIC).maxDamage(1600))

    fun register() {
        "zephyr_jewel" % zephyrJewel
        "zephyr_jewel_broken" % zephyrJewelBroken
        "zephyr_crystal" % zephyrCrystal
        "heart_of_the_sky" % heartOfTheSky
    }
}
