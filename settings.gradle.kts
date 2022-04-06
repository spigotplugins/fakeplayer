pluginManagement {
  repositories {
    maven("https://papermc.io/repo/repository/maven-public/")
    mavenLocal()
    gradlePluginPortal()
  }
}

rootProject.name = "FakePlayer"

include("api")
include("nms")
include("plugin")
include("nms:1_18_R1")
