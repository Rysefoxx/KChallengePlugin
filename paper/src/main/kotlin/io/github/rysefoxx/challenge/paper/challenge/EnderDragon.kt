package io.github.rysefoxx.challenge.paper.challenge


import io.github.rysefoxx.challenge.core.ChallengePlugin
import io.github.rysefoxx.challenge.core.module.impl.ChallengeModule
import io.github.rysefoxx.challenge.document.impl.challenge.EnderDragonDocument
import io.github.rysefoxx.challenge.extension.*
import io.github.rysefoxx.challenge.sound.GameSound
import io.github.rysefoxx.challenge.sound.GameSoundKeys
import io.github.rysefoxx.challenge.timer.bridge.TimerBridge
import io.github.rysefoxx.challenge.timer.bridge.TimerStatus
import net.kyori.adventure.text.minimessage.tag.resolver.Formatter
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class EnderDragon : ChallengeModule() {

    override val id: String
        get() = "enderdragon"

    override var enabled: Boolean
        get() = EnderDragonDocument.getBoolean("enabled")
        set(value) {
            EnderDragonDocument.set("enabled", value)
        }

    override fun onClick(event: InventoryClickEvent) {
        val player = event.whoClicked as Player

        enabled = !enabled
        inventoryManager.getContents(event.whoClicked.uniqueId).ifPresent {
            it.update(event.slot + 9, statusItem(player))
        }

        GameSound.play(player, GameSoundKeys.GUI_CHALLENGE_CLICK, ChallengePlugin.adventure)
    }

    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        if (!enabled) return
        if (TimerBridge.status() == TimerStatus.TIMER_PAUSED)
            return

        val entity = event.entity
        val killer = event.entity.killer ?: return
        if (entity.type != EntityType.ENDER_DRAGON) return

        tryToChangeGamemode()
        TimerBridge.tryPause()
        ExtensionUtil.translatedBroadcast("enderdragon_kill", killer.name, ChallengePlugin.adventure)
    }
}