package io.github.rysefoxx.challenge.document

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

abstract class AbstractDocument {

    private lateinit var file: File
    private lateinit var config: YamlConfiguration

    abstract val path: String
    abstract val name: String

    fun init() {
        file = File(path, "$name.yml")

        if (!file.parentFile.exists())
            file.parentFile.mkdirs()

        if (!file.exists())
            file.createNewFile()

        config = YamlConfiguration.loadConfiguration(file)
        config.options().copyDefaults(true)
        saveDefaults()
    }

    abstract fun saveDefaults()

    fun getString(key: String): String? {
        val value = config.getString(key)

        if(value == null || value.equals("null", true))
            return null

        return value
    }

    fun getString(key: String, def: String): String {
        val value = config.getString(key)

        if(value == null || value.equals("null", true))
            return def

        return value
    }

    fun getBoolean(key: String): Boolean {
        return config.getBoolean(key)
    }

    fun getFloat(key: String, def: Double): Float {
        return config.getDouble(key, def).toFloat()
    }

    fun getLong(key: String): Long {
        return config.getLong(key)
    }

    fun set(key: String, value: Any) {
        config.set(key, value)
        config.save(file)
    }

    fun set(key: List<String>, value: List<Any>) {
        if (key.size != value.size)
            return

        for (i in key.indices)
            config.set(key[i], value[i])

        config.save(file)
    }

    fun addDefault(key: String, value: Any) {
        config.addDefault(key, value)
        config.save(file)
    }
}