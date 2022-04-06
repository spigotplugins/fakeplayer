package io.github.portlek.fakeplayer.api;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine AI backends for each version of Minecraft.
 */
public interface AiBackend {

  /**
   * teleports the AI to the location.
   *
   * @param ai the AI to teleport.
   * @param location the location to teleport.
   */
  void teleport(@NotNull AiPlayer ai, @NotNull Location location);

  /**
   * removes the AI.
   *
   * @param ai the AI to remove.
   */
  void remove(@NotNull AiPlayer ai);

  /**
   * spawns the AI.
   *
   * @param ai the AI to spawn.
   */
  void spawn(@NotNull AiPlayer ai);

  /**
   * toggles visibility of the AI.
   *
   * @param ai the AI to toggle.
   */
  void toggleVisible(@NotNull AiPlayer ai);
}
