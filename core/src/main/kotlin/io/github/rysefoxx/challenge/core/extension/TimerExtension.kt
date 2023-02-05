package io.github.rysefoxx.challenge.core.extension

import io.github.rysefoxx.challenge.core.ChallengePlugin
import io.github.rysefoxx.challenge.core.config.impl.PluginConfig
import io.github.rysefoxx.challenge.core.util.ItemBuilder
import io.github.rysefoxx.challenge.extension.JarDownloader
import io.github.rysefoxx.challenge.extension.JarModule
import io.github.rysefoxx.inventory.plugin.content.IntelligentItem
import io.github.rysefoxx.inventory.plugin.content.InventoryContents
import net.kyori.adventure.text.minimessage.tag.resolver.Formatter
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class TimerExtension(private val plugin: ChallengePlugin) : JarModule() {

    override var downloaded: Boolean = false
        get() = PluginConfig.getBoolean("$id.downloaded")
        set(value) {
            field = value
            PluginConfig.set("$id.downloaded", value)
        }
    override var isDownloading: Boolean = false

    override var enabled: Boolean
        get() = Bukkit.getPluginManager().isPluginEnabled(id)
        set(value) {
            if (value && !enabled) Bukkit.getPluginManager().getPlugin(id)
                ?.let { Bukkit.getPluginManager().enablePlugin(it) }
            else if (!value && enabled) Bukkit.getPluginManager().getPlugin(id)
                ?.let { Bukkit.getPluginManager().disablePlugin(it) }
        }

    override val id: String
        get() = "ChallengeTimer"

    private var from: UUID? = null
        get() = UUID.fromString(PluginConfig.getString("$id.from"))
        set(value) {
            field = value
            PluginConfig.set("$id.from", value.toString())
        }

    private var date: LocalDateTime? = null
        get() = LocalDateTime.parse(PluginConfig.getString("$id.date"))
        set(value) {
            field = value
            PluginConfig.set("$id.date", value.toString())
        }

    override fun displayItem(player: Player): IntelligentItem {
        return IntelligentItem.of(
            ItemBuilder(player.toMaterial("challengetimer_item_material"))
                .displayName(player.getTranslated("challengetimer_item_displayname"))
                .lore(
                    player.toArraytest(
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
        val contents = plugin.inventoryManager.getContents(player.uniqueId)

        when {
            event.isLeftClick -> handleLeftClick(player, contents.orElse(null), event)
            event.isRightClick -> handleRightClick(player, contents.orElse(null), event)
            event.click == ClickType.MIDDLE -> handleMiddleClick(player, contents.orElse(null), event)
        }
    }

    override fun download(player: Player, enabled: Boolean): Boolean {
        val file = JarDownloader.download(id)

        if(enabled)
            JarDownloader.loadAndActive(file)

        return true
    }

    private fun handleMiddleClick(player: Player, contents: InventoryContents?, event: InventoryClickEvent) {
        player.playGuiSound()
        if (!downloaded) {
            player.translated("plugin_not_downloaded")
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
        player.translated("plugin_deleted", id)
    }

    private fun handleRightClick(player: Player, contents: InventoryContents?, event: InventoryClickEvent) {
        player.playGuiSound()
        if (!downloaded) {
            player.translated("plugin_not_downloaded")
            return
        }

        if (enabled) {
            enabled = false
            Bukkit.getPluginManager().getPlugin(id)?.let { Bukkit.getPluginManager().disablePlugin(it) }
            player.translated("plugin_disabled")
        } else {
            enabled = true
            Bukkit.getPluginManager().getPlugin(id)?.let { Bukkit.getPluginManager().enablePlugin(it) }
            player.translated("plugin_enabled")
        }

        contents?.update(event.slot, displayItem(player))
    }

    private fun handleLeftClick(player: Player, contents: InventoryContents?, event: InventoryClickEvent) {
        player.playGuiSound()
        if (downloaded) {
            player.translated("plugin_already_downloaded")
            return
        }
        if (isDownloading) {
            player.translated("plugin_in_download")
            return
        }

        isDownloading = true

        player.translated("plugin_start_download", id)
        if (download(player, true)) {
            downloaded = true
            from = player.uniqueId
            date = LocalDateTime.now()
            contents?.update(event.slot, displayItem(player))
            player.translated("plugin_download_success")
        } else {
            player.translated("plugin_download_error")
        }

        isDownloading = false
    }
}