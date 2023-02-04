package io.github.rysefoxx.challenge.paper.challenge

import io.github.rysefoxx.challenge.core.extension.getTranslated
import io.github.rysefoxx.challenge.core.extension.toArray
import io.github.rysefoxx.challenge.core.extension.toItemFlags
import io.github.rysefoxx.challenge.core.extension.toMaterial
import io.github.rysefoxx.challenge.core.module.impl.ChallengeModule
import io.github.rysefoxx.challenge.core.util.ItemBuilder
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Wither : ChallengeModule() {

    override val id: String
        get() = "wither"

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override fun displayItem(player: Player): ItemStack {
        return ItemBuilder(player.toMaterial(id + "_material"))
            .displayName(player.getTranslated(id + "_displayname"))
            .lore(player.toArray(id + "_lore"))
            .itemFlags(player.toItemFlags(id + "_itemflags"))
            .build()
    }
}