package io.github.portlek.fakeplayer.api;

import java.util.Objects;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * a class that representsAI player coordinator.
 */
@Accessors(fluent = true)
public final class AiPlayerCoordinator implements AiBackend {

  /**
   * the backend.
   */
  @Nullable
  @Setter
  private static AiBackend backend;

  /**
   * obtains the backend.
   *
   * @return backend.
   */
  @NotNull
  static AiBackend backend() {
    return Objects.requireNonNull(AiPlayerCoordinator.backend, "Backend not initiated!");
  }

  @Override
  public void remove(@NotNull final AiPlayer ai) {
    AiPlayerCoordinator.backend().remove(ai);
  }

  @Override
  public void spawn(@NotNull final AiPlayer ai) {
    AiPlayerCoordinator.backend().spawn(ai);
  }

  @Override
  public void teleport(@NotNull final AiPlayer ai, @NotNull final Location location) {
    AiPlayerCoordinator.backend().teleport(ai, location);
  }

  @Override
  public void toggleVisible(@NotNull final AiPlayer ai) {
    AiPlayerCoordinator.backend().toggleVisible(ai);
  }
}
