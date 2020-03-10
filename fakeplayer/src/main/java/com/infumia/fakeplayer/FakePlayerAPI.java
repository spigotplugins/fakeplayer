package com.infumia.fakeplayer;

import com.infumia.fakeplayer.api.Fake;
import com.infumia.fakeplayer.file.ConfigFile;
import com.infumia.fakeplayer.file.FakesFile;
import com.infumia.fakeplayer.file.LanguageFile;
import com.infumia.fakeplayer.file.MenuFile;
import com.infumia.fakeplayer.util.ListenerBasic;
import com.infumia.fakeplayer.util.UpdateChecker;
import fr.minuskube.inv.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public final class FakePlayerAPI {

    @NotNull
    public final InventoryManager inventoryManager;

    @NotNull
    public final FakePlayer fakePlayer;

    @NotNull
    public final ConfigFile configFile;

    @NotNull
    public final LanguageFile languageFile;

    @NotNull
    public final FakesFile fakesFile;

    @NotNull
    public final MenuFile menuFile;

    public FakePlayerAPI(@NotNull final FakePlayer fakePlayer) {
        this.inventoryManager = new InventoryManager(fakePlayer);
        this.fakePlayer = fakePlayer;
        this.configFile = new ConfigFile();
        this.languageFile = new LanguageFile(this.configFile);
        this.fakesFile = new FakesFile();
        this.menuFile = new MenuFile();
    }

    public void reloadPlugin(final boolean first) {
        this.disablePlugin();
        this.languageFile.load();
        this.configFile.load();
        this.fakesFile.load();
        this.menuFile.load();

        if (first) {
            this.inventoryManager.init();
            new ListenerBasic<>(
                PlayerJoinEvent.class,
                event -> event.getPlayer().hasPermission("fakeplayer.version"),
                event -> this.checkForUpdate(event.getPlayer())
            ).register(this.fakePlayer);
            new ListenerBasic<>(
                PlayerJoinEvent.class,
                event -> this.fakesFile.fakeplayers.values().forEach(npc -> {
                    npc.deSpawn();
                    npc.spawn();
                })
            ).register(this.fakePlayer);
        }

        this.checkForUpdate(this.fakePlayer.getServer().getConsoleSender());
    }

    public void disablePlugin() {
        this.fakesFile.fakeplayers.values().forEach(Fake::deSpawn);
    }

    public void checkForUpdate(@NotNull final CommandSender sender) {
        Bukkit.getScheduler().runTaskAsynchronously(this.fakePlayer, () -> {
            if (!this.configFile.check_for_update) {
                return;
            }
            final UpdateChecker updater = new UpdateChecker(this.fakePlayer, 73139);

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
                this.fakePlayer.getLogger().warning("Update checker failed, could not connect to the API.");
            }
        });
    }

}
