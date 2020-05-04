package net.adriantodt.winged.item

import net.adriantodt.winged.BROKEN_CORE_OF_FLIGHT
import net.adriantodt.winged.CEREMONIAL_KNIFE
import net.adriantodt.winged.IsWinged
import net.adriantodt.winged.REMOVE_WINGS
import net.adriantodt.winged.data.WingedPlayerData
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

class RemovalKnifeItem(settings: Settings) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val itemStack = user.getStackInHand(hand)
        if (user !is IsWinged) {
            return TypedActionResult.pass(itemStack)
        }
        if (user.wingedPlayerData.wing == null) {
            return TypedActionResult.fail(itemStack)
        }
        user.wingedPlayerData = WingedPlayerData.NO_WING
        user.giveItemStack(ItemStack(BROKEN_CORE_OF_FLIGHT))
        user.damage(REMOVE_WINGS, 12f)
        user.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0f, 1.0f)
        return TypedActionResult.success(ItemStack(CEREMONIAL_KNIFE))
    }

    override fun hasEnchantmentGlint(stack: ItemStack?) = true

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack?, world: World?, tooltip: MutableList<Text?>, ctx: TooltipContext?) {
        tooltip += TranslatableText("$translationKey.lore1")
        tooltip += TranslatableText("tooltip.winged.remove_wing_item")
    }
}