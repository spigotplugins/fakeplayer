package io.github.portlek.fakeplayer;

import java.util.Objects;

import io.github.portlek.fakeplayer.utils.FakePlayerMessageHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class FakePlayerPlugin extends JavaPlugin {
  public static FakePlayerMessageHandler messageHandler;
  public static FakePlayerPlugin INSTANCE;

  @Override
  public void onLoad() {
    INSTANCE = this;
    this.saveDefaultConfig();
    this.reload();
  }

  @Override
  public void onEnable() {
    messageHandler = new FakePlayerMessageHandler(this);
    Objects.requireNonNull(
        this.getCommand("fakeplayer"),
        "Invalid plugin.yml file! Please re-download and install the plugin."
      )
      .setExecutor(new FakePlayerCommand(this));
  }

  public void reload() {
    this.reloadConfig();
    messageHandler = new FakePlayerMessageHandler(this);
  }
}
