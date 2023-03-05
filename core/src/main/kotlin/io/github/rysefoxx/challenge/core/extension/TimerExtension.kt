package io.github.rysefoxx.challenge.core.extension

import io.github.rysefoxx.challenge.core.ChallengePlugin
import io.github.rysefoxx.challenge.core.jar.JarDownloader
import io.github.rysefoxx.challenge.core.jar.JarModule
import io.github.rysefoxx.challenge.document.impl.TimerExtensionDocument
import io.github.rysefoxx.challenge.extension.*
import io.github.rysefoxx.inventory.plugin.content.IntelligentItem
import io.github.rysefoxx.inventory.plugin.content.InventoryContents
import net.kyori.adventure.text.minimessage.tag.resolver.Formatter
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class TimerExtension(private val plugin: ChallengePlugin) : JarModule() {

    override val id: String
        get() = "ChallengeTimer"
    override var isDownloading: Boolean = false

    override var downloaded: Boolean = false
        get() = TimerExtensionDocument.getBoolean("$id.downloaded") || Bukkit.getPluginManager().isPluginEnabled(id)
        set(value) {
            field = value
            TimerExtensionDocument.set("$id.downloaded", value)
        }

    override var enabled: Boolean
        get() = Bukkit.getPluginManager().isPluginEnabled(id)
        set(value) {
            if (value && !enabled) Bukkit.getPluginManager().getPlugin(id)
                ?.let { Bukkit.getPluginManager().enablePlugin(it) }
            else if (!value && enabled) Bukkit.getPluginManager().getPlugin(id)
                ?.let { Bukkit.getPluginManager().disablePlugin(it) }
        }

    private var from: UUID? = null
        get() {
            val value = TimerExtensionDocument.getString("$id.from")
            return if (value == null) null else UUID.fromString(value)
        }
        set(value) {
            field = value
            TimerExtensionDocument.set("$id.from", value.toString())
        }

    private var date: LocalDateTime? = null
        get() {
            val value = TimerExtensionDocument.getString("$id.date")
            return if (value == null) null else LocalDateTime.parse(value)
        }
        set(value) {
            field = value
            TimerExtensionDocument.set("$id.date", value.toString())
        }

    override fun displayItem(player: Player): IntelligentItem {
        return IntelligentItem.of(
            ItemBuilder(player.toMaterial("challengetimer_item_material"))
                .displayName(player.getTranslated("challengetimer_item_displayname"))
                .lore(
                    player.toArray(
                        "challengetimer_item_lore",
                        listOf(
                            if (from == null) "" else Bukkit.getOfflinePlayer(from!!).name,
                            date?.format(DateTimeFormatter.ofPattern(player.getTranslatedString("date_format")))
                        ),
                        listOf(
                            Formatter.choice("choice", downloaded.toInt()),
                            Formatter.choice("choice", if (from == null) 0 else 1),
                            Formatter.choice(
                                "choice",
                                if (date == null) 0 else
                                    date!!.format(DateTimeFormatter.ofPattern(player.getTranslatedString("date_format")))
                                        ?.toInt()
                            ),
                            Formatter.choice("choice", enabled.toInt())
                        )
                    )
                )
                .build()
        ) {
            onClick(it)
        }
    }

    override fun onClick(event: InventoryClickEvent) {
        val player = event.whoClicked as? Player ?: return
        val contents = plugin.inventoryManager.getContents(player.uniqueId).orElse(null)

        when {
            !event.isShiftClick && event.isLeftClick -> download(player, contents, event)
            !event.isShiftClick && event.isRightClick -> toggle(player, contents, event)
            event.isShiftClick && event.isRightClick -> delete(player, contents, event)
        }
    }

    override fun download(enable: Boolean): Boolean {
        val file = JarDownloader.download(id + "1")

        if (enable)
            JarDownloader.enableJar(file)

        return true
    }

    private fun delete(player: Player, contents: InventoryContents?, event: InventoryClickEvent) {
        player.playGuiSound()
        if (!downloaded) {
            player.translated("plugin_not_downloaded", ChallengePlugin.adventure)
            return
        }

        if (enabled) {
            enabled = false
            Bukkit.getPluginManager().getPlugin(id)?.let { Bukkit.getPluginManager().disablePlugin(it) }
        }

        val file = File("plugins/", "$id.jar")

        if (file.exists()) {
            val success = file.deleteRecursively()
            if (!success)
                plugin.logger.severe("Could not delete plugin jar $id")
        }

        from = null
        date = null
        downloaded = false
        contents?.update(event.slot, displayItem(player))
        player.translated("plugin_deleted", id, ChallengePlugin.adventure)
    }

    private fun toggle(player: Player, contents: InventoryContents?, event: InventoryClickEvent) {
        player.playGuiSound()
        if (!downloaded) {
            player.translated("plugin_not_downloaded", ChallengePlugin.adventure)
            return
        }

        if (enabled) {
            enabled = false
            Bukkit.getPluginManager().getPlugin(id)?.let { Bukkit.getPluginManager().disablePlugin(it) }
            player.translated("plugin_disabled", ChallengePlugin.adventure)
        } else {
            enabled = true
            Bukkit.getPluginManager().getPlugin(id)?.let { Bukkit.getPluginManager().enablePlugin(it) }
            player.translated("plugin_enabled", ChallengePlugin.adventure)
        }

        contents?.update(event.slot, displayItem(player))
    }

    private fun download(player: Player, contents: InventoryContents?, event: InventoryClickEvent) {
        player.playGuiSound()
        if (downloaded) {
            player.translated("plugin_already_downloaded", ChallengePlugin.adventure)
            return
        }
        if (isDownloading) {
            player.translated("plugin_in_download", ChallengePlugin.adventure)
            return
        }

        isDownloading = true

        player.translated("plugin_start_download", id, ChallengePlugin.adventure)
        if (download(true)) {
            downloaded = true
            from = player.uniqueId
            date = LocalDateTime.now()
            contents?.update(event.slot, displayItem(player))
            player.translated("plugin_download_success", ChallengePlugin.adventure)
        } else {
            player.translated("plugin_download_error", ChallengePlugin.adventure)
        }

        isDownloading = false
    }
}