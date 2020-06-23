package net.adriantodt.winged.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.adriantodt.winged.Winged.playerComponentType
import net.adriantodt.winged.Winged.wingRegistry
import net.adriantodt.winged.command.WingArgumentType.Companion.wing
import net.adriantodt.winged.data.Wing
import net.adriantodt.winged.identifier
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import net.minecraft.command.arguments.ArgumentTypes
import net.minecraft.command.arguments.EntityArgumentType.getPlayer
import net.minecraft.command.arguments.EntityArgumentType.player
import net.minecraft.command.arguments.serialize.ConstantArgumentSerializer
import net.minecraft.command.suggestion.SuggestionProviders
import net.minecraft.server.command.CommandSource
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.TranslatableText
import net.minecraft.util.Util

object WingedCommand : CommandRegistrationCallback {
    val allWings = SuggestionProviders.register<ServerCommandSource>(identifier("available_wings")) { _, it ->
        CommandSource.suggestIdentifiers(wingRegistry.ids, it)
    }

    fun init() {
        ArgumentTypes.register("winged:wing", WingArgumentType::class.java, ConstantArgumentSerializer(::wing))
        CommandRegistrationCallback.EVENT.register(this)
    }

    override fun register(dispatcher: CommandDispatcher<ServerCommandSource>, dedicated: Boolean) {
        dispatcher.withDSL("winged") {
            then("player") {
                thenArgument("player", player()) {
                    then("clear") {
                        requires { hasPermissionLevel(2) }
                        executes { clearWing(getPlayer(this, "player")) }
                    }
                    then("set") {
                        requires { hasPermissionLevel(2) }
                        thenArgument("wing", wing()) {
                            suggests(allWings)
                            executes {
                                setWing(
                                    getPlayer(this, "player"),
                                    getArgument("wing", Wing::class.java)
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
                executes { clearWing(source.player) }
            }
            then("set") {
                requires { hasPermissionLevel(2) }
                thenArgument("wing", wing()) {
                    suggests(allWings)
                    executes {
                        setWing(
                            source.player,
                            getArgument("wing", Wing::class.java)
                        )
                    }
                }
            }
            executes { getWing(source.player) }
        }
    }

    private fun CommandContext<ServerCommandSource>.getWing(player: ServerPlayerEntity): Int {
        val wing = playerComponentType[player].wing
        if (wing != null) {
            source.sendFeedback(
                TranslatableText(
                    "commands.winged.get",
                    player.name,
                    TranslatableText(
                        Util.createTranslationKey("wing", wingRegistry.getId(wing))
                    )
                ),
                false
            )
        } else {
            source.sendFeedback(TranslatableText("commands.winged.get_no_wing", player.name), false)
        }
        return 1
    }

    private fun CommandContext<ServerCommandSource>.setWing(player: ServerPlayerEntity, wing: Wing): Int {
        playerComponentType[player].wing = wing

        source.sendFeedback(
            TranslatableText(
                "commands.winged.set",
                player.name,
                TranslatableText(
                    Util.createTranslationKey("wing", wingRegistry.getId(playerComponentType[player].wing))
                )
            ),
            false
        )
        return 1
    }

    private fun CommandContext<ServerCommandSource>.clearWing(player: ServerPlayerEntity): Int {
        playerComponentType[player].wing = null

        source.sendFeedback(
            TranslatableText(
                "commands.winged.clear",
                player.name
            ),
            false
        )
        return 1
    }
}

