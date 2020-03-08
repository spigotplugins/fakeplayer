package com.infumia.fakeplayer.api;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public final class MockFakeCreated implements FakeCreated {

    @NotNull
    @Override
    public INPC create(@NotNull final Location location) {
        return new MockNPC();
    }

}
