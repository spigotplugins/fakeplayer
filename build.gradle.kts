plugins {
  java
  `java-library`
}

allprojects {
  apply {
    plugin("java")
    plugin("java-library")
  }

  group = "io.github.portlek"

  val projectName = findProperty("projectname") as String? ?: "FakePlayer"

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
      archiveClassifier.set(null as String?)
      archiveClassifier.convention(null as String?)
      archiveBaseName.set(projectName)
      archiveBaseName.convention(projectName)
      archiveVersion.set(null as String?)
      archiveVersion.convention(null as String?)
    }
  }

  val javadocJar by tasks.creating(Jar::class) {
    dependsOn("javadoc")
    archiveClassifier.set("javadoc")
    archiveClassifier.convention("javadoc")
    archiveBaseName.set(projectName)
    archiveBaseName.convention(projectName)
    archiveVersion.set(null as String?)
    archiveVersion.convention(null as String?)
    from(tasks.javadoc)
  }

  val sourcesJar by tasks.creating(Jar::class) {
    dependsOn("classes")
    archiveClassifier.set("sources")
    archiveClassifier.convention("sources")
    archiveBaseName.set(projectName)
    archiveBaseName.convention(projectName)
    archiveVersion.set(null as String?)
    archiveVersion.convention(null as String?)
    from(sourceSets["main"].allSource)
  }
}

subprojects {
  repositories {
    mavenCentral()
    maven("https://jitpack.io/")
    maven("https://repo.opencollab.dev/maven-snapshots/")
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

    compileOnlyApi("it.unimi.dsi:fastutil:8.5.8")
    compileOnlyApi("tr.com.infumia:InfumiaShared:3.1.9")
    compileOnlyApi("tr.com.infumia:InfumiaPaperApi:3.1.9")
  }
}
