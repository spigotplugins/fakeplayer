import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

apply {
  plugin("com.github.johnrengelman.shadow")
}

dependencies {
  implementation(project(":api"))
  implementation(project(":nms:1_18_R1"))
  implementation(project(":nms:1_18_R2"))

  implementation("tr.com.infumia:reflection:0.1.4")
  implementation("tr.com.infumia:versionmatched:0.1.1")

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
  }

  build {
    dependsOn(getByName("shadowJar"))
  }
}
