package com.infumia.exampleplugin;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.ConditionFailedException;
import com.infumia.exampleplugin.commands.ExamplePluginCommand;
import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.dependency.Dependency;
import org.bukkit.plugin.java.annotation.dependency.DependsOn;
import org.bukkit.plugin.java.annotation.permission.ChildPermission;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.bukkit.plugin.java.annotation.permission.Permissions;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import org.bukkit.plugin.java.annotation.plugin.author.Authors;
import org.jetbrains.annotations.NotNull;

// TODO Change the values as you want.
@Plugin(
    name = "ExamplePlugin",
    version = "1.0"
)
@Description("Example plugin for Bukkit servers")
@ApiVersion(ApiVersion.Target.v1_13)
@Authors(@Author("portlek"))
@Permissions({
    @Permission(
        name = "exampleplugin.*",
        desc = "Allows you to access to the all plugin.",
        defaultValue = PermissionDefault.OP,
        children = {
            @ChildPermission(name = "exampleplugin.version"),
            @ChildPermission(name = "exampleplugin.command.*")
        }
    ),
    @Permission(
        name = "exampleplugin.command.*",
        desc = "It allows you to use to all commands of the plugin.",
        defaultValue = PermissionDefault.OP,
        children = {
            @ChildPermission(name = "exampleplugin.command.main"),
            @ChildPermission(name = "exampleplugin.command.help"),
            @ChildPermission(name = "exampleplugin.command.reload"),
            @ChildPermission(name = "exampleplugin.command.version")
        }
    ),
    @Permission(
        name = "exampleplugin.version",
        desc = "It allows you to notify if there is new update for the plugin on join.",
        defaultValue = PermissionDefault.OP
    ),
    @Permission(
        name = "exampleplugin.command.main",
        desc = "It allows you to use to /exampleplugin command.",
        defaultValue = PermissionDefault.TRUE
    ),
    @Permission(
        name = "exampleplugin.command.help",
        desc = "It allows you to use to /exampleplugin help command.",
        defaultValue = PermissionDefault.TRUE
    ),
    @Permission(
        name = "exampleplugin.command.reload",
        desc = "It allows you to use to /exampleplugin reload command.",
        defaultValue = PermissionDefault.OP
    ),
    @Permission(
        name = "exampleplugin.command.version",
        desc = "It allows you to use to /exampleplugin version command.",
        defaultValue = PermissionDefault.OP
    )
})
@DependsOn({
    @Dependency("Vault"),
    @Dependency("PlaceholderAPI"),
    @Dependency("PermissionsEx"),
    @Dependency("LuckPerms"),
    @Dependency("GroupManager")
})
// TODO Change the class name as you want.
public final class ExamplePlugin extends JavaPlugin {

    private static ExamplePluginAPI api;

    private static ExamplePlugin instance;

    @NotNull
    public static ExamplePlugin getInstance() {
        if (ExamplePlugin.instance == null) {
            // TODO Change the ExamplePlugin case as you want.
            throw new IllegalStateException("You cannot be used ExamplePlugin plugin before its start!");
        }

        return ExamplePlugin.instance;
    }

    @NotNull
    public static ExamplePluginAPI getAPI() {
        if (ExamplePlugin.api == null) {
            // TODO Change the ExamplePlugin case as you want.
            throw new IllegalStateException("You cannot be used ExamplePlugin plugin before its start!");
        }

        return ExamplePlugin.api;
    }

    @Override
    public void onLoad() {
        ExamplePlugin.instance = this;
    }

    @Override
    public void onDisable() {
        if (ExamplePlugin.api != null) {
            ExamplePlugin.api.disablePlugin();
        }
    }

    @Override
    public void onEnable() {
        final BukkitCommandManager manager = new BukkitCommandManager(this);
        ExamplePlugin.api = new ExamplePluginAPI(this);

        this.getServer().getScheduler().runTask(this, () ->
            this.getServer().getScheduler().runTaskAsynchronously(this, () ->
                ExamplePlugin.api.reloadPlugin(true)
            )
        );
        manager.getCommandConditions().addCondition(String[].class, "player", (c, exec, value) -> {
            if (value == null || value.length == 0) {
                return;
            }

            final String playerName = value[c.getConfigValue("arg", 0)];

            if (c.hasConfig("arg") && Bukkit.getPlayer(playerName) == null) {
                throw new ConditionFailedException(
                    ExamplePlugin.api.languageFile.errors.player_not_found.build("%player_name%", () -> playerName)
                );
            }
        });
        manager.registerCommand(
            new ExamplePluginCommand(ExamplePlugin.api)
        );
    }

}
