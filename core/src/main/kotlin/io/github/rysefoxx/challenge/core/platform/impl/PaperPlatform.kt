package io.github.rysefoxx.challenge.core.platform.impl

import io.github.rysefoxx.challenge.core.ChallengePlugin
import io.github.rysefoxx.challenge.core.platform.Platform

class PaperPlatform(plugin: ChallengePlugin) : Platform(plugin, "io.github.rysefoxx.challenge.paper") {

    override fun loadModules() {
        plugin.logger.info("Found paper platform. Loading modules...")
        registerModules(modulePath)
    }
}