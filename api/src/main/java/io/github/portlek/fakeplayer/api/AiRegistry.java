package io.github.portlek.fakeplayer.api;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine AI registries.
 */
public interface AiRegistry {

  /**
   * creates a simple AI registry.
   *
   * @return simple AI registry.
   */
  @NotNull
  static AiRegistry simple() {
    return new Impl();
  }

  /**
   * removes the AI.
   *
   * @param ai the ai to remove.
   */
  void remove(@NotNull AiPlayer ai);

  /**
   * spawns the AI.
   *
   * @param ai the AI to spawn.
   */
  void spawn(@NotNull AiPlayer ai);

  /**
   * teleports the AI to the location.
   *
   * @param ai the AI to teleport.
   * @param location the location to teleport.
   */
  void teleport(@NotNull AiPlayer ai, @NotNull Location location);

  /**
   * toggles visibility of the AI.
   *
   * @param ai the AI to toggle.
   */
  void toggleVisible(@NotNull AiPlayer ai);

  /**
   * a class that represents simple implementation of {@link AiRegistry}.
   */
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  final class Impl implements AiRegistry {

    /**
     * the registry.
     */
    private final Map<UUID, AiPlayer> registry = new ConcurrentHashMap<>();

    @Override
    public void remove(@NotNull final AiPlayer ai) {
      final var nms = this.registry.remove(ai.uniqueId());
      if (nms != null) {
        nms.remove();
      }
    }

    @Override
    public void spawn(@NotNull final AiPlayer ai) {
      final var nms = AiPlayerCoordinator.backend().createPlayer(ai);
      this.registry.put(ai.uniqueId(), nms);
      nms.spawn();
    }

    @Override
    public void teleport(@NotNull final AiPlayer ai, @NotNull final Location location) {
      final var nms = this.registry.get(ai.uniqueId());
      if (nms != null) {
        nms.location(location);
      }
    }

    @Override
    public void toggleVisible(@NotNull final AiPlayer ai) {
      final var nms = this.registry.get(ai.uniqueId());
      if (nms != null) {
        nms.toggleVisible();
      }
    }
  }
}
