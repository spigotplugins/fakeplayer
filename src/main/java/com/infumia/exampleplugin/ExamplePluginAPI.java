package com.infumia.exampleplugin;

import com.infumia.exampleplugin.file.ConfigFile;
import com.infumia.exampleplugin.file.LanguageFile;
import com.infumia.exampleplugin.util.ListenerBasic;
import com.infumia.exampleplugin.util.UpdateChecker;
import io.github.portlek.database.SQL;
import org.bukkit.command.CommandSender;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

// TODO Change the class name as you want.
public final class ExamplePluginAPI {

    @NotNull
    public final ExamplePlugin examplePlugin;

    @NotNull
    public final ConfigFile configFile;

    @NotNull
    public final LanguageFile languageFile;

    @NotNull
    public SQL sql;

    public ExamplePluginAPI(@NotNull final ExamplePlugin examplePlugin) {
        this.examplePlugin = examplePlugin;
        this.configFile = new ConfigFile();
        this.languageFile = new LanguageFile(this.configFile);
        this.sql = this.configFile.createSQL();
    }

    public void reloadPlugin(final boolean first) {
        this.languageFile.load();
        this.configFile.load();

        if (first) {
            new ListenerBasic<>(
                PlayerJoinEvent.class,
                event -> event.getPlayer().hasPermission("exampleplugin.version"),
                event -> this.checkForUpdate(event.getPlayer())
            ).register(this.examplePlugin);
            // TODO: Listeners should be here.
        } else {
            this.sql = this.configFile.createSQL();
        }

        this.examplePlugin.getServer().getScheduler().cancelTasks(this.examplePlugin);

        if (this.configFile.saving.auto_save) {
            this.examplePlugin.getServer().getScheduler().runTaskTimer(
                this.examplePlugin,
                () -> {
                    // TODO Add codes for saving data as automatic
                },
                this.configFile.saving.auto_save_time * 20L,
                this.configFile.saving.auto_save_time * 20L
            );
        }

        this.checkForUpdate(this.examplePlugin.getServer().getConsoleSender());
    }

    public void checkForUpdate(@NotNull final CommandSender sender) {
        if (!this.configFile.check_for_update) {
            return;
        }
        // TODO Change the UpdateChecker resource id as you want.
        final UpdateChecker updater = new UpdateChecker(this.examplePlugin, 11111);

        try {
            if (updater.checkForUpdates()) {
                sender.sendMessage(
                    this.languageFile.generals.new_version_found
                        .build("%version%", updater::getLatestVersion)
                );
            } else {
                sender.sendMessage(
                    this.languageFile.generals.latest_version
                        .build("%version%", updater::getLatestVersion)
                );
            }
        } catch (final Exception exception) {
            this.examplePlugin.getLogger().warning("Update checker failed, could not connect to the API.");
        }
    }

    public void disablePlugin() {
        if (this.configFile.saving.save_when_plugin_disable) {
            // TODO Add codes for saving data
        }

        this.sql.getDatabase().disconnect();
    }

}
