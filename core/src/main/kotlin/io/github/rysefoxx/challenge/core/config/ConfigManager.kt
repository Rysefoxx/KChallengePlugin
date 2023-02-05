package io.github.rysefoxx.challenge.core.config

import com.google.common.collect.ImmutableList
import io.github.rysefoxx.challenge.core.config.impl.PluginConfig

object ConfigManager {

    private lateinit var configList: ImmutableList<AbstractConfig>

    fun loadAll() {
        configList = ImmutableList.of(
            PluginConfig
        )
    }
}