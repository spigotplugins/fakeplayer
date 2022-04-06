package io.github.portlek.fakeplayer.nms.v1_18_R1;

import io.github.portlek.fakeplayer.api.AiBackend;
import io.github.portlek.fakeplayer.api.AiPlayer;
import org.jetbrains.annotations.NotNull;

/**
 * a class that represents backed implementation for 1_18_R1 version.
 */
public final class Backend1_18_R1 implements AiBackend {

  @NotNull
  @Override
  public AiPlayer createPlayer(@NotNull final AiPlayer ai) {
    return new AiPlayerNms(ai);
  }
}
