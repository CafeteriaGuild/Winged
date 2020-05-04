package net.adriantodt.winged.mixin;

import net.adriantodt.winged.IsWinged;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(CapeFeatureRenderer.class)
public abstract class CapeFeatureRendererMixin {
    @Inject(
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;getEquippedStack(Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/item/ItemStack;"
            ),
            method = "render",
            cancellable = true
    )
    public void render(MatrixStack m, VertexConsumerProvider v, int i, AbstractClientPlayerEntity player, float f, float g, float h, float j, float k, float l, CallbackInfo info) {
        if (player instanceof IsWinged && ((IsWinged) player).getWingedPlayerData().getWing() != null)
            info.cancel();
    }
}
