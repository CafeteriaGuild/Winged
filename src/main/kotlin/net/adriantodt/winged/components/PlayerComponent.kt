package net.adriantodt.winged.components

import nerdhub.cardinal.components.api.component.Component
import nerdhub.cardinal.components.api.component.extension.CopyableComponent
import nerdhub.cardinal.components.api.util.sync.EntitySyncedComponent
import net.adriantodt.winged.data.Wing

interface PlayerComponent : Component, EntitySyncedComponent, CopyableComponent<PlayerComponent> {
    var wing: Wing?
}