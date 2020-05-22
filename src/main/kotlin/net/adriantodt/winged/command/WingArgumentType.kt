package net.adriantodt.winged.command

import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.adriantodt.winged.Winged.wingRegistry
import net.adriantodt.winged.data.Wing
import net.minecraft.server.command.CommandSource
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import java.util.concurrent.CompletableFuture

class WingArgumentType : ArgumentType<Wing> {
    override fun parse(stringReader: StringReader): Wing {
        val identifier = Identifier.fromCommandInput(stringReader)
        return wingRegistry.getOrEmpty(identifier).orElseThrow { UNKNOWN_WING_EXCEPTION.create(identifier) }
    }

    override fun getExamples() = EXAMPLES

    override fun <S> listSuggestions(
        context: CommandContext<S>,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> {
        return CommandSource.suggestIdentifiers(wingRegistry.ids, builder)
    }

    companion object {
        private val EXAMPLES: Collection<String> = listOf("minecraft:elytra", "winged:rainbow_wing")
        val UNKNOWN_WING_EXCEPTION = DynamicCommandExceptionType { TranslatableText("wing.notFound", it) }

        fun wing() = WingArgumentType()
    }
}