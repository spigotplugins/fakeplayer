package io.github.portlek.fakeplayer.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import io.github.portlek.fakeplayer.FakePlayer;
import io.github.portlek.fakeplayer.api.Fake;
import io.github.portlek.mapentry.MapEntry;
import java.util.Arrays;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("fakeplayer|fp")
public final class FakePlayerCommand extends BaseCommand {

  @Default
  @CommandPermission("fakeplayer.command.main")
  public static void defaultCommand(final CommandSender sender) {
    sender.sendMessage(FakePlayer.getAPI().languageFile.help_messages.get().build());
  }

  @Subcommand("help")
  @CommandPermission("fakeplayer.command.help")
  public static void helpCommand(final CommandSender sender) {
    sender.sendMessage(FakePlayer.getAPI().languageFile.help_messages.get().build());
  }

  @Subcommand("reload")
  @CommandPermission("fakeplayer.command.reload")
  public static void reloadCommand(final CommandSender sender) {
    final long millis = System.currentTimeMillis();
    FakePlayer.getAPI().reloadPlugin(false);
    sender.sendMessage(
      FakePlayer.getAPI().languageFile.generals.reload_complete.get()
        .build("%ms%", () -> String.valueOf(System.currentTimeMillis() - millis)));
  }

  @Subcommand("version")
  @CommandPermission("fakeplayer.command.version")
  public static void versionCommand(final CommandSender sender) {
    FakePlayer.getAPI().checkForUpdate(sender);
  }

  @Subcommand("menu")
  @CommandPermission("fakeplayer.command.menu")
  public static void listCommand(final Player player) {
    FakePlayer.getAPI().menuFile.fakePlayers.inventory().open(player);
  }

  @Syntax("<name>")
  @Subcommand("add")
  @CommandPermission("fakeplayer.command.add")
  public static void addCommand(final CommandSender sender, final String name) {
    if (FakePlayer.getAPI().fakesFile.fakeplayers.containsKey(name)) {
      sender.sendMessage(FakePlayer.getAPI().languageFile.errors.there_is_already.get().build(
        MapEntry.from("%player_name%", () -> name)));
      return;
    }
    sender.sendMessage(name + " add fake player success");
    if (sender instanceof Player) {
      FakePlayer.getAPI().fakesFile.addFakes(name.trim(), ((Player) sender).getLocation());
    } else {
      FakePlayer.getAPI().fakesFile.addFakes(name.trim(), new Location(Bukkit.getWorld("world"), 0, 0, 0));
    }
  }

  @Syntax("<name>")
  @Subcommand("remove")
  @CommandPermission("fakeplayer.command.remove")
  public static void removeCommand(final CommandSender sender, final String name) {
    if (!FakePlayer.getAPI().fakesFile.fakeplayers.containsKey(name)) {
      sender.sendMessage(FakePlayer.getAPI().languageFile.errors.not_found.get().build(
        MapEntry.from("%player_name%", () -> name)));
      return;
    }
    sender.sendMessage(FakePlayer.getAPI().languageFile.generals.fake_player_removed.get().build(
      MapEntry.from("%player_name%", () -> name)));
    FakePlayer.getAPI().fakesFile.remove(name.trim());
  }

  @Syntax("<name>")
  @Subcommand("toggle")
  @CommandPermission("fakeplayer.command.toggle")
  public static void toggleCommand(final CommandSender sender, final String name) {
    final Optional<Fake> optional = Optional.ofNullable(FakePlayer.getAPI().fakesFile.fakeplayers.get(name));
    if (optional.isPresent()) {
      optional.get().toggleVisible();
      sender.sendMessage(FakePlayer.getAPI().languageFile.generals.toggle_fake_player.get().build(
        MapEntry.from("%player_name%", () -> name)));
    } else {
      sender.sendMessage(FakePlayer.getAPI().languageFile.errors.not_found.get().build(
        MapEntry.from("%player_name%", () -> name)));
    }
  }

  @Syntax("<name>")
  @Subcommand("tp")
  @CommandPermission("fakeplayer.command.tp")
  public static void chatCommand(final Player sender, final String name) {
    if (!FakePlayer.getAPI().fakesFile.fakeplayers.containsKey(name)) {
      sender.sendMessage(FakePlayer.getAPI().languageFile.errors.not_found.get().build(
        MapEntry.from("%player_name%", () -> name)));
      return;
    }
    sender.teleport(
      FakePlayer.getAPI().fakesFile.fakeplayers.get(name).getSpawnPoint());
  }

  @Syntax("<name> <message>")
  @Subcommand("chat")
  @CommandPermission("fakeplayer.command.chat")
  public static void chatCommand(final CommandSender sender, final String name, final String msg) {
    if (FakePlayer.getAPI().fakesFile.fakeplayers.containsKey(name)) {
      Bukkit.getOnlinePlayers().forEach(player ->
        player.sendMessage(FakePlayer.getAPI().configFile.chat_format.build(Arrays.asList(
          MapEntry.from("%player_name%", () -> name),
          MapEntry.from("%message%", () -> msg)))));
    } else {
      sender.sendMessage(FakePlayer.getAPI().languageFile.errors.not_found.get().build(
        MapEntry.from("%player_name%", () -> name)));
    }
  }
}
