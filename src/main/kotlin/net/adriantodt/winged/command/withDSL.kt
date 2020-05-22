package net.adriantodt.winged.command

import com.mojang.brigadier.CommandDispatcher

fun <S> CommandDispatcher<S>.withDSL(name: String, block: LiteralArgumentBuilderDSL<S>.() -> Unit) {
    register(LiteralArgumentBuilderDSL<S>(name).also(block).builder)
}
