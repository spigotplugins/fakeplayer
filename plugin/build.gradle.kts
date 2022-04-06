
dependencies {
  implementation(project(":api"))
  implementation(project(":nms:1_18_R1"))
  implementation(project(":nms:1_18_R2"))

  compileOnlyApi("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
}

tasks.withType<ProcessResources> {
  duplicatesStrategy = DuplicatesStrategy.INCLUDE
  from(project.the<SourceSetContainer>()["main"].resources.srcDirs) {
    expand("pluginVersion" to project.version)
    include("plugin.yml")
  }
}
