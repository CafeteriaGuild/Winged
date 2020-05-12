package net.adriantodt.winged

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer
import nerdhub.cardinal.components.api.ComponentRegistry
import nerdhub.cardinal.components.api.ComponentType
import nerdhub.cardinal.components.api.event.EntityComponentCallback
import nerdhub.cardinal.components.api.util.EntityComponents
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy
import net.adriantodt.winged.data.Wing
import net.adriantodt.winged.data.WingedConfig
import net.adriantodt.winged.data.WingedDataObject
import net.adriantodt.winged.data.components.PlayerComponent
import net.adriantodt.winged.data.components.impl.DefaultPlayerComponent
import net.adriantodt.winged.data.impl.WingedDataObjectImpl
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.registry.DefaultedRegistry

@Suppress("MemberVisibilityCanBePrivate")
object Winged : ModInitializer {
    val data: WingedDataObject get() = WingedDataObjectImpl(configHolder.config)

    val wingRegistry = DefaultedRegistry<Wing>("minecraft:elytra")

    val playerComponentType: ComponentType<PlayerComponent> = ComponentRegistry.INSTANCE
        .registerIfAbsent(identifier("player_data"), PlayerComponent::class.java)
        .attach(EntityComponentCallback.event(PlayerEntity::class.java), ::DefaultPlayerComponent)

    val configHolder = AutoConfig.register(WingedConfig::class.java, ::JanksonConfigSerializer)

    fun init() {
        EntityComponents.setRespawnCopyStrategy(playerComponentType, RespawnCopyStrategy.ALWAYS_COPY)
    }

    val mainGroup: ItemGroup = FabricItemGroupBuilder.create(identifier("main"))
        .icon { ItemStack(WingedLoreItems.coreOfFlight) }
        .build()

    val showcaseGroup: ItemGroup = FabricItemGroupBuilder.create(identifier("showcase"))
        .icon { ItemStack(WingItems.elytra) }
        .build()

    override fun onInitialize() {
        init()
        WingedLoreItems.register()
        WingedUtilityItems.register()
        WingItems.register()
        WingedLootTables.register(configHolder.config)

    }
}
