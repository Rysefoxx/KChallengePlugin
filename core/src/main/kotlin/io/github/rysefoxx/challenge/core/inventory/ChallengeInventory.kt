package io.github.rysefoxx.challenge.core.inventory

import io.github.rysefoxx.challenge.core.ChallengePlugin
import io.github.rysefoxx.challenge.core.module.ModuleManager
import io.github.rysefoxx.challenge.core.module.impl.ChallengeModule
import io.github.rysefoxx.challenge.extension.fillDefaultBorders
import io.github.rysefoxx.challenge.extension.getTranslated
import io.github.rysefoxx.inventory.plugin.content.IntelligentItem
import io.github.rysefoxx.inventory.plugin.content.InventoryContents
import io.github.rysefoxx.inventory.plugin.content.InventoryProvider
import io.github.rysefoxx.inventory.plugin.pagination.RyseInventory
import io.github.rysefoxx.inventory.plugin.pagination.SlotIterator
import org.bukkit.entity.Player

object ChallengeInventory {

    fun open(player: Player, plugin: ChallengePlugin) {
        RyseInventory.builder()
            .title(player.getTranslated("challenge_inventory_title"))
            .rows(4)
            .provider(object : InventoryProvider {
                override fun init(player: Player?, contents: InventoryContents?) {
                    contents!!.fillDefaultBorders(player!!)
                    val pagination = contents.pagination()
                    pagination.itemsPerPage = 7
                    pagination.iterator(
                        SlotIterator.builder()
                            .startPosition(1, 1)
                            .type(SlotIterator.SlotIteratorType.HORIZONTAL)
                            .override()
                            .build()
                    )

                    var slot = 19
                    ModuleManager.challengeModules.forEach { module ->
                        if (module !is ChallengeModule)
                            return@forEach

                        val displayItem = module.displayItem(player)

                        pagination.addItem(IntelligentItem.of(displayItem) { module.onClick(it) })
                        contents.set(slot, module.statusItem(player))
                        slot++
                    }
                }
            })
            .build(plugin)
            .open(player)
    }
}