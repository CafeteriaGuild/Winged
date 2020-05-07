package net.adriantodt.winged

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer
import nerdhub.cardinal.components.api.ComponentRegistry
import nerdhub.cardinal.components.api.ComponentType
import nerdhub.cardinal.components.api.event.EntityComponentCallback
import nerdhub.cardinal.components.api.util.EntityComponents
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy
import net.adriantodt.winged.data.DefaultWingedComponent
import net.adriantodt.winged.data.Wing
import net.adriantodt.winged.data.WingedComponent
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.registry.DefaultedRegistry


val mainGroup: ItemGroup = FabricItemGroupBuilder.create(identifier("main"))
    .icon { ItemStack(CORE_OF_FLIGHT) }
    .build()

val showcaseGroup: ItemGroup = FabricItemGroupBuilder.create(identifier("showcase"))
    .icon { ItemStack(ITEM_WING_ELYTRA) }
    .build()

val wingedConfig = AutoConfig.register(WingedConfig::class.java, ::JanksonConfigSerializer)

val wingRegistry = DefaultedRegistry<Wing>("minecraft:elytra")

val wingedComponent: ComponentType<WingedComponent> =
    ComponentRegistry.INSTANCE.registerIfAbsent(identifier("player_data"), WingedComponent::class.java)
        .attach(EntityComponentCallback.event(PlayerEntity::class.java), ::DefaultWingedComponent)

val removeWings = RemoveWingsDamageSource()

@Suppress("unused")
fun init() {
    EntityComponents.setRespawnCopyStrategy(wingedComponent, RespawnCopyStrategy.ALWAYS_COPY)
    initItems()
    initWings()
    initLootTables()
}

