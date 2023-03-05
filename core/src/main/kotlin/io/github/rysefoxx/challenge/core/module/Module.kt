package io.github.rysefoxx.challenge.core.module

import io.github.rysefoxx.challenge.core.ChallengePlugin
import io.github.rysefoxx.challenge.extension.*
import io.github.rysefoxx.inventory.plugin.pagination.InventoryManager
import net.kyori.adventure.text.minimessage.tag.resolver.Formatter
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class Module() {

    lateinit var plugin: ChallengePlugin
    val inventoryManager: InventoryManager
        get() {
            return plugin.inventoryManager
        }

    abstract var enabled: Boolean
    abstract val id: String
    abstract val bundleName: String

    open fun start() {}

    open fun stop() {}

    open fun displayItem(player: Player): ItemStack {
        return ItemBuilder(player.toMaterial("${id}_material"))
            .displayName(player.getTranslated("${id}_displayname"))
            .lore(player.toArray("${id}_lore"))
            .itemFlags(player.toItemFlags("${id}_itemflags"))
            .build()
    }

    open fun statusItem(player: Player): ItemStack {
        return ItemBuilder(player.toMaterial("${id}_status_material"))
            .displayName(player.getTranslated("${id}_status_displayname_${if (enabled) "enabled" else "disabled"}"))
            .lore(player.toArray("${id}_status_lore", Formatter.choice("choice", enabled.toInt())))
            .itemFlags(player.toItemFlags("${id}_status_itemflags"))
            .build()
    }
}