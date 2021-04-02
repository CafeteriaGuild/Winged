package net.adriantodt.winged

import net.adriantodt.winged.entityfeature.WingedFeatureRendererCallback
import net.adriantodt.winged.item.SpeedometerModelPredicateProvider
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.client.model.FabricModelPredicateProviderRegistry
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry
import net.fabricmc.fabric.api.client.model.ModelVariantProvider
import net.fabricmc.fabric.api.client.rendereregistry.v1.LivingEntityFeatureRendererRegistrationCallback
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry
import net.minecraft.client.util.ModelIdentifier
import net.minecraft.util.Identifier

object WingedClient : ClientModInitializer {
    override fun onInitializeClient() {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(WingedFeatureRendererCallback())
        ClientSidePacketRegistry.INSTANCE.register(WingedPacketHandler.sync) { _, attachedData ->
            WingedPacketHandler.updateConfigs(attachedData.readCompoundTag()!!)
        }
        FabricModelPredicateProviderRegistry.register(
            WingedUtilityItems.speedometer, identifier("speed"), SpeedometerModelPredicateProvider
        )

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