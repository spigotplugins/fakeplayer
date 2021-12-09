package io.github.portlek.fakeplayer.handle;

import io.github.portlek.fakeplayer.FakePlayer;
import io.github.portlek.fakeplayer.api.Fake;
import io.github.portlek.fakeplayer.api.FakeCreated;
import io.github.portlek.fakeplayer.api.INPC;
import io.github.portlek.fakeplayer.nms.v1_10_R1.FakeCreated1_10_R1;
import io.github.portlek.fakeplayer.nms.v1_11_R1.FakeCreated1_11_R1;
import io.github.portlek.fakeplayer.nms.v1_12_R1.FakeCreated1_12_R1;
import io.github.portlek.fakeplayer.nms.v1_13_R1.FakeCreated1_13_R1;
import io.github.portlek.fakeplayer.nms.v1_13_R2.FakeCreated1_13_R2;
import io.github.portlek.fakeplayer.nms.v1_14_R1.FakeCreated1_14_R1;
import io.github.portlek.fakeplayer.nms.v1_15_R1.FakeCreated1_15_R1;
import io.github.portlek.fakeplayer.nms.v1_16_R1.FakeCreated1_16_R1;
import io.github.portlek.fakeplayer.nms.v1_16_R2.FakeCreated1_16_R2;
import io.github.portlek.fakeplayer.nms.v1_16_R3.FakeCreated1_16_R3;
import io.github.portlek.fakeplayer.nms.v1_8_R1.FakeCreated1_8_R1;
import io.github.portlek.fakeplayer.nms.v1_8_R2.FakeCreated1_8_R2;
import io.github.portlek.fakeplayer.nms.v1_8_R3.FakeCreated1_8_R3;
import io.github.portlek.fakeplayer.nms.v1_9_R1.FakeCreated1_9_R1;
import io.github.portlek.fakeplayer.nms.v1_9_R2.FakeCreated1_9_R2;
import io.github.portlek.mapentry.MapEntry;
import io.github.portlek.versionmatched.VersionMatched;
import java.util.Optional;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class FakeBasic implements Fake {

  private static final FakeCreated FAKE_CREATED = new VersionMatched<>(
    FakeCreated1_16_R3.class,
    FakeCreated1_16_R2.class,
    FakeCreated1_16_R1.class,
    FakeCreated1_15_R1.class,
    FakeCreated1_14_R1.class,
    FakeCreated1_13_R2.class,
    FakeCreated1_13_R1.class,
    FakeCreated1_12_R1.class,
    FakeCreated1_11_R1.class,
    FakeCreated1_10_R1.class,
    FakeCreated1_9_R2.class,
    FakeCreated1_9_R1.class,
    FakeCreated1_8_R3.class,
    FakeCreated1_8_R2.class,
    FakeCreated1_8_R1.class)
    .of()
    .create()
    .orElseThrow(() ->
      new RuntimeException("Somethings were wrong!"));

  @NotNull
  private final String name;

  @NotNull
  private final Location spawnpoint;

  @Nullable
  private INPC npc;

  public FakeBasic(@NotNull final String name, @NotNull final Location spawnpoint) {
    this.name = name;
    this.spawnpoint = spawnpoint;
  }

  @NotNull
  @Override
  public String getName() {
    return this.name;
  }

  @NotNull
  @Override
  public Location getSpawnPoint() {
    return this.spawnpoint;
  }

  @Override
  public void spawn() {
    Optional.ofNullable(this.spawnpoint.getWorld()).ifPresent(world -> {
      if (!Optional.ofNullable(this.npc).isPresent()) {
        this.npc = FakeBasic.FAKE_CREATED.create(
          this.name,
          FakePlayer.getAPI().configFile.tab_name
            .build(MapEntry.from("%player_name%", this::getName)),
          world);
      }
      this.npc.spawn(this.spawnpoint);
    });
  }

  @Override
  public void deSpawn() {
    Optional.ofNullable(this.npc).ifPresent(INPC::deSpawn);
  }

  @Override
  public void toggleVisible() {
    Optional.ofNullable(this.npc).ifPresent(INPC::toggleVisible);
  }
}
