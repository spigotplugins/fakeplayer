package io.github.portlek.fakeplayer;

import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
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
    @NotNull final String[] args
  ) {
    if (!(sender instanceof Player) || args.length == 0) {
      System.out.println("x");
      return true;
    }
    if (args[0].equals("reload")) {
      Bukkit
        .getScheduler()
        .runTaskAsynchronously(
          this.plugin,
          () -> {
            this.plugin.reloadFiles();
            // TODO Make '&aReload complete.' customizable.
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
