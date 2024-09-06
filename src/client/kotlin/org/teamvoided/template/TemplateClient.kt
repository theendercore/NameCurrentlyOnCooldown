package org.teamvoided.template

import com.mojang.blaze3d.platform.GlStateManager.DestFactor
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor
import com.mojang.blaze3d.systems.RenderSystem
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.LivingEntity
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress("unused")
object TemplateClient {
    const val MODID = "template"

    @JvmField
    val log: Logger = LoggerFactory.getLogger(TemplateClient::class.simpleName)

    val COOLDOWN_INDICATOR_FULL: Identifier = Identifier.ofDefault("hud/crosshair_attack_indicator_full")
    val COOLDOWN_INDICATOR_BACKGROUND: Identifier = Identifier.ofDefault("hud/crosshair_attack_indicator_background")
    val COOLDOWN_INDICATOR_PROGRESS: Identifier = Identifier.ofDefault("hud/crosshair_attack_indicator_progress")

    fun init() {
        log.info("Hello from Client")

        HudRenderCallback.EVENT.register hud@{ graphics, deltaTracker ->
            val client = MinecraftClient.getInstance()
            val player = client.player ?: return@hud

            val selectedItem = player.mainHandStack ?: return@hud
            if (selectedItem.isEmpty) return@hud

            RenderSystem.enableBlend()
            RenderSystem.blendFuncSeparate(
                SourceFactor.ONE_MINUS_DST_COLOR, DestFactor.ONE_MINUS_SRC_COLOR, SourceFactor.ONE, DestFactor.ZERO
            )
            val f: Float = player.getAttackCooldownProgress(0.0f)
            var bl = false
            if (client.targetedEntity != null && client.targetedEntity is LivingEntity && (f >= 1.0f)) {
                bl = (player.attackCooldownProgressPerTick > 5.0f) and player.isAlive
            }

            val j = graphics.scaledWindowHeight / 2 + 7 - 16
            val k = graphics.scaledWindowWidth / 2 + 8
            if (bl) {
                graphics.drawGuiTexture(COOLDOWN_INDICATOR_FULL, k, j, 16, 16)
            } else if (f < 1.0f) {
                val l = (f * 17.0f).toInt()
                graphics.drawGuiTexture(COOLDOWN_INDICATOR_BACKGROUND, k, j, 16, 4)
                graphics.drawGuiTexture(COOLDOWN_INDICATOR_PROGRESS, 16, 4, 0, 0, k, j, l, 4)
            }
            RenderSystem.disableBlend()
        }
    }

    fun id(path: String) = Identifier.of(MODID, path)
}
