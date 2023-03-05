package io.github.rysefoxx.challenge.sound.i18n

import TranslationRegistration
import net.kyori.adventure.key.Key
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Path
import java.util.*

object SoundTranslationProvider {

    fun registerAll(plugin: JavaPlugin) {
        TranslationRegistration.register(
            plugin,
            Key.key("sound"),
            Path.of("sound"),
            listOf(Locale.ENGLISH, Locale.GERMAN)
        )
    }
}