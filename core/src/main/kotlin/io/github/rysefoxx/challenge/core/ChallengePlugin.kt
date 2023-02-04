package io.github.rysefoxx.challenge.core

import io.github.rysefoxx.challenge.core.command.SettingsCommand
import io.github.rysefoxx.challenge.core.i18n.TranslationProvider
import io.github.rysefoxx.challenge.core.platform.PlatformManager
import io.github.rysefoxx.inventory.plugin.pagination.InventoryManager
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin


class ChallengePlugin : JavaPlugin() {

    companion object {
        lateinit var adventure: BukkitAudiences
        lateinit var miniMessage: MiniMessage
    }

    private val inventoryManager: InventoryManager = InventoryManager(this)

    override fun onEnable() {
        checkDepends()
        init()
    }

    override fun onDisable() {
        adventure.close()
    }

    private fun init() {
        registerCommands()

        adventure = BukkitAudiences.create(this)
        miniMessage = MiniMessage.miniMessage()
        inventoryManager.invoke()
        PlatformManager.load(this)
        TranslationProvider.registerAll(this)
    }

    private fun checkDepends() {
//        if (!Bukkit.getPluginManager().isPluginEnabled("ChallengeTimer")) {
//            JarFetcher.download("Challenge-Timer-0.1-alpha-allasadsdsadasdasdasdas")
//        }
    }

    private fun registerCommands() {
        getCommand("settings")?.setExecutor(SettingsCommand(this))
    }
}