pluginManagement {
  repositories {
    maven("https://papermc.io/repo/repository/maven-public/")
    mavenLocal()
    gradlePluginPortal()
  }
}

rootProject.name = "FakePlayer"

include("api")

include("plugin")
