dependencies {
  compileOnlyApi("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
}

val projectName = getProjectName()

tasks {
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
    dependsOn(javadocJar)
    dependsOn(sourcesJar)
  }

  withType<Sign> {
    required((rootProject.property("dev") as String) == "false")
  }
}

publishing {
  publications {
    create<MavenPublication>("mavenJava") {
      groupId = project.group.toString()
      artifactId = projectName
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
  }
}

signing {
  useGpgCmd()
  sign(publishing.publications["mavenJava"])
}
