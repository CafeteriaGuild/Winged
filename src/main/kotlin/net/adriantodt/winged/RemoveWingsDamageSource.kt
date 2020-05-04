package net.adriantodt.winged

import net.minecraft.entity.damage.DamageSource

class RemoveWingsDamageSource : DamageSource("removeWings") {
    init {
        setBypassesArmor()
        setUnblockable()
    }
}