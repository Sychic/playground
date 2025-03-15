plugins {
    kotlin("jvm") version "1.9.20"
    id("gg.essential.loom") version "1.6.21"
    id("gg.essential.defaults") version "0.6.3"
    id("gg.essential.defaults.repo") version "0.6.3"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    java
    idea
}

group = "sychic.playground"
version = "1.0-SNAPSHOT"

val shadowMe: Configuration by configurations.creating {
    configurations.implementation.get().extendsFrom(this)
}

dependencies {
    implementation(kotlin("stdlib"))
    shadowMe("gg.essential:loader-fabric:1.2.1")
    modImplementation("gg.essential:essential-1.20.4-fabric:17141+gd6f4cfd3a8")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.97.2+1.20.4")
}

sourceSets {
    main {
        output.setResourcesDir(file("${buildDir}/classes/kotlin/main"))
    }
}

tasks {
    processResources {
        inputs.property("version", project.version)
    }
}