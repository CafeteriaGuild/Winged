package net.adriantodt.winged.data.impl

import net.adriantodt.winged.data.WingedData
import net.adriantodt.winged.item.BoosterItem

class FuelPelletDataImpl(
    override val standardPellets: Int,
    override val fastPellets: Int,
    override val slowPellets: Int,
    lazyResult: () -> BoosterItem
) : WingedData.FuelPelletData {
    override val resultItem by lazy(lazyResult)
}