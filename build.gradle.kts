plugins {
    kotlin("jvm") version "1.9.20"
    id("gg.essential.loom") version "1.3.12"
    id("gg.essential.defaults") version "0.3.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    java
    idea
}

group = "sychic.playground"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.sk1er.club/repository/maven-public")
}

val shadowMe: Configuration by configurations.creating {
    configurations.implementation.get().extendsFrom(this)
}

dependencies {
    implementation(kotlin("stdlib"))
    shadowMe("gg.essential:loader-launchwrapper:1.2.1")
    implementation("gg.essential:essential-1.8.9-forge:14616+g169bd9af6a")
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