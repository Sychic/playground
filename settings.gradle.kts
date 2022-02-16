pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
        maven {
            name = "sonatype"
            url = uri("https://oss.sonatype.org/content/repositories/snapshots")
        }
        maven { url = uri("https://maven.minecraftforge.net/") }
        maven { url = uri("https://jitpack.io") }
    }
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "net.minecraftforge.gradle.forge" ->
                    useModule("com.github.Skytils:ForgeGradle:${requested.version}")
            }
        }
    }
}
rootProject.name = "playground"