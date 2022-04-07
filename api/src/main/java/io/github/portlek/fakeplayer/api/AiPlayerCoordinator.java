package io.github.portlek.fakeplayer.api;

import java.util.Objects;
import lombok.Getter;
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
  @Setter
  @Nullable
  private AiBackend backend;

  /**
   * the registry.
   */
  @Getter
  @Setter
  @NotNull
  private AiRegistry registry = AiRegistry.simple();

  /**
   * obtains the backend.
   *
   * @return backend.
   */
  @NotNull
  AiBackend backend() {
    return Objects.requireNonNull(AiPlayerCoordinator.backend, "Backend not initiated!");
  }

  /**
   * removes the AI.
   *
   * @param ai the AI to remove.
   */
  void remove(@NotNull final AiPlayer ai) {
    AiPlayerCoordinator.registry.remove(ai.uniqueId()).ifPresent(AiPlayerFunction::remove);
  }

  /**
   * spawns the AI.
   *
   * @param ai the AI to spawn.
   */
  void spawn(@NotNull final AiPlayer ai) {
    final var nms = AiPlayerCoordinator.backend().createPlayer(ai);
    AiPlayerCoordinator.registry.put(ai);
    nms.spawn();
  }

  /**
   * teleports the AI to the location.
   *
   * @param ai the AI to teleport.
   * @param location the location to teleport.
   */
  void teleport(@NotNull final AiPlayer ai, @NotNull final Location location) {
    AiPlayerCoordinator.registry.get(ai.uniqueId()).ifPresent(p -> p.location(location));
  }

  /**
   * toggles visibility of the AI.
   *
   * @param ai the AI to toggle.
   */
  void toggleVisible(@NotNull final AiPlayer ai) {
    AiPlayerCoordinator.registry.get(ai.uniqueId()).ifPresent(AiPlayerFunction::toggleVisible);
  }
}
