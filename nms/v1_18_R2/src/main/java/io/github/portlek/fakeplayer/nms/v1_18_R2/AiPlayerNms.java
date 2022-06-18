package io.github.portlek.fakeplayer.nms.v1_18_R2;

import com.github.steveice10.mc.auth.service.SessionService;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.packetlib.event.session.DisconnectedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import com.github.steveice10.packetlib.event.session.SessionListener;
import com.github.steveice10.packetlib.tcp.TcpClientSession;
import io.github.portlek.fakeplayer.api.AiPlayer;
import io.github.portlek.fakeplayer.api.AiPlayerFunction;
import io.github.portlek.fakeplayer.api.FakePlayerConfig;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class AiPlayerNms implements AiPlayer {

  @NotNull
  @Delegate(excludes = AiPlayerFunction.class)
  AiPlayer ai;

  @Override
  public void connect() {
    final var client = new TcpClientSession(
      FakePlayerConfig.instance().host(),
      FakePlayerConfig.instance().port(),
      new MinecraftProtocol(this.ai.name())
    );
    client.setFlag(MinecraftConstants.SESSION_SERVICE_KEY, new SessionService());
    client.addListener(Listener.INSTANCE);
    client.connect();
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  private static final class Listener extends SessionAdapter {

    private static final SessionListener INSTANCE = new Listener();

    @Override
    public void disconnected(final DisconnectedEvent event) {
      System.out.printf("Disconnected: %s%n", event.getSession().getHost());
      if (event.getCause() != null) {
        event.getCause().printStackTrace();
      }
    }
  }
}
