package net.adriantodt.winged.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    protected boolean onGround;
    @Shadow
    @Final
    protected DataTracker dataTracker;

    @Shadow
    protected abstract boolean getFlag(int index);

    @Shadow
    public abstract boolean hasVehicle();
}
