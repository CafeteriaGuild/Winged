package net.adriantodt.winged.command

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.context.CommandContext

open class ArgumentBuilderDSL<S, T : ArgumentBuilder<S, T>>(open val builder: ArgumentBuilder<S, T>) {
    fun then(string: String, block: LiteralArgumentBuilderDSL<S>.() -> Unit) {
        builder.then(LiteralArgumentBuilderDSL<S>(string).also(block).builder)
    }

    fun <U : ArgumentBuilder<S, U>> then(builder: ArgumentBuilder<S, U>, block: ArgumentBuilderDSL<S, U>.() -> Unit) {
        builder.then(ArgumentBuilderDSL(builder).also(block).builder)
    }

    fun <U> thenArgument(name: String, type: ArgumentType<U>, block: RequiredArgumentBuilderDSL<S, U>.() -> Unit) {
        builder.then(RequiredArgumentBuilderDSL<S, U>(name, type).also(block).builder)
    }

    fun requires(requirement: S.() -> Boolean) {
        builder.requires(requirement)
    }

    fun executes(block: CommandContext<S>.() -> Int) {
        builder.executes(block)
    }
}