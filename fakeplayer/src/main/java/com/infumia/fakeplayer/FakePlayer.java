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

import java.util.Optional;

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
        Optional.ofNullable(FakePlayer.api).ifPresent(FakePlayerAPI::disablePlugin);
    }

    @Override
    public void onEnable() {
        final BukkitCommandManager manager = new BukkitCommandManager(this);
        FakePlayer.api = new FakePlayerAPI(this);
        this.getServer().getScheduler().runTask(this, () ->
            this.getServer().getScheduler().runTaskAsynchronously(this, () ->
                FakePlayer.api.reloadPlugin(true)));
        manager.registerCommand(new FakePlayerCommand());
    }

}
