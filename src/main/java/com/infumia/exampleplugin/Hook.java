package com.infumia.exampleplugin;

import org.jetbrains.annotations.NotNull;

public interface Hook {

    boolean initiate();

    @NotNull
    Wrapped create();

}
