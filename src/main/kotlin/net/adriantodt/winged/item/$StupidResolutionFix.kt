@file:JvmName("UnmeasurableStupidity")
/*
 * You shouldn't be here.
 */
package net.adriantodt.winged.item

import net.minecraft.text.Text

@JvmName("wingedTricksCompilerForNotNullProfit")
operator fun MutableList<Text>.plusAssign(text: Text) {
    this.add(text)
}

@JvmName("wingedTricksCompilerForNullableProfit")
operator fun MutableList<Text?>.plusAssign(text: Text?) {
    this.add(text)
}

