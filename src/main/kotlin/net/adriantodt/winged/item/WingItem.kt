package net.adriantodt.winged.item

import io.github.ladysnake.pal.Pal
import io.github.ladysnake.pal.VanillaAbilities
import net.adriantodt.winged.Winged
import net.adriantodt.winged.Winged.playerComponentType
import net.adriantodt.winged.Winged.wingRegistry
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.ModifyEntries
import net.minecraft.block.Blocks
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA
import net.minecraft.text.Text
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.TypedActionResult
import net.minecraft.util.Util
import net.minecraft.world.World


class WingItem(settings: Settings, val wingId: Identifier, val creativeFlight: Boolean = false) : Item(settings) {
    private val wing by lazy { wingRegistry[wingId] }
    private val wingTranslationKey: String = Util.createTranslationKey("wing", wingId)

    init {
        ItemGroupEvents.modifyEntriesEvent(Winged.showcaseGroupKey).register(ModifyEntries { content -> content.add(this) })
    }
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val itemStack = user.getStackInHand(hand)
        val wingedComponent =
            playerComponentType.maybeGet(user).orElse(null) ?: return TypedActionResult.pass(itemStack)
        if (wingedComponent.wing != null) {
            return TypedActionResult.fail(itemStack)
        }
        wingedComponent.wing = wing
        wingedComponent.creativeFlight = creativeFlight
        if (!world.isClient && creativeFlight) {
            Pal.grantAbility(user, VanillaAbilities.ALLOW_FLYING, Winged.wingSource)
        }
        itemStack.count = 0
        user.playSound(ITEM_ARMOR_EQUIP_ELYTRA, 1.0f, 1.0f)
        return TypedActionResult.success(itemStack)
    }

    override fun getOrCreateTranslationKey() = wingTranslationKey

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack?, world: World?, tooltip: MutableList<Text?>, ctx: TooltipContext?) {
        if (creativeFlight) {
            tooltip += Text.translatable("text.winged.creativeFlight")
        }
        tooltip += Text.translatable("$wingTranslationKey.description")
        tooltip += Text.translatable("tooltip.winged.any_wing_item")
        wing.authors?.let { tooltip += Text.translatable("tooltip.winged.wing_author", it) }
    }
}
