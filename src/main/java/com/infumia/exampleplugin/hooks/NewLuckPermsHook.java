package com.infumia.exampleplugin.hooks;

import com.infumia.exampleplugin.Hook;
import com.infumia.exampleplugin.Wrapped;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;

public final class NewLuckPermsHook implements Hook {

    private LuckPerms luckPerms;

    @Override
    public boolean initiate() {
        try {
            Class.forName("net.luckperms.api.LuckPerms");
        } catch (final Exception exception) {
            return false;
        }

        final boolean check = Bukkit.getPluginManager().getPlugin("LuckPerms") != null;

        if (check) {
            final RegisteredServiceProvider<LuckPerms> provider =
                Bukkit.getServicesManager().getRegistration(LuckPerms.class);
            if (provider != null) {
                this.luckPerms = provider.getProvider();
            }
        }

        return this.luckPerms != null;
    }

    @Override
    @NotNull
    public Wrapped create() {
        if (this.luckPerms == null) {
            throw new IllegalStateException("LuckPerms not initiated! Use NewLuckPermsHook#initiate method.");
        }

        return new NewLuckPermsWrapper(this.luckPerms);
    }

}
