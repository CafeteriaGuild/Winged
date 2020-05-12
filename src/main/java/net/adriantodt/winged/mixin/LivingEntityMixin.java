package net.adriantodt.winged.mixin;

import net.adriantodt.winged.Winged;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin {
    @ModifyArg(
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;setFlag(IZ)V"
            ), method = "initAi", index = 1
    )
    private boolean injectAiFix(boolean value) {
        boolean bl = this.getFlag(7);

        if (bl && !this.isOnGround() && !this.hasVehicle()) {
            return Winged.INSTANCE.getPlayerComponentType().get(this).getWing() != null || value;
        }
        return value;
    }
}
