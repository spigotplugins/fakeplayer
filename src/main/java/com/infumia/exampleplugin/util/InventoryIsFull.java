package com.infumia.exampleplugin.util;

import java.util.Map;
import java.util.function.Predicate;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class InventoryIsFull implements Predicate<ItemStack> {

    @NotNull
    private final Player player;

    /**
     * ctor.
     *
     * @param plyr the player to check
     */
    public InventoryIsFull(@NotNull final Player plyr) {
        this.player = plyr;
    }

    /**
     * checks if player has enough space for the itemStack
     *
     * @param item the item to check
     * @return returns true if player has not enough space for the itemStack.
     */
    @Override
    public boolean test(@NotNull final ItemStack item) {
        if (item.getType() == Material.AIR) {
            return false;
        }
        if (item.getAmount() > 5000) {
            return true;
        }
        if (this.player.getInventory().firstEmpty() >= 0 && item.getAmount() <= item.getMaxStackSize()) {
            return false;
        }
        if (item.getAmount() > item.getMaxStackSize()) {
            final ItemStack clone = item.clone();
            clone.setAmount(item.getMaxStackSize());
            if (this.test(clone)) {
                return true;
            }
            clone.setAmount(item.getAmount() - item.getMaxStackSize());
            return this.test(clone);
        }
        final Map<Integer, ? extends ItemStack> all = this.player.getInventory().all(item);
        int amount = item.getAmount();
        for (final ItemStack element : all.values()) {
            amount -= element.getMaxStackSize() - element.getAmount();
        }
        return amount > 0;
    }

}
