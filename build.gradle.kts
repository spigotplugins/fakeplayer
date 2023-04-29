import com.diffplug.gradle.spotless.YamlExtension.JacksonYamlGradleConfig
import com.diffplug.spotless.LineEnding

plugins {
  java
  `maven-publish`
  signing
  id("com.diffplug.spotless") version "6.18.0"
  id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
  id("io.papermc.paperweight.userdev") version "1.5.5" apply false
}

val spotlessApply = property("spotless.apply").toString().toBoolean()

repositories { mavenCentral() }

allprojects {
  group = "io.github.portlek"
}

subprojects {
  apply<JavaPlugin>()

  val projectName = property("project.name").toString()
  description = "A Minecraft plugin that allows you to create fake players to increase your server player amount."

  java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  tasks {
    compileJava { options.encoding = Charsets.UTF_8.name() }

    jar {
      archiveBaseName.set(projectName)
      archiveVersion.set("")
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

if (spotlessApply) {
  spotless {
    lineEndings = LineEnding.UNIX
    isEnforceCheck = false

    val prettierConfig =
      mapOf(
        "prettier" to "latest",
        "prettier-plugin-java" to "latest",
      )

    format("encoding") {
      target("*.*")
      encoding("UTF-8")
      endWithNewline()
      trimTrailingWhitespace()
    }

    yaml {
      target(
        "**/src/main/resources/plugin.yml",
        ".github/**/*.yml",
      )
      endWithNewline()
      trimTrailingWhitespace()
      val jackson = jackson() as JacksonYamlGradleConfig
      jackson.yamlFeature("LITERAL_BLOCK_STYLE", true)
      jackson.yamlFeature("MINIMIZE_QUOTES", true)
      jackson.yamlFeature("SPLIT_LINES", false)
    }

    kotlinGradle {
      target("**/*.gradle.kts")
      indentWithSpaces(2)
      endWithNewline()
      trimTrailingWhitespace()
      ktlint()
    }

    java {
      target("**/src/main/java/io/github/portlek/fakeplayer/**/*.java")
      importOrder()
      removeUnusedImports()
      indentWithSpaces(2)
      endWithNewline()
      trimTrailingWhitespace()
      prettier(prettierConfig)
        .config(
          mapOf("parser" to "java", "tabWidth" to 2, "useTabs" to false, "printWidth" to 100),
        )
    }
  }
}
