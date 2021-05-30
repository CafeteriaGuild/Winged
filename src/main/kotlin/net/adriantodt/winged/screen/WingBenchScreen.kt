package net.adriantodt.winged.screen

import com.mojang.blaze3d.systems.RenderSystem
import net.adriantodt.winged.identifier
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

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        this.drawMouseoverTooltip(matrices, mouseX, mouseY)
    }

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
//        RenderSystem.color4f(1f, 1f, 1f, 1f)
        RenderSystem.setShaderTexture(0, texture)
        this.drawTexture(matrices, x, (height - backgroundHeight) / 2, 0, 0, backgroundWidth, backgroundHeight)
    }
}
