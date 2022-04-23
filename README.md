# Fake Player (WIP)
A Minecraft plugin that allows you to spawn fake players to your game.\
They increase player count of your server.\
This plugin would help to both server owners and plugin developers for benchmarking their servers/plugins.

[![idea](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

![master](https://github.com/spigotplugins/fakeplayer/workflows/build/badge.svg)
![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/io.github.portlek/FakePlayerApi?label=maven-central&server=https%3A%2F%2Foss.sonatype.org%2F)
![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/io.github.portlek/FakePlayerApi?label=maven-central&server=https%3A%2F%2Foss.sonatype.org)

[![Discord](https://img.shields.io/discord/967385751870390333.svg?label=Discord&logo=Discord&colorB=7289da&style=for-the-badge)](https://discord.gg/jQhquEkb58)

## Todo
- Nms implementations for 1.8-1.18.2 versions.
- Configuration
  - random-names -> Use these names if you don't specify a name when adding fake player.
  - tab-format -> Specifies the tab format of each fake player if the custom one does not exist.
  - language -> Specifies the plugin's language.
  - join-quit-message -> Join/Quit message settings.
    - enabled -> Enables it.
    - join -> Specifies the join message. (`null` for default)
    - quit -> Specifies the quit message. (`null` for default)
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
#### Installing
Download latest [FakePlayerPlugin](https://github.com/spigotplugins/fakeplayer/releases/) release.\
Put the plugin file into plugins folder.
#### Scripting (Planning)
##### authme.ts
If a fakeplayer joins to the server, gets its password then run /login \<password\> to pass AuthMe.
```typescript
import { Core } from 'FakePlayerBase';

// Runs when the plugin enables.
Core.onEnable(() => {
})

// Runs when the plugin disables.
Core.onDisable(() => {
  const all = Core.allFakePlayers()
  for (var fp of all) {
    fp.removeProperty('password')
  }
})

// Runs when a fake player joins to the server.
Core.onFakePlayerJoin((event) => {
  const player = event.player()
  Core.runAfter(Core.seconds(3), () => {
    let password = player.getProperty('password')
    if (password == null) {
      password = Core.randomPassword()
      player.setProperty('password', password)
    }
    player.sendCommand('/register ${password} ${password}')
    Core.runAfter(Core.seconds(1), 'seconds', () => {
      player.sendCommand('/login ${password}')
    })
  })
})
```
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
  <scope>provided</scope>
</dependency>
```
#### Gradle
```groovy
// Groovy
compileOnly "io.github.portlek:FakePlayerApi:VERSION"

// Kotlin DSL
compileOnly("io.github.portlek:FakePlayerApi:VERSION")
```
