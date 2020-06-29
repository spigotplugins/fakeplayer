package io.github.portlek.fakeplayer.file;

import io.github.portlek.configs.annotations.Config;
import io.github.portlek.configs.annotations.Property;
import io.github.portlek.configs.bukkit.BukkitManaged;
import io.github.portlek.configs.bukkit.util.ColorUtil;
import io.github.portlek.configs.replaceable.Replaceable;
import io.github.portlek.configs.replaceable.ReplaceableString;

@Config(
    value = "config",
    location = "%basedir%/FakePlayer"
)
public final class ConfigFile extends BukkitManaged {

    @Property
    public ReplaceableString plugin_prefix = Replaceable.from("&6[&eFakePlayer&6]")
        .map(ColorUtil::colored);

    @Property
    public String plugin_language = "en";

    @Property
    public boolean check_for_update = true;

    @Property
    public ReplaceableString tab_name = Replaceable.from("&e[Player] &a%player_name%")
        .map(ColorUtil::colored)
        .replaces("%player_name%");

    @Override
    public void onLoad() {
        this.setAutoSave(true);
    }

}
