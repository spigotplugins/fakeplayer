package com.infumia.fakeplayer.file;

import io.github.portlek.configs.BukkitManaged;
import io.github.portlek.configs.annotations.Config;
import io.github.portlek.configs.annotations.Value;
import io.github.portlek.configs.util.ColorUtil;
import io.github.portlek.configs.util.Replaceable;

@Config(
    name = "config",
    location = "%basedir%/FakePlayer"
)
public final class ConfigFile extends BukkitManaged {

    @Value
    public Replaceable<String> plugin_prefix = Replaceable.of("&6[&eFakePlayer&6]")
        .map(ColorUtil::colored);

    @Value
    public String plugin_language = "en";

    @Value
    public boolean check_for_update = true;

    @Value
    public Replaceable<String> tab_name = Replaceable.of("&e[Player] &a%player_name%")
        .map(ColorUtil::colored)
        .replaces("%player_name%");

    @Override
    public void onLoad() {
        this.setAutoSave(true);
    }

}
