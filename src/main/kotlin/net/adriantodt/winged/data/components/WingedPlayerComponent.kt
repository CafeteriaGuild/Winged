package net.adriantodt.winged.data.components

import dev.onyxstudios.cca.api.v3.component.ComponentV3
import dev.onyxstudios.cca.api.v3.component.CopyableComponent
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import net.adriantodt.winged.data.Wing

interface WingedPlayerComponent : ComponentV3, AutoSyncedComponent, CopyableComponent<WingedPlayerComponent> {
    var wing: Wing?
    var creativeFlight: Boolean
}
