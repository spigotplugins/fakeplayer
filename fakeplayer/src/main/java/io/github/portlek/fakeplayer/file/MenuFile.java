package io.github.portlek.fakeplayer.file;

import com.cryptomorin.xseries.XMaterial;
import io.github.portlek.bukkititembuilder.ItemStackBuilder;
import io.github.portlek.configs.annotations.Config;
import io.github.portlek.configs.annotations.Instance;
import io.github.portlek.configs.annotations.Property;
import io.github.portlek.configs.annotations.Section;
import io.github.portlek.configs.bukkit.BukkitManaged;
import io.github.portlek.configs.bukkit.BukkitSection;
import io.github.portlek.configs.bukkit.util.ColorUtil;
import io.github.portlek.configs.replaceable.Replaceable;
import io.github.portlek.configs.replaceable.ReplaceableString;
import io.github.portlek.configs.util.MapEntry;
import io.github.portlek.fakeplayer.FakePlayer;
import io.github.portlek.fakeplayer.file.provider.ListMenuProvider;
import io.github.portlek.fakeplayer.util.FileElement;
import io.github.portlek.smartinventory.Page;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Config(
    value = "menu",
    location = "%basedir%/FakePlayer"
)
public final class MenuFile extends BukkitManaged {

    @Instance
    public final MenuFile.FakePlayers fakePlayers = new MenuFile.FakePlayers();

    @Override
    public void onCreate() {
    }

    @Section("fake-players")
    public static final class FakePlayers extends BukkitSection {

        @Property
        public String type_fake_player = "Type fake player name";

        @Property
        public String put_less_than_16_charater = "Put less than 16 char!";

        @Property
        public ReplaceableString title = Replaceable.from("&eFake Players")
            .map(ColorUtil::colored);

        @Property
        public FileElement fake_player = new FileElement(
            ItemStackBuilder.from(XMaterial.PLAYER_HEAD)
                .name("&a%player_name%")
                .lore("",
                    "&7Right Click to delete this fake player.",
                    "&7Left Click to teleport this fake player.",
                    "&7Middle Click to toggle visible this fake player.")
                .itemStack(),
            0, 0);

        @Property
        public FileElement add = new FileElement(
            ItemStackBuilder.from(XMaterial.APPLE)
                .name("&aAdd Fake Player")
                .lore("", "&7Click and add fake player to your location.")
                .itemStack(),
            4, 4);

        @Property
        public FileElement next = new FileElement(
            ItemStackBuilder.from(XMaterial.ARROW)
                .name("&aNext")
                .lore("",
                    "&7Click and see the next page.")
                .itemStack(),
            5, 5);

        @Property
        public FileElement previous = new FileElement(
            ItemStackBuilder.from(XMaterial.ARROW)
                .name("&aPrevious")
                .lore("",
                    "&7Click and see the previous page.")
                .itemStack(),
            5, 3);

        public void openAnvil(@NotNull final Player player) {
            new AnvilGUI.Builder()
                .onComplete((clicker, s) -> {
                    if (FakePlayer.getAPI().fakesFile.fakeplayers.containsKey(s)) {
                        clicker.sendMessage(FakePlayer.getAPI().languageFile.errors.there_is_already
                            .build(MapEntry.from("%name%", () -> s)));
                        return AnvilGUI.Response.close();
                    }
                    if (s.trim().length() > 16) {
                        return AnvilGUI.Response.text(this.put_less_than_16_charater);
                    }
                    FakePlayer.getAPI().fakesFile.addFakes(s.trim(), clicker.getLocation());
                    clicker.sendMessage(FakePlayer.getAPI().languageFile.generals.fake_player_added
                        .build(MapEntry.from("%name%", () -> s)));
                    return AnvilGUI.Response.close();
                })
                .text(this.type_fake_player)
                .plugin(FakePlayer.getInstance())
                .open(player);
        }

        @NotNull
        public Page inventory() {
            return Page.build(
                FakePlayer.getAPI().inventory,
                new ListMenuProvider(
                    this.fake_player,
                    this.add,
                    this.next,
                    this.previous
                )).row(6)
                .title(this.title.build());
        }

    }

}
