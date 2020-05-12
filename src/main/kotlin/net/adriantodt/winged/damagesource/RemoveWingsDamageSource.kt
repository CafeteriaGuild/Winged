package net.adriantodt.winged.damagesource

import net.minecraft.entity.damage.DamageSource

object RemoveWingsDamageSource : DamageSource("removeWings") {
    init {
        setBypassesArmor()
        setUnblockable()
    }
}