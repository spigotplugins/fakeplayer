package io.github.portlek.fakeplayer.api;

import java.util.Objects;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * a class that representsAI player coordinator.
 */
@UtilityClass
@Accessors(fluent = true)
public class AiPlayerCoordinator {

  /**
   * the backend.
   */
  @Nullable
  @Setter
  private AiBackend backend;

  /**
   * removes the AI.
   *
   * @param ai the AI to remove.
   */
  void remove(@NotNull final AiPlayer ai) {
    AiPlayerCoordinator.backend().remove(ai);
  }

  /**
   * spawns the AI.
   *
   * @param ai the AI to spawn.
   */
  void spawn(@NotNull final AiPlayer ai) {
    AiPlayerCoordinator.backend().spawn(ai);
  }

  /**
   * teleports the AI to the location.
   *
   * @param ai the AI to teleport.
   * @param location the location to teleport.
   */
  void teleport(@NotNull final AiPlayer ai, @NotNull final Location location) {
    AiPlayerCoordinator.backend().teleport(ai, location);
  }

  /**
   * toggles visibility of the AI.
   *
   * @param ai the AI to toggle.
   */
  void toggleVisible(@NotNull final AiPlayer ai) {
    AiPlayerCoordinator.backend().toggleVisible(ai);
  }

  /**
   * obtains the backend.
   *
   * @return backend.
   */
  @NotNull
  private AiBackend backend() {
    return Objects.requireNonNull(AiPlayerCoordinator.backend, "Backend not initiated!");
  }
}
