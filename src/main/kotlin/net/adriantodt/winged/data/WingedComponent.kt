package net.adriantodt.winged.data

import nerdhub.cardinal.components.api.component.Component
import nerdhub.cardinal.components.api.component.extension.CopyableComponent
import nerdhub.cardinal.components.api.util.sync.EntitySyncedComponent

interface WingedComponent : Component, EntitySyncedComponent, CopyableComponent<WingedComponent> {
    var wing: Wing?
}