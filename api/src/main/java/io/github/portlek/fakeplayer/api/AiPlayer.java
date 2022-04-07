package io.github.portlek.fakeplayer.api;

import java.util.UUID;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine AI players.
 */
public interface AiPlayer extends AiPlayerFunction {

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
    return new Impl(name, location, uniqueId);
  }

  /**
   * obtains the location.
   *
   * @return location.
   */
  @NotNull
  Location location();

  /**
   * obtains the name.
   *
   * @return name.
   */
  @NotNull
  String name();

  /**
   * obtains the spawn point.
   *
   * @return spawn point.
   */
  @NotNull
  Location spawnPoint();

  /**
   * obtains the unique id.
   *
   * @return unique id.
   */
  @NotNull
  UUID uniqueId();

  /**
   * a simple implementation of {@link AiPlayer}.
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
    private volatile Location location;

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

    @Override
    public void location(@NotNull final Location location) {
      this.location = location;
      AiPlayerCoordinator.teleport(this, location);
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
