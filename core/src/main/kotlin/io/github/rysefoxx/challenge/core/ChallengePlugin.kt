package io.github.rysefoxx.challenge.core

import io.github.rysefoxx.challenge.core.command.SettingsCommand
import io.github.rysefoxx.challenge.core.extension.ExtensionManager
import io.github.rysefoxx.challenge.core.i18n.CoreTranslationProvider
import io.github.rysefoxx.challenge.core.platform.PlatformManager
import io.github.rysefoxx.challenge.document.DocumentManager
import io.github.rysefoxx.inventory.plugin.pagination.InventoryManager
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.plugin.java.JavaPlugin


class ChallengePlugin : JavaPlugin() {

    companion object {
        lateinit var adventure: BukkitAudiences
        lateinit var miniMessage: MiniMessage
    }

    val inventoryManager: InventoryManager = InventoryManager(this)

    override fun onEnable() {
        init()
    }

    override fun onDisable() {
        adventure.close()
    }

    private fun init() {
        registerCommands()
        DocumentManager.loadAll()

        registerAdventure()
        registerManager()
        registerTranslations()
    }

    private fun registerCommands() {
        getCommand("settings")?.setExecutor(SettingsCommand(this))
    }

    private fun registerTranslations() {
        CoreTranslationProvider.registerAll(this)
    }

    private fun registerManager() {
        inventoryManager.invoke()
        PlatformManager.load(this)
        ExtensionManager.loadAll(this)
    }

    private fun registerAdventure() {
        adventure = BukkitAudiences.create(this)
        miniMessage = MiniMessage.miniMessage()
    }
}