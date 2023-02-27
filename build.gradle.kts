plugins {
  java
  `maven-publish`
  signing
  id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
  id("com.github.johnrengelman.shadow") version "8.1.0" apply false
}

val signRequired = !rootProject.property("dev").toString().toBoolean()

allprojects {
  group = "io.github.portlek"
}

subprojects {
  apply<JavaPlugin>()

  if (isJar()) {
    java {
      toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
      }
    }

    tasks {
      compileJava {
        options.encoding = Charsets.UTF_8.name()
      }

      jar {
        define()
      }

      build {
        dependsOn(jar)
      }
    }

    repositories {
      mavenCentral()
      maven(JITPACK)
      maven(MINECRAFT)
      maven(SNAPSHOTS)
      maven(SPONGEPOWERED)
      maven(PAPERMC)
      maven(OPENCOLLAB)
      maven(CODEMC)
      maven(CODEMC_NMS)
      mavenLocal()
    }

    dependencies {
      compileOnly("org.projectlombok:lombok:1.18.26")
      compileOnly("org.jetbrains:annotations:24.0.0")

      annotationProcessor("org.projectlombok:lombok:1.18.26")
      annotationProcessor("org.jetbrains:annotations:24.0.0")

      testAnnotationProcessor("org.projectlombok:lombok:1.18.26")
      testAnnotationProcessor("org.jetbrains:annotations:24.0.0")
    }
  }

  if (isPlugin()) {
    apply {
      plugin("com.github.johnrengelman.shadow")
    }

    tasks {
      processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        from(project.the<SourceSetContainer>()["main"].resources.srcDirs) {
          expand("pluginVersion" to project.version)
          include("plugin.yml")
        }
      }
    }
  }

  if (isPublishing()) {
    apply {
      plugin("maven-publish")
      plugin("signing")
    }

    tasks {
      javadoc {
        options.encoding = Charsets.UTF_8.name()
        (options as StandardJavadocDocletOptions).tags("todo")
      }

      val javadocJar by creating(Jar::class) {
        dependsOn("javadoc")
        define(classifier = "javadoc")
        from(javadoc)
      }

      val sourcesJar by creating(Jar::class) {
        dependsOn("classes")
        define(classifier = "sources")
        from(sourceSets["main"].allSource)
      }

      build {
        dependsOn(sourcesJar)
        dependsOn(javadocJar)
      }
    }

    publishing {
      publications {
        val publication = create<MavenPublication>("mavenJava") {
          groupId = project.group.toString()
          artifactId = getQualifiedProjectName()
          version = project.version.toString()

          from(components["java"])
          artifact(tasks["sourcesJar"])
          artifact(tasks["javadocJar"])
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

        signing {
          isRequired = signRequired
          if (isRequired) {
            useGpgCmd()
            sign(publication)
          }
        }
      }
    }
  }
}

nexusPublishing {
  repositories {
    sonatype()
  }
}
