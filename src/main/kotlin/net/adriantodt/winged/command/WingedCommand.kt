package net.adriantodt.winged.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.adriantodt.winged.Winged.playerComponentType
import net.adriantodt.winged.Winged.wingRegistry
import net.adriantodt.winged.data.Wing
import net.adriantodt.winged.identifier
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.command.CommandSource
import net.minecraft.command.argument.EntityArgumentType.getPlayer
import net.minecraft.command.argument.EntityArgumentType.player
import net.minecraft.command.argument.IdentifierArgumentType.getIdentifier
import net.minecraft.command.argument.IdentifierArgumentType.identifier
import net.minecraft.command.suggestion.SuggestionProviders
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.Util

object WingedCommand : CommandRegistrationCallback {
    val allWings = SuggestionProviders.register<ServerCommandSource>(identifier("available_wings")) { _, it ->
        CommandSource.suggestIdentifiers(wingRegistry.ids, it)
    }

    fun init() {
        CommandRegistrationCallback.EVENT.register(this)
    }

    override fun register(dispatcher: CommandDispatcher<ServerCommandSource>, registryAccess: CommandRegistryAccess, env: CommandManager.RegistrationEnvironment) {
        dispatcher.withDSL("winged") {
            then("player") {
                thenArgument("player", player()) {
                    then("clear") {
                        requires { hasPermissionLevel(2) }
                        executes { clearWing(getPlayer(this, "player")) }
                    }
                    then("set") {
                        requires { hasPermissionLevel(2) }
                        thenArgument("wing", identifier()) {
                            suggests(allWings)
                            executes {
                                setWing(
                                    getPlayer(this, "player"),
                                    wingRegistry[getIdentifier(this, "wing")]
                                )
                            }
                        }
                    }
                    executes { getWing(getPlayer(this, "player")) }
                }
            }

            // self
            then("clear") {
                requires { hasPermissionLevel(2) }
                executes { clearWing(source.player!!) }
            }
            then("set") {
                requires { hasPermissionLevel(2) }
                thenArgument("wing", identifier()) {
                    suggests(allWings)
                    executes {
                        setWing(
                            source.player!!,
                            wingRegistry[getIdentifier(this, "wing")]
                        )
                    }
                }
            }
            executes { getWing(source.player!!) }
        }
    }

    private fun CommandContext<ServerCommandSource>.getWing(player: ServerPlayerEntity): Int {
        val wing = playerComponentType[player].wing
        if (wing != null) {
            source.sendFeedback(
                {
                    Text.translatable(
                        "commands.winged.get",
                        player.name,
                        Text.translatable(
                            Util.createTranslationKey("wing", wingRegistry.getId(wing))
                        )
                    )
                },
                false
            )
        } else {
            source.sendFeedback({ Text.translatable("commands.winged.get_no_wing", player.name) }, false)
        }
        return 1
    }

    private fun CommandContext<ServerCommandSource>.setWing(player: ServerPlayerEntity, wing: Wing): Int {
        playerComponentType[player].wing = wing

        source.sendFeedback(
            {
                Text.translatable(
                    "commands.winged.set",
                    player.name,
                    Text.translatable(
                        Util.createTranslationKey("wing", wingRegistry.getId(playerComponentType[player].wing))
                    )
                )
            },
            false
        )
        return 1
    }

    private fun CommandContext<ServerCommandSource>.clearWing(player: ServerPlayerEntity): Int {
        playerComponentType[player].wing = null

        source.sendFeedback(
            {
                Text.translatable(
                    "commands.winged.clear",
                    player.name
                )
            },
            false
        )
        return 1
    }
}

