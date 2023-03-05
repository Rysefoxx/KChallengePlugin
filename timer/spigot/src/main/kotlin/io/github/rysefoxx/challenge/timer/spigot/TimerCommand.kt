package io.github.rysefoxx.challenge.timer.spigot

import io.github.rysefoxx.challenge.extension.translated
import io.github.rysefoxx.challenge.timer.spigot.util.TimeUtil
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class TimerCommand(private val plugin: TimerPlugin) : CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player)
            return true

        if (args == null)
            return true

        if (args.size == 1 && (args[0].equals("resume", true) || args[0].equals("start", true))) {
            if (Timer.enabled) {
                sender.translated("timer_already_running", TimerPlugin.adventure)
                return true
            }
            Timer.start(sender, plugin)
            return true
        }

        if (args.size == 1 && (args[0].equals("pause", true) || args[0].equals("stop", true))) {
            if (!Timer.enabled) {
                sender.translated("timer_already_paused", TimerPlugin.adventure)
                return true
            }
            Timer.pause(sender, plugin)
            return true
        }

        if (args.size == 1 && args[0].equals("reset", true)) {
            Timer.reset(sender, plugin)
            return true
        }

        if (args.size == 1 && args[0].equals("direction", true)) {
            Timer.direction(sender, plugin)
            return true
        }

        if (args.isNotEmpty() && args[0].equals("set", true)) {
            val startIndex = args.indexOf("set") + 1
            val input = args.slice(startIndex until args.size).joinToString(" ")
            val time = TimeUtil.convertToSeconds(input)

            if(time == -1L) {
                sender.translated("timer_set_invalid", input, TimerPlugin.adventure)
                return true
            }

            Timer.set(time, input, sender, plugin)
            return true
        }

        sender.translated("timer_help", TimerPlugin.adventure)
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String>? {
        if (sender !is Player)
            return null

        if (args == null)
            return null

        if (args.size == 1) {
            val list = mutableListOf("resume", "pause", "reset", "direction", "set")
            if (args[0].isEmpty())
                return list

            return list.filter { it.startsWith(args[0], true) }.toMutableList()
        }

        if (args.size == 2 && args[0].equals("set", true))
            return mutableListOf("1d 5h 10m 20s")

        return mutableListOf()
    }
}