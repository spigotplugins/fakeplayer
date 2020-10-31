package io.github.portlek.fakeplayer.util;

import org.jetbrains.annotations.NotNull;

public final class Placeholder {

  @NotNull
  private final String regex;

  @NotNull
  private final String replace;

  public Placeholder(@NotNull final String regex, @NotNull final String replace) {
    this.regex = regex;
    this.replace = replace;
  }

  @NotNull
  public String getRegex() {
    return this.regex;
  }

  @NotNull
  public String getReplace() {
    return this.replace;
  }

  @NotNull
  public String replace(@NotNull final String text) {
    return text.replace(this.regex, this.replace);
  }
}