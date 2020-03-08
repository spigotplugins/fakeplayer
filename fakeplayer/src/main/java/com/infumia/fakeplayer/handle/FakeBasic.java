package com.infumia.fakeplayer.handle;

import com.infumia.fakeplayer.Fake;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public final class FakeBasic implements Fake {

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

    }

}
