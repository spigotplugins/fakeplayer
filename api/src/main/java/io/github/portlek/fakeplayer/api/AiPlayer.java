package io.github.portlek.fakeplayer.api;

import it.unimi.dsi.fastutil.objects.ObjectCollection;
import java.util.UUID;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import tr.com.infumia.infumialib.shared.Definition;
import tr.com.infumia.infumialib.shared.registries.Registry;

/**
 * an interface to determine AI players.
 */
public interface AiPlayer extends Definition.Name, Definition.UniqueId, Definition.Key<UUID> {

  /**
   * the registry.
   */
  Registry<UUID, AiPlayer> REGISTRY = new Registry<>();

  /**
   * creates a simple AI player.
   *
   * @param name the name to create.
   * @param location the location to create.
   *
   * @return AI player.
   */
  @NotNull
  static AiPlayer create(@NotNull final String name, @NotNull final Location location) {
    return AiPlayer.create(name, UUID.randomUUID(), location);
  }

  /**
   * creates a simple AI player.
   *
   * @param name the name to create.
   * @param uniqueId the unique id to create.
   * @param location the location to create.
   *
   * @return AI player.
   */
  @NotNull
  static AiPlayer create(@NotNull final String name, @NotNull final UUID uniqueId, @NotNull final Location location) {
    final Impl ai = new Impl(name, location, uniqueId);
    AiPlayer.REGISTRY.register(ai);
    return ai;
  }

  @Override
  @NotNull
  default UUID key() {
    return this.uniqueId();
  }

  /**
   * obtains the location.
   *
   * @return location.
   */
  @NotNull
  Location location();

  /**
   * sets the location of the AI.
   *
   * @param location the location to set.
   *
   * @return {@code this} for the chain.
   */
  @NotNull
  AiPlayer location(@NotNull Location location);

  /**
   * removes the AI from the server.
   */
  void remove();

  /**
   * spawns the AI.
   *
   * @see #spawnPoint()
   */
  void spawn();

  /**
   * obtains the spawn point.
   *
   * @return spawn point.
   */
  @NotNull
  Location spawnPoint();

  /**
   * toggles visible.
   */
  void toggleVisible();

  /**
   * a simple implementation of {@link  AiPlayer}.
   */
  @Getter
  @Accessors(fluent = true)
  final class Impl implements AiPlayer {

    /**
     * the name.
     */
    @NotNull
    private final String name;

    /**
     * the spawn point.
     */
    @NotNull
    private final Location spawnPoint;

    /**
     * the unique id.
     */
    @NotNull
    private final UUID uniqueId;

    /**
     * the location.
     */
    @NotNull
    private Location location;

    /**
     * ctor.
     *
     * @param name the name.
     * @param spawnPoint the spawn point.
     * @param uniqueId the unique id.
     */
    private Impl(@NotNull final String name, @NotNull final Location spawnPoint, @NotNull final UUID uniqueId) {
      this.name = name;
      this.spawnPoint = spawnPoint;
      this.uniqueId = uniqueId;
      this.location = spawnPoint;
    }

    @NotNull
    @Override
    public AiPlayer location(@NotNull final Location location) {
      this.location = location;
      AiPlayerCoordinator.teleport(this, location);
      return this;
    }

    @Override
    public void remove() {
      AiPlayerCoordinator.remove(this);
    }

    @Override
    public void spawn() {
      AiPlayerCoordinator.spawn(this);
    }

    @Override
    public void toggleVisible() {
      AiPlayerCoordinator.toggleVisible(this);
    }
  }
}
