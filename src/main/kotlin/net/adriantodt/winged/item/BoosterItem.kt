package net.adriantodt.winged.item

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World


class BoosterItem(settings: Settings, active: () -> ActiveBoosterItem) : Item(settings) {
    private val activeBooster by lazy(active)

    override fun use(world: World?, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)

        if (user.isFallFlying) {
            return TypedActionResult.success(ItemStack(activeBooster).apply {
                damage = stack.damage
                stack.tag?.getInt("TicksLeft")?.let { stack.orCreateTag.putInt("TicksLeft", it) }
            })
        }
        return TypedActionResult.fail(stack)
    }

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, ctx: TooltipContext) {
        tooltip += TranslatableText("$translationKey.description")
        tooltip += TranslatableText("tooltip.winged.activate_booster")
        if (ctx.isAdvanced) {
            tooltip += TranslatableText("tooltip.winged.time_left",
                ((maxDamage - stack.damage) * activeBooster.ticksPerDamage + (stack.tag?.getInt("TicksLeft")
                    ?: 0)) / 20.0
            )
        }
    }
}