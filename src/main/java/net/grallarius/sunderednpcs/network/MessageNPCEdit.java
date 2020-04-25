package net.grallarius.sunderednpcs.network;

import net.grallarius.sunderednpcs.SunderedNPCs;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageNPCEdit {

    private int windowId;
    private int entityId;

    public MessageNPCEdit(int windowId, int entityId)
    {
        this.windowId = windowId;
        this.entityId = entityId;
    }

    //fromBytes
    public static void encode(MessageNPCEdit pkt, PacketBuffer buf)
    {
        buf.writeVarInt(pkt.windowId);
        buf.writeVarInt(pkt.entityId);
    }

    //toBytes
    public static MessageNPCEdit decode(PacketBuffer buf)
    {
        int windowId = buf.readVarInt();
        int entityId = buf.readVarInt();
        return new MessageNPCEdit(windowId, entityId);
    }

    //onMessage
    public static class Handler
    {
        public static void handle(final MessageNPCEdit pkt, Supplier<NetworkEvent.Context> ctx)
        {
            ctx.get().enqueueWork(() -> SunderedNPCs.proxy.openNPCEdit(pkt));
            ctx.get().setPacketHandled(true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public int getWindowId()
    {
        return this.windowId;
    }

    @OnlyIn(Dist.CLIENT)
    public int getEntityId()
    {
        return this.entityId;
    }
}
