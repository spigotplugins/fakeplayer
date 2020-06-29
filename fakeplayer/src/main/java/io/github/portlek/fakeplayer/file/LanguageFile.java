package io.github.portlek.fakeplayer.file;

import io.github.portlek.configs.annotations.*;
import io.github.portlek.configs.bukkit.BukkitLinkedManaged;
import io.github.portlek.configs.bukkit.BukkitSection;
import io.github.portlek.configs.bukkit.util.ColorUtil;
import io.github.portlek.configs.replaceable.Replaceable;
import io.github.portlek.configs.replaceable.ReplaceableString;
import io.github.portlek.configs.util.MapEntry;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

@LinkedConfig(@LinkedFile(
    key = "en",
    config = @Config(
        value = "en",
        location = "%basedir%/FakePlayer/languages"
    )
))
public final class LanguageFile extends BukkitLinkedManaged {

    @Instance
    public final LanguageFile.Errors errors = new LanguageFile.Errors();

    @Instance
    public LanguageFile.General generals = new LanguageFile.General();

    @Property
    public ReplaceableString help_messages = this.match(s ->
        Optional.of(
            Replaceable.from(
                new StringBuilder()
                    .append("&a====== %prefix% &a======")
                    .append('\n')
                    .append("&7/fakeplayer &r> &eShows help message.")
                    .append('\n')
                    .append("&7/fakeplayer help &r> &eShows help message.")
                    .append('\n')
                    .append("&7/fakeplayer reload &r> &eReloads the plugin.")
                    .append('\n')
                    .append("&7/fakeplayer version &r> &eChecks for update.")
                    .append('\n')
                    .append("&7/fakeplayer menu &r> &eShows the main menu."))
                .map(ColorUtil::colored)
                .replace(this.getPrefix())
        )
    );

    public LanguageFile(@NotNull final ConfigFile configFile) {
        super(() -> configFile.plugin_language, MapEntry.from("config", configFile));
    }

    @NotNull
    public Map.Entry<String, Supplier<String>> getPrefix() {
        return MapEntry.from("%prefix%", () -> this.getConfig().plugin_prefix.build());
    }

    @NotNull
    private ConfigFile getConfig() {
        return (ConfigFile) this.object("config").orElseThrow(() ->
            new IllegalStateException("Config couldn't put into the objects!"));
    }

    @Section("errors")
    public final class Errors extends BukkitSection {

        @Property
        public ReplaceableString there_is_already = LanguageFile.this.match(s ->
            Optional.of(
                Replaceable.from("%prefix% &cThere is already fake player such that name (%name%).")
                    .map(ColorUtil::colored)
                    .replaces("%name%")
                    .replace(LanguageFile.this.getPrefix())
            )
        );

    }

    @Section("general")
    public final class General extends BukkitSection {

        @Property
        public ReplaceableString join_message = LanguageFile.this.match(s ->
            Optional.of(
                Replaceable.from("%prefix% &a%player_name% just joined the server!")
                    .map(ColorUtil::colored)
                    .replaces("%player_name%")
                    .replace(LanguageFile.this.getPrefix())
            )
        );

        @Property
        public ReplaceableString quit_message = LanguageFile.this.match(s ->
            Optional.of(
                Replaceable.from("%prefix% &a%player_name% just quit the server!")
                    .map(ColorUtil::colored)
                    .replaces("%player_name%")
                    .replace(LanguageFile.this.getPrefix())
            )
        );

        @Property
        public ReplaceableString reload_complete = LanguageFile.this.match(s ->
            Optional.of(
                Replaceable.from("%prefix% &aReload complete! &7Took (%ms%ms)")
                    .map(ColorUtil::colored)
                    .replace(LanguageFile.this.getPrefix())
                    .replaces("%ms%")
            )
        );

        @Property
        public ReplaceableString new_version_found = LanguageFile.this.match(s ->
            Optional.of(
                Replaceable.from("%prefix% &eNew version found (v%version%)")
                    .map(ColorUtil::colored)
                    .replaces("%version%")
                    .replace(LanguageFile.this.getPrefix())
            )
        );

        @Property
        public ReplaceableString latest_version = LanguageFile.this.match(s ->
            Optional.of(
                Replaceable.from("%prefix% &aYou're using the latest version (v%version%)")
                    .map(ColorUtil::colored)
                    .replaces("%version%")
                    .replace(LanguageFile.this.getPrefix())
            )
        );

        @Property
        public ReplaceableString fake_player_added = LanguageFile.this.match(s ->
            Optional.of(
                Replaceable.from("%prefix% &aFake player added (%name%)")
                    .map(ColorUtil::colored)
                    .replaces("%name%")
                    .replace(LanguageFile.this.getPrefix())
            )
        );

    }

}
