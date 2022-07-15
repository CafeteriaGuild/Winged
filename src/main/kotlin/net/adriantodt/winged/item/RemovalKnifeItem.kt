package net.adriantodt.winged.item

import io.github.ladysnake.pal.Pal
import io.github.ladysnake.pal.VanillaAbilities
import net.adriantodt.winged.Winged
import net.adriantodt.winged.WingedLoreItems
import net.adriantodt.winged.WingedUtilityItems
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
        val wasFlight = playerComponent.creativeFlight
        playerComponent.creativeFlight = false

        if (!world.isClient) {
            Pal.revokeAbility(user, VanillaAbilities.ALLOW_FLYING, Winged.wingSource)
        }

        val config = Winged.configHolder.config
        if (!user.isCreative) user.giveItemStack(ItemStack(getCoreItem(wasFlight, config.wingRemovalBrokenCore)))
        val dmg = config.removeWingsDamage
        if (dmg > 0 && !world.isClient) {
            user.damage(RemoveWingsDamageSource, dmg)
        }
        user.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0f, 1.0f)
        return TypedActionResult.success(if (user.isCreative) itemStack else ItemStack(ceremonialKnife))
    }

    override fun hasGlint(stack: ItemStack?) = true

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, ctx: TooltipContext) {
        tooltip += Text.translatable("$translationKey.lore1")
        tooltip += Text.translatable("tooltip.winged.remove_wing_item")
    }

    companion object {
        @JvmStatic
        fun getCoreItem(creativeFlight: Boolean, dropBroken: Boolean): Item {
            return when {
                creativeFlight && dropBroken -> WingedUtilityItems.heartOfTheSky75
                creativeFlight && !dropBroken -> WingedUtilityItems.heartOfTheSky
                !creativeFlight && dropBroken -> WingedLoreItems.brokenCoreOfFlight75
                else -> WingedLoreItems.coreOfFlight
            }
        }
    }
}
