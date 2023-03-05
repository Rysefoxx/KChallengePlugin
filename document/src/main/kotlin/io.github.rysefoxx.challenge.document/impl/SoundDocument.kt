package io.github.rysefoxx.challenge.document.impl

import io.github.rysefoxx.challenge.document.AbstractDocument
import org.bukkit.SoundCategory

object SoundDocument : AbstractDocument() {

    override val path: String
        get() = "plugins/ChallengeSound/sound"

    override val name: String
        get() = "sound"

    init {
        init()
    }

    override fun saveDefaults() {
        addDefault("timer_start.sound", "BLOCK_NOTE_BLOCK_FLUTE")
        addDefault("timer_start.channel", "MASTER")
        addDefault("timer_start.volume", 1.0)
        addDefault("timer_start.pitch", 1.54)

        addDefault("timer_pause.sound", "BLOCK_NOTE_BLOCK_BASS")
        addDefault("timer_pause.channel", "MASTER")
        addDefault("timer_pause.volume", 1.0)
        addDefault("timer_pause.pitch", 2)

        addDefault("timer_reset.sound", "BLOCK_NOTE_BLOCK_BIT")
        addDefault("timer_reset.channel", "MASTER")
        addDefault("timer_reset.volume", 1.0)
        addDefault("timer_reset.pitch", 1.3)

        addDefault("timer_expired.sound", "ENTITY_GENERIC_EXPLODE")
        addDefault("timer_expired.channel", "MASTER")
        addDefault("timer_expired.volume", 1.0)
        addDefault("timer_expired.pitch", 2)

        addDefault("gui_challenge_click.sound", "UI_BUTTON_CLICK")
        addDefault("gui_challenge_click.channel", "MASTER")
        addDefault("gui_challenge_click.volume", 1.0)
        addDefault("gui_challenge_click.pitch", 2)
    }
}