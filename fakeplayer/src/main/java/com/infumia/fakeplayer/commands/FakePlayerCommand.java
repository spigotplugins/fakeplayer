package com.infumia.fakeplayer.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import com.infumia.fakeplayer.FakePlayerAPI;
import io.github.portlek.configs.util.ListToString;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandAlias("fakeplayer")
public final class FakePlayerCommand extends BaseCommand {

    @NotNull
    private final FakePlayerAPI api;

    public FakePlayerCommand(@NotNull final FakePlayerAPI api) {
        this.api = api;
    }

    @Default
    @CommandPermission("fakeplayer.command.main")
    public void defaultCommand(final CommandSender sender) {
        sender.sendMessage(
            (String) this.api.languageFile.help_messages.buildMap(list ->
                new ListToString(list).value()
            )
        );
    }

    @Subcommand("help")
    @CommandPermission("fakeplayer.command.help")
    public void helpCommand(final CommandSender sender) {
        sender.sendMessage(
            (String) this.api.languageFile.help_messages.buildMap(list ->
                new ListToString(list).value()
            )
        );
    }

    @Subcommand("reload")
    @CommandPermission("fakeplayer.command.reload")
    public void reloadCommand(final CommandSender sender) {
        final long millis = System.currentTimeMillis();
        this.api.reloadPlugin(false);
        sender.sendMessage(
            this.api.languageFile.generals.reload_complete.build(
                "%ms%", () -> String.valueOf(System.currentTimeMillis() - millis)
            )
        );
    }

    @Subcommand("version")
    @CommandPermission("fakeplayer.command.version")
    public void versionCommand(final CommandSender sender) {
        this.api.checkForUpdate(sender);
    }

    @Subcommand("menu")
    @CommandPermission("fakeplayer.command.menu")
    public void listCommand(final Player player) {
        this.api.menuFile.fakePlayers.inventory().open(player);
    }

}
