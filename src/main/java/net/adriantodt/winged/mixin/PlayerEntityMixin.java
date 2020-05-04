package net.adriantodt.winged.mixin;

import net.adriantodt.winged.IsWinged;
import net.adriantodt.winged.data.WingedPlayerData;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntityMixin implements IsWinged {
    private static final TrackedData<WingedPlayerData> winged$PlayerData = DataTracker.registerData(PlayerEntity.class, WingedPlayerData.DataHandler.INSTANCE);

    @Shadow
    public abstract boolean checkFallFlying();

    @Shadow
    public abstract void startFallFlying();

    @Inject(
            at = @At("RETURN"),
            method = "initDataTracker"
    )
    protected void initDataTracker(CallbackInfo info) {
        this.dataTracker.startTracking(winged$PlayerData, WingedPlayerData.NO_WING);
    }

    @Override
    public WingedPlayerData getWingedPlayerData() {
        return dataTracker.get(winged$PlayerData);
    }

    @Override
    public void setWingedPlayerData(WingedPlayerData data) {
        dataTracker.set(winged$PlayerData, data);
    }

    @Inject(
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;getEquippedStack(Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/item/ItemStack;"
            ),
            method = "checkFallFlying",
            cancellable = true
    )
    public void checkFallFlying(CallbackInfoReturnable<Boolean> info) {
        if (getWingedPlayerData().getWing() != null) {
            this.startFallFlying();
            info.setReturnValue(true);
        }
    }

    @Inject(
            at = @At("RETURN"),
            method = "readCustomDataFromTag"
    )
    public void readCustomDataFromTag(CompoundTag tag, CallbackInfo info) {
        setWingedPlayerData(WingedPlayerData.readDataFromTag(tag.getCompound("winged$playerData")));
    }

    @Inject(
            at = @At("RETURN"),
            method = "writeCustomDataToTag"
    )
    public void writeCustomDataToTag(CompoundTag tag, CallbackInfo info) {
        CompoundTag child = new CompoundTag();
        getWingedPlayerData().writeDataToTag(child);
        tag.put("winged$playerData", child);
    }
}
