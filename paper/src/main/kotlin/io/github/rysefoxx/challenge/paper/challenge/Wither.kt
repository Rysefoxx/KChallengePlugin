package io.github.rysefoxx.challenge.paper.challenge


import io.github.rysefoxx.challenge.core.ChallengePlugin
import io.github.rysefoxx.challenge.core.module.impl.ChallengeModule
import io.github.rysefoxx.challenge.document.impl.challenge.WitherDocument
import io.github.rysefoxx.challenge.extension.ExtensionUtil
import io.github.rysefoxx.challenge.sound.GameSound
import io.github.rysefoxx.challenge.sound.GameSoundKeys
import io.github.rysefoxx.challenge.timer.bridge.TimerBridge
import io.github.rysefoxx.challenge.timer.bridge.TimerStatus
import org.bukkit.Bukkit
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent

class Wither : ChallengeModule(), Listener {

    override val id: String
        get() = "wither"

    override var enabled: Boolean
        get() = WitherDocument.getBoolean("enabled")
        set(value) {
            WitherDocument.set("enabled", value)
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
        if (entity.type != EntityType.WITHER) return

        tryToChangeGamemode()
        TimerBridge.tryPause()
        ExtensionUtil.translatedBroadcast("wither_kill", killer.name, ChallengePlugin.adventure)
    }
}