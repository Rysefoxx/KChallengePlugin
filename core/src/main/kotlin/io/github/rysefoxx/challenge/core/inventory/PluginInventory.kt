package io.github.rysefoxx.challenge.core.inventory

import io.github.rysefoxx.challenge.core.ChallengePlugin
import io.github.rysefoxx.challenge.core.extension.fillDefaultBorders
import io.github.rysefoxx.challenge.core.extension.getTranslated
import io.github.rysefoxx.challenge.core.extension.ExtensionManager
import io.github.rysefoxx.inventory.plugin.content.InventoryContents
import io.github.rysefoxx.inventory.plugin.content.InventoryProvider
import io.github.rysefoxx.inventory.plugin.pagination.RyseInventory
import io.github.rysefoxx.inventory.plugin.pagination.SlotIterator
import org.bukkit.entity.Player

object PluginInventory {

    fun open(player: Player, plugin: ChallengePlugin) {
        RyseInventory.builder()
            .title(player.getTranslated("plugin_inventory_title"))
            .rows(4)
            .provider(object : InventoryProvider {
                override fun init(player: Player?, contents: InventoryContents?) {
                    contents!!.fillDefaultBorders(player!!)
                    val pagination = contents.pagination()
                    pagination.itemsPerPage = 28
                    pagination.iterator(
                        SlotIterator.builder()
                            .startPosition(1, 1)
                            .type(SlotIterator.SlotIteratorType.HORIZONTAL)
                            .build()
                    )

                    ExtensionManager.extensions.forEach {
                        pagination.addItem(it.displayItem(player))
                    }
                }
            })
            .build(plugin)
            .open(player)
    }
}