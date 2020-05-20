package io.github.portlek.fakeplayer.file;

import io.github.portlek.fakeplayer.FakePlayer;
import io.github.portlek.fakeplayer.api.Fake;
import io.github.portlek.fakeplayer.handle.FakeBasic;
import io.github.portlek.configs.annotations.Config;
import io.github.portlek.configs.annotations.Instance;
import io.github.portlek.configs.annotations.Section;
import io.github.portlek.configs.bukkit.BukkitManaged;
import io.github.portlek.configs.bukkit.BukkitSection;
import io.github.portlek.configs.files.FileType;
import io.github.portlek.location.LocationOf;
import io.github.portlek.location.StringOf;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Config(
    name = "fakes",
    location = "%basedir%/FakePlayer",
    type = FileType.JSON
)
public final class FakesFile extends BukkitManaged {

    @Instance
    public final FakesFile.Fakes fakes = new FakesFile.Fakes();

    public final Map<String, Fake> fakeplayers = new HashMap<>();

    @Override
    public void onCreate() {
        this.fakeplayers.clear();
    }

    @Override
    public void onLoad() {
        this.setAutoSave(true);
        fakes.getKeys(false).stream()
            .map(name ->
                new FakeBasic(
                    name,
                    new LocationOf(fakes.getOrSetString(name, "")).value()
                ))
            .forEach(fake -> {
                Bukkit.getScheduler().runTask(FakePlayer.getInstance(), fake::spawn);
                this.fakeplayers.put(fake.getName(), fake);
            });
    }

    public void remove(@NotNull final String name) {
        Optional.ofNullable(this.fakeplayers.remove(name)).ifPresent(fake -> {
            Bukkit.getOnlinePlayers().forEach(player ->
                player.sendMessage(
                    FakePlayer.getAPI().languageFile.generals.quit_message
                        .build("%player_name%", () -> name)));
            fake.deSpawn();
            fakes.set(name, null);
        });
    }

    public void addFakes(@NotNull final String name, @NotNull final Location location) {
        final Fake fake = new FakeBasic(name, location);
        Bukkit.getOnlinePlayers().forEach(player ->
            player.sendMessage(
                FakePlayer.getAPI().languageFile.generals.join_message
                    .build("%player_name%", () -> name)));
        fake.spawn();
        this.fakeplayers.put(name, fake);
        fakes.set(name, new StringOf(location).asKey());
    }

    @Section(path = "fakes")
    public static final class Fakes extends BukkitSection {

    }

}
