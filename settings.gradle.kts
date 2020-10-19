pluginManagement {
    repositories {
        jcenter()
        maven { url = uri("https://maven.fabricmc.net/") }
        gradlePluginPortal()
    }
    plugins {
        id("fabric-loom") version "${extra["loom_version"]}"
        id("org.jetbrains.kotlin.jvm") version "${extra["kotlin_version"]}"
    }
}
rootProject.name = "zephyr"