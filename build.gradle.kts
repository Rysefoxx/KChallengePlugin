import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("xyz.jpenilla.run-paper") version "2.0.0"
}

group = "io.github.rysefoxx.challenge"

subprojects {
    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "kotlin")
}

allprojects {
    version = "0.1-alpha"

    tasks {
        withType(JavaCompile::class.java).configureEach {
            options.encoding = "UTF-8"
            options.release.set(17)
        }

        withType(KotlinCompile::class.java).configureEach {
            kotlinOptions.jvmTarget = "17"
        }
    }

    repositories {
        mavenCentral()
        mavenLocal()
        maven {
            url = uri("https://s01.oss.sonatype.org/content/groups/public/")
        }
    }

    dependencies {
        compileOnly("io.github.rysefoxx.inventory:RyseInventory-Plugin:1.5.7")
        compileOnly("org.jetbrains:annotations:23.1.0")
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":paper"))
    implementation(project(":spigot"))
}

tasks {
    shadowJar {
        archiveClassifier.set("")
        archiveFileName.set("Challenge.jar")
    }
    runServer {
        dependsOn(shadowJar)
        minecraftVersion("1.19.3")
    }
}
