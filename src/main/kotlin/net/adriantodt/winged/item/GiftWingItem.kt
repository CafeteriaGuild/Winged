package net.adriantodt.winged.item

import net.adriantodt.winged.WingItems
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World
import kotlin.random.asKotlinRandom


class GiftWingItem(settings: Settings) : Item(settings) {

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        user.playSound(SoundEvents.BLOCK_WOOL_BREAK, 1.0f, 0.0f)
        return if (world.isClient)
            TypedActionResult.pass(user.getStackInHand(hand))
        else
            TypedActionResult.success(ItemStack(WingItems.giftableWings.random(user.random.asKotlinRandom())))
    }

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack?, world: World?, tooltip: MutableList<Text?>, ctx: TooltipContext?) {
        tooltip += TranslatableText("$translationKey.description")
        tooltip += TranslatableText("tooltip.winged.gift_wing_item")
    }
}