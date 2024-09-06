package com.theendercore.name_currently_on_cooldown

import com.mojang.blaze3d.platform.GlStateManager.DestFactor
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor
import com.mojang.blaze3d.systems.RenderSystem
import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import me.fzzyhmstrs.fzzy_config.config.Config
import me.fzzyhmstrs.fzzy_config.config.ConfigSection
import net.minecraft.client.gui.GuiGraphics

typealias GraphicalUserInterfaceGraphics = GuiGraphics
typealias ConfigurationApplicationProgrammingInterface = ConfigApi
typealias Configuration = Config
typealias ConfigurationSection = ConfigSection


@Suppress("FunctionName")
fun RenderSystem_cursorBlend() = RenderSystem.blendFuncSeparate(
    SourceFactor.ONE_MINUS_DST_COLOR, DestFactor.ONE_MINUS_SRC_COLOR, SourceFactor.ONE, DestFactor.ZERO
)
