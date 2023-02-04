package io.github.rysefoxx.challenge.core.platform

import io.github.rysefoxx.challenge.core.ChallengePlugin
import io.github.rysefoxx.challenge.core.platform.impl.PaperPlatform
import io.github.rysefoxx.challenge.core.platform.impl.SpigotPlatform
import org.bukkit.Bukkit

object PlatformManager {

    private val spigot: Regex = Regex(".*Spigot.*")
    private val paper: Regex = Regex(".*Paper.*")

    private lateinit var platform: Platform

    fun load(plugin: ChallengePlugin) {
        findPlatform(plugin)
        platform.loadModules()
    }

    private fun findPlatform(plugin: ChallengePlugin) {
        val version: String = Bukkit.getServer().version
        when {
            spigot.matches(version) -> platform = SpigotPlatform(plugin)
            paper.matches(version) -> platform = PaperPlatform(plugin)
            else -> plugin.logger.severe("No platform for the server software $version could be found. Make sure you start the server with Spigot or Paper.")
        }

    }

}