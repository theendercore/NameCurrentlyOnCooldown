package com.theendercore.name_currently_on_cooldown

import com.theendercore.name_currently_on_cooldown.config.NameCurrentlyOnCooldownConfig
import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import me.fzzyhmstrs.fzzy_config.api.RegisterType
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.ResourcePackActivationType
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress("unused")
object NCOCClient {
    const val MODID = "name_currently_on_cooldown"
    var CONFIG = ConfigApi.registerAndLoadConfig(::NameCurrentlyOnCooldownConfig, RegisterType.CLIENT)

    @JvmField
    val cataloger: Logger = LoggerFactory.getLogger(NCOCClient::class.simpleName)
    fun initialize() {
        cataloger.info("Cooling you down!")
        if (CONFIG.enabledMod) HudElementRegistry.attachElementAfter(
            VanillaHudElements.CROSSHAIR, id("cooldown_indicator"), ::renderCooldownIndicator
        )

        registerBuiltInPack(MODID, id("old_bar"))
    }

    fun id(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(MODID, path)
    private fun registerBuiltInPack(modId: String, id: ResourceLocation) {
        FabricLoader.getInstance().getModContainer(modId).ifPresent {
            assert(ResourceManagerHelper.registerBuiltinResourcePack(id, it, ResourcePackActivationType.NORMAL)) {
                "Failed to register built-in pack \"$id\" !"
            }
        }
    }
}
