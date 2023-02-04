package io.github.rysefoxx.challenge.core.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

/**
 * @author Rysefoxx | Rysefoxx#7880
 * @since 1/29/2023
 */
class ItemBuilder(material: Material) {
    private val itemStack: ItemStack

    private var amount = 1
    private var displayName: String? = null
    private var lore: List<String> = ArrayList()
    private var itemFlags: MutableSet<ItemFlag> = HashSet()

    init {
        itemStack = ItemStack(material)
    }

    fun amount(amount: Int): ItemBuilder {
        if (amount > 64 || amount > itemStack.maxStackSize) {
            this.amount = itemStack.maxStackSize
            return this
        }
        this.amount = amount
        return this
    }

    fun displayName(component: Component): ItemBuilder {
        return displayName(SERIALIZER.serialize(component))
    }

    fun displayName(displayName: String): ItemBuilder {
        this.displayName = displayName
        return this
    }

    fun lore(lore: List<Component?>): ItemBuilder {
        this.lore = lore.stream().map { component: Component? ->
            SERIALIZER.serialize(
                component!!
            )
        }.toList()
        return this
    }

    fun setLore(lore: List<String>): ItemBuilder {
        this.lore = lore
        return this
    }

    fun lore(lore: Array<String>?): ItemBuilder {
        this.lore = lore?.toList() ?: ArrayList()
        return this
    }

    fun itemFlags(toItemFlags: MutableSet<ItemFlag>): ItemBuilder {
        this.itemFlags = toItemFlags
        return this
    }

    fun build(): ItemStack {
        itemStack.amount = amount
        var itemMeta = itemStack.itemMeta

        if (itemMeta == null) itemMeta = Bukkit.getItemFactory().getItemMeta(itemStack.type)
        if (itemMeta == null) return itemStack

        itemMeta.setDisplayName(displayName)
        itemMeta.lore = lore

        itemMeta.addItemFlags(*itemFlags.toTypedArray())

        itemStack.itemMeta = itemMeta
        return itemStack
    }
    companion object {
        private val SERIALIZER = LegacyComponentSerializer.builder()
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build()
    }
}