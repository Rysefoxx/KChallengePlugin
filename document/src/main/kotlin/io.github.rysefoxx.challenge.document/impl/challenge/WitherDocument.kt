package io.github.rysefoxx.challenge.document.impl.challenge

import io.github.rysefoxx.challenge.document.AbstractDocument

object WitherDocument : AbstractDocument() {
    override val path: String
        get() = "plugins/ChallengePlugin/settings"
    override val name: String
        get() = "wither"

    init {
        init()
    }

    override fun saveDefaults() {
        addDefault("enabled", false)
    }
}