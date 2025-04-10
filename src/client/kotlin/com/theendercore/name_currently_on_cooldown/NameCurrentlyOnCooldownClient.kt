package com.theendercore.name_currently_on_cooldown

import me.fzzyhmstrs.fzzy_config.api.RegisterType
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.ResourcePackActivationType
import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.api.ModContainer
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.function.Consumer

@Suppress("unused")
object NameCurrentlyOnCooldownClient {
    const val MODIFICATION_IDENTIFIER = "name_currently_on_cooldown"
    var CONFIGURATION = ConfigurationApplicationProgrammingInterface.registerAndLoadConfig(::NameCurrentlyOnCooldownConfiguration, RegisterType.CLIENT)

    @JvmField
    val cataloger: Logger = LoggerFactory.getLogger(NameCurrentlyOnCooldownClient::class.simpleName)
    fun initialize() {
        cataloger.info("Cooling you down!")
        if (CONFIGURATION.enabledModification) HudRenderCallback.EVENT.register(::renderCooldownIndicator)

        registerBuiltInPack(MODIFICATION_IDENTIFIER, identifier("old_bar"))
    }

    // TODO: Replace with VoidLib
    private fun registerBuiltInPack(
        modId: String, id: Identifier, packType: ResourcePackActivationType = ResourcePackActivationType.NORMAL
    ) = useMod(modId) {
        assert(ResourceManagerHelper.registerBuiltinResourcePack(id, it, packType))
        { "Failed to register built-in pack \"$id\" !" }
    }
    private fun useMod(id: String, consumer: Consumer<ModContainer>) =
        FabricLoader.getInstance().getModContainer(id).ifPresent(consumer)

    fun identifier(path: String): Identifier = Identifier.of(MODIFICATION_IDENTIFIER, path)
}
