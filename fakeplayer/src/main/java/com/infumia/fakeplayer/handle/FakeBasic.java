package com.infumia.fakeplayer.handle;

import com.infumia.fakeplayer.api.Fake;
import com.infumia.fakeplayer.api.FakeCreated;
import com.infumia.fakeplayer.api.MockFakeCreated;
import io.github.portlek.versionmatched.VersionMatched;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public final class FakeBasic implements Fake {

    private static final FakeCreated FAKE_CREATED = new VersionMatched<>(
        new MockFakeCreated()
    ).of().instance();

    @NotNull
    private final String name;

    @NotNull
    private final Location spawnpoint;

    public FakeBasic(@NotNull final String name, @NotNull final Location spawnpoint) {
        this.name = name;
        this.spawnpoint = spawnpoint;
    }

    @NotNull
    @Override
    public String getName() {
        return this.name;
    }

    @NotNull
    @Override
    public Location getSpawnPoint() {
        return this.spawnpoint;
    }

    @Override
    public void spawn() {
        FakeBasic.FAKE_CREATED.spawn(this.spawnpoint);
    }

}
