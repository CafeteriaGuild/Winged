package net.adriantodt.winged

import net.adriantodt.winged.data.Wing
import net.adriantodt.winged.item.WingItem
import net.minecraft.util.Identifier

val ELYTRA_WING = Wing(Identifier("textures/entity/elytra.png"), true)
val FIRE_DRAGON_WING = Wing(identifier("textures/entity/fire_dragon_wing.png"))

val ITEM_WING_ELYTRA = WingItem(wingItemSettings(), Identifier("minecraft", "elytra").wing(ELYTRA_WING))

val ITEM_WING_FIRE_DRAGON = WingItem(wingItemSettings(), identifier("fire_dragon_wing").wing(FIRE_DRAGON_WING))

fun initWings() {
    identifier("wing_elytra").item(ITEM_WING_ELYTRA)
    identifier("wing_fire_dragon").item(ITEM_WING_FIRE_DRAGON)
}