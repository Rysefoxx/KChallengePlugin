package io.github.rysefoxx.challenge.core.module

import io.github.rysefoxx.challenge.core.module.impl.ChallengeModule

object ModuleManager {

    val challengeModules: MutableSet<Module> = mutableSetOf()

    fun register(module: Module) {
        challengeModules.add(module)

        if(module is ChallengeModule)
            module.registerEvent()
    }
}