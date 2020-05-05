package net.adriantodt.winged.mixin;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public abstract boolean isOnGround();

    @Shadow
    protected abstract boolean getFlag(int index);

    @Shadow
    public abstract boolean hasVehicle();
}
