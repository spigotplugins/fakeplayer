package io.github.portlek.fakeplayer.nms;

import io.github.portlek.fakeplayer.api.AiBackend;
import io.github.portlek.fakeplayer.api.AiPlayer;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * a class that represents backed implementation for 1_8_R1 version.
 */
@RequiredArgsConstructor
public final class Backend1_8_R1 implements AiBackend {

  /**
   * the plugin.
   */
  @NotNull
  private final Plugin plugin;

  @Override
  public void remove(@NotNull final AiPlayer ai) {
  }

  @Override
  public void spawn(@NotNull final AiPlayer ai) {
  }

  @Override
  public void teleport(@NotNull final AiPlayer ai, @NotNull final Location location) {
  }

  @Override
  public void toggleVisible(@NotNull final AiPlayer ai) {
  }
}
