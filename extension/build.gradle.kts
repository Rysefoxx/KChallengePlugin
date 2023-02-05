import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

group = "io.github.rysefoxx.challenge.extension"

repositories {
    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.21")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
}

tasks.withType(ShadowJar::class.java) {
    archiveFileName.set("Challenge-Extension.jar")
}