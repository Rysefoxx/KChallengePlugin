package io.github.rysefoxx.challenge.timer.spigot

import net.kyori.adventure.text.Component
import java.util.concurrent.TimeUnit

object TimeFormatter {

    fun format(timeInSeconds: Long, format: String): Component {
        val days = TimeUnit.SECONDS.toDays(timeInSeconds)
        val hours = TimeUnit.SECONDS.toHours(timeInSeconds) % 24
        val minutes = TimeUnit.SECONDS.toMinutes(timeInSeconds) % 60
        val remainingSeconds = timeInSeconds % 60

        return TimerPlugin.miniMessage.deserialize(String.format(
            format.replace("[DD]", "%02d")
                .replace("[HH]", "%02d")
                .replace("[MM]", "%02d")
                .replace("[SS]", "%02d"),
            days, hours, minutes, remainingSeconds
        ))
    }
}