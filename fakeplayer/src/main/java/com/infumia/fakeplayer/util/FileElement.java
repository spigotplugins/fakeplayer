package com.infumia.fakeplayer.util;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import io.github.portlek.configs.BukkitManaged;
import io.github.portlek.configs.Managed;
import io.github.portlek.configs.Provided;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public final class FileElement {

    @NotNull
    private final String id;

    @NotNull
    private final ItemStack itemStack;

    private final int row;

    private final int column;

    public FileElement(@NotNull final String id, @NotNull final ItemStack itemStack, final int row, final int column) {
        this.id = id;
        this.itemStack = itemStack;
        this.row = row;
        this.column = column;
    }

    public FileElement(@NotNull final FileElement fileElement) {
        this.id = fileElement.id;
        this.itemStack = fileElement.itemStack;
        this.row = fileElement.row;
        this.column = fileElement.column;
    }

    public void insert(@NotNull final InventoryContents contents, @NotNull final Consumer<InventoryClickEvent> consumer) {
        contents.set(this.row, this.column, this.clickableItem(consumer));
    }

    @NotNull
    public ClickableItem clickableItem(@NotNull final Consumer<InventoryClickEvent> consumer) {
        return ClickableItem.of(this.itemStack, consumer);
    }

    public void fill(@NotNull final InventoryContents contents) {
        this.fill(contents, event -> {
        });
    }

    public void fill(@NotNull final InventoryContents contents, @NotNull final Consumer<InventoryClickEvent> consumer) {
        contents.fill(this.clickableItem(consumer));
    }

    @NotNull
    public FileElement replace(final boolean name, final boolean lore, @NotNull final Placeholder... placeholders) {
        return this.replace(name, lore, Arrays.asList(placeholders));
    }

    @NotNull
    public FileElement replace(final boolean displayName, final boolean lore,
                               @NotNull final Iterable<Placeholder> placeholders) {
        final ItemStack clone = this.itemStack.clone();
        final ItemMeta itemMeta = clone.getItemMeta();
        if (itemMeta == null) {
            return this;
        }
        if (displayName && itemMeta.hasDisplayName()) {
            for (final Placeholder placeholder : placeholders) {
                itemMeta.setDisplayName(placeholder.replace(itemMeta.getDisplayName()));
            }
        }
        if (lore && itemMeta.getLore() != null && itemMeta.hasLore()) {
            final List<String> finalLore = new ArrayList<>();
            itemMeta.getLore().forEach(s -> {
                final AtomicReference<String> finalString = new AtomicReference<>(s);
                placeholders.forEach(placeholder ->
                    finalString.set(placeholder.replace(finalString.get()))
                );
                finalLore.add(finalString.get());
            });
            itemMeta.setLore(finalLore);
        }
        clone.setItemMeta(itemMeta);
        return new FileElement(this.id, clone, this.row, this.column);
    }

    @NotNull
    public FileElement replace(@NotNull final Material material) {
        final ItemStack clone = this.itemStack.clone();
        clone.setType(material);
        return new FileElement(this.id, clone, this.row, this.column);
    }

    @NotNull
    public String getId() {
        return this.id;
    }

    @NotNull
    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public static class Provider implements Provided<FileElement> {
        @Override
        public void set(@NotNull final Object o, @NotNull final Managed managed, @NotNull final String s) {
            final FileElement fileElement = (FileElement) o;
            final BukkitManaged bukkitManaged = (BukkitManaged) managed;

            bukkitManaged.set(s + ".row", fileElement.row);
            bukkitManaged.set(s + ".column", fileElement.column);
            bukkitManaged.setItemStack(s, fileElement.itemStack);
        }

        @NotNull
        @Override
        public Optional<FileElement> get(@NotNull final Managed managed, @NotNull final String s) {
            if (!s.contains("element") || !managed.getSection(s).isPresent()) {
                return Optional.empty();
            }
            final BukkitManaged bukkitManaged = (BukkitManaged) managed;
            final Optional<ItemStack> optional = bukkitManaged.getItemStack(s);
            return optional.map(stack ->
                new FileElement(
                    s,
                    stack,
                    bukkitManaged.getInt(s + ".row"),
                    bukkitManaged.getInt(s + ".column")
                )
            );
        }

    }

}