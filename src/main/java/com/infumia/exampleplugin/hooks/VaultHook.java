package com.infumia.exampleplugin.hooks;

import com.infumia.exampleplugin.Hook;
import com.infumia.exampleplugin.Wrapped;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;

public final class VaultHook implements Hook {

    private Economy economy;

    @Override
    public boolean initiate() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        final RegisteredServiceProvider<Economy> economyProvider =
            Bukkit.getServicesManager().getRegistration(Economy.class);

        if (economyProvider != null) {
            this.economy = economyProvider.getProvider();
        }

        return this.economy != null;
    }

    @NotNull
    @Override
    public Wrapped create() {
        if (this.economy == null) {
            throw new IllegalStateException("Vault not initiated! Use VaultHook#initiate() method.");
        }

        return new VaultWrapper(this.economy);
    }

}
