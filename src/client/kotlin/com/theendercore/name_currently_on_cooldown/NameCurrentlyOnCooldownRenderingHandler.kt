package com.theendercore.name_currently_on_cooldown

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.DeltaTracker
import net.minecraft.world.GameMode
import com.theendercore.name_currently_on_cooldown.NameCurrentlyOnCooldownClient.CONFIGURATION
import com.theendercore.name_currently_on_cooldown.NameCurrentlyOnCooldownClient.identifier

val COOLDOWN_INDICATOR_BACKGROUND = identifier("hud/cooldown_indicator_background")
val COOLDOWN_INDICATOR = identifier("hud/cooldown_indicator")

@Suppress("UNUSED_PARAMETER")
fun renderCooldownIndicator(graphicalUserInterfaceGraphics: GraphicalUserInterfaceGraphics, deltaTracker: DeltaTracker) {
    if (!CONFIGURATION.enabledModification) return
    val minecraftClient = MinecraftClient.getInstance()
    val clientPlayerEntity = minecraftClient.player ?: return

    if (!minecraftClient.options.perspective.isFirstPerson || minecraftClient.interactionManager?.currentGameMode == GameMode.SPECTATOR) return
    if (clientPlayerEntity.offHandStack.isEmpty && clientPlayerEntity.mainHandStack.isEmpty) return

    val mainHandItemCooldown = clientPlayerEntity.itemCooldownManager.getCooldownProgress(clientPlayerEntity.mainHandStack.item, 0.0f)
    val offHandItemCooldown = clientPlayerEntity.itemCooldownManager.getCooldownProgress(clientPlayerEntity.offHandStack.item, 0.0f)
    RenderSystem.enableBlend()
    RenderSystem_cursorBlend()
    when (CONFIGURATION.cooldownIndicatorDisplayType) {
        NameCurrentlyOnCooldownConfiguration.CooldownIndicatorDisplayType.SINGLE_INDICATOR -> {
            val cooldown = if (mainHandItemCooldown > 0f) mainHandItemCooldown else offHandItemCooldown
            if (cooldown > 0f) graphicalUserInterfaceGraphics.drawPrimaryIndicator(cooldown)
        }

        NameCurrentlyOnCooldownConfiguration.CooldownIndicatorDisplayType.ONLY_MAINHAND_INDICATOR -> if (mainHandItemCooldown > 0.0f) graphicalUserInterfaceGraphics.drawPrimaryIndicator(mainHandItemCooldown)
        NameCurrentlyOnCooldownConfiguration.CooldownIndicatorDisplayType.ONLY_OFFHAND_INDICATOR -> if (offHandItemCooldown > 0.0f) graphicalUserInterfaceGraphics.drawPrimaryIndicator(offHandItemCooldown)
        NameCurrentlyOnCooldownConfiguration.CooldownIndicatorDisplayType.TOW_LINKED_INDICATORS -> {
            if (mainHandItemCooldown > 0.0f) graphicalUserInterfaceGraphics.drawPrimaryIndicator(mainHandItemCooldown)
            if (offHandItemCooldown > 0.0f) {
                val xCoordinate = graphicalUserInterfaceGraphics.scaledWindowWidth / 2 + CONFIGURATION.indicatorXOffset
                val yCoordinate = graphicalUserInterfaceGraphics.scaledWindowHeight / 2 + CONFIGURATION.indicatorYOffset + if (mainHandItemCooldown > 0f) CONFIGURATION.linkedIndicatorConfiguration.verticalOffsetBetweenIndicators else 0
                graphicalUserInterfaceGraphics.drawIndicator(xCoordinate, yCoordinate, offHandItemCooldown)
            }
        }

        NameCurrentlyOnCooldownConfiguration.CooldownIndicatorDisplayType.TOW_SEPARATE_INDICATORS -> {
            if (mainHandItemCooldown > 0.0f) graphicalUserInterfaceGraphics.drawPrimaryIndicator(mainHandItemCooldown)
            if (offHandItemCooldown > 0.0f) {
                val xCoordinate = graphicalUserInterfaceGraphics.scaledWindowWidth / 2 + CONFIGURATION.separateIndicatorConfiguration.offHandIndicatorXOffset
                val yCoordinate = graphicalUserInterfaceGraphics.scaledWindowHeight / 2 + CONFIGURATION.separateIndicatorConfiguration.offHandIndicatorYOffset
                graphicalUserInterfaceGraphics.drawIndicator(xCoordinate, yCoordinate, offHandItemCooldown)
            }
        }
    }
    RenderSystem.defaultBlendFunc()
    RenderSystem.disableBlend()
}

fun GraphicalUserInterfaceGraphics.drawPrimaryIndicator(cooldown: Float) {
    val xCoordinate = this.scaledWindowWidth / 2 + CONFIGURATION.indicatorXOffset
    val yCoordinate = this.scaledWindowHeight / 2 + CONFIGURATION.indicatorYOffset
    this.drawIndicator(xCoordinate, yCoordinate, cooldown)
}

fun GraphicalUserInterfaceGraphics.drawIndicator(xCoordinate: Int, yCoordinate: Int, cooldown: Float) {
    val widthAtCurrentCooldown = (cooldown * 17).toInt()
    this.drawGuiTexture(COOLDOWN_INDICATOR_BACKGROUND, xCoordinate, yCoordinate, 16, 4)
    this.drawGuiTexture(COOLDOWN_INDICATOR, 16, 4, 0, 0, xCoordinate, yCoordinate, widthAtCurrentCooldown, 4)
}
