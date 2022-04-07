package io.github.portlek.fakeplayer.api;

import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine AI backend.
 */
public interface AiBackend {

  /**
   * creates a functional AI.
   *
   * @param ai the ai to create.
   *
   * @return functional AI.
   */
  @NotNull
  AiPlayer createPlayer(@NotNull AiPlayer ai);
}
