import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

group = "io.github.rysefoxx.challenge.document"

repositories {
    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
}

tasks.withType(ShadowJar::class.java) {
    archiveFileName.set("Challenge-Document.jar")
}