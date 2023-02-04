package io.github.rysefoxx.challenge.core.extension

import com.google.common.collect.ImmutableList
import io.github.rysefoxx.challenge.core.ChallengePlugin
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag

private val serializer = LegacyComponentSerializer.legacySection()

fun Player.toMaterial(key: String): Material {
    val componentString = serializer.serialize(Component.translatable(key))
    return Material.getMaterial(componentString) ?: Material.AIR
}

fun Player.toItemFlags(key: String): MutableSet<ItemFlag> {
    val componentString = serializer.serialize(Component.translatable(key))

    if(componentString.isEmpty())
        return mutableSetOf()

    return componentString.split(",").map { s ->
        ItemFlag.valueOf(s)
    }.toHashSet()
}


fun Player.toArray(key: String): Array<String> = toArray(key, ImmutableList.of())

fun Player.toArray(key: String, placeholder: String): Array<String> = toArray(key, Component.text(placeholder))

fun Player.toArray(key: String, placeholder: Component): Array<String> = toArray(key, ImmutableList.of(placeholder))

fun Player.toArray(key: String, placeholder: ImmutableList<Component>): Array<String> {
    val componentString = serializer.serialize(Component.translatable(key))
    val array = componentString.split("\n").toTypedArray()
    var placeholderIndex = 0

    //Wenn der String leer ist, geben wir ein leeres Array zurück, da wir sonst eine leere Zeile bekommen
    if (array.isEmpty() || componentString.isEmpty())
        return arrayOf()

    return array.map { s ->
        if (s.equals("<PH>", true) && placeholderIndex < placeholder.size) {
            serializer.serialize(placeholder[placeholderIndex++])
        } else {
            serializer.serialize(ChallengePlugin.miniMessage.deserialize(s))
        }
    }.toTypedArray()
}

fun Player.translated(key: String) {
    val componentString = serializer.serialize(Component.translatable(key))
    val prefixString = serializer.serialize(Component.translatable("prefix"))

    ChallengePlugin.adventure.player(this).sendMessage(
        ChallengePlugin.miniMessage.deserialize(
            componentString,
            Placeholder.component("prefix", ChallengePlugin.miniMessage.deserialize(prefixString))
        )
    )
}

fun Player.getTranslated(key: String): Component {
    val componentString = serializer.serialize(Component.translatable(key))

    if (componentString.isEmpty()) {
        return Component.text(" ")
    }

    return ChallengePlugin.miniMessage.deserialize(componentString)
}
