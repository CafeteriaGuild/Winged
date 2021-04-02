package net.adriantodt.winged.block

import net.adriantodt.winged.screen.WingBenchScreenHandler
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.ScreenHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class WingBenchBlock(settings: Settings) : Block(settings) {

    override fun onUse(state: BlockState?, world: World, pos: BlockPos, player: PlayerEntity?, hand: Hand?, hit: BlockHitResult?): ActionResult {
        if (!world.isClient) {
            val factory = object : ExtendedScreenHandlerFactory {
                override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity?): ScreenHandler = WingBenchScreenHandler(syncId, inv)

                override fun getDisplayName(): Text = LiteralText("WingBench")

                override fun writeScreenOpeningData(player: ServerPlayerEntity?, buf: PacketByteBuf) {}
            }
            player?.openHandledScreen(factory)
        }
        return ActionResult.success(world.isClient)
    }
}