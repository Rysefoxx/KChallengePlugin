package io.github.rysefoxx.challenge.sound

import io.github.rysefoxx.challenge.document.impl.SoundDocument
import io.github.rysefoxx.challenge.extension.translated
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.Player
import java.lang.Exception

object GameSound {

    fun play(player: Player, key: String, audiences: BukkitAudiences) {
        val sound = SoundManager.fetch("$key.sound") ?: return
        val volume = SoundDocument.getFloat("$key.volume", 1.0)
        val pitch = SoundDocument.getFloat("$key.pitch", 1.0)

        val channel = try {
            SoundCategory.valueOf(SoundDocument.getString("$key.channel", "MASTER"))
        }catch (ex: Exception) {
            player.translated("invalid_channel", key, audiences)
            SoundCategory.MASTER
        }

        player.playSound(player.location, sound, channel, volume, pitch)
    }

}