package io.github.rysefoxx.challenge.core.module

object ModuleManager {

    val challengeModules: MutableSet<Module> = mutableSetOf()

    fun register(challengeModule: Module) {
        challengeModules.add(challengeModule)
    }
}