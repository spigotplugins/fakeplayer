package com.infumia.fakeplayer.file.provider;

import com.infumia.fakeplayer.FakePlayer;
import com.infumia.fakeplayer.util.FileElement;
import com.infumia.fakeplayer.util.Placeholder;
import io.github.portlek.configs.util.MapEntry;
import io.github.portlek.smartinventory.*;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class ListMenuProvider implements InventoryProvided {

    private static final Icon[] CLICKABLE_ITEMS = new Icon[0];

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
    public void init(@NotNull final InventoryContents contents) {
        final Player player = contents.player();
        final List<Icon> items = new ArrayList<>();
        FakePlayer.getAPI().fakesFile.fakeplayers.values().forEach(fake ->
            items.add(this.fakePlayer
                .replace(true, true, new Placeholder("%player_name%", fake.getName()))
                .clickableItem(event -> {
                    event.cancel();
                    final ClickType type = event.click();
                    if (type.isRightClick()) {
                        FakePlayer.getAPI().fakesFile.remove(fake.getName());
                        FakePlayer.getAPI().menuFile.fakePlayers.inventory()
                            .open(player);
                    } else if (type.isLeftClick()) {
                        player.teleport(fake.getSpawnPoint());
                        player.closeInventory();
                    } else if (type == ClickType.MIDDLE) {
                        fake.toggleVisible();
                    }
                })));
        final Pagination pagination = contents.pagination();
        pagination.setItems(items.toArray(ListMenuProvider.CLICKABLE_ITEMS));
        pagination.setItemsPerPage(36);
        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 0, 0));
        this.next.insert(contents, event ->
            contents.page().open(player, pagination.next().getPage()));
        this.previous.insert(contents, event ->
            contents.page().open(player, pagination.previous().getPage()));
        this.add.insert(contents, event -> {
            event.cancel();
            new AnvilGUI.Builder()
                .onComplete((clicker, s) -> {
                    if (FakePlayer.getAPI().fakesFile.fakeplayers.containsKey(s)) {
                        clicker.sendMessage(FakePlayer.getAPI().languageFile.errors.there_is_already
                            .build(MapEntry.from("%name%", () -> s)));
                        return AnvilGUI.Response.close();
                    }
                    FakePlayer.getAPI().fakesFile.addFakes(s, clicker.getLocation());
                    clicker.sendMessage(FakePlayer.getAPI().languageFile.generals.fake_player_added
                        .build(MapEntry.from("%name%", () -> s)));
                    return AnvilGUI.Response.close();
                })
                .text("Type...")
                .plugin(FakePlayer.getInstance())
                .open(player);
        });
    }

}
