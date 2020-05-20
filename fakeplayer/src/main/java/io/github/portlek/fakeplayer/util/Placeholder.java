package io.github.portlek.fakeplayer.util;

import org.jetbrains.annotations.NotNull;

public final class Placeholder {

    @NotNull
    private final String regex;

    @NotNull
    private final String replace;

    public Placeholder(@NotNull String regex, @NotNull String replace) {
        this.regex = regex;
        this.replace = replace;
    }

    @NotNull
    public String getRegex() {
        return regex;
    }

    @NotNull
    public String getReplace() {
        return replace;
    }

    @NotNull
    public String replace(@NotNull String text) {
        return text.replace(regex, replace);
    }

}