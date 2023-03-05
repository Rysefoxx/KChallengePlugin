package io.github.rysefoxx.challenge.extension

import org.bukkit.inventory.ItemFlag

fun ItemFlag.parse(key: String) : ItemFlag? {
    enumValues<ItemFlag>().forEach {
        if (it.name.equals(key, true)) {
            return it
        }
    }
    return null
}