package io.github.portlek.fakeplayer.api;

import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine AI backend.
 */
public interface AiBackend {

  /**
   * wraps the Ai player with the backend Ai player.
   *
   * @param ai the Ai to create.
   *
   * @return functional Ai.
   */
  @NotNull
  AiPlayer wrap(@NotNull AiPlayer ai);
}
