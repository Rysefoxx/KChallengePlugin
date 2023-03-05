package io.github.rysefoxx.challenge.core.i18n

import TranslationRegistration
import io.github.rysefoxx.challenge.core.module.ModuleManager
import net.kyori.adventure.key.Key
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Path
import java.util.*

object CoreTranslationProvider {

    fun registerAll(plugin: JavaPlugin) {
        TranslationRegistration.register(
            plugin,
            Key.key("general"),
            Path.of("general"),
            listOf(Locale.ENGLISH, Locale.GERMAN)
        )
        TranslationRegistration.register(
            plugin,
            Key.key("inventory"),
            Path.of("inventory"),
            listOf(Locale.ENGLISH, Locale.GERMAN)
        )

        registerModules(plugin)
    }

    private fun registerModules(plugin: JavaPlugin) {
        ModuleManager.challengeModules.forEach { module ->
            TranslationRegistration.register(
                plugin,
                Key.key(module.id),
                Path.of(module.bundleName),
                listOf(Locale.ENGLISH, Locale.GERMAN)
            )
        }
    }
}