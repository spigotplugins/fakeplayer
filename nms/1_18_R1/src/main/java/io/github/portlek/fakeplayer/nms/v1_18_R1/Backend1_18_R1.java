package io.github.portlek.fakeplayer.nms.v1_18_R1;

import io.github.portlek.fakeplayer.api.AiBackend;
import io.github.portlek.fakeplayer.api.AiPlayer;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * a class that represents backed implementation for 1_18_R1 version.
 */
@RequiredArgsConstructor
public final class Backend1_18_R1 implements AiBackend {

  /**
   * the plugin.
   */
  @NotNull
  private final Plugin plugin;

  /**
   * the registry.
   */
  private final Object2ObjectMap<UUID, AiPlayerNms> registry = Object2ObjectMaps.synchronize(
    new Object2ObjectOpenHashMap<>());

  @Override
  public void teleport(@NotNull final AiPlayer ai, @NotNull final Location location) {
    final var nms = this.registry.get(ai.uniqueId());
    if (nms != null) {
      nms.location(location);
    }
  }

  @Override
  public void remove(@NotNull final AiPlayer ai) {
    final var nms = this.registry.remove(ai.uniqueId());
    if (nms != null) {
      nms.remove();
    }
  }

  @Override
  public void spawn(@NotNull final AiPlayer ai) {
    final var nms = new AiPlayerNms(ai);
    this.registry.put(ai.uniqueId(), nms);
    nms.spawn();
  }

  @Override
  public void toggleVisible(@NotNull final AiPlayer ai) {
    final var nms = this.registry.get(ai.uniqueId());
    if (nms != null) {
      nms.toggleVisible();
    }
  }
}
