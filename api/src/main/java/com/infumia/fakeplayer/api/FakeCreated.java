package com.infumia.fakeplayer.api;

import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public interface FakeCreated {

    @NotNull
    INPC create(@NotNull String name, @NotNull World world, @NotNull Location location);

}
