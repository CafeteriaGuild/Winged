import net.fabricmc.loom.task.RemapJarTask
import net.fabricmc.loom.task.RemapSourcesJarTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.text.MessageFormat.format as messageFormat

plugins {
    id("fabric-loom")
    id("maven-publish")
    id("org.jetbrains.kotlin.jvm")
}
repositories {
    jcenter()
    maven { url = uri("http://maven.fabricmc.net/") }
    maven { url = uri("https://dl.bintray.com/ladysnake/libs") }
    maven { url = uri("https://dl.bintray.com/adriantodt/maven") }
    maven { url = uri("https://maven.abusedmaster.xyz/") }
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    minecraft("com.mojang:minecraft:${project.extra["minecraft_version"]}")
    mappings("net.fabricmc:yarn:${project.extra["yarn_mappings"]}:v2")
    modImplementation("net.fabricmc:fabric-loader:${project.extra["loader_version"]}")

    // DEPENDENCY: Fabric API
    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.extra["fabric_version"]}")

    // DEPENDENCY: Fabric Language Kotlin
    modImplementation("net.fabricmc:fabric-language-kotlin:${project.extra["fabric_kotlin_version"]}")

    // DEPENDENCY: Trinkets
    modImplementation("com.github.emilyploszaj:trinkets:${project.extra["trinkets_version"]}") {
        exclude("io.github.onyxstudios.Cardinal-Components-API", "cardinal-components-base")
        exclude("io.github.onyxstudios.Cardinal-Components-API", "cardinal-components-entity")
    }

    // DEPENDENCY: Cardinal Components Base
    modApi("com.github.onyxstudios.Cardinal-Components-API:cardinal-components-base:${project.extra["cardinal_components_version"]}")
    include("com.github.onyxstudios.Cardinal-Components-API:cardinal-components-base:${project.extra["cardinal_components_version"]}")

    // DEPENDENCY: Cardinal Components Entity
    modImplementation("com.github.onyxstudios.Cardinal-Components-API:cardinal-components-entity:${project.extra["cardinal_components_version"]}")
    include("com.github.onyxstudios.Cardinal-Components-API:cardinal-components-entity:${project.extra["cardinal_components_version"]}")

    // DEPENDENCY: Player Ability Lib
    modImplementation("io.github.ladysnake:PlayerAbilityLib:${project.extra["pal_version"]}")
    include("io.github.ladysnake:PlayerAbilityLib:${project.extra["pal_version"]}")

    // DEPENDENCY: Fall Flying Lib
    modImplementation("net.adriantodt.fabricmc:fallflyinglib:${project.extra["ffl_version"]}")
    include("net.adriantodt.fabricmc:fallflyinglib:${project.extra["ffl_version"]}")

    // DEPENDENCY: Cloth Config 2
    modApi("me.shedaniel.cloth:config-2:${project.extra["cloth_config_version"]}") {
        exclude("net.fabricmc.fabric-api", "fabric-api")
    }
    include("me.shedaniel.cloth:config-2:${project.extra["cloth_config_version"]}") {
        exclude("net.fabricmc.fabric-api", "fabric-api")
    }

    //DEPENDENCY: AutoConfig1u
    modApi("me.sargunvohra.mcmods:autoconfig1u:${project.extra["autoconfig1u_version"]}")
    include("me.sargunvohra.mcmods:autoconfig1u:${project.extra["autoconfig1u_version"]}")

    //COMPATIBILITY: ModMenu
    modImplementation("io.github.prospector:modmenu:${project.extra["modmenu_version"]}")

    // Test environiment with some mods
    modRuntime("me.shedaniel:RoughlyEnoughItems:${project.extra["rei_version"]}")

}

base.archivesBaseName = "${project.extra["archives_base_name"]}"
group = "${project.extra["maven_group"]}"
version = "${project.extra["mod_version"]}"

minecraft {}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }
tasks.withType<JavaCompile> { options.encoding = "UTF-8" }

tasks.processResources {
    inputs.property("version", project.version)

    from(sourceSets["main"].resources.srcDirs) {
        include("fabric.mod.json")
        expand("version" to project.version)
    }
    from(sourceSets["main"].resources.srcDirs) {
        exclude("fabric.mod.json")
    }
}

val remapJar = tasks.getByName<RemapJarTask>("remapJar")
val remapSourcesJar = tasks.getByName<RemapSourcesJarTask>("remapSourcesJar")

// Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
// if it is present.
// If you remove this task, sources will not be generated.
val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

tasks.jar { from("LICENSE") }

// configure the maven publication
publishing {
    publications {
        create("mavenJava", MavenPublication::class.java) {
            groupId = project.group.toString()
            artifactId = project.name.toLowerCase()
            version = project.version.toString()

            // add all the jars that should be included when publishing to maven
            artifact(remapJar) { builtBy(remapJar) }
            artifact(sourcesJar) { builtBy(remapSourcesJar) }
        }
    }
    // select the repositories you want to publish to
    repositories { mavenLocal() }
}

tasks.create("printInfo") {
    doLast {
        println(
            messageFormat(
                """
            ----- BEGIN -----
            ### Release Info
            This release was built for Minecraft **{0}**, with Fabric Loader **{1}**, Fabric API **{2}** and Fabric Language Kotlin **{3}**.
    
            Mod Menu Integration was tested using version **{4}**.
            ------ END ------""".trimIndent(),
                project.extra["minecraft_version"],
                project.extra["loader_version"],
                project.extra["fabric_version"],
                project.extra["fabric_kotlin_version"],
                project.extra["modmenu_version"]
            )
        )
    }
}
