package net.adriantodt.winged.recipe

import com.google.gson.JsonObject
import net.adriantodt.winged.identifier
import net.adriantodt.winged.screen.WingBenchCraftingInventory
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.*
import net.minecraft.registry.DynamicRegistryManager
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import net.minecraft.world.World

class WingcraftingRecipe(
    private val id: Identifier,
    val topInput: Ingredient,
    val centerInput: Ingredient,
    val wingTopInput: Ingredient,
    val wingMidInput: Ingredient,
    val wingLowInput: Ingredient,
    val output: ItemStack
) : Recipe<WingBenchCraftingInventory> {

    val ingredients by lazy {
        listOf(
            wingTopInput, topInput, wingTopInput,
            wingMidInput, centerInput, wingMidInput,
            wingLowInput, wingLowInput
        )
    }

    override fun matches(inv: WingBenchCraftingInventory, world: World?): Boolean {
        return ingredients.withIndex().all { (index, value) -> value.test(inv.getStack(index)) }
    }

    override fun craft(inventory: WingBenchCraftingInventory?, registryManager: DynamicRegistryManager?): ItemStack = output.copy()

    override fun fits(width: Int, height: Int): Boolean = true

    override fun getOutput(registryManager: DynamicRegistryManager?): ItemStack = output

    override fun getId(): Identifier = id

    override fun getSerializer(): RecipeSerializer<*> = SERIALIZER

    override fun getType(): RecipeType<*> = TYPE

    companion object {
        val TYPE = object : RecipeType<WingcraftingRecipe> {}
        val SERIALIZER = Serializer()
        val ID = identifier("wingcrafting")

        class Serializer : RecipeSerializer<WingcraftingRecipe> {

            override fun read(identifier: Identifier, json: JsonObject): WingcraftingRecipe {
                val topInput = readRequiredIngredient(json, "top")
                val centerInput = readOptionalIngredient(json, "center")
                val wingTopInput = readOptionalIngredient(json, "wing_top")
                val wingMidInput = readOptionalIngredient(json, "wing_mid")
                val wingLowInput = readOptionalIngredient(json, "wing_low")

                val output = ItemStack(ShapedRecipe.getItem(JsonHelper.getObject(json, "result")))
                return WingcraftingRecipe(
                    identifier, topInput, centerInput, wingTopInput, wingMidInput, wingLowInput, output
                )
            }

            private fun readRequiredIngredient(json: JsonObject, key: String): Ingredient {
                return Ingredient.fromJson(JsonHelper.getObject(json, key))
            }

            private fun readOptionalIngredient(json: JsonObject, key: String): Ingredient {
                if (!json.has(key)) {
                    return Ingredient.EMPTY
                }
                return readRequiredIngredient(json, key)
            }

            override fun read(identifier: Identifier, buf: PacketByteBuf): WingcraftingRecipe {
                val topInput = Ingredient.fromPacket(buf)
                val centerInput = Ingredient.fromPacket(buf)
                val wingTopInput = Ingredient.fromPacket(buf)
                val wingMidInput = Ingredient.fromPacket(buf)
                val wingLowInput = Ingredient.fromPacket(buf)
                val output = buf.readItemStack()

                return WingcraftingRecipe(
                    identifier, topInput, centerInput, wingTopInput, wingMidInput, wingLowInput, output
                )
            }

            override fun write(buf: PacketByteBuf, recipe: WingcraftingRecipe) {
                recipe.topInput.write(buf)
                recipe.centerInput.write(buf)
                recipe.wingTopInput.write(buf)
                recipe.wingMidInput.write(buf)
                recipe.wingLowInput.write(buf)
                buf.writeItemStack(recipe.output)
            }

        }
    }
}
