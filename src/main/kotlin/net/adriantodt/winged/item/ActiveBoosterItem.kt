package net.adriantodt.winged.item

import net.adriantodt.winged.WingedPlayerInventory
import net.adriantodt.winged.WingedUtilityItems.emptyBooster
import net.adriantodt.winged.boost
import net.adriantodt.winged.data.WingedDataObject
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


class ActiveBoosterItem(settings: Settings, private val data: WingedDataObject.BoosterData) : Item(settings) {
    override fun use(world: World?, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        return TypedActionResult.success(data.toBooster(user.getStackInHand(hand)))
    }

    override fun inventoryTick(stack: ItemStack, world: World, entity: Entity, slot: Int, selected: Boolean) {
        if (entity is PlayerEntity) {
            val inv = entity.inventory as WingedPlayerInventory
            if (entity.isFallFlying) {
                val ticksLeft = stack.tag?.getInt("TicksLeft") ?: 0
                if (world.isClient && ticksLeft % 5 == 1) {
                    world.addParticle(
                        ParticleTypes.FIREWORK,
                        entity.x, entity.y - 0.3, entity.z,
                        entity.random.nextGaussian() * 0.05,
                        -entity.velocity.y * 0.5,
                        entity.random.nextGaussian() * 0.05
                    )
                }
                when {
                    entity.isCreative -> {
                        entity.boost(data)
                        inv.ensureOnlyActiveBooster(stack)
                    }
                    ticksLeft > 0 -> {
                        stack.orCreateTag.putInt("TicksLeft", ticksLeft - 1)
                        entity.boost(data)
                        inv.ensureOnlyActiveBooster(stack)
                    }
                    stack.damage < stack.maxDamage -> {
                        stack.damage++
                        stack.orCreateTag.putInt("TicksLeft", data.ticksPerDamage)
                        entity.boost(data)
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