package io.github.rysefoxx.challenge.sound

import io.github.rysefoxx.challenge.document.impl.SoundDocument
import org.bukkit.Sound
import org.bukkit.plugin.java.JavaPlugin

object SoundManager {

    fun fetch(path: String): Sound? {
        val key = SoundDocument.getString(path) ?: return null

        return Sound.valueOf(key)
    }

}