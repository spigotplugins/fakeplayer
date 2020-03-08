package com.infumia.exampleplugin.file;

import com.infumia.exampleplugin.ExamplePlugin;
import com.infumia.exampleplugin.Hook;
import com.infumia.exampleplugin.Wrapped;
import com.infumia.exampleplugin.hooks.*;
import io.github.portlek.configs.BukkitManaged;
import io.github.portlek.configs.annotations.Config;
import io.github.portlek.configs.annotations.Instance;
import io.github.portlek.configs.annotations.Section;
import io.github.portlek.configs.annotations.Value;
import io.github.portlek.configs.util.ColorUtil;
import io.github.portlek.configs.util.Replaceable;
import io.github.portlek.database.Database;
import io.github.portlek.database.SQL;
import io.github.portlek.database.database.MySQL;
import io.github.portlek.database.database.SQLite;
import io.github.portlek.database.sql.SQLBasic;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

@Config(
    name = "config",
    // TODO: Change the plugin data folder as you want.
    location = "%basedir%/ExamplePlugin"
)
public final class ConfigFile extends BukkitManaged {

    private final Map<String, Wrapped> wrapped = new HashMap<>();

    // TODO: Change the plugin prefix as you want.
    @Value
    public Replaceable<String> plugin_prefix = Replaceable.of("&6[&eExamplePlugin&6]")
        .map(ColorUtil::colored);

    @Value
    public String plugin_language = "en";

    @Value
    public boolean check_for_update = true;

    @Override
    public void load() {
        super.load();
        this.loadWrapped();
        this.setAutoSave(true);
    }

    @Instance
    public final ConfigFile.Saving saving = new ConfigFile.Saving();

    @Section(path = "saving")
    public static class Saving {

        @Value
        public boolean save_when_plugin_disable = true;

        @Value
        public boolean auto_save = true;

        @Value
        public int auto_save_time = 60;

        @Value
        private final String storage_type = "sqlite";

        @Instance
        public final ConfigFile.Saving.MySQL mysql = new ConfigFile.Saving.MySQL();

        @Section(path = "mysql")
        public static class MySQL {

            @Value
            private final String host = "localhost";

            @Value
            private final int port = 3306;

            @Value
            private final String database = "database";

            @Value
            private final String username = "username";

            @Value
            private final String password = "password";

        }

    }

    @Instance
    public final ConfigFile.Hooks hooks = new ConfigFile.Hooks();

    @Section(path = "hooks")
    public static class Hooks {

        @Value
        public boolean auto_detect = true;

        @Value
        private boolean PlaceholderAPI;

        @Value
        private boolean GroupManager;

        @Value
        private boolean LuckPerms;

        @Value
        private boolean PermissionsEX;

        @Value
        private boolean Vault;

    }

    @NotNull
    public SQL createSQL() {
        final Database database;

        if (this.isMySQL()) {
            database = new MySQL(
                this.saving.mysql.host,
                this.saving.mysql.port,
                this.saving.mysql.database,
                this.saving.mysql.username,
                this.saving.mysql.password
            );
        } else {
            database = new SQLite(ExamplePlugin.getInstance(), "exampleplugin.db");
        }

        return new SQLBasic(
            database
        );
    }

    private boolean isMySQL() {
        return this.saving.storage_type.equalsIgnoreCase("mysql") ||
            this.saving.storage_type.equalsIgnoreCase("remote") ||
            this.saving.storage_type.equalsIgnoreCase("net");
    }

    private static final String HOOKS_PATH = "hooks.";
    private static final String PLACEHOLDER_API = "PlaceholderAPI";
    private static final String GROUP_MANAGER = "GroupManager";
    private static final String LUCK_PERMS = "LuckPerms";
    private static final String PERMISSIONS_EX = "PermissionsEx";
    private static final String VAULT = "Vault";

    @NotNull
    @SuppressWarnings("unchecked")
    public <T extends Wrapped> Optional<T> getWrapped(@NotNull final String wrappedId) {
        return Optional.ofNullable(
            (T) this.wrapped.get(wrappedId)
        );
    }

    private void loadWrapped() {
        this.hookWithTry("net.luckperms.api.LuckPerms", this.hooks.LuckPerms, new NewLuckPermsHook(), ConfigFile.LUCK_PERMS,
            () -> {
                this.hooks.LuckPerms = true;
                this.set(ConfigFile.HOOKS_PATH + ConfigFile.LUCK_PERMS, true);
            },
            () -> {
                this.hooks.LuckPerms = false;
                this.set(ConfigFile.HOOKS_PATH + ConfigFile.LUCK_PERMS, false);
            });
        this.hookWithTry("me.lucko.luckperms.common.plugin.LuckPermsPlugin", this.hooks.LuckPerms, new OldLuckPermsHook(),
            ConfigFile.LUCK_PERMS,
            () -> {
                this.hooks.LuckPerms = true;
                this.set(ConfigFile.HOOKS_PATH + ConfigFile.LUCK_PERMS, true);
            },
            () -> {
                this.hooks.LuckPerms = false;
                this.set(ConfigFile.HOOKS_PATH + ConfigFile.LUCK_PERMS, false);
            });

        if (!this.wrapped.containsKey(ConfigFile.LUCK_PERMS)) {
            this.hooks.LuckPerms = false;
        }

        this.hookLittle(this.hooks.PlaceholderAPI, new PlaceholderAPIHook(), ConfigFile.PLACEHOLDER_API,
            () -> {
                this.hooks.PlaceholderAPI = true;
                this.set(ConfigFile.HOOKS_PATH + ConfigFile.PLACEHOLDER_API, true);
            },
            () -> {
                this.hooks.PlaceholderAPI = false;
                this.set(ConfigFile.HOOKS_PATH + ConfigFile.PLACEHOLDER_API, false);
            });
        this.hookLittle(this.hooks.GroupManager, new GroupManagerHook(), ConfigFile.GROUP_MANAGER, () ->
                !this.wrapped.containsKey(ConfigFile.PERMISSIONS_EX) && !this.wrapped.containsKey(ConfigFile.LUCK_PERMS),
            () -> {
                this.hooks.GroupManager = true;
                this.set(ConfigFile.HOOKS_PATH + ConfigFile.GROUP_MANAGER, true);
            },
            () -> {
                this.hooks.GroupManager = false;
                this.set(ConfigFile.HOOKS_PATH + ConfigFile.GROUP_MANAGER, false);
            });
        this.hookLittle(this.hooks.PermissionsEX, new PermissionsExHook(), ConfigFile.PERMISSIONS_EX, () ->
                !this.wrapped.containsKey(ConfigFile.GROUP_MANAGER) && !this.wrapped.containsKey(ConfigFile.LUCK_PERMS),
            () -> {
                this.hooks.PermissionsEX = true;
                this.set(ConfigFile.HOOKS_PATH + ConfigFile.PERMISSIONS_EX, true);
            },
            () -> {
                this.hooks.PermissionsEX = false;
                this.set(ConfigFile.HOOKS_PATH + ConfigFile.PERMISSIONS_EX, false);
            });
        this.hookLittle(this.hooks.Vault, new VaultHook(), ConfigFile.VAULT,
            () -> {
                this.hooks.Vault = true;
                this.set(ConfigFile.HOOKS_PATH + ConfigFile.VAULT, true);
            },
            () -> {
                this.hooks.Vault = false;
                this.set(ConfigFile.HOOKS_PATH + ConfigFile.VAULT, true);
            });
    }

    private void hookWithTry(@NotNull final String className, final boolean force, @NotNull final Hook hook, @NotNull final String path,
                             @NotNull final Runnable succeed, @NotNull final Runnable failed) {
        try {
            Class.forName(className);
            this.hookLittle(
                force,
                hook,
                path,
                succeed,
                failed
            );
        } catch (final Exception ignored) {
            // ignored
        }
    }

    private void hookLittle(final boolean force, @NotNull final Hook hook, @NotNull final String path, @NotNull final Runnable succeed,
                            @NotNull final Runnable failed) {
        this.hookLittle(force, hook, path, () -> true, succeed, failed);
    }

    private void hookLittle(final boolean force, @NotNull final Hook hook, @NotNull final String path,
                            @NotNull final BooleanSupplier supplier, @NotNull final Runnable succeed, @NotNull final Runnable failed) {
        if ((this.hooks.auto_detect || force) && hook.initiate() && supplier.getAsBoolean()) {
            this.wrapped.put(path, hook.create());
            this.sendHookNotify(path);
            this.set(ConfigFile.HOOKS_PATH + path, true);
            succeed.run();
        } else {
            this.set(ConfigFile.HOOKS_PATH + path, false);
            failed.run();
        }
    }

    private void sendHookNotify(@NotNull final String path) {
        Bukkit.getConsoleSender().sendMessage(
            // TODO Change the message as you want.
            ColorUtil.colored(
                this.plugin_prefix.build() + " &r>> &a" + path + " is hooking"
            )
        );
    }

}
