package io.github.rysefoxx.challenge.core.config

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

abstract class AbstractConfig {

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
    }

    fun getString(key: String): String {
        return config.getString(key) ?: key
    }

    fun getBoolean(key: String): Boolean {
        return config.getBoolean(key)
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


}