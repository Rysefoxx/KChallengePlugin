package io.github.rysefoxx.challenge.core.config.impl

import io.github.rysefoxx.challenge.core.ChallengePlugin
import io.github.rysefoxx.challenge.core.config.AbstractConfig

object PluginConfig : AbstractConfig() {

    override val path: String
        get() = "plugins/ChallengePlugin/Plugin"

    override val name: String
        get() = "plugin"

    init {
        init()
    }
}