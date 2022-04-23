# Fake Player (WIP)

A Minecraft plugin that allows you to spawn fake players to your game.\
They increase player count of your server.\
This plugin would help to both server owners and plugin developers for benchmarking their servers/plugins.

[![idea](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

![master](https://github.com/spigotplugins/fakeplayer/workflows/build/badge.svg)
![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/io.github.portlek/FakePlayerApi?label=maven-central&server=https%3A%2F%2Foss.sonatype.org%2F)
![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/io.github.portlek/FakePlayerApi?label=maven-central&server=https%3A%2F%2Foss.sonatype.org)

[![Support Server](https://img.shields.io/discord/967385751870390333.svg?label=Discord&logo=Discord&colorB=7289da&style=for-the-badge)](https://discord.gg/jQhquEkb58)

## Todo

- Nms implementations for 1.8-1.18.2 versions.
- Configuration
  - random-names -> Use these names if user don't specify a name for fake player when creating it.
  - tab-format -> Specifies the tab format of each fake player if the custom one does not exist.
- Add commands.
  - /fakeplayer -> Shows help message.
  - help -> Shows help message.
  - reload -> Reloads config and script files.
  - version -> Shows version also checks for update.
  - add [name] [location] -> Creates a fake player to the location.
  - remove \<name\> -> Removes the fake player.
  - toggle \<name\> -> Actives/Deactives the fake player.
  - teleport/tp \<name\> [location] -> Teleports the fake player to location.
  - chat \<name\> \<message\> -> Sends a message by the fake player.
  - menu -> Opens the management menu for all fake players.
- A simple scripting to manage fake players.

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
