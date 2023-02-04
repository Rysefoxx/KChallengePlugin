package io.github.rysefoxx.challenge.core.i18n

import io.github.rysefoxx.challenge.core.ChallengePlugin
import io.github.rysefoxx.challenge.core.module.ModuleManager
import io.github.rysefoxx.challenge.core.util.Translation
import net.kyori.adventure.key.Key
import java.nio.file.Path
import java.util.*

object TranslationProvider {

    fun registerAll(plugin: ChallengePlugin) {
        Translation(plugin, Key.key("general"), Path.of("translations"), listOf(Locale.ENGLISH, Locale.GERMAN))

        registerModules(plugin)
    }

    private fun registerModules(plugin: ChallengePlugin) {
        ModuleManager.challengeModules.forEach { module ->
            Translation(plugin, Key.key(module.id), Path.of(module.bundleName), listOf(Locale.ENGLISH, Locale.GERMAN))
        }
    }
}