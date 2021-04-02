package net.adriantodt.winged.item

import io.github.ladysnake.pal.VanillaAbilities
import net.adriantodt.winged.Winged
import net.adriantodt.winged.Winged.playerComponentType
import net.adriantodt.winged.Winged.wingRegistry
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.TypedActionResult
import net.minecraft.util.Util
import net.minecraft.world.World


class WingItem(settings: Settings, val wingId: Identifier, val creativeFlight: Boolean = false) : Item(settings) {
    private val wing by lazy { wingRegistry[wingId] }
    private val wingTranslationKey: String = Util.createTranslationKey("wing", wingId)

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val itemStack = user.getStackInHand(hand)
        val wingedComponent =
            playerComponentType.maybeGet(user).orElse(null) ?: return TypedActionResult.pass(itemStack)
        if (wingedComponent.wing != null) {
            return TypedActionResult.fail(itemStack)
        }
        wingedComponent.wing = wing
        wingedComponent.creativeFlight = creativeFlight
        if (!world.isClient) {
            if (creativeFlight) {
                val allowFlyingTracker = VanillaAbilities.ALLOW_FLYING.getTracker(user)
                if (!allowFlyingTracker.isGrantedBy(Winged.heartOfTheSkyAbilitySource))
                    allowFlyingTracker.addSource(Winged.heartOfTheSkyAbilitySource)
            }
        }
        itemStack.count = 0
        user.playSound(ITEM_ARMOR_EQUIP_ELYTRA, 1.0f, 1.0f)
        return TypedActionResult.success(itemStack)
    }

    override fun getOrCreateTranslationKey() = wingTranslationKey

    override fun getName(itemStack: ItemStack): Text {
        return if (creativeFlight) LiteralText("").append(super.getName()).append(" (Creative)") else super.getName()
    }

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack?, world: World?, tooltip: MutableList<Text?>, ctx: TooltipContext?) {
        tooltip += TranslatableText("$wingTranslationKey.description")
        tooltip += TranslatableText("tooltip.winged.any_wing_item")
        wing.authors?.let { tooltip += TranslatableText("tooltip.winged.wing_author", it) }
    }
}