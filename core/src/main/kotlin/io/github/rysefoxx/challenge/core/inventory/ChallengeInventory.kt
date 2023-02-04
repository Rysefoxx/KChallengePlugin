package io.github.rysefoxx.challenge.core.inventory

import io.github.rysefoxx.challenge.core.ChallengePlugin
import io.github.rysefoxx.challenge.core.extension.fillDefaultBorders
import io.github.rysefoxx.challenge.core.extension.getTranslated
import io.github.rysefoxx.challenge.core.module.ModuleManager
import io.github.rysefoxx.challenge.core.module.impl.ChallengeModule
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
                            .startPosition(1,1)
                            .type(SlotIterator.SlotIteratorType.HORIZONTAL)
                            .override()
                            .build()
                    )

                    ModuleManager.challengeModules.forEach {
                        if (it !is ChallengeModule)
                            return@forEach

                        pagination.addItem(it.displayItem(player))
                    }
                }
            })
            .build(plugin)
            .open(player)
    }
}