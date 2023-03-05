import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

group = "io.github.rysefoxx.challenge.core"

repositories {
    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
}

dependencies {
    implementation(project(":document"))
    implementation(project(":extension"))
    implementation(project(":i18n"))

    implementation("org.reflections:reflections:0.10.2")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.21")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    compileOnly("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
    compileOnly("net.kyori:adventure-text-minimessage:4.12.0")
    compileOnly("net.kyori:adventure-platform-bukkit:4.2.0")
}


tasks.withType(ShadowJar::class.java) {
    archiveFileName.set("Challenge-Core.jar")
}