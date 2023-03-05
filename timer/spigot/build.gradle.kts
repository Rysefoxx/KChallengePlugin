group = "io.github.rysefoxx.challenge.timer.spigot"

repositories {
    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
}

dependencies {
    implementation(project(":sound"))
    implementation(project(":document"))
    implementation(project(":extension"))
    implementation(project(":i18n"))
    compileOnly("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
    compileOnly("net.kyori:adventure-text-minimessage:4.12.0")
    compileOnly("net.kyori:adventure-platform-bukkit:4.2.0")
}

tasks {
    shadowJar {
        archiveClassifier.set("")
        archiveFileName.set("ChallengeTimer-Spigot.jar")
    }
}