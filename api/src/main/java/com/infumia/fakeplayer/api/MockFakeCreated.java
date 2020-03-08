package com.infumia.fakeplayer.api;

import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public final class MockFakeCreated implements FakeCreated {
    @NotNull
    @Override
    public INPC create(final @NotNull String name, final @NotNull World world, final @NotNull Location location) {
        return new MockNPC();
    }

}
