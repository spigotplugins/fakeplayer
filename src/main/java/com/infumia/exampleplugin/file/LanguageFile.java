package com.infumia.exampleplugin.file;

import io.github.portlek.configs.BukkitLinkedManaged;
import io.github.portlek.configs.annotations.*;
import io.github.portlek.configs.util.ColorUtil;
import io.github.portlek.configs.util.MapEntry;
import io.github.portlek.configs.util.Replaceable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

@LinkedConfig(configs = @Config(
    name = "en",
    // TODO: Change the plugin data folder as you want.
    location = "%basedir%/ExamplePlugin/languages"
))
public final class LanguageFile extends BukkitLinkedManaged {

    public LanguageFile(@NotNull final ConfigFile configFile) {
        super(configFile.plugin_language, MapEntry.of("config", configFile));
    }

    @NotNull
    public Map<String, Supplier<String>> getPrefix() {
        final Map<String, Supplier<String>> map = new HashMap<>();
        this.pull("config").ifPresent(o ->
            map.put("%prefix%", () -> ((ConfigFile)o).plugin_prefix.build())
        );
        return map;
    }

    @Instance
    public LanguageFile.Error errors = new LanguageFile.Error();

    @Section(path = "error")
    public class Error {

        @Value
        public Replaceable<String> player_not_found = LanguageFile.this.match(s -> {
            if ("en".equals(s)) {
                return Optional.of(
                    Replaceable.of("%prefix% &cPlayer not found! (%player%)")
                        .map(ColorUtil::colored)
                        .replaces("%player%")
                        .replace(LanguageFile.this.getPrefix())
                );
            }

            return Optional.empty();
        });

    }

    @Instance
    public LanguageFile.General generals = new LanguageFile.General();

    @Section(path = "general")
    public class General {

        @Value
        public Replaceable<String> reload_complete = LanguageFile.this.match(s -> {
            if ("en".equals(s)) {
                return Optional.of(
                    Replaceable.of("%prefix% &aReload complete! &7Took (%ms%ms)")
                        .map(ColorUtil::colored)
                        .replace(LanguageFile.this.getPrefix())
                        .replaces("%ms%")
                );
            }

            return Optional.empty();
        });

        @Value
        public Replaceable<String> new_version_found = LanguageFile.this.match(s -> {
            if ("en".equals(s)) {
                return Optional.of(
                    Replaceable.of("%prefix% &eNew version found (v%version%)")
                        .map(ColorUtil::colored)
                        .replaces("%version%")
                        .replace(LanguageFile.this.getPrefix())
                );
            }

            return Optional.empty();
        });

        @Value
        public Replaceable<String> latest_version = LanguageFile.this.match(s -> {
            if ("en".equals(s)) {
                return Optional.of(
                    Replaceable.of("%prefix% &aYou''re using the latest version (v%version%)")
                        .map(ColorUtil::colored)
                        .replaces("%version%")
                        .replace(LanguageFile.this.getPrefix())
                );
            }

            return Optional.empty();
        });

    }

    @Value
    public Replaceable<List<String>> help_messages = this.match(s -> {
        if ("en".equals(s)) {
            return Optional.of(
                Replaceable.of(
                    "&a====== %prefix% &a======",
                    "&7/exampleplugin &r> &eShows help message.",
                    "&7/exampleplugin help &r> &eShows help message.",
                    "&7/exampleplugin reload &r> &eReloads the plugin.",
                    "&7/exampleplugin version &r> &eChecks for update."
                )
                    .map(ColorUtil::colored)
                    .replace(this.getPrefix())
            );
        }

        return Optional.empty();
    });

}
