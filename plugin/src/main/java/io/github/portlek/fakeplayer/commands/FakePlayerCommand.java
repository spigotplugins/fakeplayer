package io.github.portlek.fakeplayer.commands;

import io.github.portlek.fakeplayer.FakePlayer;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Default;
import revxrsal.commands.annotation.Dependency;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.BukkitCommandActor;
import revxrsal.commands.help.CommandHelp;

/**
 * a command class that contains Fake Player plugin's commands.
 */
@Command({"fakeplayer", "fp", "fakep", "fplayer"})
public class FakePlayerCommand {

  /**
   * the plugin.
   */
  @Dependency
  private FakePlayer plugin;

  /**
   * the command for '/fakeplayer help' command.
   *
   * @param actor the actor to run.
   */
  @Subcommand("help")
  @Description("Shows help message for 'fakeplayer' command.")
  public void help(
    final BukkitCommandActor actor,
    final CommandHelp<String> help,
    @Default("1") final int page
  ) {
    help.paginate(page, 7).forEach(actor::reply);
  }
}
