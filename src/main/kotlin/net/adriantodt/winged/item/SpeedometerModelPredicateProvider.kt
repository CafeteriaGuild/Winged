package net.adriantodt.winged.item

import net.adriantodt.winged.WingedLivingEntity
import net.minecraft.client.item.ModelPredicateProvider
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack

object SpeedometerModelPredicateProvider : ModelPredicateProvider {
    override fun call(stack: ItemStack?, world: ClientWorld?, entity: LivingEntity?): Float {
        if (entity == null) {
            return 0f
        }
        val lastPos = (entity as? WingedLivingEntity)?.lastPos
        if (lastPos != null) {
            // Basically, velocity * 20 / 12 * 2.5
            // ... but rounded up a bit
            return lastPos.distanceTo(entity.pos).toFloat() * 4.16f
        }
        // Fallback
        return entity.velocity.length().toFloat() * 4.16f
    }
}