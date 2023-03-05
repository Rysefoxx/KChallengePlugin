package io.github.rysefoxx.challenge.timer.spigot.i18n

import TranslationRegistration
import net.kyori.adventure.key.Key
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Path
import java.util.*

object TimerTranslationProvider {

    fun registerAll(plugin: JavaPlugin) {
        TranslationRegistration.register(
            plugin,
            Key.key("timer"),
            Path.of("timer"),
            listOf(Locale.ENGLISH, Locale.GERMAN)
        )
    }
}