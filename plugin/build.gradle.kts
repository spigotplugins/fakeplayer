import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import kr.entree.spigradle.module.spigot.SpigotExtension

apply {
  plugin("com.github.johnrengelman.shadow")
  plugin("kr.entree.spigradle")
}

dependencies {
  implementation(project(":api"))
  implementation(project(":nms:1_18_R1"))
  implementation(project(":nms:1_18_R2"))

  implementation("tr.com.infumia:reflection:0.1.6")
  implementation("tr.com.infumia:versionmatched:0.1.4")
  implementation("com.github.Revxrsal.Lamp:common:3.0.4")
  implementation("com.github.Revxrsal.Lamp:brigadier:3.0.4") {
    exclude("com.mojang")
  }
  implementation("com.github.Revxrsal.Lamp:bukkit:3.0.4") {
    exclude("com.mojang")
  }

  compileOnlyApi("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
}

tasks {
  withType<ProcessResources> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from(project.the<SourceSetContainer>()["main"].resources.srcDirs) {
      expand("pluginVersion" to project.version)
      include("plugin.yml")
    }
  }

  withType<ShadowJar> {
    define()
    rootProject.findProperty("pluginsFolder")?.let {
      val path = it.toString()
      if (path.isNotEmpty() && path.isNotBlank()) {
        destinationDirectory.set(File(path))
      }
    }
    configurations = listOf(project.configurations["runtimeClasspath"], project.configurations["shadow"])
  }

  build {
    dependsOn("shadowJar")
  }

  "prepareSpigotPlugins" {
    dependsOn("shadowJar")
  }

  "generateSpigotDescription" {
    onlyIf { false }
  }
}

configure<SpigotExtension> {
  debug {
    eula = true
  }
}
