package io.github.rysefoxx.challenge.extension

import org.bukkit.GameMode
import java.util.*

fun String.toGameMode(input: String, default: GameMode): GameMode {
    return when (input.lowercase(Locale.getDefault())) {
        "survival" -> GameMode.SURVIVAL
        "creative" -> GameMode.CREATIVE
        "adventure" -> GameMode.ADVENTURE
        "spectator" -> GameMode.SPECTATOR
        else -> default
    }
}