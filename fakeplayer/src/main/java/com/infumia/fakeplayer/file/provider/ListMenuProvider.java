package com.infumia.fakeplayer.file.provider;

import com.infumia.fakeplayer.FakePlayer;
import com.infumia.fakeplayer.util.FileElement;
import com.infumia.fakeplayer.util.Placeholder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import io.github.portlek.configs.util.MapEntry;
import java.util.ArrayList;
import java.util.List;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;

public final class ListMenuProvider implements InventoryProvider {

    private static final ClickableItem[] CLICKABLE_ITEMS = new ClickableItem[0];

    @NotNull
    private final FileElement fakePlayer;

    @NotNull
    private final FileElement add;

    @NotNull
    private final FileElement next;

    @NotNull
    private final FileElement previous;

    public ListMenuProvider(@NotNull final FileElement fakePlayer, @NotNull final FileElement add,
                            @NotNull final FileElement next, @NotNull final FileElement previous) {
        this.fakePlayer = fakePlayer;
        this.add = add;
        this.next = next;
        this.previous = previous;
    }

    @Override
    public void init(@NotNull final Player player, @NotNull final InventoryContents contents) {
        final List<ClickableItem> items = new ArrayList<>();
        FakePlayer.getAPI().fakesFile.fakeplayers.values().forEach(fake ->
            items.add(this.fakePlayer
                .replace(true, true, new Placeholder("%player_name%", fake.getName()))
                .clickableItem(event -> {
                    event.setCancelled(true);
                    final ClickType type = event.getClick();
                    if (type.isRightClick()) {
                        FakePlayer.getAPI().fakesFile.remove(fake.getName());
                        FakePlayer.getAPI().menuFile.fakePlayers.inventory()
                            .open((Player) event.getWhoClicked());
                    } else if (type.isLeftClick()) {
                        event.getWhoClicked().teleport(fake.getSpawnPoint());
                        event.getWhoClicked().closeInventory();
                    }
                }))
        );
        final Pagination pagination = contents.pagination();
        pagination.setItems(items.toArray(ListMenuProvider.CLICKABLE_ITEMS));
        pagination.setItemsPerPage(36);
        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 0, 0));
        this.next.insert(contents, event ->
            contents.inventory().open((Player) event.getWhoClicked(), pagination.next().getPage())
        );
        this.previous.insert(contents, event ->
            contents.inventory().open((Player) event.getWhoClicked(), pagination.previous().getPage())
        );
        this.add.insert(contents, event -> {
            event.setCancelled(true);
            new AnvilGUI.Builder()
                .onComplete((clicker, s) -> {
                    if (FakePlayer.getAPI().fakesFile.fakeplayers.containsKey(s)) {
                        clicker.sendMessage(
                            FakePlayer.getAPI().languageFile.errors.there_is_already
                                .build(MapEntry.of("%name%", () -> s))
                        );
                        return AnvilGUI.Response.close();
                    }
                    FakePlayer.getAPI().fakesFile.addFakes(s, clicker.getLocation());
                    clicker.sendMessage(
                        FakePlayer.getAPI().languageFile.generals.fake_player_added
                            .build(MapEntry.of("%name%", () -> s))
                    );
                    return AnvilGUI.Response.close();
                })
                .text("Type...")
                .plugin(FakePlayer.getInstance())
                .open((Player) event.getWhoClicked());
        });
    }

}
