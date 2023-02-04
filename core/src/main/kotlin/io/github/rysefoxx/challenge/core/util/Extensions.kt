package io.github.rysefoxx.challenge.core.util

import net.kyori.adventure.util.UTF8ResourceBundleControl
import org.bukkit.plugin.java.JavaPlugin
import java.io.*
import java.net.URLClassLoader
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.*

internal fun JavaPlugin.saveResource(resourcePath: String, savePath: String, replace: Boolean = false) {
    getResource(resourcePath)
        ?.readBytes()?.let {
            val file = File(dataFolder, savePath)
            if (!file.exists() || replace) {
                if (!file.parentFile.exists()) {
                    file.parentFile.mkdirs()
                }
                file.writeBytes(it)
            }
        }
}

internal fun loadProperties(path: String): Properties {
    val inputStream = File(path).inputStream()
    val properties = Properties()
    properties.load(InputStreamReader(inputStream, Charset.forName("UTF-8")))
    inputStream.close()
    return properties
}

internal fun saveProperties(path: String, properties: Properties) {
    val outputStream = File(path).outputStream()
    properties.store(OutputStreamWriter(outputStream, Charsets.UTF_8), null)
    outputStream.flush()
    outputStream.close()
}

internal fun resourceBundleFromClassLoader(path: String, bundleName: String, locale: Locale): ResourceBundle {
    val file = File(path)
    val urls = arrayOf(file.toURI().toURL())
    val loader: ClassLoader = URLClassLoader(urls)
    return ResourceBundle.getBundle(bundleName, locale, loader)
}