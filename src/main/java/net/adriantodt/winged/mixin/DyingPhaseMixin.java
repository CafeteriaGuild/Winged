package net.adriantodt.winged.mixin;

import net.adriantodt.winged.Winged;
import net.adriantodt.winged.WingedUtilityItems;
import net.adriantodt.winged.ext.WingedEnderDragonExtension;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.AbstractPhase;
import net.minecraft.entity.boss.dragon.phase.DyingPhase;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(DyingPhase.class)
public abstract class DyingPhaseMixin extends AbstractPhase {

    public DyingPhaseMixin(EnderDragonEntity dragon) {
        super(dragon);
    }

    @Inject(method = "serverTick", at = @At("RETURN"))
    private void winged_dropHeartOfTheSky(CallbackInfo ci) {
        if (dragon.getHealth() <= 0 && Winged.INSTANCE.getConfigHolder().getConfig().getLootTables().getEnderdragonDropsHeartOfTheSky()) {
            WingedEnderDragonExtension ext = (WingedEnderDragonExtension) dragon;
            UUID taggedPlayer = ext.getTaggedPlayer();
            if (taggedPlayer != null) {
                ServerPlayerEntity player = dragon.getServer().getPlayerManager().getPlayer(taggedPlayer);
                if (player != null) {
                    ItemScatterer.spawn(dragon.world, dragon.getBlockPos(), DefaultedList.ofSize(1, new ItemStack(WingedUtilityItems.INSTANCE.getHeartOfTheSky75())));
                    player.sendMessage(new TranslatableText("misc.winged.dragonKill")
                            .formatted(Formatting.DARK_PURPLE, Formatting.ITALIC), false);
                }
            }
        }
    }
}
