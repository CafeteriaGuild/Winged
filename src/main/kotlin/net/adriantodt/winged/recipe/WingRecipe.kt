package net.adriantodt.winged.recipe

import com.google.gson.JsonObject
import net.adriantodt.winged.identifier
import net.adriantodt.winged.mixin.ShapedRecipeAccessor
import net.adriantodt.winged.screen.WingBenchCraftingInventory
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.*
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.World

class WingRecipe(
    private val id: Identifier,
    val inputs: DefaultedList<Ingredient>,
    private val output: ItemStack
) : Recipe<WingBenchCraftingInventory> {

    override fun matches(inv: WingBenchCraftingInventory, world: World?): Boolean {
        for (i in 0 until 7) {
            if (!inputs[i].test(inv.getStack(i))) return false
        }

        return true
    }

    override fun craft(inv: WingBenchCraftingInventory?): ItemStack = output.copy()

    override fun fits(width: Int, height: Int): Boolean = true

    override fun getOutput(): ItemStack = output

    override fun getId(): Identifier = id

    override fun getSerializer(): RecipeSerializer<*> = SERIALIZER

    override fun getType(): RecipeType<*> = TYPE

    companion object {
        val TYPE = object : RecipeType<WingRecipe> {}
        val SERIALIZER = Serializer()
        val ID = identifier("wingrecipe")

        class Serializer : RecipeSerializer<WingRecipe> {

            override fun read(identifier: Identifier, jsonObject: JsonObject?): WingRecipe {
                val map = ShapedRecipeAccessor.winged_getComponents(JsonHelper.getObject(jsonObject, "key"))
                val strings = ShapedRecipeAccessor.winged_combinePattern(*ShapedRecipeAccessor.winged_getPattern(JsonHelper.getArray(jsonObject, "pattern")))
                val i = strings[0].length
                val j = strings.size
                val defaultedList = ShapedRecipeAccessor.winged_getIngredients(strings, map, i, j)

                assert(defaultedList[4].isEmpty)
                //silently ignoring it because i don't want to modify every recipe
                //assert(defaultedList[7].isEmpty)

                val actual = DefaultedList.ofSize(7, Ingredient.EMPTY)
                defaultedList.forEachIndexed { index, ing ->
                    if (index != 7 && index != 4) {
                        if (index < 4) actual[index] = ing
                        else if (index < 7) actual[index - 1] = ing
                        else actual[index - 2] = ing
                    }
                }

                val itemStack = ShapedRecipe.getItemStack(JsonHelper.getObject(jsonObject, "result"))
                return WingRecipe(identifier, actual, itemStack)
            }

            override fun read(identifier: Identifier, packetByteBuf: PacketByteBuf): WingRecipe {
                val defaultedList = DefaultedList.ofSize(7, Ingredient.EMPTY)
                for (k in defaultedList.indices) {
                    defaultedList[k] = Ingredient.fromPacket(packetByteBuf)
                }
                val itemStack = packetByteBuf.readItemStack()
                return WingRecipe(identifier, defaultedList, itemStack)
            }

            override fun write(packetByteBuf: PacketByteBuf, shapedRecipe: WingRecipe) {
                val var3: Iterator<*> = shapedRecipe.inputs.iterator()
                while (var3.hasNext()) {
                    val ingredient = var3.next() as Ingredient
                    ingredient.write(packetByteBuf)
                }
                packetByteBuf.writeItemStack(shapedRecipe.output)
            }

        }
    }
}