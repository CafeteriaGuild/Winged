package net.adriantodt.winged.data.components

import dev.onyxstudios.cca.api.v3.component.ComponentV3
import dev.onyxstudios.cca.api.v3.component.CopyableComponent
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import net.adriantodt.fallflyinglib.FallFlyingAbility
import net.adriantodt.winged.data.Wing

interface PlayerComponent : ComponentV3, AutoSyncedComponent, CopyableComponent<PlayerComponent>, FallFlyingAbility {
    var wing: Wing?
    var creativeFlight: Boolean

    override fun allowFallFlying(): Boolean = wing != null
    override fun shouldHideCape(): Boolean = wing != null
}