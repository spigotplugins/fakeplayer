subprojects {
  dependencies {
    implementation(project(":api"))

    compileOnlyApi("it.unimi.dsi:fastutil:8.5.8")
  }
}
