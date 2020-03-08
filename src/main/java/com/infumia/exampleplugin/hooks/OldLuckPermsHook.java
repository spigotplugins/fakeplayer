package com.infumia.exampleplugin.hooks;

import com.infumia.exampleplugin.Hook;
import com.infumia.exampleplugin.Wrapped;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class OldLuckPermsHook implements Hook {

    @Nullable
    private LuckPermsApi luckPermsApi;

    @Override
    public boolean initiate() {
        try {
            Class.forName("me.lucko.luckperms.api.LuckPermsApi");
        } catch (final Exception exception) {
            return false;
        }

        final boolean check = Bukkit.getPluginManager().getPlugin("LuckPerms") != null;

        if (check) {
            this.luckPermsApi = LuckPerms.getApi();
        }

        return this.luckPermsApi != null;
    }

    @Override
    @NotNull
    public Wrapped create() {
        if (this.luckPermsApi == null) {
            throw new IllegalStateException("LuckPerms not initiated! Use OldLuckPermsHook#initiate method.");
        }

        return new OldLuckPermsWrapper(this.luckPermsApi);
    }

}
