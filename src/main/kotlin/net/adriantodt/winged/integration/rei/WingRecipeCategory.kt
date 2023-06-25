package net.adriantodt.winged.integration.rei

import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import me.shedaniel.math.Point
import me.shedaniel.math.Rectangle
import me.shedaniel.rei.api.client.gui.Renderer
import me.shedaniel.rei.api.client.gui.widgets.Widget
import me.shedaniel.rei.api.client.gui.widgets.Widgets
import me.shedaniel.rei.api.client.registry.display.DisplayCategory
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.entry.EntryIngredient
import me.shedaniel.rei.api.common.entry.EntryStack
import me.shedaniel.rei.api.common.util.EntryStacks
import net.adriantodt.winged.identifier
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.render.Tessellator
import net.minecraft.client.render.VertexFormat
import net.minecraft.client.render.VertexFormats
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class WingRecipeCategory(private val identifier: Identifier, private val logo: EntryStack<*>, private val categoryName: String)
    : DisplayCategory<WingRecipeDisplay> {
    override fun getIdentifier(): Identifier = identifier

    override fun getCategoryIdentifier(): CategoryIdentifier<out WingRecipeDisplay> {
        return CategoryIdentifier.of(identifier("wingcrafting"))
    }

    override fun getIcon(): Renderer {
        return logo
    }

    override fun setupDisplay(recipeDisplay: WingRecipeDisplay?, bounds: Rectangle): MutableList<Widget> {
        val list = mutableListOf<Widget>(Widgets.createCategoryBase(bounds))
        list.add(Widgets.createDrawableWidget { ctx, _, _, _->
            drawBg(ctx.matrices, bounds.x + 5, bounds.y + 7)
        })

        val input = recipeDisplay!!.inputEntries
        val recipe = recipeDisplay.recipe
        val outputs = EntryStacks.of(recipe.output)
        addSlot(list, Point(bounds.x + 42, bounds.y + 17), input, 0)
        addSlot(list, Point(bounds.x + 67, bounds.y + 10), input, 1)
        addSlot(list, Point(bounds.x + 92, bounds.y + 17), input, 2)
        addSlot(list, Point(bounds.x + 29, bounds.y + 53), input, 3)
        addSlot(list, Point(bounds.x + 67, bounds.y + 34), input, 4)
        addSlot(list, Point(bounds.x + 106, bounds.y + 53), input, 5)
        addSlot(list, Point(bounds.x + 13, bounds.y + 89), input, 6)
        addSlot(list, Point(bounds.x + 121, bounds.y + 89), input, 7)
        list.add(Widgets.createSlot(Point(bounds.x + 67, bounds.y + 96)).entry(outputs).disableBackground().markOutput())

        return list
    }
    
    private fun addSlot(list: MutableList<Widget>, point: Point, input: List<EntryIngredient>, index: Int) {
        if (input.size > index && !input[index].isEmpty())
            list.add(Widgets.createSlot(point).entries(input[index]).disableBackground())
    }

    override fun getDisplayHeight(): Int {
        return 124
    }

    private fun drawBg(matrices: MatrixStack, x: Int, y: Int) {
        val width = 140
        val height = 110
        RenderSystem.setShaderTexture(0, identifier("textures/gui/rei_integration.png"))

        val tessellator = Tessellator.getInstance()
        val buffer = tessellator.buffer
        val model = matrices.peek().positionMatrix
        RenderSystem.enableBlend()
        RenderSystem.setShader { GameRenderer.getPositionColorTexProgram() }
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO)
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE) //I thought GL_QUADS was deprecated but okay, sure.
        buffer.vertex(model, x.toFloat(), (y + height).toFloat(), 0f).color(1f, 1f, 1f, 1f).texture(0f, 1f).next()
        buffer.vertex(model, (x + width).toFloat(), (y + height).toFloat(), 0f).color(1f, 1f, 1f, 1f).texture(1f, 1f).next()
        buffer.vertex(model, (x + width).toFloat(), y.toFloat(), 0f).color(1f, 1f, 1f, 1f).texture(1f, 0f).next()
        buffer.vertex(model, x.toFloat(), y.toFloat(), 0f).color(1f, 1f, 1f, 1f).texture(0f, 0f).next()
        tessellator.draw()
        RenderSystem.disableBlend()
    }

    override fun getTitle(): Text = Text.translatable(categoryName)
}
