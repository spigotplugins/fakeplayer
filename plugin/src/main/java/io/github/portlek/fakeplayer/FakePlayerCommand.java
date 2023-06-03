package io.github.portlek.fakeplayer;

import io.github.portlek.fakeplayer.utils.FakePlayerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
      FakePlayerPlugin.messageHandler.sendMessages(sender, "Command.Help");
      return true;
    }
    if (args.length == 1) {
      if (args[0].equalsIgnoreCase("reload")) {
        this.plugin.reload();
        FakePlayerPlugin.messageHandler.sendMessage(sender, "Command.Reload");
        return true;
      } else if (args[0].equalsIgnoreCase("version")) {
        FakePlayerPlugin.messageHandler.sendMessage(sender, "Version.Info");
        FakePlayerUtils.checkVersion(sender);
        return true;
      }
    }
    return false;
  }

  @NotNull
  @Override
  public List<String> onTabComplete(
    @NotNull final CommandSender sender,
    @NotNull final Command command,
    @NotNull final String label,
    @NotNull final String[] args
  ) {
    if (args.length == 0){
      return Arrays.asList("help", "reload", "version", "add",
              "remove", "toggle", "teleport", "tp", "chat", "menu",
              "stress");
    } else if (args.length == 1) {
      if (args[0].equals("stress")) {
        return Arrays.asList("start", "stop");
      }
    }
    return new ArrayList<>();
  }
}
