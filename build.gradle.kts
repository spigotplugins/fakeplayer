plugins {
  java
  `java-library`
  id("com.github.johnrengelman.shadow") version "7.1.1"
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
    compileOnlyApi("org.projectlombok:lombok:1.18.22")
    compileOnlyApi("org.jetbrains:annotations:23.0.0")
    compileOnlyApi("it.unimi.dsi:fastutil:8.5.6")
    compileOnlyApi("tr.com.infumia:InfumiaShared:3.0.10")
    compileOnlyApi("tr.com.infumia:InfumiaCommon:3.0.10")
    compileOnlyApi("tr.com.infumia:InfumiaPaper:3.1.0")

    annotationProcessor("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.jetbrains:annotations:23.0.0")
  }
}
