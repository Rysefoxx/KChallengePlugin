package io.github.rysefoxx.challenge.core.platform

import io.github.rysefoxx.challenge.core.ChallengePlugin
import io.github.rysefoxx.challenge.core.module.ModuleManager
import io.github.rysefoxx.challenge.core.module.Module
import org.reflections.Reflections
import java.lang.reflect.Modifier
import java.util.logging.Level

abstract class Platform(val plugin: ChallengePlugin, val modulePath: String) {

    abstract fun loadModules()

    protected fun registerModules(modulePath: String) {
        val reflections = Reflections(modulePath)
        val classes = reflections.getSubTypesOf(Module::class.java)
            .filter {!Modifier.isAbstract(it.modifiers) }

        plugin.logger.info("Found ${if (classes.isEmpty()) "no modules" else "${classes.size} module(s)"}")

        classes.forEach {

            val module: Module
            try {
                module = it.getConstructor().newInstance()
            } catch (e: Exception) {
                plugin.logger.log(Level.SEVERE, "Could not load module ${it.simpleName}", e)
                return@forEach
            }

            ModuleManager.register(module)
        }
    }
}