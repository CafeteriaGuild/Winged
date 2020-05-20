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
    public abstract boolean method_23668(); //checkFallFlying

    @Shadow
    public abstract void method_23669(); //startFallFlying

    @Inject(
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;getEquippedStack(Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/item/ItemStack;"
            ),
            method = "method_23668",
            cancellable = true
    )
    public void checkFallFlying(CallbackInfoReturnable<Boolean> info) {
        if (Winged.INSTANCE.getPlayerComponentType().get(this).getWing() != null) {
            this.method_23669();
            info.setReturnValue(true);
        }
    }
}
