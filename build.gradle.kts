plugins {
  java
  `java-library`
  // `maven-publish`
  // signing
  id("com.github.johnrengelman.shadow") version "7.1.0"
  // id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
  // id("io.papermc.paperweight.userdev") version "1.3.1" apply false
}

allprojects {
  apply {
    plugin("java")
    plugin("java-library")
    plugin("com.github.johnrengelman.shadow")
  }

  group = "io.github.portlek"

  val projectName = findProperty("projectname") as String? ?: "FakePlayer"

  val sourcesJar by tasks.creating(Jar::class) {
    dependsOn("classes")
    archiveClassifier.convention("sources")
    archiveClassifier.set("sources")
    archiveBaseName.set(projectName)
    archiveVersion.set(null as String?)
    archiveVersion.convention(null as String?)
    from(sourceSets["main"].allSource)
  }

  val javadocJar by tasks.creating(Jar::class) {
    dependsOn("javadoc")
    archiveClassifier.convention("")
    archiveClassifier.set("javadoc")
    archiveBaseName.set(projectName)
    archiveVersion.set(null as String?)
    archiveVersion.convention(null as String?)
    from(tasks.javadoc)
  }

  tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    dependsOn(sourcesJar)
    dependsOn(javadocJar)
    archiveClassifier.set(null as String?)
    archiveVersion.set(null as String?)
    archiveVersion.convention(null as String?)
    archiveBaseName.set(projectName)
  }

  tasks.withType<JavaCompile> {
    options.encoding = Charsets.UTF_8.name()
  }

  tasks.withType<Javadoc> {
    options.encoding = Charsets.UTF_8.name()
    (options as StandardJavadocDocletOptions).tags("todo")
  }

  repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    mavenLocal()
  }
}

subprojects {
  dependencies {
    compileOnlyApi("org.projectlombok:lombok:1.18.22")
    compileOnlyApi("org.jetbrains:annotations:23.0.0")

    annotationProcessor("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.jetbrains:annotations:23.0.0")
  }
}
