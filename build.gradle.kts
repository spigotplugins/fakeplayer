plugins {
  java
  `maven-publish`
  signing
  id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
  id("io.papermc.paperweight.userdev") version "1.5.5" apply false
}

repositories { mavenCentral() }

allprojects {
  group = "io.github.portlek"
}

subprojects {
  apply<JavaPlugin>()

  val projectName = property("projectName").toString()
  description = "A Minecraft plugin that allows you to create fake players to increase your server player amount."

  java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  tasks {
    compileJava { options.encoding = Charsets.UTF_8.name() }

    jar {
      archiveBaseName.set(projectName)
    }

    build { dependsOn(jar) }
  }

  repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.dmulloy2.net/repository/public/")
    mavenLocal()
  }

  dependencies {
    compileOnly(rootProject.libs.annotations)
  }
}

nexusPublishing {
  repositories {
    sonatype()
  }
}
