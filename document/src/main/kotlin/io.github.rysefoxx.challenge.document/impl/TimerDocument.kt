package io.github.rysefoxx.challenge.document.impl

import io.github.rysefoxx.challenge.document.AbstractDocument

object TimerDocument : AbstractDocument() {

    override val path: String
        get() = "plugins/ChallengeTimer/timer"

    override val name: String
        get() = "timer"

    init {
        init()
    }

    override fun saveDefaults() {
        addDefault("time", 0)
        addDefault("forward", true)
    }
}