package io.github.rysefoxx.challenge.core.module.impl

import io.github.rysefoxx.challenge.core.module.Module
import io.github.rysefoxx.challenge.extension.toGameMode
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

abstract class ChallengeModule : Module(), Listener {

    override val bundleName: String
        get() = "challenges"

    abstract fun onClick(event: InventoryClickEvent)

    fun registerEvent() {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    fun tryToChangeGamemode() {
        if (!plugin.config.getBoolean("challenge_stop_after_victory", true))
            return

        val gameMode = plugin.config.getString("challenge_gamemode_after_victory", "SPECTATOR")
        Bukkit.getOnlinePlayers().forEach {
            it.gameMode = gameMode!!.toGameMode(gameMode, GameMode.SPECTATOR)
        }
    }
}