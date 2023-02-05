package io.github.rysefoxx.challenge.extension

import io.github.rysefoxx.inventory.plugin.content.IntelligentItem
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

abstract class JarModule {

    abstract var downloaded : Boolean
    abstract var isDownloading : Boolean

    abstract var enabled : Boolean
    abstract val id: String

    abstract fun displayItem(player: Player): IntelligentItem

    abstract fun onClick(event: InventoryClickEvent)

    abstract fun download(player: Player, enable: Boolean) : Boolean

}