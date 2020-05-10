package net.adriantodt.winged.item

import net.adriantodt.winged.boostTheLivingShitOfThisMotherFucker
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World


class ForwardLauncherItem(settings: Settings) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)

        if (user.isOnGround) {
            world.playSound(user, user.blockPos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.PLAYERS, 0.8f, 1.2f)
            user.boostTheLivingShitOfThisMotherFucker(1.5, 0.1, 0.5)
            user.isOnGround = false
            user.checkFallFlying()
            if (!user.isCreative) {
                stack.damage += 1
                if (stack.damage == maxDamage) {
                    world.playSound(
                        user,
                        user.blockPos,
                        SoundEvents.ENTITY_ITEM_BREAK,
                        SoundCategory.PLAYERS,
                        0.8f,
                        1.2f
                    )
                    stack.count = 0
                    user.sendEquipmentBreakStatus(
                        when (hand) {
                            Hand.MAIN_HAND -> EquipmentSlot.MAINHAND
                            Hand.OFF_HAND -> EquipmentSlot.OFFHAND
                        }
                    )
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