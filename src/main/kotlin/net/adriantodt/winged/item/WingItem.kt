package net.adriantodt.winged.item

import net.adriantodt.winged.IsWinged
import net.adriantodt.winged.WING_REGISTRY
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
import net.minecraft.util.Identifier
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World


class WingItem(settings: Settings, private val wingId: Identifier) : Item(settings) {
    private val wing by lazy { WING_REGISTRY[wingId] }

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val itemStack = user.getStackInHand(hand)
        if (user !is IsWinged) {
            return TypedActionResult.pass(itemStack)
        }
        if (user.wingedPlayerData.wing != null) {
            return TypedActionResult.fail(itemStack)
        }
        user.wingedPlayerData = WingedPlayerData(wing)
        itemStack.count = 0
        user.playSound(SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA, 1.0f, 1.0f)
        return TypedActionResult.success(itemStack)
    }

    override fun hasEnchantmentGlint(stack: ItemStack?): Boolean {
        return wing.enchantmentGlint || super.hasEnchantmentGlint(stack)
    }

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack?, world: World?, tooltip: MutableList<Text?>, ctx: TooltipContext?) {
        tooltip += TranslatableText("$translationKey.description")
        tooltip += TranslatableText("tooltip.winged.any_wing_item")
    }
}