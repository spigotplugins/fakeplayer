plugins {
  java
  `java-library`
  `maven-publish`
  signing
  id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

allprojects {
  apply {
    plugin("java")
    plugin("java-library")
    plugin("maven-publish")
    plugin("signing")
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

  if (projectName == "FakePlayerApi") {
    publishing {
      publications {
        create<MavenPublication>("mavenJava") {
          groupId = project.group.toString()
          artifactId = projectName
          version = project.version.toString()

          from(components["java"])
          artifact(sourcesJar)
          artifact(javadocJar)
          pom {
            name.set("FakePlayerApi")
            description.set("A Minecraft plugin that allows you to create fake players to increase your server player amount.")
            url.set("https://infumia.com.tr/")
            licenses {
              license {
                name.set("MIT License")
                url.set("https://mit-license.org/license.txt")
              }
            }
            developers {
              developer {
                id.set("portlek")
                name.set("Hasan Demirtaş")
                email.set("utsukushihito@outlook.com")
              }
            }
            scm {
              connection.set("scm:git:git://github.com/spigotplugins/fakeplayer.git")
              developerConnection.set("scm:git:ssh://github.com/spigotplugins/fakeplayer.git")
              url.set("https://github.com/spigotplugins/fakeplayer")
            }
          }
        }
      }
    }

    tasks.withType<Sign>().configureEach {
      onlyIf { (rootProject.property("dev") as String) == "false" }
    }

    signing {
      useGpgCmd()
      sign(publishing.publications["mavenJava"])
    }
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

    compileOnlyApi("tr.com.infumia:InfumiaShared:3.1.9")
    compileOnlyApi("tr.com.infumia:InfumiaPaperApi:3.1.9")
  }
}

nexusPublishing {
  repositories {
    sonatype()
  }
}
