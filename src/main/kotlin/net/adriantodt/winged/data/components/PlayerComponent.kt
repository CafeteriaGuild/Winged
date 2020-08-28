package net.adriantodt.winged.data.components

import nerdhub.cardinal.components.api.component.Component
import nerdhub.cardinal.components.api.component.extension.CopyableComponent
import nerdhub.cardinal.components.api.util.sync.EntitySyncedComponent
import net.adriantodt.fallflyinglib.FallFlyingAbility
import net.adriantodt.winged.data.Wing

interface PlayerComponent : Component, EntitySyncedComponent, CopyableComponent<PlayerComponent>, FallFlyingAbility {
    var wing: Wing?

    override fun allowFallFlying(): Boolean = wing != null
    override fun shouldHideCape(): Boolean = wing != null
}