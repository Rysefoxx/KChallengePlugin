package io.github.rysefoxx.challenge.timer.spigot.util

import java.lang.Exception

object TimeUtil {
    fun convertToSeconds(input: String): Long {
        var totalSeconds = 0L
        val timeValues = input.split(" ")
        for (i in timeValues.indices) {

            val value = try {
                timeValues[i].dropLast(1).toInt()
            }catch (ex : Exception) {
                totalSeconds = -1
                break
            }

            when (timeValues[i].last()) {
                'd' -> totalSeconds += value * 24 * 60 * 60
                'h' -> totalSeconds += value * 60 * 60
                'm' -> totalSeconds += value * 60
                's' -> totalSeconds += value
                else -> {
                    totalSeconds = -1
                    break
                }
            }
        }
        return totalSeconds
    }
}