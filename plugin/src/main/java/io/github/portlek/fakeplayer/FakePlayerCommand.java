package io.github.portlek.fakeplayer;

import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

public final class FakePlayerCommand implements TabExecutor {

  @NotNull
  private final FakePlayerPlugin plugin;

  FakePlayerCommand(@NotNull final FakePlayerPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(
    @NotNull final CommandSender sender,
    @NotNull final Command command,
    @NotNull final String label,
    @NotNull final String@NotNull[] args
  ) {
    if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
      // TODO: portlek, Send help message to sender.
      return true;
    }
    if (args[0].equalsIgnoreCase("reload")) {
      Bukkit
        .getScheduler()
        .runTaskAsynchronously(
          this.plugin,
          () -> {
            this.plugin.reloadFiles();
            // TODO: portlek, Make the message customizable.
            sender.sendMessage("&aReload complete.");
          }
        );
    }
    return true;
  }

  @NotNull
  @Override
  public List<String> onTabComplete(
    @NotNull final CommandSender sender,
    @NotNull final Command command,
    @NotNull final String label,
    @NotNull final String[] args
  ) {
    return Collections.emptyList();
  }
}
