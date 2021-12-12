package io.github.portlek.fakeplayer.nms;

import com.github.steveice10.mc.protocol.MinecraftProtocol;
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

  /**
   * the protocol.
   */
  private final MinecraftProtocol protocol = new MinecraftProtocol();

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
