package io.github.portlek.fakeplayer.nms;

import io.github.portlek.fakeplayer.api.AiPlayer;
import io.github.portlek.fakeplayer.api.AiPlayerFunction;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * a class that represents AI player nms.
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
final class AiPlayerNms implements AiPlayerFunction {

  /**
   * the ai.
   */
  @NotNull
  private final AiPlayer ai;

  @Override
  public void location(@NotNull final Location location) {
  }

  @Override
  public void remove() {
  }

  @Override
  public void spawn() {
  }

  @Override
  public void toggleVisible() {
  }
}
