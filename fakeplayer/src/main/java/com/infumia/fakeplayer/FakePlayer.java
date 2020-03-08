package com.infumia.fakeplayer;

import co.aikar.commands.BukkitCommandManager;
import com.infumia.fakeplayer.commands.FakePlayerCommand;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.permission.ChildPermission;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.bukkit.plugin.java.annotation.permission.Permissions;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import org.bukkit.plugin.java.annotation.plugin.author.Authors;
import org.jetbrains.annotations.NotNull;

@Plugin(
    name = "FakePlayer",
    version = "1.2"
)
@Description("FakePlayer plugin for Bukkit servers")
@ApiVersion(ApiVersion.Target.v1_13)
@Authors(@Author("portlek"))
@Permissions({
    @Permission(
        name = "fakeplayer.*",
        desc = "Allows you to access to the all plugin.",
        defaultValue = PermissionDefault.OP,
        children = {
            @ChildPermission(name = "fakeplayer.version"),
            @ChildPermission(name = "fakeplayer.command.*")
        }
    ),
    @Permission(
        name = "fakeplayercommand.*",
        desc = "It allows you to use to all commands of the plugin.",
        defaultValue = PermissionDefault.OP,
        children = {
            @ChildPermission(name = "fakeplayer.command.main"),
            @ChildPermission(name = "fakeplayer.command.help"),
            @ChildPermission(name = "fakeplayer.command.reload"),
            @ChildPermission(name = "fakeplayer.command.version")
        }
    ),
    @Permission(
        name = "fakeplayer.version",
        desc = "It allows you to notify if there is new update for the plugin on join.",
        defaultValue = PermissionDefault.OP
    ),
    @Permission(
        name = "fakeplayer.command.main",
        desc = "It allows you to use to /fakeplayer command.",
        defaultValue = PermissionDefault.TRUE
    ),
    @Permission(
        name = "fakeplayer.command.help",
        desc = "It allows you to use to /fakeplayer help command.",
        defaultValue = PermissionDefault.TRUE
    ),
    @Permission(
        name = "fakeplayer.command.reload",
        desc = "It allows you to use to /fakeplayer reload command.",
        defaultValue = PermissionDefault.OP
    ),
    @Permission(
        name = "fakeplayer.command.version",
        desc = "It allows you to use to /fakeplayer version command.",
        defaultValue = PermissionDefault.OP
    )
})
public final class FakePlayer extends JavaPlugin {

    private static FakePlayerAPI api;

    private static FakePlayer instance;

    @NotNull
    public static FakePlayer getInstance() {
        if (FakePlayer.instance == null) {
            throw new IllegalStateException("You cannot be used FakePlayer plugin before its start!");
        }

        return FakePlayer.instance;
    }

    @NotNull
    public static FakePlayerAPI getAPI() {
        if (FakePlayer.api == null) {
            throw new IllegalStateException("You cannot be used FakePlayer plugin before its start!");
        }

        return FakePlayer.api;
    }

    @Override
    public void onLoad() {
        FakePlayer.instance = this;
    }

    @Override
    public void onDisable() {
        if (FakePlayer.api != null) {
            FakePlayer.api.disablePlugin();
        }
    }

    @Override
    public void onEnable() {
        final BukkitCommandManager manager = new BukkitCommandManager(this);
        FakePlayer.api = new FakePlayerAPI(this);
        this.getServer().getScheduler().runTask(this, () ->
            this.getServer().getScheduler().runTaskAsynchronously(this, () ->
                FakePlayer.api.reloadPlugin(true)
            )
        );
        manager.registerCommand(
            new FakePlayerCommand(FakePlayer.api)
        );
    }

}
