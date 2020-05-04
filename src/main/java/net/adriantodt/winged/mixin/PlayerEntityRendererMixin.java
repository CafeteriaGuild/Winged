package net.adriantodt.winged.mixin;

import net.adriantodt.winged.entityfeature.WingedFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRendererMixin<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    @Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/client/render/entity/EntityRenderDispatcher;Z)V")
    public void init(EntityRenderDispatcher dispatcher, boolean bl, CallbackInfo info) {
        this.addFeature(new WingedFeatureRenderer<>(this));
    }
}
