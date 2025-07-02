package com.theendercore.name_currently_on_cooldown

import com.theendercore.name_currently_on_cooldown.NCOCClient.CONFIG
import com.theendercore.name_currently_on_cooldown.NCOCClient.id
import com.theendercore.name_currently_on_cooldown.config.IndicatorDisplayType
import net.minecraft.client.DeltaTracker
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.renderer.RenderPipelines.CROSSHAIR
import net.minecraft.world.level.GameType

val INDICATOR_BACKGROUND = id("hud/cooldown_indicator_background")
val INDICATOR = id("hud/cooldown_indicator")

@Suppress("UNUSED_PARAMETER")
fun renderCooldownIndicator(gui: GuiGraphics, deltaTracker: DeltaTracker) {
    if (!CONFIG.enabledMod) return
    val client = Minecraft.getInstance()
    val player = client.player ?: return

    if (client.options.hideGui) return
    if (!client.options.cameraType.isFirstPerson) return
    if (client.gameMode?.playerMode == GameType.SPECTATOR) return
    if (player.offhandItem.isEmpty && player.mainHandItem.isEmpty) return

    val mainCooldown = player.cooldowns.getCooldownPercent(player.mainHandItem, 0.0f)
    val offCooldown = player.cooldowns.getCooldownPercent(player.offhandItem, 0.0f)
    when (CONFIG.cooldownIndicatorDisplayType) {
        IndicatorDisplayType.SINGLE_INDICATOR -> {
            val cooldown = if (mainCooldown > 0f) mainCooldown else offCooldown
            if (cooldown > 0f) gui.drawPrimaryIndicator(cooldown)
        }

        IndicatorDisplayType.ONLY_MAINHAND_INDICATOR -> if (mainCooldown > 0.0f) gui.drawPrimaryIndicator(mainCooldown)
        IndicatorDisplayType.ONLY_OFFHAND_INDICATOR -> if (offCooldown > 0.0f) gui.drawPrimaryIndicator(offCooldown)
        IndicatorDisplayType.TOW_LINKED_INDICATORS -> {
            if (mainCooldown > 0.0f) gui.drawPrimaryIndicator(mainCooldown)
            if (offCooldown > 0.0f) {
                val xCoordinate = gui.guiWidth() / 2 + CONFIG.indicatorXOffset
                val yCoordinate =
                    gui.guiHeight() / 2 + CONFIG.indicatorYOffset + if (mainCooldown > 0f) CONFIG.verticalOffsetBetweenIndicators else 0
                gui.drawIndicator(xCoordinate, yCoordinate, offCooldown)
            }
        }

        IndicatorDisplayType.TOW_SEPARATE_INDICATORS -> {
            if (mainCooldown > 0.0f) gui.drawPrimaryIndicator(mainCooldown)
            if (offCooldown > 0.0f) {
                val xCoordinate = gui.guiWidth() / 2 + CONFIG.offHandIndicatorXOffset
                val yCoordinate = gui.guiHeight() / 2 + CONFIG.offHandIndicatorYOffset
                gui.drawIndicator(xCoordinate, yCoordinate, offCooldown)
            }
        }
    }
}

fun GuiGraphics.drawPrimaryIndicator(cooldown: Float) {
    val xCoordinate = this.guiWidth() / 2 + CONFIG.indicatorXOffset
    val yCoordinate = this.guiHeight() / 2 + CONFIG.indicatorYOffset
    this.drawIndicator(xCoordinate, yCoordinate, cooldown)
}

fun GuiGraphics.drawIndicator(xCoordinate: Int, yCoordinate: Int, cooldown: Float) {
    this.blitSprite(CROSSHAIR, INDICATOR_BACKGROUND, xCoordinate, yCoordinate, 16, 4)
    this.blitSprite(CROSSHAIR, INDICATOR, 16, 4, 0, 0, xCoordinate, yCoordinate, (cooldown * 17).toInt(), 4)
}
