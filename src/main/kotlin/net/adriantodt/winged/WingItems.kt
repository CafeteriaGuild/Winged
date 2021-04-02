package net.adriantodt.winged

import net.adriantodt.winged.data.Wing
import net.adriantodt.winged.item.WingItem
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry
import java.util.*
import kotlin.collections.ArrayList

@Suppress("MemberVisibilityCanBePrivate")
object WingItems {
    val elytra = wingItem(mcIdentifier("elytra").wing(Wings.elytra))
    val mojangCape = wingItem(identifier("mojang_cape").wing(Wings.mojangCape))
    val mojangCapeAlt = wingItem(identifier("mojang_cape_alt").wing(Wings.mojangCapeAlt))
    val minecon2011Cape = wingItem(identifier("minecon_2011_cape").wing(Wings.minecon2011Cape))
    val minecon2012Cape = wingItem(identifier("minecon_2012_cape").wing(Wings.minecon2012Cape))
    val minecon2013Cape = wingItem(identifier("minecon_2013_cape").wing(Wings.minecon2013Cape))
    val minecon2015Cape = wingItem(identifier("minecon_2015_cape").wing(Wings.minecon2015Cape))
    val minecon2016Cape = wingItem(identifier("minecon_2016_cape").wing(Wings.minecon2016Cape))
    val minecon2019Cape = wingItem(identifier("minecon_2019_cape").wing(Wings.minecon2019Cape))
    val cobaltCape = wingItem(identifier("cobalt_cape").wing(Wings.cobaltCape))
    val scrollsCape = wingItem(identifier("scrolls_cape").wing(Wings.scrollsCape))
    val crowdinCape = wingItem(identifier("crowdin_cape").wing(Wings.crowdinCape))
    val mapmakerCape = wingItem(identifier("mapmaker_cape").wing(Wings.mapmakerCape))
    val millionthCape = wingItem(identifier("millionth_cape").wing(Wings.millionthCape))
    val mojiraModCape = wingItem(identifier("mojira_mod_cape").wing(Wings.mojiraModCape))
    val dannybstyleCape = wingItem(identifier("dannybstyle_cape").wing(Wings.dannybstyleCape))
    val julianclarkCape = wingItem(identifier("julianclark_cape").wing(Wings.julianclarkCape))
    val julianclarkCapeAlt = wingItem(identifier("julianclark_cape_alt").wing(Wings.julianclarkCapeAlt))
    val mrmessiahCape = wingItem(identifier("mrmessiah_cape").wing(Wings.mrmessiahCape))
    val prismarineCape = wingItem(identifier("prismarine_cape").wing(Wings.prismarineCape))
    val turtleCape = wingItem(identifier("turtle_cape").wing(Wings.turtleCape))
    val eagle = wingItem(identifier("eagle").wing(Wings.eagle))
    val greenwingMacaw = wingItem(identifier("greenwing_macaw").wing(Wings.greenwingMacaw))
    val fairyonline = wingItem(identifier("fairyonline").wing(Wings.fairyonline))
    val angel = wingItem(identifier("angel").wing(Wings.angel))
    val demon = wingItem(identifier("demon").wing(Wings.demon))
    val phoenix = wingItem(identifier("phoenix").wing(Wings.phoenix))
    val dragonfly = wingItem(identifier("dragonfly").wing(Wings.dragonfly))
    val greenBeetle = wingItem(identifier("green_beetle").wing(Wings.greenBeetle))
    val purpleBeetle = wingItem(identifier("purple_beetle").wing(Wings.purpleBeetle))
    val goldenBeetle = wingItem(identifier("golden_beetle").wing(Wings.goldenBeetle))
    val friedChicken = wingItem(identifier("fried_chicken").wing(Wings.friedChicken))
    val greenDragon = wingItem(identifier("green_dragon").wing(Wings.greenDragon))
    val redDragon = wingItem(identifier("red_dragon").wing(Wings.redDragon))
    val fireDragon = wingItem(identifier("fire_dragon").wing(Wings.fireDragon))
    val phantom = wingItem(identifier("phantom").wing(Wings.phantom))
    val phantomMembrane = wingItem(identifier("phantom_membrane").wing(Wings.phantomMembrane))
    val phantomAlt = wingItem(identifier("phantom_alt").wing(Wings.phantomAlt))
    val bat = wingItem(identifier("bat").wing(Wings.bat))
    val batAlt = wingItem(identifier("bat_alt").wing(Wings.batAlt))
    val mechDragon = wingItem(identifier("mech_dragon").wing(Wings.mechDragon))
    val redstone = wingItem(identifier("redstone").wing(Wings.redstone))
    val shulker = wingItem(identifier("shulker").wing(Wings.shulker))
    val tnt = wingItem(identifier("tnt").wing(Wings.tnt))
    val tinyBird = wingItem(identifier("tiny_bird").wing(Wings.tinyBird))
    val vex = wingItem(identifier("vex").wing(Wings.vex))
    val creamyWhite = wingItem(identifier("creamy_white").wing(Wings.creamyWhite))
    val brazil = wingItem(identifier("brazil").wing(Wings.brazil))
    val usa = wingItem(identifier("usa").wing(Wings.usa))
    val earth = wingItem(identifier("earth").wing(Wings.earth))
    val rainbow = wingItem(identifier("rainbow").wing(Wings.rainbow))
    val elytraWhite = wingItem(identifier("white_elytra").wing(Wings.whiteElytra))
    val elytraOrange = wingItem(identifier("orange_elytra").wing(Wings.orangeElytra))
    val elytraMagenta = wingItem(identifier("magenta_elytra").wing(Wings.magentaElytra))
    val elytraLightBlue = wingItem(identifier("light_blue_elytra").wing(Wings.lightBlueElytra))
    val elytraYellow = wingItem(identifier("yellow_elytra").wing(Wings.yellowElytra))
    val elytraLime = wingItem(identifier("lime_elytra").wing(Wings.limeElytra))
    val elytraPink = wingItem(identifier("pink_elytra").wing(Wings.pinkElytra))
    val elytraGray = wingItem(identifier("gray_elytra").wing(Wings.grayElytra))
    val elytraLightGray = wingItem(identifier("light_gray_elytra").wing(Wings.lightGrayElytra))
    val elytraCyan = wingItem(identifier("cyan_elytra").wing(Wings.cyanElytra))
    val elytraPurple = wingItem(identifier("purple_elytra").wing(Wings.purpleElytra))
    val elytraBlue = wingItem(identifier("blue_elytra").wing(Wings.blueElytra))
    val elytraBrown = wingItem(identifier("brown_elytra").wing(Wings.brownElytra))
    val elytraGreen = wingItem(identifier("green_elytra").wing(Wings.greenElytra))
    val elytraRed = wingItem(identifier("red_elytra").wing(Wings.redElytra))
    val elytraBlack = wingItem(identifier("black_elytra").wing(Wings.blackElytra))
    val xmasTree = wingItem(identifier("xmas_tree").wing(Wings.xmasTree))
    val xmasStar = wingItem(identifier("xmas_star").wing(Wings.xmasStar))
    val glider = wingItem(identifier("glider").wing(Wings.glider))
    val spooky = wingItem(identifier("spooky").wing(Wings.spooky))
    val irreality = wingItem(identifier("irreality").wing(Wings.irreality))
    val glass = wingItem(identifier("glass").wing(Wings.glass))

    //TEMPLATE -- FOR MODDERS/ARTISTS -- /GIVE ONLY
    val template = WingItem(Item.Settings().maxCount(1), identifier("template").wing(Wings.template))

    fun register() {
        registerWingItems("wing_elytra", elytra)
        registerWingItems("wing_mojang_cape", mojangCape)
        registerWingItems("wing_mojang_cape_alt", mojangCapeAlt)
        registerWingItems("wing_minecon_2011_cape", minecon2011Cape)
        registerWingItems("wing_minecon_2012_cape", minecon2012Cape)
        registerWingItems("wing_minecon_2013_cape", minecon2013Cape)
        registerWingItems("wing_minecon_2015_cape", minecon2015Cape)
        registerWingItems("wing_minecon_2016_cape", minecon2016Cape)
        registerWingItems("wing_minecon_2019_cape", minecon2019Cape)
        registerWingItems("wing_cobalt_cape", cobaltCape)
        registerWingItems("wing_scrolls_cape", scrollsCape)
        registerWingItems("wing_crowdin_cape", crowdinCape)
        registerWingItems("wing_mapmaker_cape", mapmakerCape)
        registerWingItems("wing_millionth_cape", millionthCape)
        registerWingItems("wing_mojira_mod_cape", mojiraModCape)
        registerWingItems("wing_dannybstyle_cape", dannybstyleCape)
        registerWingItems("wing_julianclark_cape", julianclarkCape)
        registerWingItems("wing_julianclark_cape_alt", julianclarkCapeAlt)
        registerWingItems("wing_mrmessiah_cape", mrmessiahCape)
        registerWingItems("wing_prismarine_cape", prismarineCape)
        registerWingItems("wing_turtle_cape", turtleCape)
        registerWingItems("wing_eagle", eagle)
        registerWingItems("wing_greenwing_macaw", greenwingMacaw)
        registerWingItems("wing_fairyonline", fairyonline)
        registerWingItems("wing_angel", angel)
        registerWingItems("wing_demon", demon)
        registerWingItems("wing_phoenix", phoenix)
        registerWingItems("wing_dragonfly", dragonfly)
        registerWingItems("wing_green_beetle", greenBeetle)
        registerWingItems("wing_purple_beetle", purpleBeetle)
        registerWingItems("wing_golden_beetle", goldenBeetle)
        registerWingItems("wing_fried_chicken", friedChicken)
        registerWingItems("wing_green_dragon", greenDragon)
        registerWingItems("wing_red_dragon", redDragon)
        registerWingItems("wing_fire_dragon", fireDragon)
        registerWingItems("wing_phantom", phantom)
        registerWingItems("wing_phantom_alt", phantomAlt)
        registerWingItems("wing_phantom_membrane", phantomMembrane)
        registerWingItems("wing_bat", bat)
        registerWingItems("wing_bat_alt", batAlt)
        registerWingItems("wing_mech_dragon", mechDragon)
        registerWingItems("wing_redstone", redstone)
        registerWingItems("wing_shulker", shulker)
        registerWingItems("wing_tnt", tnt)
        registerWingItems("wing_tiny_bird", tinyBird)
        registerWingItems("wing_vex", vex)
        registerWingItems("wing_creamy_white", creamyWhite)
        registerWingItems("wing_brazil", brazil)
        registerWingItems("wing_usa", usa)
        registerWingItems("wing_earth", earth)
        registerWingItems("wing_rainbow", rainbow)
        registerWingItems("wing_elytra_white", elytraWhite)
        registerWingItems("wing_elytra_orange", elytraOrange)
        registerWingItems("wing_elytra_magenta", elytraMagenta)
        registerWingItems("wing_elytra_light_blue", elytraLightBlue)
        registerWingItems("wing_elytra_yellow", elytraYellow)
        registerWingItems("wing_elytra_lime", elytraLime)
        registerWingItems("wing_elytra_pink", elytraPink)
        registerWingItems("wing_elytra_gray", elytraGray)
        registerWingItems("wing_elytra_light_gray", elytraLightGray)
        registerWingItems("wing_elytra_cyan", elytraCyan)
        registerWingItems("wing_elytra_purple", elytraPurple)
        registerWingItems("wing_elytra_blue", elytraBlue)
        registerWingItems("wing_elytra_brown", elytraBrown)
        registerWingItems("wing_elytra_green", elytraGreen)
        registerWingItems("wing_elytra_red", elytraRed)
        registerWingItems("wing_elytra_black", elytraBlack)
        registerWingItems("wing_xmas_tree", xmasTree)
        registerWingItems("wing_xmas_star", xmasStar)
        registerWingItems("wing_glider", glider)
        registerWingItems("wing_spooky", spooky)
        registerWingItems("wing_irreality", irreality)
        registerWingItems("wing_glass", glass)

        //TEMPLATE -- FOR MODDERS/ARTISTS -- /GIVE ONLY
        identifier("wing_template").item(template)
    }

    private fun registerWingItems(path: String, pair: Pair<WingItem, WingItem>, giftable: Boolean = false) {
        identifier(path).item(pair.first.also { if (giftable) it.giftable() })
        identifier("${path}_creative_flight").item(pair.second)
    }

    private fun wingItem(identifier: Identifier) = WingItem(
        Item.Settings().group(Winged.showcaseGroup).rarity(Rarity.EPIC).maxCount(1),
        identifier
    ) to WingItem(
        Item.Settings().group(Winged.showcaseGroup).rarity(Rarity.EPIC).maxCount(1),
        identifier,
        true
    )

    val giftableWings = ArrayList<WingItem>()

    fun randomWing(random: Random) = giftableWings.elementAt(random.nextInt(giftableWings.size))

    private fun Identifier.wing(wing: Wing) = apply {
        Registry.register(Winged.wingRegistry, this, wing)
    }

    private fun WingItem.giftable() = apply { giftableWings += this }
}