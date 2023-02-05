package io.github.rysefoxx.challenge.core.extension

import com.google.common.collect.ImmutableList
import io.github.rysefoxx.challenge.core.ChallengePlugin
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.resolver.Formatter
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag

private val serializer = LegacyComponentSerializer.legacySection()

fun Player.playSound(sound: Sound) = playSound(location, sound, 1f, 1f)
fun Player.playGuiSound() = playSound(Sound.UI_BUTTON_CLICK)

fun Player.toMaterial(key: String): Material {
    val componentString = serializer.serialize(Component.translatable(key))
    return Material.getMaterial(componentString) ?: Material.AIR
}

fun Player.toItemFlags(key: String): MutableSet<ItemFlag> {
    val componentString = serializer.serialize(Component.translatable(key))

    if (componentString.isEmpty())
        return mutableSetOf()

    return componentString.split(",").map { s ->
        ItemFlag.valueOf(s)
    }.toHashSet()
}


fun Player.toArray(key: String): Array<String> = toComponentArray(key)

fun Player.toArray(key: String, placeholder: String): Array<String> = toArray(key, Component.text(placeholder))

fun Player.toArray(key: String, placeholder: Component): Array<String> =
    toComponentArray(key, ImmutableList.of(placeholder))

fun Player.toArray(key: String, formatter: TagResolver): Array<String> =
    toComponentArray(key, formatter = listOf(formatter))

fun Player.toArray(
    key: String,
    placeholder: List<String?> = listOf(),
    formatter: TagResolver? = null
): Array<String> =
    toComponentArray(key, placeholder.filterNotNull().map { Component.text(it) }.toList(), listOf(formatter))

fun Player.toArray(
    key: String,
    placeholder: List<String?> = listOf(),
    formatter: List<TagResolver?> = listOf()
): Array<String> =
    toComponentArray(key, placeholder.filterNotNull().map { Component.text(it) }.toList(), formatter)

fun Player.toArraytest(
    key: String,
    placeholder: List<String?> = listOf(),
    formatter: List<TagResolver?> = listOf()
): Array<String> =
    toComponentArray(key, placeholder.filterNotNull().map { Component.text(it) }.toList(), formatter)

fun Player.toComponentArray(
    key: String,
    placeholder: List<Component>? = listOf(),
    formatter: List<TagResolver?> = listOf()
): Array<String> {
    var componentString = serializer.serialize(Component.translatable(key))

    placeholder?.withIndex()?.forEach { (_, value) ->
        componentString = componentString.replaceFirst("[PH]", serializer.serialize(value))
    }

    val array = componentString.split("\n").toTypedArray()

    //Wenn der String leer ist, geben wir ein leeres Array zurück, da wir sonst eine leere Zeile bekommen
    if (array.isEmpty() || componentString.isEmpty())
        return arrayOf()

    var choiceCounter = 0

    return array.map { s ->
        if (choiceCounter < formatter.size && Regex(".*<choice.*").matches(s)) {
            serializer.serialize(
                ChallengePlugin.miniMessage.deserialize(
                    s,
                    formatter[choiceCounter++] ?: Formatter.choice("", -1)
                )
            )
        } else {
            serializer.serialize(ChallengePlugin.miniMessage.deserialize(s))
        }
    }.toTypedArray()
}

fun Player.translated(key: String) = translated(key, ImmutableList.of())
fun Player.translated(key: String, placeholderKey: String) = translated(key, ImmutableList.of(placeholderKey))

fun Player.translated(key: String, placeholderKey: ImmutableList<String>) {
    var componentString = serializer.serialize(Component.translatable(key))
    val prefixString = serializer.serialize(Component.translatable("prefix"))

    placeholderKey.withIndex().forEach { (_, value) ->
        componentString = componentString.replaceFirst("[PH]", value)
    }

    ChallengePlugin.adventure.player(this).sendMessage(
        ChallengePlugin.miniMessage.deserialize(
            componentString,
            Placeholder.component("prefix", ChallengePlugin.miniMessage.deserialize(prefixString)),
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

fun Player.getTranslatedString(key: String): String {
    val componentString = serializer.serialize(Component.translatable(key))

    if (componentString.isEmpty()) {
        return " "
    }

    return componentString
}
