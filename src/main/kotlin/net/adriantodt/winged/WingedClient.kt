package net.adriantodt.winged

import net.adriantodt.winged.entityfeature.WingedFeatureRendererCallback
import net.adriantodt.winged.screen.WingBenchScreen
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry
import net.fabricmc.fabric.api.client.model.ModelVariantProvider
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import net.minecraft.client.util.ModelIdentifier
import net.minecraft.util.Identifier

object WingedClient : ClientModInitializer {
    override fun onInitializeClient() {

        ScreenRegistry.register(Winged.wingbenchType) { handler, inv, title -> WingBenchScreen(handler,inv, title) }

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(WingedFeatureRendererCallback())
        ModelLoadingRegistry.INSTANCE.registerVariantProvider { manager ->
            ModelVariantProvider { modelId, a ->
                if (modelId.namespace == "winged" && modelId.path.endsWith("creative_flight")) {
                    val actualModelId = ModelIdentifier(Identifier(modelId.namespace, modelId.path.replace("_creative_flight", "")), modelId.variant)
                    return@ModelVariantProvider a.loadModel(actualModelId)
                } else return@ModelVariantProvider null
            }
        }
    }
}