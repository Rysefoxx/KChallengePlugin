@file:Suppress("unused", "")
package util

import net.kyori.adventure.key.Key
import net.kyori.adventure.translation.GlobalTranslator
import net.kyori.adventure.translation.TranslationRegistry
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.nio.file.Path
import java.util.*
import kotlin.io.path.div

/**
 * @author https://github.com/l4zs/translations
 */

class Translation(
    private val plugin: JavaPlugin,
    private val key: Key,
    private val resourcePath: Path,
    private val templateLocales: List<Locale>,
) {

    private val baseConfigName = "translations.yml"
    private val dir = File(plugin.dataFolder, "translations").resolve(key.value())
    private val config = TranslationConfig(dir.parentFile, baseConfigName, key, templateLocales, plugin)
    private var translationRegistry = blankTranslationRegistry()

    init {
        if (!dir.exists()) {
            dir.mkdirs()
        }
        reloadTranslations()
    }

    private fun reloadTranslations() {
        saveTemplateBundles()
        loadTranslations()
    }

    private fun blankTranslationRegistry(): TranslationRegistry {
        return TranslationRegistry.create(key).apply {
            defaultLocale(config.fallbackLocale)
        }
    }

    private fun loadTranslations() {
        if (config.locales.isEmpty()) {
            config.locales = templateLocales
        }
        if (GlobalTranslator.translator().sources().contains(translationRegistry)) {
            GlobalTranslator.translator().removeSource(translationRegistry)
        }
        translationRegistry = blankTranslationRegistry()
        registerLocalesToRegistry()
    }

    private fun registerLocalesToRegistry() {
        GlobalTranslator.translator().addSource(translationRegistry)
        plugin.logger.info("Checking for new translations for ${key.value()}")
        var wasUpdated = false
        config.locales.forEach {
            if (updateProperties(dir.path, it)) {
                wasUpdated = true
            }
            val bundle = resourceBundleFromClassLoader(dir.path, key.value(), it)
            translationRegistry.registerAll(it, bundle, true)
        }
        if (wasUpdated) {
            plugin.logger.info("Found new translations for ${key.value()}. Make sure to update your custom translations.")
        } else {
            plugin.logger.info("No new translations found for ${key.value()}.")
        }
    }

    private fun saveTemplateBundles(override: Boolean = false) {
        templateLocales.forEach {
            val fileName = "${key.value()}_${it.toLanguageTag().replace("-", "_")}.properties"
            val bundleFile = (dir.toPath() / fileName).toFile()
            if (!bundleFile.exists() || override) {
                plugin.saveResource(
                    "$resourcePath/$fileName",
                    "${dir.path.substringAfter(plugin.dataFolder.path)}/$fileName", override
                )
            }
        }
    }

    private fun updateProperties(path: String, locale: Locale): Boolean {
        val fileName = "${key.value()}_${locale.toLanguageTag().replace("-", "_")}.properties"
        val bundleInputStream = plugin.getResource("$resourcePath/$fileName") ?: return false
        val bundle = Properties()
        bundle.load(bundleInputStream)
        bundleInputStream.close()
        val file = (dir.toPath() / fileName).toFile()
        if (!file.exists()) {
            return false
        }
        val properties = loadProperties("$path/$fileName")
        var wasUpdated = false
        bundle.toSortedMap(compareBy { it.toString() }).forEach { (key, value) ->
            if (!properties.containsKey(key)) {
                properties[key] = value
                wasUpdated = true
            }
        }
        saveProperties("$path/$fileName", properties)
        return wasUpdated
    }
}
