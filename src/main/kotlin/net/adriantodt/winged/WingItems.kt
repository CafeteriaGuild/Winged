package net.adriantodt.winged

import net.adriantodt.winged.data.Wing
import net.adriantodt.winged.item.WingItem
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.util.registry.Registry

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
    val spookyAlt = wingItem(identifier("spooky_alt").wing(Wings.spookyAlt))
    val irreality = wingItem(identifier("irreality").wing(Wings.irreality))
    val glass = wingItem(identifier("glass").wing(Wings.glass))

    //TEMPLATE -- FOR MODDERS/ARTISTS -- /GIVE ONLY
    val template = wingItem(identifier("template").wing(Wings.template))

    fun register() {
        registerWings("wing_elytra", elytra)
        registerWings("wing_mojang_cape", mojangCape)
        registerWings("wing_mojang_cape_alt", mojangCapeAlt)
        registerWings("wing_minecon_2011_cape", minecon2011Cape)
        registerWings("wing_minecon_2012_cape", minecon2012Cape)
        registerWings("wing_minecon_2013_cape", minecon2013Cape)
        registerWings("wing_minecon_2015_cape", minecon2015Cape)
        registerWings("wing_minecon_2016_cape", minecon2016Cape)
        registerWings("wing_minecon_2019_cape", minecon2019Cape)
        registerWings("wing_cobalt_cape", cobaltCape)
        registerWings("wing_scrolls_cape", scrollsCape)
        registerWings("wing_crowdin_cape", crowdinCape)
        registerWings("wing_mapmaker_cape", mapmakerCape)
        registerWings("wing_millionth_cape", millionthCape)
        registerWings("wing_mojira_mod_cape", mojiraModCape)
        registerWings("wing_dannybstyle_cape", dannybstyleCape)
        registerWings("wing_julianclark_cape", julianclarkCape)
        registerWings("wing_julianclark_cape_alt", julianclarkCapeAlt)
        registerWings("wing_mrmessiah_cape", mrmessiahCape)
        registerWings("wing_prismarine_cape", prismarineCape)
        registerWings("wing_turtle_cape", turtleCape)
        registerWings("wing_eagle", eagle)
        registerWings("wing_greenwing_macaw", greenwingMacaw)
        registerWings("wing_fairyonline", fairyonline)
        registerWings("wing_angel", angel)
        registerWings("wing_demon", demon)
        registerWings("wing_phoenix", phoenix)
        registerWings("wing_dragonfly", dragonfly)
        registerWings("wing_green_beetle", greenBeetle)
        registerWings("wing_purple_beetle", purpleBeetle)
        registerWings("wing_golden_beetle", goldenBeetle)
        registerWings("wing_fried_chicken", friedChicken)
        registerWings("wing_green_dragon", greenDragon)
        registerWings("wing_red_dragon", redDragon)
        registerWings("wing_fire_dragon", fireDragon)
        registerWings("wing_phantom", phantom)
        registerWings("wing_phantom_alt", phantomAlt)
        registerWings("wing_phantom_membrane", phantomMembrane)
        registerWings("wing_bat", bat)
        registerWings("wing_bat_alt", batAlt)
        registerWings("wing_mech_dragon", mechDragon)
        registerWings("wing_redstone", redstone)
        registerWings("wing_shulker", shulker)
        registerWings("wing_tnt", tnt)
        registerWings("wing_tiny_bird", tinyBird)
        registerWings("wing_vex", vex)
        registerWings("wing_creamy_white", creamyWhite)
        registerWings("wing_brazil", brazil)
        registerWings("wing_usa", usa)
        registerWings("wing_earth", earth)
        registerWings("wing_rainbow", rainbow)
        registerWings("wing_elytra_white", elytraWhite)
        registerWings("wing_elytra_orange", elytraOrange)
        registerWings("wing_elytra_magenta", elytraMagenta)
        registerWings("wing_elytra_light_blue", elytraLightBlue)
        registerWings("wing_elytra_yellow", elytraYellow)
        registerWings("wing_elytra_lime", elytraLime)
        registerWings("wing_elytra_pink", elytraPink)
        registerWings("wing_elytra_gray", elytraGray)
        registerWings("wing_elytra_light_gray", elytraLightGray)
        registerWings("wing_elytra_cyan", elytraCyan)
        registerWings("wing_elytra_purple", elytraPurple)
        registerWings("wing_elytra_blue", elytraBlue)
        registerWings("wing_elytra_brown", elytraBrown)
        registerWings("wing_elytra_green", elytraGreen)
        registerWings("wing_elytra_red", elytraRed)
        registerWings("wing_elytra_black", elytraBlack)
        registerWings("wing_xmas_tree", xmasTree)
        registerWings("wing_xmas_star", xmasStar)
        registerWings("wing_glider", glider)
        registerWings("wing_spooky", spooky)
        registerWings("wing_spooky_alt", spookyAlt)
        registerWings("wing_irreality", irreality)
        registerWings("wing_glass", glass)

        //TEMPLATE -- FOR MODDERS/ARTISTS -- /GIVE ONLY
        registerWings("wing_template", template, false)
    }

    private fun registerWings(path: String, variations: WingVariations, giftable: Boolean = true) {
        if (giftable) {
            giftableWings += variations
        }
        identifier(path).item(variations.standard)
        identifier("${path}_creative_flight").item(variations.creativeFlight)
    }

    private fun wingItem(identifier: Identifier): WingVariations {
        return WingVariations(
            standard = WingItem(
                Item.Settings().group(Winged.showcaseGroup).rarity(Rarity.RARE).maxCount(1),
                identifier
            ),
            creativeFlight = WingItem(
                Item.Settings().group(Winged.showcaseGroup).rarity(Rarity.EPIC).maxCount(1),
                identifier,
                true
            )
        )
    }

    val giftableWings = ArrayList<WingVariations>()

    private fun Identifier.wing(wing: Wing) = apply {
        Registry.register(Winged.wingRegistry, this, wing)
    }

    data class WingVariations(
        val standard: WingItem,
        val creativeFlight: WingItem
    ) {
        val asList by lazy { listOf(standard, creativeFlight) }
    }
}
