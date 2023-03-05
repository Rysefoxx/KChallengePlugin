package io.github.rysefoxx.challenge.core.extension

import com.google.common.collect.ImmutableSet
import io.github.rysefoxx.challenge.core.ChallengePlugin
import io.github.rysefoxx.challenge.core.jar.JarModule

object ExtensionManager {

    lateinit var extensions: ImmutableSet<JarModule>

    fun loadAll(plugin: ChallengePlugin) {
        extensions = ImmutableSet.of(
            TimerExtension(plugin)
        )
    }

}