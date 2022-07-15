package net.adriantodt.winged.block

import net.adriantodt.winged.screen.WingBenchScreenHandler
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.HorizontalFacingBlock
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.screen.SimpleNamedScreenHandlerFactory
import net.minecraft.state.StateManager
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class WingBenchBlock(settings: Settings) : HorizontalFacingBlock(settings) {
    private val title = Text.translatable("container.winged.wingbench")

    init {
        this.defaultState = stateManager.defaultState.with(FACING, Direction.NORTH)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        builder?.add(FACING)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        return defaultState.with(FACING, ctx.playerFacing.opposite)
    }

    override fun onUse(state: BlockState?, world: World, pos: BlockPos, player: PlayerEntity?, hand: Hand?, hit: BlockHitResult?): ActionResult {
        if (!world.isClient) {
            player?.openHandledScreen(SimpleNamedScreenHandlerFactory({ syncId, inv, _ ->
                WingBenchScreenHandler(syncId, inv)
            }, title))
        }
        return ActionResult.success(world.isClient)
    }
}
