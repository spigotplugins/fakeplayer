dependencies {
  implementation(project(":api"))
  implementation(project(":nms:1_18_R1"))
  implementation(project(":nms:1_18_R2"))

  implementation("tr.com.infumia:reflection:0.1.4")
  implementation("tr.com.infumia:versionmatched:0.1.1")
  implementation("com.github.Revxrsal.Lamp:common:3.0.2")
  implementation("com.github.Revxrsal.Lamp:brigadier:3.0.2") {
    exclude("com.mojang")
  }
  implementation("com.github.Revxrsal.Lamp:bukkit:3.0.2") {
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

  shadowJar {
    define()
    rootProject.findProperty("pluginsFolder")?.let {
      val path = it.toString()
      if (path.isNotEmpty() && path.isNotBlank()) {
        destinationDirectory.set(File(path))
      }
    }
  }

  build {
    dependsOn(shadowJar)
  }

  debugPaper {
    dependsOn(shadowJar)
  }
}

spigot {
  debug {
    eula = true
  }
}
