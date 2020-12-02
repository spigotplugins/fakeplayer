package io.github.portlek.fakeplayer.file;

import io.github.portlek.bukkititembuilder.util.ColorUtil;
import io.github.portlek.configs.annotations.Config;
import io.github.portlek.configs.annotations.Property;
import io.github.portlek.configs.bukkit.BukkitManaged;
import io.github.portlek.configs.type.YamlFileType;
import io.github.portlek.replaceable.Replaceable;
import io.github.portlek.replaceable.rp.RpString;

@Config(
  name = "config",
  type = YamlFileType.class,
  location = "%basedir%/FakePlayer"
)
public final class ConfigFile extends BukkitManaged {

  @Property
  public RpString plugin_prefix = Replaceable.from("&6[&eFakePlayer&6]")
    .map(ColorUtil::colored);

  @Property
  public String plugin_language = "en";

  @Property
  public boolean check_for_update = true;

  @Property
  public RpString tab_name = Replaceable.from("&e[Player] &a%player_name%")
    .map(ColorUtil::colored)
    .replaces("%player_name%");

  @Property
  public RpString chat_format = Replaceable.from("&e[Player] &a%player_name%: &7%message%")
    .map(ColorUtil::colored)
    .replaces("%player_name%", "%message%");

  @Override
  public void onLoad() {
    this.setAutoSave(true);
  }
}
