package net.adriantodt.winged

import net.adriantodt.winged.data.Wing
import net.adriantodt.winged.item.WingItem

val ELYTRA_WING = Wing(mcIdentifier("textures/entity/elytra.png"), "Mojang")
val FIRE_DRAGON_WING = Wing(identifier("textures/entity/fire_dragon_wing.png"), "Lemoncake, AdrianTodt")
val PHANTOM_WING = Wing(identifier("textures/entity/phantom_wing.png"), "Noatry")
val PHANTOM_WING_ALT = Wing(identifier("textures/entity/phantom_wing_alt.png"), "Shosh99")
val BAT_WING = Wing(identifier("textures/entity/bat_wing.png"), "Lemoncake")
val BAT_WING_ALT = Wing(identifier("textures/entity/bat_wing_alt.png"), "Doctor_CZ")
val REDSTONE_WING = Wing(identifier("textures/entity/redstone_wing.png"), "dbrighthd")
val SHULKER_WING = Wing(identifier("textures/entity/shulker_wing.png"), "dbrighthd")
val TNT_WING = Wing(identifier("textures/entity/tnt_wing.png"), "dbrighthd")

val ITEM_WING_ELYTRA = WingItem(wingItemSettings(), mcIdentifier("elytra").wing(ELYTRA_WING))
val ITEM_WING_FIRE_DRAGON = WingItem(wingItemSettings(), identifier("fire_dragon_wing").wing(FIRE_DRAGON_WING))
val ITEM_WING_PHANTOM = WingItem(wingItemSettings(), identifier("phantom_wing").wing(PHANTOM_WING))
val ITEM_WING_PHANTOM_ALT = WingItem(wingItemSettings(), identifier("phantom_wing_alt").wing(PHANTOM_WING_ALT))
val ITEM_WING_BAT = WingItem(wingItemSettings(), identifier("bat_wing").wing(BAT_WING))
val ITEM_WING_BAT_ALT = WingItem(wingItemSettings(), identifier("bat_wing_alt").wing(BAT_WING_ALT))
val ITEM_WING_REDSTONE = WingItem(wingItemSettings(), identifier("redstone_wing").wing(REDSTONE_WING))
val ITEM_WING_SHULKER = WingItem(wingItemSettings(), identifier("shulker_wing").wing(SHULKER_WING))
val ITEM_WING_TNT = WingItem(wingItemSettings(), identifier("tnt_wing").wing(TNT_WING))

fun initWings() {
    identifier("wing_elytra").item(ITEM_WING_ELYTRA)
    identifier("wing_fire_dragon").item(ITEM_WING_FIRE_DRAGON)
    identifier("wing_phantom").item(ITEM_WING_PHANTOM)
    identifier("wing_phantom_alt").item(ITEM_WING_PHANTOM_ALT)
    identifier("wing_bat").item(ITEM_WING_BAT)
    identifier("wing_bat_alt").item(ITEM_WING_BAT_ALT)
    identifier("wing_redstone").item(ITEM_WING_REDSTONE)
    identifier("wing_shulker").item(ITEM_WING_SHULKER)
    identifier("wing_tnt").item(ITEM_WING_TNT)
}