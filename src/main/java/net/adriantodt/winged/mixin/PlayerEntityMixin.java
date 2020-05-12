package net.adriantodt.winged.mixin;

import net.adriantodt.winged.Winged;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntityMixin {

    @Shadow
    public abstract boolean checkFallFlying();

    @Shadow
    public abstract void startFallFlying();

    @Inject(
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;getEquippedStack(Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/item/ItemStack;"
            ),
            method = "checkFallFlying",
            cancellable = true
    )
    public void checkFallFlying(CallbackInfoReturnable<Boolean> info) {
        if (Winged.INSTANCE.getPlayerComponentType().get(this).getWing() != null) {
            this.startFallFlying();
            info.setReturnValue(true);
        }
    }
}
