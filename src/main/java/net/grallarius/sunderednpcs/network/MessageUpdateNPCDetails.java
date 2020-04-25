package net.grallarius.sunderednpcs.network;

import net.grallarius.sunderednpcs.entity.NPCEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageUpdateNPCDetails {
    private int entityId;
    private String npc_name;

    public MessageUpdateNPCDetails(int entityId, String npc_name)
    {
        this.entityId = entityId;
        this.npc_name = npc_name;
    }

    //fromBytes
    public static void encode(MessageUpdateNPCDetails pkt, PacketBuffer buf)
    {
        buf.writeVarInt(pkt.entityId);
        buf.writeString(pkt.npc_name, 20);
    }

    //toBytes
    public static MessageUpdateNPCDetails decode(PacketBuffer buf)
    {
        int entityId = buf.readVarInt();
        String npc_name = buf.readString();
        return new MessageUpdateNPCDetails(entityId, npc_name);
    }

    //onMessage
    public static class Handler
    {
        public static void handle(final MessageUpdateNPCDetails pkt, Supplier<NetworkEvent.Context> ctx)
        {
            //ctx.get().enqueueWork(() -> SunderedQuesting.proxy.syncNPCDetails(pkt));


            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity player = ctx.get().getSender();
                World world = player.world;
                Entity targetEntity = world.getEntityByID(pkt.entityId);
                if (targetEntity instanceof NPCEntity){
                    ((NPCEntity) targetEntity).setNpc_name(pkt.npc_name);
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public int getEntityId()
    {
        return entityId;
    }

    @OnlyIn(Dist.CLIENT)
    public String getNPCName()
    {
        return npc_name;
    }
}
