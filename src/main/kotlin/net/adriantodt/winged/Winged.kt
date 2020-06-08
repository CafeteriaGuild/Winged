package net.adriantodt.winged

import com.mojang.serialization.Lifecycle
import io.github.ladysnake.pal.AbilitySource
import io.github.ladysnake.pal.Pal
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig
import me.sargunvohra.mcmods.autoconfig1u.ConfigHolder
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer
import nerdhub.cardinal.components.api.ComponentRegistry
import nerdhub.cardinal.components.api.ComponentType
import nerdhub.cardinal.components.api.component.Component
import nerdhub.cardinal.components.api.component.ComponentContainer
import nerdhub.cardinal.components.api.event.EntityComponentCallback
import nerdhub.cardinal.components.api.util.EntityComponents
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy
import net.adriantodt.winged.command.WingedCommand
import net.adriantodt.winged.data.Wing
import net.adriantodt.winged.data.WingedConfig
import net.adriantodt.winged.data.WingedData
import net.adriantodt.winged.data.components.PlayerComponent
import net.adriantodt.winged.data.components.impl.DefaultPlayerComponent
import net.adriantodt.winged.data.impl.WingedDataImpl
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import net.fabricmc.fabric.api.event.Event
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.registry.DefaultedRegistry
import net.minecraft.util.registry.RegistryKey
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Suppress("MemberVisibilityCanBePrivate")
object Winged : ModInitializer {
    val logger: Logger = LogManager.getLogger(Winged.javaClass)

    val configHolder: ConfigHolder<WingedConfig> =
        AutoConfig.register(WingedConfig::class.java, ::JanksonConfigSerializer)

    val data: WingedData = WingedDataImpl(configHolder.config)

    val wingRegistry = DefaultedRegistry<Wing>(
        "minecraft:elytra",
        RegistryKey.ofRegistry(identifier("wing")),
        Lifecycle.experimental()
    )

    val playerComponentType: ComponentType<PlayerComponent> = ComponentRegistry.INSTANCE
        .registerIfAbsent(identifier("player_data"), PlayerComponent::class.java)

    val heartOfTheSkyAbilitySource: AbilitySource = Pal.getAbilitySource(identifier("heart_of_the_sky"))

    fun init() {
        EntityComponentCallback.event(PlayerEntity::class.java).register { entity, components ->
            components[playerComponentType] = DefaultPlayerComponent(entity)
        }
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
        CommandRegistrationCallback.EVENT.register(WingedCommand)
    }

    private fun <T : Entity> Event<EntityComponentCallback<T>>.register(function: (T, ComponentContainer<Component>) -> Unit) {
        register(EntityComponentCallback(function))
    }
}
