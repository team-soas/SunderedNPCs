package net.grallarius.sunderednpcs.network;

import net.grallarius.sunderednpcs.entity.NPCEntity;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageNPCCommand {
    private int entityId;
    private String command;

    public MessageNPCCommand(int entityId, String command)
    {
        this.entityId = entityId;
        this.command = command;
    }

    //fromBytes
    public static void encode(MessageNPCCommand pkt, PacketBuffer buf)
    {
        buf.writeVarInt(pkt.entityId);
        buf.writeString(pkt.command, 256);
    }

    //toBytes
    public static MessageNPCCommand decode(PacketBuffer buf)
    {
        int entityId = buf.readVarInt();
        String npc_name = buf.readString();
        return new MessageNPCCommand(entityId, npc_name);
    }

    //onMessage
    public static class Handler
    {
        public static void handle(final MessageNPCCommand pkt, Supplier<NetworkEvent.Context> ctx)
        {
            //ctx.get().enqueueWork(() -> SunderedQuesting.proxy.syncNPCDetails(pkt));


            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity player = ctx.get().getSender();
                World world = player.world;
                Entity targetEntity = world.getEntityByID(pkt.entityId);
                if (targetEntity instanceof NPCEntity){

                    //TODO change the name displayed for who is executing the command?
                    int permissionLevel = 2;
                    CommandSource source = new CommandSource(targetEntity, new Vec3d(targetEntity.posX, targetEntity.posY, targetEntity.posZ), targetEntity.getPitchYaw(), player.world instanceof ServerWorld ? (ServerWorld)player.world : null, permissionLevel, player.getName().getString(), player.getDisplayName(), player.world.getServer(), player);

                    //TODO put some checks in here this is horrible
                    world.getServer().getCommandManager().handleCommand(source, pkt.command);

                    /*            private void handleSlashCommand(String command) {
                this.server.getCommandManager().handleCommand(this.player.getCommandSource(), command);
            }*/

                    //from entity:
/*            public CommandSource getCommandSource() {
                return new CommandSource(this, new Vec3d(this.posX, this.posY, this.posZ), this.getPitchYaw(), this.world instanceof ServerWorld ? (ServerWorld)this.world : null, this.getPermissionLevel(), this.getName().getString(), this.getDisplayName(), this.world.getServer(), this);
            }*/


                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
