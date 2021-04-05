package net.adriantodt.winged.entityfeature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class WingedFeatureRendererCallback implements LivingEntityFeatureRendererRegistrationCallback {
    @Override
    public void registerRenderers(EntityType<? extends LivingEntity> t, LivingEntityRenderer<?, ?> context, RegistrationHelper helper) {
        helper.register(new WingedFeatureRenderer<>(context));
    }
}
