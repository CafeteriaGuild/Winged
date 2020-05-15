package net.adriantodt.winged.data.impl

import net.adriantodt.winged.data.WingedConfig
import net.adriantodt.winged.data.WingedData

class BoosterVelocityImpl(
    override var constantVelocity: Double,
    override var interpolatingVelocity: Double,
    override var frictionFactor: Double
) : WingedData.BoosterVelocity {
    constructor(c: WingedConfig.ForwardLauncherValues) : this(
        c.constantVelocity, c.interpolatingVelocity, c.frictionFactor
    )
}