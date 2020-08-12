package net.adriantodt.winged.mixin;

import net.adriantodt.winged.Winged;
import net.adriantodt.winged.WingedLivingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin implements WingedLivingEntity {
    protected Vec3d winged$lastPos;

    @Override
    public Vec3d getLastPos() {
        return winged$lastPos;
    }

    @Inject(at = @At("HEAD"), method = "tickMovement")
    public void beforeTickMovement(CallbackInfo info) {
        winged$lastPos = this.getPos();
    }

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
