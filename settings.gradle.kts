rootProject.name = "OrganizaME"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        // Kotlin Multiplatform
        id("org.jetbrains.kotlin.multiplatform") version "2.0.0"
        // Plugin del compilador de Compose para Kotlin 2.0+
        id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
        // Compose Multiplatform
        id("org.jetbrains.compose") version "1.9.21"
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(":composeApp")