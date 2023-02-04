package io.github.rysefoxx.challenge.core.command

import io.github.rysefoxx.challenge.core.ChallengePlugin
import io.github.rysefoxx.challenge.core.default.PermissionDefaults
import io.github.rysefoxx.challenge.core.extension.*
import io.github.rysefoxx.challenge.core.inventory.ChallengeInventory
import io.github.rysefoxx.challenge.core.module.ModuleManager
import io.github.rysefoxx.challenge.core.util.ItemBuilder
import io.github.rysefoxx.inventory.plugin.content.IntelligentItem
import io.github.rysefoxx.inventory.plugin.content.InventoryContents
import io.github.rysefoxx.inventory.plugin.content.InventoryProvider
import io.github.rysefoxx.inventory.plugin.pagination.RyseInventory
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SettingsCommand(private val plugin: ChallengePlugin) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player)
            return true

        if(!sender.hasPermission(PermissionDefaults.SETTINGS_COMMAND)) {
            sender.translated("no_permission")
            return true
        }

        if (ModuleManager.challengeModules.isEmpty()) {
            sender.translated("no_modules")
            return true
        }

        RyseInventory.builder()
            .title(sender.getTranslated("settings_title"))
            .rows(6)
            .provider(object : InventoryProvider {
                override fun init(player: Player?, contents: InventoryContents?) {
                    contents!!.fillDefault(player!!)
                    contents.removeItemWithConsumer(
                        10, 11, 13, 15, 16, 18, 21, 22, 23, 26, 27, 30, 31, 32, 35, 37, 38, 40, 42, 43
                    );

                    contents.set(1, 4, IntelligentItem.of(
                        ItemBuilder(sender.toMaterial("item_challenge_material"))
                            .displayName(sender.getTranslated("item_challenge_displayname"))
                            .lore(sender.toArray("item_challenge_lore"))
                            .build()
                    ) {
                        ChallengeInventory.open(player, plugin)
                    })
                }
            })
            .build(plugin)
            .open(sender)

        return true
    }
}