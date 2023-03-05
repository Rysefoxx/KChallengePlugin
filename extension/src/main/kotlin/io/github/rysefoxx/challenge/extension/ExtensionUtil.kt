package io.github.rysefoxx.challenge.extension

import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.Bukkit

object ExtensionUtil {

    fun translatedBroadcast(key: String, placeHolder: String, audiences: BukkitAudiences) {
        Bukkit.getOnlinePlayers().forEach {
            it.translated(key, placeHolder, audiences)
        }
    }

}