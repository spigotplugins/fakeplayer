package io.github.portlek.fakeplayer.api;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.UtilityClass;
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
   * connects the Ai to the server.
   *
   * @param ai the Ai to connect.
   */
  void connect(
    @NotNull final AiPlayer ai
  ) {
    final var wrapped = AiPlayerCoordinator.backend().wrapAi(ai);
    AiPlayerCoordinator.registry.put(wrapped.uniqueId(), wrapped);
    wrapped.connect();
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
