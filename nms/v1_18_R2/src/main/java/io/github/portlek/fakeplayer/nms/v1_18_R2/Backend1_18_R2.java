package io.github.portlek.fakeplayer.nms.v1_18_R2;

import io.github.portlek.fakeplayer.api.AiBackend;
import io.github.portlek.fakeplayer.api.AiPlayer;
import org.jetbrains.annotations.NotNull;

public final class Backend1_18_R2 implements AiBackend {

  @NotNull
  @Override
  public AiPlayer wrapAi(@NotNull final AiPlayer ai) {
    return new AiPlayerNms(ai);
  }
}
