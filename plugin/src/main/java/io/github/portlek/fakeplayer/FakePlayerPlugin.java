package io.github.portlek.fakeplayer;

import io.github.portlek.fakeplayer.api.AiBackend;
import io.github.portlek.fakeplayer.api.AiPlayerCoordinator;
import io.github.portlek.fakeplayer.api.FakePlayerConfig;
import io.github.portlek.fakeplayer.nms.v1_18_R2.Backend1_18_R2;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.bukkit.plugin.java.JavaPlugin;
import tr.com.infumia.versionmatched.VersionMatched;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class FakePlayerPlugin extends JavaPlugin {

  AiBackend backend = new VersionMatched<>(
    Backend1_18_R2.class
  )
    .of()
    .create()
    .orElseThrow(() -> new IllegalStateException("Something went wrong when creating the backend nms class!"));

  @Override
  public void onLoad() {
    this.reloadFiles();
  }

  @Override
  public void onDisable() {
  }

  @Override
  public void onEnable() {
    AiPlayerCoordinator.backend(this.backend);
    Objects.requireNonNull(this.getCommand("fakeplayer"), "fakeplayer")
      .setExecutor(new FakePlayerCommand(this));
  }

  @SneakyThrows
  public void reloadFiles() {
    FakePlayerConfig.init(this.getDataFolder().toPath());
  }
}
