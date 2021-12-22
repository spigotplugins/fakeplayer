plugins {
  id("io.papermc.paperweight.userdev") version "1.3.1"
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
  paperDevBundle("1.18-R0.1-SNAPSHOT")
  implementation("com.github.GeyserMC:MCProtocolLib:1.18-2")
}
