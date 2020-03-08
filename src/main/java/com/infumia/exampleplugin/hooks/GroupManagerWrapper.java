package com.infumia.exampleplugin.hooks;

import com.infumia.exampleplugin.Wrapped;
import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class GroupManagerWrapper implements Wrapped {

    @NotNull
    private final GroupManager groupManager;

    public GroupManagerWrapper(@NotNull final GroupManager groupManager) {
        this.groupManager = groupManager;
    }

    @NotNull
    public String getGroup(@NotNull final Player player) {
        final AnjoPermissionsHandler handler = this.groupManager.getWorldsHolder().getWorldPermissions(player);

        if (handler == null) {
            return "";
        }

        return handler.getGroup(player.getName());
    }

    // TODO Add new GroupManager methods as you want.

}
