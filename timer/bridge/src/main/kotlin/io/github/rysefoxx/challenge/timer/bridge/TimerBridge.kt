package io.github.rysefoxx.challenge.timer.bridge

import io.github.rysefoxx.challenge.timer.spigot.Timer
import io.github.rysefoxx.challenge.timer.spigot.TimerPlugin
import org.bukkit.Bukkit

object TimerBridge {
    private val plugin: TimerPlugin?
        get() = Bukkit.getPluginManager().getPlugin("ChallengeTimer") as TimerPlugin?

    fun status(): TimerStatus {
        return plugin?.let {
            if (!it.isEnabled) TimerStatus.PLUGIN_UNLOADED
            else if (!Timer.enabled) TimerStatus.TIMER_PAUSED
            else TimerStatus.TIMER_RUNNING
        } ?: TimerStatus.PLUGIN_UNLOADED
    }

    fun tryPause() {
        if(status() != TimerStatus.TIMER_RUNNING)
            return

        Timer.pause(null, plugin!!)
    }

}