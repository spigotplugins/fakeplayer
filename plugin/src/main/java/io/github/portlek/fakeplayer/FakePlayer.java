package io.github.portlek.fakeplayer;

import io.github.portlek.fakeplayer.api.AiBackend;
import io.github.portlek.fakeplayer.api.AiPlayerCoordinator;
import io.github.portlek.fakeplayer.nms.v1_18_R2.Backend1_18_R2;
import org.bukkit.plugin.java.JavaPlugin;
import tr.com.infumia.versionmatched.VersionMatched;

public final class FakePlayer extends JavaPlugin {

  private final AiBackend backend = new VersionMatched<>(
    Backend1_18_R2.class
  )
    .of()
    .create()
    .orElseThrow(() -> new IllegalStateException("Something went wrong when creating the backend nms class!"));

  @Override
  public void onDisable() {
  }

  @Override
  public void onEnable() {
    AiPlayerCoordinator.backend(this.backend);
  }
}
