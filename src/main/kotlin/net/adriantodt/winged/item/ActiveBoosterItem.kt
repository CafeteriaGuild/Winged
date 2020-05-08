package net.adriantodt.winged.item

import net.adriantodt.winged.EMPTY_BOOSTER
import net.adriantodt.winged.WingedPlayerInventory
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.particle.ParticleTypes
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World


class ActiveBoosterItem(
    settings: Settings,
    private val instantVelocity: Double = 0.1,
    private val maxVelocity: Double = 1.5,
    private val speedFactor: Double = 0.5,
    val ticksPerDamage: Int = 20,
    inactive: () -> BoosterItem
) : Item(settings) {
    private val inactiveBooster by lazy(inactive)

    override fun use(world: World?, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)
        return TypedActionResult.success(ItemStack(inactiveBooster).apply {
            damage = stack.damage
            stack.tag?.getInt("TicksLeft")?.let { stack.orCreateTag.putInt("TicksLeft", it) }
        })
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

                if (ticksLeft > 0) {
                    stack.orCreateTag.putInt("TicksLeft", ticksLeft - 1)
                    boostTheLivingShitOfThisMotherFucker(entity)
                    return
                }
                if (stack.damage < stack.maxDamage) {
                    stack.damage++
                    stack.orCreateTag.putInt("TicksLeft", ticksPerDamage)
                    boostTheLivingShitOfThisMotherFucker(entity)
                    return
                }
                inv.findAndReplace(stack, ItemStack(EMPTY_BOOSTER))
                return
            }
            inv.findAndReplace(stack, ItemStack(inactiveBooster).apply {
                damage = stack.damage
                stack.tag?.getInt("TicksLeft")?.let { stack.orCreateTag.putInt("TicksLeft", it) }
            })
        }
    }

    private fun boostTheLivingShitOfThisMotherFucker(livingEntity: LivingEntity) {
        //val maxVelocity = 0.8
        //val instantVelocity = 0.1
        //val speedFactor = 0.5
        val rotation = livingEntity.rotationVector
        val velocity: Vec3d = livingEntity.velocity

        livingEntity.velocity = velocity.add(
            rotation.x * instantVelocity + (rotation.x * maxVelocity - velocity.x) * speedFactor,
            rotation.y * instantVelocity + (rotation.y * maxVelocity - velocity.y) * speedFactor,
            rotation.z * instantVelocity + (rotation.z * maxVelocity - velocity.z) * speedFactor
        )
    }

    @Environment(EnvType.CLIENT)
    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, ctx: TooltipContext) {
        tooltip += TranslatableText("$translationKey.description")
        tooltip += TranslatableText("tooltip.winged.deactivate_booster")
        tooltip += TranslatableText("tooltip.winged.autodeactivate_booster")
        if (ctx.isAdvanced) {
            tooltip += TranslatableText(
                "tooltip.winged.time_left",
                ((maxDamage - stack.damage) * ticksPerDamage + (stack.tag?.getInt("TicksLeft") ?: 0)) / 20.0
            )
        }
    }
}