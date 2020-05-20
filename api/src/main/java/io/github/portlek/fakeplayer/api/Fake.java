package io.github.portlek.fakeplayer.api;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public interface Fake {

    @NotNull
    String getName();

    @NotNull
    Location getSpawnPoint();

    void spawn();

    void deSpawn();

    void toggleVisible();

}
