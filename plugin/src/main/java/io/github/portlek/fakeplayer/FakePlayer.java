package io.github.portlek.fakeplayer;

import io.github.portlek.fakeplayer.api.AiBackend;
import io.github.portlek.fakeplayer.api.AiPlayerCoordinator;
import io.github.portlek.fakeplayer.commands.FakePlayerCommand;
import io.github.portlek.fakeplayer.nms.v1_18_R1.Backend1_18_R1;
import io.github.portlek.fakeplayer.nms.v1_18_R2.Backend1_18_R2;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;
import tr.com.infumia.versionmatched.VersionMatched;

/**
 * a class that represents main class of FakePlayer plugin.
 */
public final class FakePlayer extends JavaPlugin {

  /**
   * the backend.
   */
  private final AiBackend backend = new VersionMatched<>(
    Backend1_18_R1.class,
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
    final var handler = BukkitCommandHandler.create(this);
    handler.registerBrigadier();
    handler.setMessagePrefix("&6[&eFakePlayer&6]&r ");
    handler.setHelpWriter((c, a) -> "%s %s - %s"
      .formatted(c.getPath().toRealString(), c.getUsage(), c.getDescription()));
    handler.register(new FakePlayerCommand());
  }
}
