package net.adriantodt.winged.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder

class LiteralArgumentBuilderDSL<S>(
    override val builder: LiteralArgumentBuilder<S>
) : ArgumentBuilderDSL<S, LiteralArgumentBuilder<S>>(builder) {
    constructor(string: String) : this(LiteralArgumentBuilder.literal(string))
}