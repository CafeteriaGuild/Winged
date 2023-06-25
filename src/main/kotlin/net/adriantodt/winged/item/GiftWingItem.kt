package net.adriantodt.winged.item

import net.adriantodt.winged.WingItems
import net.adriantodt.winged.Winged
import net.adriantodt.winged.random
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.ModifyEntries
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World
import kotlin.random.asKotlinRandom


class GiftWingItem(settings: Settings, private val creativeFlight: Boolean = false) : Item(settings) {

    init {
        ItemGroupEvents.modifyEntriesEvent(Winged.mainGroupKey).register(ModifyEntries { content -> content.add(this) })
    }

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        user.playSound(SoundEvents.BLOCK_WOOL_BREAK, 1.0f, 0.0f)
        return if (world.isClient) {
            TypedActionResult.pass(user.getStackInHand(hand))
        } else {
            TypedActionResult.success(ItemStack(wingVariations(user)))
        }
    }

    private fun wingVariations(user: PlayerEntity): WingItem {
        val variation = WingItems.giftableWings.random(user.random)
        return if (creativeFlight) variation.creativeFlight else variation.standard
    }

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack?, world: World?, tooltip: MutableList<Text?>, ctx: TooltipContext?) {
        if (creativeFlight) {
            tooltip += Text.translatable("text.winged.creativeFlight")
        }
        tooltip += Text.translatable("$translationKey.description")
        tooltip += Text.translatable("tooltip.winged.gift_wing_item")
    }
}
