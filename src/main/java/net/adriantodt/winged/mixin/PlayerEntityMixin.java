package net.adriantodt.winged.mixin;

import net.adriantodt.winged.Winged;
import net.adriantodt.winged.data.Wing;
import net.adriantodt.winged.data.WingedConfig;
import net.adriantodt.winged.data.components.PlayerComponent;
import net.adriantodt.winged.item.RemovalKnifeItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow public abstract ItemEntity dropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "dropInventory", at = @At(value = "TAIL"))
    private void winged_maybeDropWings(CallbackInfo info) {
        WingedConfig config = Winged.INSTANCE.getConfigHolder().getConfig();
        if (config.getKeepWingsAfterDeath() || this.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
            return;
        }

        PlayerComponent component = Winged.INSTANCE.getPlayerComponentType().getNullable(this);
        if (component == null) {
            return;
        }

        Wing wing = component.getWing();
        if (wing == null) {
            return;
        }

        ItemStack itemStack = new ItemStack(
            RemovalKnifeItem.getCoreItem(component.getCreativeFlight(), config.getWingRemovalBrokenCore())
        );

        this.dropItem(itemStack, true, false);
    }
}
