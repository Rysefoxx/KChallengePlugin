import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("xyz.jpenilla.run-paper") version "2.0.0"
}

group = "io.github.rysefoxx.challenge"
archivesName.set("Challenge")

subprojects {
    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "kotlin")
}

allprojects {
    version = "0.1-alpha"

    tasks {
        withType(JavaCompile::class.java).configureEach {
            options.encoding = "UTF-8"
            options.release.set(18)
        }

        withType(KotlinCompile::class.java).configureEach {
            kotlinOptions.jvmTarget = "18"
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
        implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.21")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
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
    }
    runServer {
        dependsOn(shadowJar)
        minecraftVersion("1.19.3")
    }
}
