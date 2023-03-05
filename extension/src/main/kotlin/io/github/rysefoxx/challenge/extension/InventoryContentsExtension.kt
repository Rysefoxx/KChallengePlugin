package io.github.rysefoxx.challenge.extension

import io.github.rysefoxx.inventory.plugin.content.InventoryContents
import org.bukkit.entity.Player


fun InventoryContents.fillDefaultBorders(player: Player) {

    val itemStack = ItemBuilder(player.toMaterial("empty_item_material"))
        .displayName(player.getTranslated("empty_item_displayname"))
        .lore(player.toArray("empty_item_lore"))
        .build()

    this.fillBorders(itemStack)
}

fun InventoryContents.fillDefault(player: Player) {

    val itemStack = ItemBuilder(player.toMaterial("empty_item_material"))
        .displayName(player.getTranslated("empty_item_displayname"))
        .lore(player.toArray("empty_item_lore"))
        .build()

    this.fill(itemStack)
}