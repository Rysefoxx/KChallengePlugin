package io.github.rysefoxx.challenge.document.impl

import io.github.rysefoxx.challenge.document.AbstractDocument

object TimerExtensionDocument : AbstractDocument() {

    override val path: String
        get() = "plugins/ChallengeTimer/extension"

    override val name: String
        get() = "timer"

    init {
        init()
    }

    override fun saveDefaults() {
        addDefault("downloaded", false)
        addDefault("from", "null")
        addDefault("date", "null")
    }
}