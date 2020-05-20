package io.github.portlek.fakeplayer.util;

import io.github.portlek.configs.bukkit.BkktSection;
import io.github.portlek.configs.provided.Provided;
import io.github.portlek.configs.structure.managed.section.CfgSection;
import io.github.portlek.smartinventory.Icon;
import io.github.portlek.smartinventory.InventoryContents;
import io.github.portlek.smartinventory.event.abs.ClickEvent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public final class FileElement {

    @NotNull
    private final ItemStack itemStack;

    private final int row;

    private final int column;

    public FileElement(@NotNull final ItemStack itemStack, final int row, final int column) {
        this.itemStack = itemStack;
        this.row = row;
        this.column = column;
    }

    public FileElement(@NotNull final FileElement fileElement) {
        this.itemStack = fileElement.itemStack;
        this.row = fileElement.row;
        this.column = fileElement.column;
    }

    public void insert(@NotNull final InventoryContents contents, @NotNull final Consumer<ClickEvent> consumer) {
        contents.set(this.row, this.column, this.clickableItem(consumer));
    }

    @NotNull
    public Icon clickableItem(@NotNull final Consumer<ClickEvent> consumer) {
        return Icon.from(this.itemStack).whenclick(consumer);
    }

    public void fill(@NotNull final InventoryContents contents) {
        this.fill(contents, event -> {
        });
    }

    public void fill(@NotNull final InventoryContents contents, @NotNull final Consumer<ClickEvent> consumer) {
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
        return new FileElement(clone, this.row, this.column);
    }

    @NotNull
    public FileElement replace(@NotNull final Material material) {
        final ItemStack clone = this.itemStack.clone();
        clone.setType(material);
        return new FileElement(clone, this.row, this.column);
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
        public void set(@NotNull final FileElement fileElement, @NotNull final CfgSection section, @NotNull final String s) {
            section.set("row", fileElement.row);
            section.set("column", fileElement.column);
            ((BkktSection) section).setItemStack(s, fileElement.itemStack);
        }

        @NotNull
        @Override
        public Optional<FileElement> get(@NotNull final CfgSection section, @NotNull final String s) {
            final Optional<ItemStack> itemStackOptional = ((BkktSection) section).getItemStack(s);
            final Optional<Integer> rowOptional = section.getInteger("row");
            final Optional<Integer> columnOptional = section.getInteger("column");
            if (!itemStackOptional.isPresent() || !rowOptional.isPresent() || !columnOptional.isPresent()) {
                return Optional.empty();
            }
            return Optional.of(
                new FileElement(itemStackOptional.get(), rowOptional.get(), columnOptional.get())
            );
        }

    }

}