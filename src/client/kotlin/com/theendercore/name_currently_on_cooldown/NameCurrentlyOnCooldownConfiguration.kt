package com.theendercore.name_currently_on_cooldown

import me.fzzyhmstrs.fzzy_config.annotations.Comment
import com.theendercore.name_currently_on_cooldown.NameCurrentlyOnCooldownClient.MODIFICATION_IDENTIFIER
import com.theendercore.name_currently_on_cooldown.NameCurrentlyOnCooldownClient.identifier


class NameCurrentlyOnCooldownConfiguration : Configuration(identifier(MODIFICATION_IDENTIFIER)) {
    @Comment("Enables the cooldown indicator")
    var enabledModification = true

    @Comment("The way which the cooldown indicator is displayed")
    var cooldownIndicatorDisplayType = CooldownIndicatorDisplayType.TOW_LINKED_INDICATORS

    @Comment("Horizontal Offset for the cooldown indicator")
    var indicatorXOffset = -8

    @Comment("Vertical Offset for the cooldown indicator")
    var indicatorYOffset = -12

    @Comment("Only important if display type is TOW_LINKED_INDICATORS")
    var linkedIndicatorConfiguration = TowLinkedIndicatorConfiguration()

    class TowLinkedIndicatorConfiguration : ConfigurationSection() {
        var verticalOffsetBetweenIndicators = -3
    }

    @Comment("Only offhand bar config, main hand uses base values")
    var separateIndicatorConfiguration = TowSeparateIndicatorConfiguration()

    class TowSeparateIndicatorConfiguration : ConfigurationSection() {
        var offHandIndicatorXOffset = -8
        var offHandIndicatorYOffset = -15
    }

    enum class CooldownIndicatorDisplayType {
        TOW_LINKED_INDICATORS,
        TOW_SEPARATE_INDICATORS,
        SINGLE_INDICATOR,
        ONLY_MAINHAND_INDICATOR,
        ONLY_OFFHAND_INDICATOR
    }
}
