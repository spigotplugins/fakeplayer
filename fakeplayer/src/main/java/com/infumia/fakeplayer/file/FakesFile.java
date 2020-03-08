package com.infumia.fakeplayer.file;

import com.infumia.fakeplayer.api.Fake;
import com.infumia.fakeplayer.handle.FakeBasic;
import io.github.portlek.configs.BukkitManaged;
import io.github.portlek.configs.annotations.Config;
import io.github.portlek.configs.annotations.Instance;
import io.github.portlek.configs.annotations.Section;
import io.github.portlek.configs.util.FileType;
import io.github.portlek.location.LocationOf;
import java.util.HashMap;
import java.util.Map;
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

    public void addFakes(@NotNull final String name, @NotNull final Location location) {
        final Fake fake = new FakeBasic(name, location);
        fake.spawn();
        this.fakeplayers.put(name, fake);
    }

    @Override
    public void load() {
        this.fakeplayers.clear();
        super.load();
        this.setAutoSave(true);
        this.getOrCreateSection("fakes").ifPresent(section ->
            section.getKeys(false).forEach(s ->
                this.fakeplayers.put(
                    s,
                    new FakeBasic(
                        s,
                        new LocationOf(this.getOrSet("fakes." + s + ".spawn-point", "")).value()
                    )
                )
            )
        );
    }

    @Section(path = "fakes")
    public static final class Fakes {

    }

}
