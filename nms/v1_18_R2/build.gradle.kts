plugins {
  id("io.papermc.paperweight.userdev") version "1.3.7"
}

dependencies {
  paperDevBundle("1.18.2-R0.1-SNAPSHOT")

  implementation("com.github.steveice10:mcprotocollib:1.18.2-1") {
    exclude("net.kyori")
    exclude("io.netty")
    exclude("io.netty.incubator")
    exclude("org.junit")
    exclude("com.google.code.gson")
    exclude("com.github.spotbugs")
  }
}
