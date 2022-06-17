package io.github.portlek.fakeplayer.nms.v1_18_R2;

import io.github.portlek.fakeplayer.api.AiPlayer;
import io.github.portlek.fakeplayer.api.AiPlayerFunction;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
final class AiPlayerNms implements AiPlayer {

  @NotNull
  @Delegate(excludes = AiPlayerFunction.class)
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
