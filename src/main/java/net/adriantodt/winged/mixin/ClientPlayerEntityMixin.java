package net.adriantodt.winged.mixin;

import net.adriantodt.winged.Winged;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntityMixin {

    @Shadow
    @Final
    public ClientPlayNetworkHandler networkHandler;

    @Inject(
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;getEquippedStack(Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/item/ItemStack;"
            ),
            method = "tickMovement"
    )
    public void tickMovement(CallbackInfo info) {
        if (Winged.INSTANCE.getPlayerComponentType().get(this).getWing() != null && this.checkFallFlying()) {
            //noinspection ConstantConditions
            this.networkHandler.sendPacket(new ClientCommandC2SPacket((ClientPlayerEntity) ((Object) this), ClientCommandC2SPacket.Mode.START_FALL_FLYING));
        }
    }
}
