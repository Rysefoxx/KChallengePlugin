import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

group = "io.github.rysefoxx.challenge.paper"

repositories {
    maven {
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }
}

dependencies {
    compileOnly(project(":core"))
    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
}

tasks.withType(ShadowJar::class.java) {
    archiveFileName.set("Challenge-Paper.jar")
}