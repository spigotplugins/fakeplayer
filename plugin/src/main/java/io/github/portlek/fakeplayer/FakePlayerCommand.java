package io.github.portlek.fakeplayer;

import io.github.portlek.fakeplayer.api.AiPlayer;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class FakePlayerCommand implements TabExecutor {

  @Override
  public boolean onCommand(
    @NotNull final CommandSender sender,
    @NotNull final Command command,
    @NotNull final String label,
    @NotNull final String[] args
  ) {
    if (!(sender instanceof Player player) || args.length == 0) {
      System.out.println("x");
      return true;
    }
    switch (args[0]) {
      case "create" -> {
        final var count = Integer.parseInt(args[1]);
        for (var index = 0; index < count; index++) {
          AiPlayer.create(UUID.randomUUID().toString().substring(0, 7), player.getLocation()).connect();
        }
      }
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
