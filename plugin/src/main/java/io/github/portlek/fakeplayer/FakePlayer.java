package io.github.portlek.fakeplayer;

import io.github.portlek.fakeplayer.api.AiBackend;
import io.github.portlek.fakeplayer.api.AiPlayerCoordinator;
import io.github.portlek.fakeplayer.nms.Backend1_18_R1;
import org.bukkit.plugin.java.JavaPlugin;
import tr.com.infumia.infumialib.platform.paper.versionmatched.VersionMatched;

/**
 * a class that represents main class of FakePlayer plugin.
 */
public final class FakePlayer extends JavaPlugin {

  /**
   * the backend.
   */
  private final AiBackend backend = new VersionMatched<AiBackend>(
    Backend1_18_R1.class
  )
    .of(this)
    .create(this)
    .orElseThrow(() -> new IllegalStateException("Something went wrong when creating the backend nms class!"));

  @Override
  public void onDisable() {
  }

  @Override
  public void onEnable() {
    AiPlayerCoordinator.backend(this.backend);
  }
}
