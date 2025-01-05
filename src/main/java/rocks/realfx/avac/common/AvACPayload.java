package rocks.realfx.avac.common;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.List;

public record AvACPayload(List<String> mods, List<String> rpacks)
    implements net.minecraft.network.packet.payload.CustomPayload {

  public AvACPayload(PacketByteBuf buf) {
    this(buf.readList(PacketByteBuf::readString), buf.readList(PacketByteBuf::readString));
  }

  @Override
  public void write(PacketByteBuf buf) {
    buf.writeCollection(this.mods, PacketByteBuf::writeString);
    buf.writeCollection(this.rpacks, PacketByteBuf::writeString);
  }

  @Override
  public Identifier id() {
    return NetworkingConstants.HIGHLIGHT_PACKET_ID;
  }
}
