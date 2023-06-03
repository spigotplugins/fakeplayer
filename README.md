# Fake Player (WIP)
A Minecraft plugin that allows you to spawn fake players to your game.\
They increase player count of your server.\
This plugin would help to both server owners and plugin developers for benchmarking their servers/plugins.

[![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/io.github.portlek/FakePlayerApi?label=maven-central&server=https%3A%2F%2Foss.sonatype.org%2F)](https://repo1.maven.org/maven2/io/github/portlek/FakePlayerApi/)
[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/io.github.portlek/FakePlayerApi?label=maven-central&server=https%3A%2F%2Foss.sonatype.org)](https://oss.sonatype.org/service/local/repositories/snapshots/content/io/github/portlek/FakePlayerApi/)

## Todo
- [ ] Nms implementations.
  - [ ] 1.8.8
  - [ ] 1.9.4
  - [ ] 1.10.2
  - [ ] 1.11.2
  - [ ] 1.12.2
  - [ ] 1.13.2
  - [ ] 1.14.4
  - [ ] 1.15.2
  - [ ] 1.16.5
  - [ ] 1.17.1
  - [ ] 1.18.2
  - [ ] 1.19.4
- [ ] Configuration
  - [*] random-names -> Use these names if you don't specify a name when adding fake player.
  - [ ] tab-format -> Specifies the tab format of each fake player if the custom one does not exist.
  - [*] language -> Specifies the plugin's language.
  - [*] join-quit-message -> Join/Quit message settings.
    - [*] enabled -> Enables it.
    - [*] join -> Specifies the join message. (`null` for default)
    - [*] quit -> Specifies the quit message. (`null` for default)
  - [*] Create a custom configuration file specifically for listing fake players and their settings like tab-format, names etc.
- [*] Add commands.
  - [*] /fakeplayer -> Shows help message.
    - [*] help -> Shows help message.
    - [*] reload -> Reloads config and script files.
    - [*] version -> Shows version also checks for update.
    - [ ] add \[name\] \[location\] -> Creates a fake player to the location.
    - [ ] remove \<name\> -> Removes the fake player.
    - [ ] toggle \<name\> -> Actives/Deactivates the fake player.
    - [ ] teleport/tp \<name\> \[location\] -> Teleports the fake player to location.
    - [ ] chat \<name\> \<message\> -> Sends a message by the fake player.
    - [ ] menu -> Opens the management menu for all fake players.
    - [ ] stress
      - [ ] start \[mode\] \[count\] -> Starts a stress test.
      - [ ] stop -> Stops the currently running stress test manually.
- [ ] A simple scripting to manage fake players.
- [ ] Stress test.
- [ ] Standalone application for more consistent stress testing.

## How to Use
### Server Owners
#### Installing
Download latest [FakePlayerPlugin](https://github.com/spigotplugins/fakeplayer/releases/) release.\
Put the plugin file into plugins folder.
#### Scripting
Planning
### Developers
#### Build the plugin Jar file
`./gradlew build`
- You can find the plugin jar file in plugin/build/libs/ folder.
#### Publish FakePlayerApi to your local maven repository
`./gradlew publishToMavenLocal`
#### plugin.yml
```yaml
depend:
- FakePlayer
softdepend:
- FakePlayer
```
#### Api
[![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/io.github.portlek/FakePlayerApi?label=maven-central&server=https%3A%2F%2Foss.sonatype.org%2F)](https://repo1.maven.org/maven2/io/github/portlek/FakePlayerApi/)
[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/io.github.portlek/FakePlayerApi?label=maven-central&server=https%3A%2F%2Foss.sonatype.org)](https://oss.sonatype.org/service/local/repositories/snapshots/content/io/github/portlek/FakePlayerApi/)
##### Maven
```xml
<dependency>
  <groupId>io.github.portlek</groupId>
  <artifactId>FakePlayerApi</artifactId>
  <version>VERSION</version>
  <scope>provided</scope>
</dependency>
```
##### Gradle
```groovy
// Groovy
compileOnly "io.github.portlek:FakePlayerApi:VERSION"

// Kotlin DSL
compileOnly("io.github.portlek:FakePlayerApi:VERSION")
```
