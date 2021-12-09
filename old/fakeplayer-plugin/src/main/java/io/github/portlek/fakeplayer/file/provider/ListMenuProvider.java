package io.github.portlek.fakeplayer.file.provider;

import io.github.portlek.fakeplayer.FakePlayer;
import io.github.portlek.fakeplayer.util.FileElement;
import io.github.portlek.fakeplayer.util.Placeholder;
import io.github.portlek.smartinventory.*;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;

public final class ListMenuProvider implements InventoryProvider {

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
    pagination.setIcons(items.toArray(ListMenuProvider.CLICKABLE_ITEMS));
    pagination.setIconsPerPage(36);
    pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 0, 0));
    this.next.insert(contents, event ->
      contents.page().open(player, pagination.next().getPage()));
    this.previous.insert(contents, event ->
      contents.page().open(player, pagination.previous().getPage()));
    this.add.insert(contents, event -> {
      event.cancel();
      FakePlayer.getAPI().menuFile.fakePlayers.openAnvil(event.contents().player());
    });
  }
}
