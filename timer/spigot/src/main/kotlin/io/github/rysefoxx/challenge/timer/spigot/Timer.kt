package io.github.rysefoxx.challenge.timer.spigot

import io.github.rysefoxx.challenge.document.impl.TimerDocument
import io.github.rysefoxx.challenge.extension.getTranslated
import io.github.rysefoxx.challenge.extension.getTranslatedString
import io.github.rysefoxx.challenge.extension.toGameMode
import io.github.rysefoxx.challenge.extension.translated
import io.github.rysefoxx.challenge.sound.GameSound
import io.github.rysefoxx.challenge.sound.GameSoundKeys
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player

object Timer {

    private var time: Long
        get() = TimerDocument.getLong("time")
        set(value) {
            TimerDocument.set("time", value)
        }

    private var forward: Boolean
        get() = TimerDocument.getBoolean("forward")
        set(value) {
            TimerDocument.set("forward", value)
        }

    var enabled = false

    fun start(player: Player, plugin: TimerPlugin) {
        enabled = true
        if(broadcast(GameSoundKeys.TIMER_START, plugin))
            return

        player.translated(GameSoundKeys.TIMER_START, TimerPlugin.adventure)
        GameSound.play(player, GameSoundKeys.TIMER_START, TimerPlugin.adventure)
    }

    fun pause(player: Player?, plugin: TimerPlugin) {
        enabled = false
        if(broadcast(GameSoundKeys.TIMER_PAUSE, plugin))
            return

        player?.translated(GameSoundKeys.TIMER_PAUSE, TimerPlugin.adventure)
        player?.let { GameSound.play(it, GameSoundKeys.TIMER_PAUSE, TimerPlugin.adventure) }
    }

    fun reset(player: Player, plugin: TimerPlugin) {
        time = 0
        forward = true
        enabled = false
        if(broadcast(GameSoundKeys.TIMER_RESET, plugin))
            return

        player.translated(GameSoundKeys.TIMER_RESET, TimerPlugin.adventure)
        GameSound.play(player, GameSoundKeys.TIMER_RESET, TimerPlugin.adventure)
    }

    fun set(timeInSeconds: Long, input: String, player: Player, plugin: TimerPlugin) {
        time = timeInSeconds

        if(broadcast(GameSoundKeys.TIMER_SET, plugin))
            return

        player.translated(GameSoundKeys.TIMER_SET, input, TimerPlugin.adventure)
        GameSound.play(player, GameSoundKeys.TIMER_SET, TimerPlugin.adventure)
    }

    fun direction(player: Player, plugin: TimerPlugin) {
        forward = !forward
        val key = GameSoundKeys.TIMER_DIRECTION

        if (plugin.config.getBoolean("${key}_broadcast", false)) {
            Bukkit.getOnlinePlayers().forEach {
                it.translated(
                    if (forward) "${key}_forward" else "${key}_backward",
                    TimerPlugin.adventure
                )
                if (plugin.config.getBoolean("${key}_broadcast_sound", false)) {
                    GameSound.play(it, key, TimerPlugin.adventure)
                }
            }
            return
        }

        player.translated(
            if (forward) "${key}_forward" else "${key}_backward",
            TimerPlugin.adventure
        )

        GameSound.play(player, key, TimerPlugin.adventure)
    }

    fun startTask(plugin: TimerPlugin) {
        Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
            if (!enabled) {
                Bukkit.getOnlinePlayers().forEach {
                    TimerPlugin.adventure.player(it).sendActionBar(it.getTranslated("timer_paused_actionbar"))
                }
                return@Runnable
            }

            if (forward) {
                time++
            } else {
                if (time <= 0) {
                    timerExpired(plugin)
                    return@Runnable
                }
                time--
            }
            Bukkit.getOnlinePlayers().forEach {
                TimerPlugin.adventure.player(it)
                    .sendActionBar(TimeFormatter.format(time, it.getTranslatedString("timer_enabled_actionbar")))
            }
        }, 0, 20)
    }

    private fun timerExpired(plugin: TimerPlugin) {
        time = 0
        enabled = false
        forward = true

        if (plugin.config.getBoolean("timer_expired_change_gamemode", true)) {
            val gameMode = plugin.config.getString("timer_expired_gamemode", "SPECTATOR")
            Bukkit.getOnlinePlayers().forEach {
                it.gameMode = gameMode!!.toGameMode(gameMode, GameMode.SPECTATOR)
            }
        }

        if(plugin.config.getBoolean("timer_expired_broadcast", true)) {
            Bukkit.getOnlinePlayers().forEach {
                it.translated("timer_expired_broadcast", TimerPlugin.adventure)
            }
        }

        if(plugin.config.getBoolean("timer_expired_broadcast_sound", true)) {
            Bukkit.getOnlinePlayers().forEach {
                GameSound.play(it, GameSoundKeys.TIMER_EXPIRED, TimerPlugin.adventure)
            }
        }
    }

    private fun broadcast(key: String, plugin: TimerPlugin) : Boolean {
        var returnVal = false
        if (plugin.config.getBoolean("${key}_broadcast", false)) {
            Bukkit.getOnlinePlayers().forEach {
                it.translated(key, TimerPlugin.adventure)
            }
            returnVal = true
        }
        if (plugin.config.getBoolean("${key}_broadcast_sound", false)) {
            Bukkit.getOnlinePlayers().forEach {
                GameSound.play(it, key, TimerPlugin.adventure)
            }
            returnVal = true
        }
        return returnVal
    }
}