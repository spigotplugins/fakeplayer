package io.github.portlek.fakeplayer.api;

import java.nio.file.Path;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.jackson.JacksonConfigurationLoader;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

/**
 * a class that represents fake player config.
 */
@Getter
@Setter
@ConfigSerializable
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class FakePlayerConfig {

  /**
   * the instance.
   */
  private static FakePlayerConfig INSTANCE = new FakePlayerConfig();

  /**
   * the host.
   */
  @Setting
  @Comment("The server address for fake players to join.")
  String host = "127.0.0.1";

  /**
   * the port.
   */
  @Setting
  @Comment("The server port for fake players to join.")
  int port = 25565;

  /**
   * initiates the config.
   *
   * @param directory the directory to initiate.
   *
   * @throws ConfigurateException if something goes wrong when initiating the configuration file.
   */
  public static void init(@NotNull final Path directory) throws ConfigurateException {
    final var loader = JacksonConfigurationLoader.builder()
      .path(directory.resolve("config.json"))
      .defaultOptions(options -> options
        .implicitInitialization(false))
      .build();
    final var node = loader.load();
    FakePlayerConfig.INSTANCE = node.get(FakePlayerConfig.class, new FakePlayerConfig());
    node.set(FakePlayerConfig.class, FakePlayerConfig.INSTANCE);
    loader.save(node);
  }

  /**
   * obtains the instance.
   *
   * @return instance.
   */
  @NotNull
  public static FakePlayerConfig instance() {
    return FakePlayerConfig.INSTANCE;
  }
}
