package net.adriantodt.winged.item

import net.adriantodt.winged.WingedUtilityItems.emptyBooster
import net.adriantodt.winged.data.WingedData.BoosterVelocity
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundCategory.PLAYERS
import net.minecraft.sound.SoundEvents.BLOCK_PISTON_EXTEND
import net.minecraft.sound.SoundEvents.ENTITY_ITEM_BREAK
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World


class ForwardLauncherItem(settings: Settings, private val data: BoosterVelocity) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)
        if (user.onGround) {
            world.playSound(user, user.blockPos, BLOCK_PISTON_EXTEND, PLAYERS, 0.8f, 1.2f)
            data.applyBoost(user)
            user.onGround = false
            user.method_23668() //checkFallFlying
            if (!user.isCreative && stack.tag?.getBoolean("Unbreakable") != true) {
                stack.damage += 1
                if (stack.damage == maxDamage) {
                    world.playSound(user, user.blockPos, ENTITY_ITEM_BREAK, PLAYERS, 0.8f, 1.2f)
                    return TypedActionResult.success(ItemStack(emptyBooster))
                }
            }
            return TypedActionResult.success(stack)
        }
        return TypedActionResult.fail(stack)
    }

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, ctx: TooltipContext) {
        tooltip += TranslatableText("$translationKey.description")
        tooltip += TranslatableText("tooltip.winged.activate_forward")
    }
}