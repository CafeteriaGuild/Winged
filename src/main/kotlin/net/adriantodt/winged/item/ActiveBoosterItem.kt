package net.adriantodt.winged.item

import net.adriantodt.winged.WingedPlayerInventory
import net.adriantodt.winged.WingedUtilityItems.emptyBooster
import net.adriantodt.winged.data.WingedData
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.particle.ParticleTypes
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World


class ActiveBoosterItem(settings: Settings, private val data: WingedData.BoosterData) : Item(settings) {
    override fun use(world: World?, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        return TypedActionResult.success(data.toBooster(user.getStackInHand(hand)))
    }

    override fun inventoryTick(stack: ItemStack, world: World, user: Entity, slot: Int, selected: Boolean) {
        if (user is PlayerEntity) {
            val inv = user.inventory as WingedPlayerInventory
            if (user.isFallFlying) {
                val ticksLeft = stack.tag?.getInt("TicksLeft") ?: 0
                if (world.isClient && ticksLeft % 5 == 1) {
                    world.addParticle(
                        ParticleTypes.FIREWORK,
                        user.x, user.y - 0.3, user.z,
                        user.random.nextGaussian() * 0.05,
                        -user.velocity.y * 0.5,
                        user.random.nextGaussian() * 0.05
                    )
                }
                when {
                    user.isCreative -> {
                        data.applyBoost(user)
                        inv.ensureOnlyActiveBooster(stack)
                    }
                    ticksLeft > 0 -> {
                        stack.orCreateTag.putInt("TicksLeft", ticksLeft - 1)
                        data.applyBoost(user)
                        inv.ensureOnlyActiveBooster(stack)
                    }
                    stack.damage < stack.maxDamage -> {
                        stack.damage++
                        stack.orCreateTag.putInt("TicksLeft", data.ticksPerDamage)
                        data.applyBoost(user)
                        inv.ensureOnlyActiveBooster(stack)
                    }
                    else -> {
                        inv.findAndReplace(stack, ItemStack(emptyBooster))
                    }
                }
                return
            }
            inv.findAndReplace(stack, data.toBooster(stack))
        }
    }

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, ctx: TooltipContext) {
        tooltip += TranslatableText("$translationKey.description")
        tooltip += TranslatableText("tooltip.winged.deactivate_booster")
        tooltip += TranslatableText("tooltip.winged.autodeactivate_booster")
        if (ctx.isAdvanced) {
            tooltip += TranslatableText("tooltip.winged.time_left", data.secondsLeft(stack))
        }
    }

    fun deactivateBooster(itemStack: ItemStack) = data.toBooster(itemStack)
}