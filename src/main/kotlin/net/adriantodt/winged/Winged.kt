package net.adriantodt.winged

import com.mojang.serialization.Lifecycle
import dev.onyxstudios.cca.api.v3.component.ComponentKey
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactory
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer
import io.github.ladysnake.pal.AbilitySource
import io.github.ladysnake.pal.Pal
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.ConfigHolder
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy
import net.adriantodt.fallflyinglib.FallFlyingLib
import net.adriantodt.winged.block.WingBenchBlock
import net.adriantodt.winged.command.WingedCommand
import net.adriantodt.winged.data.Wing
import net.adriantodt.winged.data.WingedConfig
import net.adriantodt.winged.data.WingedData
import net.adriantodt.winged.data.components.PlayerComponent
import net.adriantodt.winged.data.components.impl.DefaultPlayerComponent
import net.adriantodt.winged.data.impl.WingedDataImpl
import net.adriantodt.winged.recipe.WingRecipe
import net.adriantodt.winged.screen.WingBenchScreenHandler
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.fabricmc.fabric.impl.screenhandler.ExtendedScreenHandlerType
import net.minecraft.block.Blocks
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.registry.DefaultedRegistry
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Suppress("MemberVisibilityCanBePrivate")
object Winged : ModInitializer, EntityComponentInitializer {
    val logger: Logger = LogManager.getLogger(Winged.javaClass)

    val configHolder: ConfigHolder<WingedConfig> =
        AutoConfig.register(WingedConfig::class.java, ::JanksonConfigSerializer)

    val data: WingedData = WingedDataImpl(configHolder.config)

    val wingRegistry = DefaultedRegistry<Wing>(
        "minecraft:elytra",
        RegistryKey.ofRegistry(identifier("wing")),
        Lifecycle.stable()
    )

    val playerComponentType: ComponentKey<PlayerComponent> = ComponentRegistryV3.INSTANCE.getOrCreate(identifier("player_data"), PlayerComponent::class.java)

    val heartOfTheSkyAbilitySource: AbilitySource = Pal.getAbilitySource(identifier("heart_of_the_sky"))


    val wingbenchType = ScreenHandlerRegistry.registerExtended(WingBenchScreenHandler.ID) { syncId, inv, buf ->
        WingBenchScreenHandler(syncId, inv)
    } as ExtendedScreenHandlerType<WingBenchScreenHandler>

    fun init() {
        FallFlyingLib.registerAccessor(playerComponentType::get)
    }

    val mainGroup: ItemGroup = FabricItemGroupBuilder.create(identifier("main"))
        .icon { ItemStack(WingedLoreItems.coreOfFlight) }
        .build()

    val showcaseGroup: ItemGroup = FabricItemGroupBuilder.create(identifier("showcase"))
        .icon { ItemStack(WingItems.elytra.first) }
        .build()

    val wingBenchBlock = WingBenchBlock(FabricBlockSettings.copyOf(Blocks.END_STONE))

    override fun onInitialize() {
        init()
        WingedLoreItems.register()
        WingedUtilityItems.register()
        WingItems.register()
        WingedLootTables.register(configHolder.config)
        WingedCommand.init()

        Registry.register(Registry.RECIPE_TYPE, WingRecipe.ID, WingRecipe.TYPE)
        Registry.register(Registry.RECIPE_SERIALIZER, WingRecipe.ID, WingRecipe.SERIALIZER)

        Registry.register(Registry.BLOCK, identifier("wingbench"), wingBenchBlock)
        Registry.register(Registry.ITEM, identifier("wingbench"), BlockItem(wingBenchBlock, itemSettings()))
    }

    override fun registerEntityComponentFactories(registry: EntityComponentFactoryRegistry) {
        registry.registerForPlayers(playerComponentType, EntityComponentFactory { player -> DefaultPlayerComponent(player, false) }, RespawnCopyStrategy.ALWAYS_COPY)
    }
}