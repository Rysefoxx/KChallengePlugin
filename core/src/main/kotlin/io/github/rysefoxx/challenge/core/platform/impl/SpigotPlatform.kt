package io.github.rysefoxx.challenge.core.platform.impl

import io.github.rysefoxx.challenge.core.ChallengePlugin
import io.github.rysefoxx.challenge.core.platform.Platform

class SpigotPlatform(plugin: ChallengePlugin) : Platform(plugin, "io.github.rysefoxx.challenge.spigot") {

    override fun loadModules() {
        plugin.logger.info("Found spigot platform. Loading modules...")
        registerModules(modulePath)
    }
}