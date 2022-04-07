# Fake Player (WIP)

A Minecraft plugin that allows you to spawn fake players to your game.\
They increase player count of your server.\
This plugin would help to both server owners and plugin developers for benchmarking their servers/plugins.

[![idea](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

![master](https://github.com/spigotplugins/fakeplayer/workflows/build/badge.svg)
![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/io.github.portlek/FakePlayerApi?label=maven-central&server=https%3A%2F%2Foss.sonatype.org%2F)
![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/io.github.portlek/FakePlayerApi?label=maven-central&server=https%3A%2F%2Foss.sonatype.org)

## How to Use

### Server Owners

Download latest [FakePlayerPlugin](https://github.com/spigotplugins/fakeplayer/releases/) release.\
Put the plugin file into plugins folder.

### Developers

#### Build the plugin Jar file

`./gradlew build`

#### Publish FakePlayerApi to your local maven repository

`./gradlew publishToMavenLocal -Pdev=true`

#### Maven

```xml
<dependency>
  <groupId>io.github.portlek</groupId>
  <artifactId>FakePlayerApi</artifactId>
  <version>VERSION</version>
</dependency>
```

#### Gradle

```groovy
// Groovy
implementation "io.github.portlek:FakePlayerApi:VERSION"

// Kotlin DSL
implementation("io.github.portlek:FakePlayerApi:VERSION")
```
