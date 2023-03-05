package io.github.rysefoxx.challenge.timer.spigot

import io.github.rysefoxx.challenge.document.DocumentManager
import io.github.rysefoxx.challenge.sound.i18n.SoundTranslationProvider
import io.github.rysefoxx.challenge.timer.spigot.i18n.TimerTranslationProvider
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.plugin.java.JavaPlugin

class TimerPlugin : JavaPlugin() {

    companion object {
        lateinit var adventure: BukkitAudiences
            private set
        lateinit var miniMessage: MiniMessage
            private set
    }

    override fun onEnable() {
        init()
    }

    override fun onDisable() {
        adventure.close()
    }

    private fun init() {
        saveDefaultConfig()
        DocumentManager.loadAll()

        registerAdventure()
        registerCommands()
        registerTranslations()

        Timer.startTask(this)
    }

    private fun registerCommands() {
        getCommand("timer")?.setExecutor(TimerCommand(this))
    }

    private fun registerTranslations() {
        TimerTranslationProvider.registerAll(this)
        SoundTranslationProvider.registerAll(this)
    }

    private fun registerAdventure() {
        adventure = BukkitAudiences.create(this)
        miniMessage = MiniMessage.miniMessage()
    }
}