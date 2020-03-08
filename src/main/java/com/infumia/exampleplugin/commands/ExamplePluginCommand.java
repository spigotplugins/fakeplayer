package com.infumia.exampleplugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.infumia.exampleplugin.ExamplePluginAPI;
import io.github.portlek.configs.util.ListToString;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandAlias("exampleplugin|ep")
// TODO Change the class name as you want.
public final class ExamplePluginCommand extends BaseCommand {

    @NotNull
    private final ExamplePluginAPI api;

    public ExamplePluginCommand(@NotNull final ExamplePluginAPI api) {
        this.api = api;
    }

    @Default
    // TODO Change the permission as you want.
    @CommandPermission("exampleplugin.command.main")
    public void defaultCommand(final CommandSender sender) {
        sender.sendMessage(
            (String) this.api.languageFile.help_messages.buildMap(list ->
                new ListToString(list).value()
            )
        );
    }

    // TODO Change the sub-command name as you want.
    @Subcommand("help")
    // TODO Change the permission as you want.
    @CommandPermission("exampleplugin.command.help")
    public void helpCommand(final CommandSender sender) {
        sender.sendMessage(
            (String) this.api.languageFile.help_messages.buildMap(list ->
                new ListToString(list).value()
            )
        );
    }

    // TODO Change the sub-command name as you want.
    @Subcommand("reload")
    // TODO Change the permission as you want.
    @CommandPermission("exampleplugin.command.reload")
    public void reloadCommand(final CommandSender sender) {
        final long ms = System.currentTimeMillis();

        this.api.reloadPlugin(false);
        sender.sendMessage(
            this.api.languageFile.generals.reload_complete.build(
                "%ms%", () -> String.valueOf(System.currentTimeMillis() - ms)
            )
        );
    }

    // TODO Change the sub-command name as you want.
    @Subcommand("version")
    // TODO Change the permission as you want.
    @CommandPermission("exampleplugin.command.version")
    public void versionCommand(final CommandSender sender) {
        this.api.checkForUpdate(sender);
    }

    @Subcommand("message")
    @CommandPermission("exampleplugin.command.message")
    @CommandCompletion("@players <message>")
    public void messageCommand(final CommandSender sender, @Conditions("player:arg=0") final String[] args) {
        if (args.length < 1) {
            return;
        }

        final Player player = Bukkit.getPlayer(args[0]);

        // player cannot be null cause @Conditions("player:arg=0") this condition checks
        // if args[0] is in the server.
        if (player == null) {
            return;
        }

        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            stringBuilder.append(
                ChatColor.translateAlternateColorCodes('&', args[i])
            );
        }

        player.sendMessage(stringBuilder.toString());
    }

}
