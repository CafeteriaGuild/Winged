package net.adriantodt.winged.mixin;

import net.adriantodt.winged.WingedUtilityItems;
import net.adriantodt.winged.ext.WingedEnderDragonExtension;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(EnderDragonEntity.class)
public abstract class EnderDragonEntityMixin extends MobEntity implements WingedEnderDragonExtension {

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    private UUID taggedPlayer = null;

    protected EnderDragonEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "damagePart", at = @At("HEAD"))
    private void winged_tagPlayer(EnderDragonPart part, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        Entity attacker = source.getAttacker();
        if (attacker instanceof PlayerEntity && ((PlayerEntity) attacker).getMainHandStack().getItem() == WingedUtilityItems.INSTANCE.getDippedCeremonialKnife()) {
            taggedPlayer = attacker.getUuid();
        }
    }

    @Nullable
    @Override
    public UUID getTaggedPlayer() {
        return taggedPlayer;
    }
}
