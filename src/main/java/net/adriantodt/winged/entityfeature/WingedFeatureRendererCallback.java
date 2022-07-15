package net.adriantodt.winged.entityfeature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class WingedFeatureRendererCallback implements LivingEntityFeatureRendererRegistrationCallback {
    @Override
    public void registerRenderers(EntityType<? extends LivingEntity> entityType, LivingEntityRenderer<?, ?> entityRenderer, RegistrationHelper helper, EntityRendererFactory.Context context) {
        helper.register(new WingedFeatureRenderer<>(entityRenderer, context.getModelLoader()));
    }
}
