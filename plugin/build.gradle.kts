java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
  implementation(project(":api"))
  compileOnlyApi("org.spigotmc:spigot-api:1.18-R0.1-SNAPSHOT")

  implementation(project(":nms:1_8_R1"))
}

tasks.withType<ProcessResources> {
  duplicatesStrategy = DuplicatesStrategy.INCLUDE
  from(project.the<SourceSetContainer>()["main"].resources.srcDirs) {
    expand("pluginVersion" to project.version)
    include("plugin.yml")
  }
}
