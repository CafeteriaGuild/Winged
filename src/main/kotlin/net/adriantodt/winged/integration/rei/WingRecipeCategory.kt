package net.adriantodt.winged.integration.rei

import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import it.unimi.dsi.fastutil.ints.IntList
import me.shedaniel.math.Point
import me.shedaniel.math.Rectangle
import me.shedaniel.rei.api.EntryStack
import me.shedaniel.rei.api.TransferRecipeCategory
import me.shedaniel.rei.api.widgets.Widgets
import me.shedaniel.rei.gui.widget.Widget
import net.adriantodt.winged.identifier
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.Tessellator
import net.minecraft.client.render.VertexFormats
import net.minecraft.client.resource.language.I18n
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier
import org.lwjgl.opengl.GL11

class WingRecipeCategory(private val identifier: Identifier, private val logo: EntryStack, private val categoryName: String) : TransferRecipeCategory<WingRecipeDisplay> {
    override fun getIdentifier(): Identifier = identifier

    override fun getCategoryName(): String = I18n.translate(categoryName)

    override fun renderRedSlots(matrices: MatrixStack?, widgets: MutableList<Widget>?, bounds: Rectangle?, display: WingRecipeDisplay?, redSlots: IntList?) {

    }

    override fun getLogo(): EntryStack = logo

    override fun setupDisplay(recipeDisplay: WingRecipeDisplay?, bounds: Rectangle): MutableList<Widget> {
        val list = mutableListOf<Widget>(Widgets.createCategoryBase(bounds))
        list.add(Widgets.createDrawableWidget { _, matrices, _, _, _ ->
            drawBg(matrices, bounds.x + 5, bounds.y + 7)
        })

        val input = recipeDisplay!!.inputEntries
        val recipe = recipeDisplay.recipe
        val outputs = EntryStack.create(recipe.output)
        list.add(Widgets.createSlot(Point(bounds.x + 42, bounds.y + 17)).entries(input[0]).disableBackground())
        list.add(Widgets.createSlot(Point(bounds.x + 67, bounds.y + 10)).entries(input[1]).disableBackground())
        list.add(Widgets.createSlot(Point(bounds.x + 92, bounds.y + 17)).entries(input[2]).disableBackground())
        list.add(Widgets.createSlot(Point(bounds.x + 29, bounds.y + 53)).entries(input[3]).disableBackground())
        list.add(Widgets.createSlot(Point(bounds.x + 67, bounds.y + 34)).entries(input[4]).disableBackground())
        list.add(Widgets.createSlot(Point(bounds.x + 106, bounds.y + 53)).entries(input[5]).disableBackground())
        list.add(Widgets.createSlot(Point(bounds.x + 13, bounds.y + 89)).entries(input[6]).disableBackground())
        list.add(Widgets.createSlot(Point(bounds.x + 121, bounds.y + 89)).entries(input[7]).disableBackground())
        list.add(Widgets.createSlot(Point(bounds.x + 67, bounds.y + 96)).entry(outputs).disableBackground().markOutput())

        return list
    }

    override fun getDisplayHeight(): Int {
        return 124
    }

    private fun drawBg(matrices: MatrixStack, x: Int, y: Int) {
        val width = 140
        val height = 110
        MinecraftClient.getInstance().textureManager.bindTexture( identifier("textures/gui/rei_integration.png"))

        val tessellator = Tessellator.getInstance()
        val buffer = tessellator.buffer
        val model = matrices.peek().model
        RenderSystem.enableBlend()
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO)
        buffer.begin(GL11.GL_QUADS, VertexFormats.POSITION_COLOR_TEXTURE) //I thought GL_QUADS was deprecated but okay, sure.
        buffer.vertex(model, x.toFloat(), (y + height).toFloat(), 0f).color(1f, 1f, 1f, 1f).texture(0f, 1f).next()
        buffer.vertex(model, (x + width).toFloat(), (y + height).toFloat(), 0f).color(1f, 1f, 1f, 1f).texture(1f, 1f).next()
        buffer.vertex(model, (x + width).toFloat(), y.toFloat(), 0f).color(1f, 1f, 1f, 1f).texture(1f, 0f).next()
        buffer.vertex(model, x.toFloat(), y.toFloat(), 0f).color(1f, 1f, 1f, 1f).texture(0f, 0f).next()
        tessellator.draw()
        RenderSystem.disableBlend()
    }
}
