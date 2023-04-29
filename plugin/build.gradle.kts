dependencies {
  implementation(project(":api"))

  compileOnly(libs.spigot)
  compileOnly(libs.protocollib)
}

tasks {
  processResources {
    filesMatching("plugin.yml") {
      expand(project.properties)
    }
  }
}
