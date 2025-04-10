package com.theendercore.name_currently_on_cooldown

import com.theendercore.name_currently_on_cooldown.NameCurrentlyOnCooldownClient.MODIFICATION_IDENTIFIER
import com.theendercore.name_currently_on_cooldown.NameCurrentlyOnCooldownClient.identifier
import me.fzzyhmstrs.fzzy_config.annotations.Comment
import me.fzzyhmstrs.fzzy_config.config.ConfigGroup


class NameCurrentlyOnCooldownConfiguration : Configuration(identifier(MODIFICATION_IDENTIFIER)) {
    @Comment("Enables the cooldown indicator")
    var enabledModification = true

    @Comment("The way which the cooldown indicator is displayed")
    var cooldownIndicatorDisplayType = CooldownIndicatorDisplayType.TOW_LINKED_INDICATORS

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

    enum class CooldownIndicatorDisplayType {
        TOW_LINKED_INDICATORS,
        TOW_SEPARATE_INDICATORS,
        SINGLE_INDICATOR,
        ONLY_MAINHAND_INDICATOR,
        ONLY_OFFHAND_INDICATOR
    }
}
