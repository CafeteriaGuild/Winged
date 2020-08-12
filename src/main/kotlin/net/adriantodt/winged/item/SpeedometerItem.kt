package net.adriantodt.winged.item

import net.adriantodt.winged.Winged
import net.adriantodt.winged.WingedLivingEntity
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World


class SpeedometerItem(settings: Settings) : Item(settings) {
    override fun use(world: World?, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)

        val copy = stack.copy()
        val tag = copy.orCreateTag
        tag.putBoolean("Active", !tag.getBoolean("Active"))
        return TypedActionResult.success(copy)
    }

    override fun inventoryTick(stack: ItemStack, world: World, user: Entity, slot: Int, selected: Boolean) {
        if (user is PlayerEntity && user is WingedLivingEntity && world.isClient && stack.tag?.getBoolean("Active") == true) {
            val unit = Winged.configHolder.config.velocityUnit
            val lastPos = user.lastPos
            if (lastPos != null) {
                val speed = lastPos.distanceTo(user.pos) * 20 * unit.multiplier
                user.sendMessage(LiteralText("| %.4f %s |".format(speed, unit.suffix)), true)
            }
        }
    }

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, ctx: TooltipContext) {
        tooltip += TranslatableText("$translationKey.description")
        tooltip += TranslatableText("tooltip.winged.configure_speedometer")
        tooltip += if (stack.tag?.getBoolean("Active") == true) {
            TranslatableText("tooltip.winged.deactivate_speedometer")
        } else {
            TranslatableText("tooltip.winged.activate_speedometer")
        }
    }
}