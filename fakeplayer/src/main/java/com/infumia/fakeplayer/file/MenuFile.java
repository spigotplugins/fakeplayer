package com.infumia.fakeplayer.file;

import com.cryptomorin.xseries.XMaterial;
import com.infumia.fakeplayer.FakePlayer;
import com.infumia.fakeplayer.file.provider.ListMenuProvider;
import com.infumia.fakeplayer.util.FileElement;
import io.github.portlek.bukkititembuilder.ItemStackBuilder;
import io.github.portlek.configs.BukkitManaged;
import io.github.portlek.configs.BukkitSection;
import io.github.portlek.configs.annotations.Config;
import io.github.portlek.configs.annotations.Instance;
import io.github.portlek.configs.annotations.Section;
import io.github.portlek.configs.annotations.Value;
import io.github.portlek.configs.util.ColorUtil;
import io.github.portlek.configs.util.Replaceable;
import io.github.portlek.smartinventory.Page;
import org.jetbrains.annotations.NotNull;

@Config(
    name = "menu",
    location = "%basedir%/FakePlayer"
)
public final class MenuFile extends BukkitManaged {

    @Instance
    public final MenuFile.FakePlayers fakePlayers = new MenuFile.FakePlayers();

    @Override
    public void onCreate() {
        this.addCustomValue(FileElement.class, new FileElement.Provider());
    }

    @Section(path = "fake-players")
    public static final class FakePlayers extends BukkitSection {

        @Value
        public Replaceable<String> title = Replaceable.of("&eFake Players")
            .map(ColorUtil::colored);

        @Value
        public FileElement fake_player = new FileElement(
            "fake-player",
            ItemStackBuilder.from(XMaterial.PLAYER_HEAD)
                .name("&a%player_name%")
                .lore("",
                    "&7Right Click to delete this fake player.",
                    "&7Left Click to teleport this fake player.",
                    "&7Middle Click to toggle visible this fake player.")
                .build(),
            0, 0
        );

        @Value
        public FileElement add = new FileElement(
            "add",
            ItemStackBuilder.from(XMaterial.APPLE)
                .name("&aAdd Fake Player")
                .lore("", "&7Click and add fake player to your location.")
                .build(),
            4, 4
        );

        @Value
        public FileElement next = new FileElement(
            "next",
            ItemStackBuilder.from(XMaterial.ARROW)
                .name("&aNext")
                .lore("",
                    "&7Click and see the next page.")
                .build(),
            5, 5
        );

        @Value
        public FileElement previous = new FileElement(
            "previous",
            ItemStackBuilder.from(XMaterial.ARROW)
                .name("&aPrevious")
                .lore("",
                    "&7Click and see the previous page.")
                .build(),
            5, 3
        );

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
