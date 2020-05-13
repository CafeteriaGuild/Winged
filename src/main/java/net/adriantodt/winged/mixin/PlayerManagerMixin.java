package net.adriantodt.winged.mixin;

import net.adriantodt.winged.WingedPacketHandler;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {
    @Inject(method = "onPlayerConnect", at = @At("TAIL"))
    public void onPlayerLogin(ClientConnection connection, ServerPlayerEntity player, CallbackInfo info) {
        WingedPacketHandler.INSTANCE.sendServerConfig(player);
    }
}