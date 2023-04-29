package io.github.portlek.fakeplayer;

import java.util.Objects;
import org.bukkit.plugin.java.JavaPlugin;

public final class FakePlayerPlugin extends JavaPlugin {

  @Override
  public void onLoad() {
    this.saveDefaultConfig();
    this.reloadFiles();
  }

  @Override
  public void onEnable() {
    Objects
      .requireNonNull(this.getCommand("fakeplayer"), "fakeplayer")
      .setExecutor(new FakePlayerCommand(this));
  }

  public void reloadFiles() {
    this.reloadConfig();
  }
}
