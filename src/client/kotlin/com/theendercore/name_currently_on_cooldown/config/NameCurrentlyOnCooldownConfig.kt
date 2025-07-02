package com.theendercore.name_currently_on_cooldown.config

import com.theendercore.name_currently_on_cooldown.NCOCClient.MODID
import com.theendercore.name_currently_on_cooldown.NCOCClient.id
import me.fzzyhmstrs.fzzy_config.annotations.Comment
import me.fzzyhmstrs.fzzy_config.config.Config
import me.fzzyhmstrs.fzzy_config.config.ConfigGroup


class NameCurrentlyOnCooldownConfig : Config(id(MODID)) {
    @Comment("Enables the cooldown indicator")
    var enabledMod = true

    @Comment("The way which the cooldown indicator is displayed")
    var cooldownIndicatorDisplayType = IndicatorDisplayType.TOW_LINKED_INDICATORS

    @Comment("Horizontal Offset for the cooldown indicator")
    var indicatorXOffset = -8

    @Comment("Vertical Offset for the cooldown indicator")
    var indicatorYOffset = -11

    @Suppress("unused")
    @Comment("Only important if display type is TOW_LINKED_INDICATORS")
    var linkedIndicator = ConfigGroup("linkedIndicator", false)

    @ConfigGroup.Pop
    var verticalOffsetBetweenIndicators = -2

    @Suppress("unused")
    @Comment("Only offhand bar config, main hand uses base values")
    var separateIndicator = ConfigGroup("separateIndicator", true)
    var offHandIndicatorXOffset = -8

    @ConfigGroup.Pop
    var offHandIndicatorYOffset = -13
}
