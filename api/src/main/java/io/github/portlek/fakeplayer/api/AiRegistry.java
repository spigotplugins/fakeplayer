package io.github.portlek.fakeplayer.api;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
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
   * gets the AI.
   *
   * @param uniqueId the unique id to get.
   *
   * @return AI if exists.
   */
  @NotNull
  Optional<AiPlayer> get(@NotNull UUID uniqueId);

  /**
   * puts the AI.
   *
   * @param ai the ai to put.
   */
  default void put(@NotNull final AiPlayer ai) {
    this.put(ai.uniqueId(), ai);
  }

  /**
   * puts the AI.
   *
   * @param uniqueId the unique id to put.
   * @param ai the ai to put.
   */
  void put(@NotNull UUID uniqueId, @NotNull AiPlayer ai);

  /**
   * removes the AI.
   *
   * @param uniqueId the unique id to remove.
   *
   * @return removed AI if exists.
   */
  @NotNull
  Optional<AiPlayer> remove(@NotNull UUID uniqueId);

  /**
   * a class that represents simple implementation of {@link AiRegistry}.
   */
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  final class Impl implements AiRegistry {

    /**
     * the registry.
     */
    private final Map<UUID, AiPlayer> registry = new ConcurrentHashMap<>();

    @NotNull
    @Override
    public Optional<AiPlayer> get(@NotNull final UUID uniqueId) {
      return Optional.ofNullable(this.registry.get(uniqueId));
    }

    @Override
    public void put(@NotNull final UUID uniqueId, @NotNull final AiPlayer ai) {
      this.registry.put(uniqueId, ai);
    }

    @NotNull
    @Override
    public Optional<AiPlayer> remove(@NotNull final UUID uniqueId) {
      return Optional.ofNullable(this.registry.remove(uniqueId));
    }
  }
}
