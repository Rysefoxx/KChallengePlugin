import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

group = "io.github.rysefoxx.challenge.paper"

repositories {
    maven {
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
    compileOnly(project(":core"))
    compileOnly(project(":extension"))
    compileOnly(project(":document"))
    compileOnly("net.kyori:adventure-platform-bukkit:4.2.0")

    implementation(project(":sound"))
    implementation(project(":timer:bridge"))
}

tasks.withType(ShadowJar::class.java) {
    archiveFileName.set("Challenge-Paper.jar")
}