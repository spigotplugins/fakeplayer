package com.infumia.exampleplugin.hooks;

import com.infumia.exampleplugin.Wrapped;
import java.util.List;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public final class PermissionsExWrapper implements Wrapped {

    @NotNull
    private final PermissionsEx permissionsEx;

    public PermissionsExWrapper(@NotNull final PermissionsEx permissionsEx) {
        this.permissionsEx = permissionsEx;
    }

    @NotNull
    public List<PermissionGroup> getGroup(@NotNull final Player player) {
        return this.permissionsEx.getPermissionsManager().getUser(player).getParents();
    }

    // TODO Add new PermissionsEx methods as you want.

}
