package com.infumia.fakeplayer.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import com.infumia.fakeplayer.FakePlayer;
import io.github.portlek.configs.util.ListToString;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("fakeplayer")
public final class FakePlayerCommand extends BaseCommand {

    @Default
    @CommandPermission("fakeplayer.command.main")
    public static void defaultCommand(final CommandSender sender) {
        sender.sendMessage(
            (String) FakePlayer.getAPI().languageFile.help_messages.buildMap(list ->
                new ListToString(list).value()
            )
        );
    }

    @Subcommand("help")
    @CommandPermission("fakeplayer.command.help")
    public static void helpCommand(final CommandSender sender) {
        sender.sendMessage(
            (String) FakePlayer.getAPI().languageFile.help_messages.buildMap(list ->
                new ListToString(list).value()
            )
        );
    }

    @Subcommand("reload")
    @CommandPermission("fakeplayer.command.reload")
    public static void reloadCommand(final CommandSender sender) {
        final long millis = System.currentTimeMillis();
        FakePlayer.getAPI().reloadPlugin(false);
        sender.sendMessage(
            FakePlayer.getAPI().languageFile.generals.reload_complete.build(
                "%ms%", () -> String.valueOf(System.currentTimeMillis() - millis)
            )
        );
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

}
