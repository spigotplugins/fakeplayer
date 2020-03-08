package com.infumia.fakeplayer.file;

import com.infumia.fakeplayer.FakePlayer;
import com.infumia.fakeplayer.api.Fake;
import com.infumia.fakeplayer.handle.FakeBasic;
import io.github.portlek.configs.BukkitManaged;
import io.github.portlek.configs.annotations.Config;
import io.github.portlek.configs.annotations.Instance;
import io.github.portlek.configs.annotations.Section;
import io.github.portlek.configs.util.FileType;
import io.github.portlek.location.LocationOf;
import io.github.portlek.location.StringOf;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

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
    public void load() {
        this.fakeplayers.clear();
        super.load();
        this.setAutoSave(true);
        this.getOrCreateSection("fakes").ifPresent(section ->
            section.getKeys(false).forEach(s -> {
                final Fake fake = new FakeBasic(
                    s,
                    new LocationOf(
                        this.getOrSet("fakes." + s, "")
                    ).value()
                );
                Bukkit.getScheduler().runTask(FakePlayer.getInstance(), fake::spawn);
                this.fakeplayers.put(s, fake);
            })
        );
    }

    public void remove(@NotNull final String name) {
        Optional.ofNullable(this.fakeplayers.remove(name)).ifPresent(fake -> {
            fake.deSpawn();
            this.set("fakes." + name, null);
        });
    }

    public void addFakes(@NotNull final String name, @NotNull final Location location) {
        final Fake fake = new FakeBasic(name, location);
        fake.spawn();
        this.fakeplayers.put(name, fake);
        this.set("fakes." + name, new StringOf(location).asKey());
    }

    @Section(path = "fakes")
    public static final class Fakes {

    }

}
