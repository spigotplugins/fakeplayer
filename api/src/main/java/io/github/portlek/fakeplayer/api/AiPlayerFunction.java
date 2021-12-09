package io.github.portlek.fakeplayer.api;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine AI players.
 */
public interface AiPlayerFunction {

  /**
   * sets the location of the AI.
   *
   * @param location the location to set.
   */
  void location(@NotNull Location location);

  /**
   * removes the AI from the server.
   */
  void remove();

  /**
   * spawns the AI.
   */
  void spawn();

  /**
   * toggles visible.
   */
  void toggleVisible();
}
