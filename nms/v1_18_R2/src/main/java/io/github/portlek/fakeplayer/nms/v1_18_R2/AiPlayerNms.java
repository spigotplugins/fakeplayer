package io.github.portlek.fakeplayer.nms.v1_18_R2;

import com.github.steveice10.mc.auth.service.SessionService;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.ClientboundChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.ClientboundLoginPacket;
import com.github.steveice10.mc.protocol.packet.ingame.serverbound.ServerboundChatPacket;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.DisconnectedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import com.github.steveice10.packetlib.packet.Packet;
import com.github.steveice10.packetlib.tcp.TcpClientSession;
import io.github.portlek.fakeplayer.api.AiPlayer;
import io.github.portlek.fakeplayer.api.AiPlayerFunction;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
final class AiPlayerNms implements AiPlayer {

  @NotNull
  @Delegate(excludes = AiPlayerFunction.class)
  private final AiPlayer ai;

  @Override
  public void connect() {
    final var server = Bukkit.getServer();
    final var protocol = new MinecraftProtocol(this.ai.name());
    final var sessionService = new SessionService();
    final var client = new TcpClientSession(server.getIp(), server.getPort(), protocol);
    client.setFlag(MinecraftConstants.SESSION_SERVICE_KEY, sessionService);
    client.addListener(new SessionAdapter() {
      @Override
      public void packetReceived(final Session session, final Packet packet) {
        if (packet instanceof ClientboundLoginPacket) {
          session.send(new ServerboundChatPacket("Hello, this is a test of MCProtocolLib."));
        } else if (packet instanceof ClientboundChatPacket) {
          final var message = ((ClientboundChatPacket) packet).getMessage();
          System.out.println("Received Message: " + message);
          session.disconnect("Finished");
        }
      }

      @Override
      public void disconnected(final DisconnectedEvent event) {
        System.out.println("Disconnected: " + event.getReason());
        if (event.getCause() != null) {
          event.getCause().printStackTrace();
        }
      }
    });
    client.connect();
  }
}
