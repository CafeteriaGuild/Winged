package net.adriantodt.winged.screen

import com.mojang.blaze3d.systems.RenderSystem
import net.adriantodt.winged.identifier
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.StringVisitable
import net.minecraft.text.Text

class WingBenchScreen(handler: WingBenchScreenHandler, playerInventory: PlayerInventory, title: Text) : HandledScreen<WingBenchScreenHandler>(handler, playerInventory, title) {
    private val texture = identifier("textures/gui/wingbench_gui.png")

    init {
        backgroundWidth = 176
        backgroundHeight = 224
        playerInventoryTitleY = backgroundHeight - 94
    }

    override fun init() {
        super.init()
        titleX = (backgroundWidth - textRenderer.getWidth(title as StringVisitable)) / 2
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderBackground(context)
        super.render(context, mouseX, mouseY, delta)
        this.drawMouseoverTooltip(context, mouseX, mouseY)
    }

    override fun drawBackground(context: DrawContext?, delta: Float, mouseX: Int, mouseY: Int) {
        context?.drawTexture(texture, x, (height - backgroundHeight) / 2, 0, 0, backgroundWidth, backgroundHeight)
    }
}
