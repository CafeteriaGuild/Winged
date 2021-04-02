package net.adriantodt.winged.screen

import com.mojang.blaze3d.systems.RenderSystem
import net.adriantodt.winged.identifier
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text

class WingBenchScreen(handler: WingBenchScreenHandler, playerInventory: PlayerInventory, title: Text) : HandledScreen<WingBenchScreenHandler>(handler, playerInventory, title) {
    private val TEXTURE = identifier("textures/gui/wingbench_gui.png")

    override fun init() {
        super.init()
        backgroundHeight = 220
        backgroundWidth = 174
        y -= 2
        titleY = -19
        playerInventoryTitleY =this.backgroundHeight - 118
    }

    override fun render(matrices: MatrixStack?, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderBackground(matrices)
        this.drawBackground(matrices, delta, mouseX, mouseY)
        super.render(matrices, mouseX, mouseY, delta)
        this.drawMouseoverTooltip(matrices, mouseX, mouseY)
    }


    override fun drawBackground(matrices: MatrixStack?, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f)
        client!!.textureManager.bindTexture(TEXTURE)
        val i = x
        val j = (height - backgroundHeight) / 2
        this.drawTexture(matrices, i, j, 0, 0, backgroundWidth, backgroundHeight)
    }
}