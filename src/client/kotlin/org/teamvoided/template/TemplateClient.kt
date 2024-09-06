package org.teamvoided.template

import com.mojang.blaze3d.platform.GlStateManager.DestFactor
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor
import com.mojang.blaze3d.systems.RenderSystem
import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import me.fzzyhmstrs.fzzy_config.api.RegisterType
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.render.DeltaTracker
import net.minecraft.util.Identifier
import net.minecraft.world.GameMode
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.teamvoided.template.TemplateClient.CONFIG

@Suppress("unused", "MemberVisibilityCanBePrivate")
object TemplateClient {
    const val MODID = "template"
    var CONFIG = ConfigApi.registerAndLoadConfig(::CooldownConfig, RegisterType.CLIENT)

    @JvmField
    val log: Logger = LoggerFactory.getLogger(TemplateClient::class.simpleName)
    fun init() {
        log.info("Hello from Client")
        if (CONFIG.enabled) HudRenderCallback.EVENT.register(::renderCooldown)
    }

    fun id(path: String): Identifier = Identifier.of(MODID, path)
}

@Suppress("FunctionName")
private fun RenderSystem_cursorBlend() = RenderSystem.blendFuncSeparate(
    SourceFactor.ONE_MINUS_DST_COLOR, DestFactor.ONE_MINUS_SRC_COLOR, SourceFactor.ONE, DestFactor.ZERO
)

private val COOLDOWN_INDICATOR_BACKGROUND: Identifier =
    Identifier.ofDefault("hud/crosshair_attack_indicator_background")
private val COOLDOWN_INDICATOR_PROGRESS: Identifier = Identifier.ofDefault("hud/crosshair_attack_indicator_progress")

private fun renderCooldown(graphics: GuiGraphics, deltaTracker: DeltaTracker) {
    if (!CONFIG.enabled) return
    val client = MinecraftClient.getInstance()
    val player = client.player ?: return

    if (!client.options.perspective.isFirstPerson || client.interactionManager?.currentGameMode == GameMode.SPECTATOR) return

    val selectedItem = if (player.mainHandStack.isEmpty) player.offHandStack else player.mainHandStack
    if (selectedItem.isEmpty) return

    RenderSystem.enableBlend()
    RenderSystem_cursorBlend()
    val mainCooldown = player.itemCooldownManager.getCooldownProgress(player.mainHandStack.item, 0f)
    val offHandCooldown = player.itemCooldownManager.getCooldownProgress(player.offHandStack.item, 0f)
    when (CONFIG.barMode) {
        BarMode.SINGLE -> {
            val cooldown = if (mainCooldown > 0f) mainCooldown else offHandCooldown
            if (cooldown > 0f) graphics.drawMainBar(cooldown)
        }

        BarMode.DOUBLE_LINKED -> {
            if (mainCooldown > 0f) graphics.drawMainBar(mainCooldown)

            if (offHandCooldown > 0f) {
                val x = graphics.scaledWindowWidth / 2 + CONFIG.barX
                val y =
                    graphics.scaledWindowHeight / 2 + CONFIG.barY + if (mainCooldown > 0f) CONFIG.doubleLinked.spacing else 0
                graphics.drawBar(x, y, offHandCooldown)
            }
        }

        BarMode.DOUBLE_SPLIT -> {
            if (mainCooldown > 0f) graphics.drawMainBar(mainCooldown)

            if (offHandCooldown > 0f) {
                val x = graphics.scaledWindowWidth / 2 + CONFIG.doubleSplit.offHandX
                val y = graphics.scaledWindowHeight / 2 + CONFIG.doubleSplit.offHandY
                graphics.drawBar(x, y, offHandCooldown)
            }
        }
    }
    RenderSystem.defaultBlendFunc()
    RenderSystem.disableBlend()
}

fun GuiGraphics.drawMainBar(cooldown: Float) {
    val x = this.scaledWindowWidth / 2 + CONFIG.barX
    val y = this.scaledWindowHeight / 2 + CONFIG.barY
    this.drawBar(x, y, cooldown)
}

fun GuiGraphics.drawBar(x: Int, y: Int, cooldown: Float) {
    val slice = (cooldown * 17.0f).toInt()
    this.drawGuiTexture(COOLDOWN_INDICATOR_BACKGROUND, x, y, 16, 4)
    this.drawGuiTexture(COOLDOWN_INDICATOR_PROGRESS, 16, 4, 0, 0, x, y, slice, 4)
}
