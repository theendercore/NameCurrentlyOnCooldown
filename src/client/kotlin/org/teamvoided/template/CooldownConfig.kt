package org.teamvoided.template

import me.fzzyhmstrs.fzzy_config.annotations.Comment
import me.fzzyhmstrs.fzzy_config.config.Config
import me.fzzyhmstrs.fzzy_config.config.ConfigSection
import org.teamvoided.template.TemplateClient.id

class CooldownConfig : Config(id("cooldown")) {
    @Comment("Enables the cooldown indicator")
    var enabled = true
    var barMode = BarMode.DOUBLE_LINKED

    var barX = -8
    var barY = -16

    var doubleLinked = DoubleLinked()

    class DoubleLinked : ConfigSection() {
        var spacing = -6
    }

    @Comment("Only offhand bar config, main hand uses base values")
    var doubleSplit = DoubleSplit()

    class DoubleSplit : ConfigSection() {
        var offHandX = -8
        var offHandY = -22
    }

}

enum class BarMode { SINGLE, MAINHAND_ONLY, OFFHAND_ONLY, DOUBLE_LINKED, DOUBLE_SPLIT }