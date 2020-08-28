package net.adriantodt.winged.mixin;

import net.adriantodt.winged.WingedLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements WingedLivingEntity {
    protected Vec3d winged$lastPos;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public Vec3d getLastPos() {
        return winged$lastPos;
    }

    @Inject(at = @At("HEAD"), method = "tickMovement")
    public void beforeTickMovement(CallbackInfo info) {
        winged$lastPos = this.getPos();
    }
}
