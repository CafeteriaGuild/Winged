package net.adriantodt.winged.command

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.suggestion.SuggestionProvider

class RequiredArgumentBuilderDSL<S, T>(
    override val builder: RequiredArgumentBuilder<S, T>
) : ArgumentBuilderDSL<S, RequiredArgumentBuilder<S, T>>(builder) {
    constructor(name: String, type: ArgumentType<T>) : this(RequiredArgumentBuilder.argument(name, type))


    fun suggests(provider: SuggestionProvider<S>) {
        builder.suggests(provider)
    }

}