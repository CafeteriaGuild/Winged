package net.adriantodt.winged.data.impl

import net.adriantodt.winged.data.WingedConfig
import net.adriantodt.winged.data.WingedData
import net.adriantodt.winged.item.ActiveBoosterItem
import net.adriantodt.winged.item.BoosterItem

class BoosterDataImpl(
    override var constantVelocity: Double,
    override var interpolatingVelocity: Double,
    override var frictionFactor: Double,
    override var ticksPerDamage: Int,
    lazyBooster: () -> BoosterItem,
    lazyActiveBooster: () -> ActiveBoosterItem
) : WingedData.BoosterData {
    override val boosterItem by lazy(lazyBooster)
    override val activeBoosterItem by lazy(lazyActiveBooster)

    constructor(
        c: WingedConfig.BoosterValues, lazyBooster: () -> BoosterItem, lazyActiveBooster: () -> ActiveBoosterItem
    ) : this(
        c.constantVelocity, c.interpolatingVelocity, c.frictionFactor, c.ticksPerDamage, lazyBooster, lazyActiveBooster
    )
}