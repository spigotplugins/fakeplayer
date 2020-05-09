package com.infumia.fakeplayer.file;

import io.github.portlek.configs.BukkitLinkedManaged;
import io.github.portlek.configs.BukkitSection;
import io.github.portlek.configs.annotations.*;
import io.github.portlek.configs.util.ColorUtil;
import io.github.portlek.configs.util.MapEntry;
import io.github.portlek.configs.util.Replaceable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@LinkedConfig(files = @LinkedFile(
    key = "en",
    config = @Config(
        name = "en",
        location = "%basedir%/FakePlayer/languages"
    )
))
public final class LanguageFile extends BukkitLinkedManaged {

    @Instance
    public final LanguageFile.Errors errors = new LanguageFile.Errors();

    @Instance
    public LanguageFile.General generals = new LanguageFile.General();

    @Value
    public Replaceable<List<String>> help_messages = this.match(s ->
        Optional.of(
            Replaceable.of(
                "&a====== %prefix% &a======",
                "&7/fakeplayer &r> &eShows help message.",
                "&7/fakeplayer help &r> &eShows help message.",
                "&7/fakeplayer reload &r> &eReloads the plugin.",
                "&7/fakeplayer version &r> &eChecks for update.",
                "&7/fakeplayer menu &r> &eShows the main menu."
            )
                .map(ColorUtil::colored)
                .replace(this.getPrefix())
        )
    );

    public LanguageFile(@NotNull final ConfigFile configFile) {
        super(() -> configFile.plugin_language, MapEntry.from("config", configFile));
    }

    @NotNull
    public Map<String, Supplier<String>> getPrefix() {
        final Map<String, Supplier<String>> map = new HashMap<>();
        this.pull("config").ifPresent(o ->
            map.put("%prefix%", () -> ((ConfigFile) o).plugin_prefix.build())
        );
        return map;
    }

    @Section(path = "errors")
    public final class Errors extends BukkitSection {

        @Value
        public Replaceable<String> there_is_already = LanguageFile.this.match(s ->
            Optional.of(
                Replaceable.of("%prefix% &cThere is already fake player such that name (%name%).")
                    .map(ColorUtil::colored)
                    .replaces("%name%")
                    .replace(LanguageFile.this.getPrefix())
            )
        );

    }

    @Section(path = "general")
    public final class General extends BukkitSection {

        @Value
        public Replaceable<String> join_message = LanguageFile.this.match(s ->
            Optional.of(
                Replaceable.of("%prefix% &a%player_name% just joined the server!")
                    .map(ColorUtil::colored)
                    .replaces("%player_name%")
                    .replace(LanguageFile.this.getPrefix())
            )
        );

        @Value
        public Replaceable<String> quit_message = LanguageFile.this.match(s ->
            Optional.of(
                Replaceable.of("%prefix% &a%player_name% just quit the server!")
                    .map(ColorUtil::colored)
                    .replaces("%player_name%")
                    .replace(LanguageFile.this.getPrefix())
            )
        );

        @Value
        public Replaceable<String> reload_complete = LanguageFile.this.match(s ->
            Optional.of(
                Replaceable.of("%prefix% &aReload complete! &7Took (%ms%ms)")
                    .map(ColorUtil::colored)
                    .replace(LanguageFile.this.getPrefix())
                    .replaces("%ms%")
            )
        );

        @Value
        public Replaceable<String> new_version_found = LanguageFile.this.match(s ->
            Optional.of(
                Replaceable.of("%prefix% &eNew version found (v%version%)")
                    .map(ColorUtil::colored)
                    .replaces("%version%")
                    .replace(LanguageFile.this.getPrefix())
            )
        );

        @Value
        public Replaceable<String> latest_version = LanguageFile.this.match(s ->
            Optional.of(
                Replaceable.of("%prefix% &aYou're using the latest version (v%version%)")
                    .map(ColorUtil::colored)
                    .replaces("%version%")
                    .replace(LanguageFile.this.getPrefix())
            )
        );

        @Value
        public Replaceable<String> fake_player_added = LanguageFile.this.match(s ->
            Optional.of(
                Replaceable.of("%prefix% &aFake player added (%name%)")
                    .map(ColorUtil::colored)
                    .replaces("%name%")
                    .replace(LanguageFile.this.getPrefix())
            )
        );

    }

}
