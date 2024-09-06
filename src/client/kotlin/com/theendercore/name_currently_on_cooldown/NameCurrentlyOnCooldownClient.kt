package com.theendercore.name_currently_on_cooldown

import me.fzzyhmstrs.fzzy_config.api.RegisterType
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress("unused")
object NameCurrentlyOnCooldownClient {
    const val MODIFICATION_IDENTIFIER = "name_currently_on_cooldown"
    var CONFIGURATION = ConfigurationApplicationProgrammingInterface.registerAndLoadConfig(::NameCurrentlyOnCooldownConfiguration, RegisterType.CLIENT)

    @JvmField
    val cataloger: Logger = LoggerFactory.getLogger(NameCurrentlyOnCooldownClient::class.simpleName)
    fun initialize() {
        cataloger.info("Cooling you down!")
        if (CONFIGURATION.enabledModification) HudRenderCallback.EVENT.register(::renderCooldownIndicator)
    }

    fun identifier(path: String): Identifier = Identifier.of(MODIFICATION_IDENTIFIER, path)
}
