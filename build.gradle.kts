plugins {
  java
  `java-library`
  `maven-publish`
  signing
  id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
  id("com.github.johnrengelman.shadow") version "7.1.2" apply false
}

allprojects {
  group = "io.github.portlek"
}

subprojects {
  apply {
    plugin("java")
    plugin("java-library")
    plugin("maven-publish")
    plugin("signing")
  }

  repositories {
    mavenCentral()
    maven("https://jitpack.io/")
    maven("https://repo.opencollab.dev/maven-snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    mavenLocal()
  }

  dependencies {
    // Annotations
    compileOnlyApi("org.projectlombok:lombok:1.18.22")
    compileOnlyApi("org.jetbrains:annotations:23.0.0")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.jetbrains:annotations:23.0.0")
    // Annotations
  }

  java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  tasks {
    compileJava {
      options.encoding = Charsets.UTF_8.name()
    }

    javadoc {
      options.encoding = Charsets.UTF_8.name()
      (options as StandardJavadocDocletOptions).tags("todo")
    }

    jar {
      define()
    }
  }
}

nexusPublishing {
  repositories {
    sonatype()
  }
}
