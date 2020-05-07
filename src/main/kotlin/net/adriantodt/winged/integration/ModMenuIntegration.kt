package net.adriantodt.winged.integration

import io.github.prospector.modmenu.api.ConfigScreenFactory
import io.github.prospector.modmenu.api.ModMenuApi
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig
import net.adriantodt.winged.WingedConfig
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

@Environment(EnvType.CLIENT)
internal class ModMenuIntegration : ModMenuApi {
    override fun getModId() = "winged"

    override fun getModConfigScreenFactory() = ConfigScreenFactory {
        AutoConfig.getConfigScreen(WingedConfig::class.java, it).get()
    }
}