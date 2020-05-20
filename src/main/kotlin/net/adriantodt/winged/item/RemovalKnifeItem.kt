package net.adriantodt.winged.item

import net.adriantodt.winged.Winged
import net.adriantodt.winged.WingedLoreItems.brokenCoreOfFlight
import net.adriantodt.winged.WingedUtilityItems.ceremonialKnife
import net.adriantodt.winged.damagesource.RemoveWingsDamageSource
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
        val playerComponent = Winged.playerComponentType[user]
        if (playerComponent.wing == null) {
            return TypedActionResult.fail(itemStack)
        }
        playerComponent.wing = null
        if (!user.isCreative) user.giveItemStack(ItemStack(brokenCoreOfFlight))
        val dmg = Winged.data.removeWingsDamage
        if (dmg > 0) {
            user.damage(RemoveWingsDamageSource, dmg)
        }
        user.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0f, 1.0f)
        return TypedActionResult.success(if (user.isCreative) itemStack else ItemStack(ceremonialKnife))
    }

    override fun hasEnchantmentGlint(stack: ItemStack?) = true

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, ctx: TooltipContext) {
        tooltip.add(TranslatableText("$translationKey.lore1"))
        tooltip.add(TranslatableText("tooltip.winged.remove_wing_item"))
    }
}