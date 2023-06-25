package net.adriantodt.winged

import com.mojang.serialization.Lifecycle
import dev.onyxstudios.cca.api.v3.component.ComponentKey
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy
import io.github.ladysnake.pal.AbilitySource
import io.github.ladysnake.pal.Pal
import io.github.ladysnake.pal.VanillaAbilities
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.ConfigHolder
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer
import net.adriantodt.winged.block.WingBenchBlock
import net.adriantodt.winged.command.WingedCommand
import net.adriantodt.winged.data.Wing
import net.adriantodt.winged.data.WingedConfig
import net.adriantodt.winged.data.components.WingedPlayerComponent
import net.adriantodt.winged.data.components.impl.DefaultPlayerComponent
import net.adriantodt.winged.recipe.WingcraftingRecipe
import net.adriantodt.winged.screen.WingBenchScreenHandler
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.block.Blocks
import net.minecraft.entity.damage.DamageTypes
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.registry.*
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


@Suppress("MemberVisibilityCanBePrivate")
object Winged : ModInitializer, EntityComponentInitializer {
    val logger: Logger = LogManager.getLogger(Winged.javaClass)

    val configHolder: ConfigHolder<WingedConfig> =
        AutoConfig.register(WingedConfig::class.java, ::JanksonConfigSerializer)

    val wingRegistry = SimpleDefaultedRegistry<Wing>(
        "minecraft:elytra",
        RegistryKey.ofRegistry(identifier("wing")),
        Lifecycle.stable(),
        false
    )

    val playerComponentType: ComponentKey<WingedPlayerComponent> =
        ComponentRegistryV3.INSTANCE.getOrCreate(identifier("player_data"), WingedPlayerComponent::class.java)

    val wingSource: AbilitySource = Pal.getAbilitySource(identifier("wing"))

    val wingbenchType = ScreenHandlerRegistry.registerSimple(WingBenchScreenHandler.ID) { syncId, inv ->
        WingBenchScreenHandler(syncId, inv)
    }!!

    val mainGroupKey = RegistryKey.of(RegistryKeys.ITEM_GROUP, identifier( "main"))
    val mainGroup: ItemGroup = FabricItemGroup.builder()
        .displayName(Text.translatable("winged.main"))
        .icon { ItemStack(WingedLoreItems.coreOfFlight) }
        .build()

    val showcaseGroupKey = RegistryKey.of(RegistryKeys.ITEM_GROUP, identifier( "showcase"))
    val showcaseGroup: ItemGroup = FabricItemGroup.builder()
        .displayName(Text.translatable("winged.showcase"))
        .icon { ItemStack(WingItems.elytra.standard) }
        .build()

    val wingBenchBlock = WingBenchBlock(FabricBlockSettings.copyOf(Blocks.END_STONE))

    val removeWingsDamageType = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, identifier("remove_wings"))

    override fun onInitialize() {
        EntityElytraEvents.CUSTOM.register { entity, _ ->

            val component = playerComponentType.getNullable(entity)
            return@register component?.wing != null
        }
        WingedLoreItems.register()
        WingedUtilityItems.register()
        WingItems.register()
        WingedLootTables.register(configHolder.config)
        WingedCommand.init()

        ServerTickEvents.START_WORLD_TICK.register { world ->
            world.players.forEach { player ->
                updatePalAndFfl(player, playerComponentType.getNullable(player) ?: return@forEach)
            }
        }

        Registry.register(Registries.RECIPE_TYPE, WingcraftingRecipe.ID, WingcraftingRecipe.TYPE)
        Registry.register(Registries.RECIPE_SERIALIZER, WingcraftingRecipe.ID, WingcraftingRecipe.SERIALIZER)

        Registry.register(Registries.BLOCK, identifier("wingbench"), wingBenchBlock)
        Registry.register(Registries.ITEM, identifier("wingbench"), BlockItem(wingBenchBlock, itemSettings()))

        Registry.register(Registries.ITEM_GROUP, mainGroupKey, mainGroup)
        Registry.register(Registries.ITEM_GROUP, showcaseGroupKey, showcaseGroup)

    }

    override fun registerEntityComponentFactories(registry: EntityComponentFactoryRegistry) {
        registry.registerForPlayers(
            playerComponentType,
            ::DefaultPlayerComponent,
            if (configHolder.config.keepWingsAfterDeath) RespawnCopyStrategy.ALWAYS_COPY
            else RespawnCopyStrategy.INVENTORY
        )
    }

    fun updatePalAndFfl(player: PlayerEntity, component: WingedPlayerComponent) {
        if (player.world.isClient) return
        val wing = component.wing

        val allowFlyingTracker = VanillaAbilities.ALLOW_FLYING.getTracker(player)

        if (wing == null) {
            allowFlyingTracker.removeSource(wingSource)
        } else {
            if (component.creativeFlight) {
                allowFlyingTracker.addSource(wingSource)
            } else {
                allowFlyingTracker.removeSource(wingSource)
            }
        }
    }
}
