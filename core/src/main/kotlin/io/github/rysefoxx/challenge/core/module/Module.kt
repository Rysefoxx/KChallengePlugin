package io.github.rysefoxx.challenge.core.module

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class Module() {

    val active: Boolean = false
    abstract val id: String
    abstract val bundleName: String

    abstract fun start()
    abstract fun stop()


    abstract fun displayItem(player: Player): ItemStack
}