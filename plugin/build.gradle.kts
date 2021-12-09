java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
  implementation(project(":api"))
  compileOnlyApi("org.spigotmc:spigot-api:1.18-R0.1-SNAPSHOT")
}
