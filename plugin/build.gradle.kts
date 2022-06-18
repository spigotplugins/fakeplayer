dependencies {
  implementation(project(":api"))
  implementation(project(":nms:v1_18_R2"))

  implementation(reflectionLibrary)
  implementation(versionmatchedLibrary)
  implementation(configurateJacksonLibrary)

  compileOnly(paperApiLibrary)
}

tasks {
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
    dependsOn("shadowJar")
  }
}
