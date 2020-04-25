package net.grallarius.sunderednpcs.network;

import net.grallarius.sunderednpcs.entity.NPCEntity;
import net.grallarius.sunderednpcs.util.NPCDialogItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageUpdateNPCDialog {


    private int entityId;
    private String dialogItem1;
    private String dialogItem2;
    private String dialogItem3;
    private String dialogItem4;
    private String dialogItem5;
    private String dialogItem6;


    public MessageUpdateNPCDialog(int entityId, String dialogItem1, String dialogItem2, String dialogItem3, String dialogItem4, String dialogItem5, String dialogItem6)
    {
        this.entityId = entityId;
        this.dialogItem1 = dialogItem1;
        this.dialogItem2 = dialogItem2;
        this.dialogItem3 = dialogItem3;
        this.dialogItem4 = dialogItem4;
        this.dialogItem5 = dialogItem5;
        this.dialogItem6 = dialogItem6;

    }

    //fromBytes
    public static void encode(MessageUpdateNPCDialog pkt, PacketBuffer buf)
    {
        buf.writeVarInt(pkt.entityId);
        buf.writeString(pkt.dialogItem1);
        buf.writeString(pkt.dialogItem2);
        buf.writeString(pkt.dialogItem3);
        buf.writeString(pkt.dialogItem4);
        buf.writeString(pkt.dialogItem5);
        buf.writeString(pkt.dialogItem6);

    }

    //toBytes
    public static MessageUpdateNPCDialog decode(PacketBuffer buf)
    {
        int entityId = buf.readVarInt();
        String dialogItem1 = buf.readString();
        String dialogItem2 = buf.readString();
        String dialogItem3 = buf.readString();
        String dialogItem4 = buf.readString();
        String dialogItem5 = buf.readString();
        String dialogItem6 = buf.readString();

        return new MessageUpdateNPCDialog(entityId, dialogItem1, dialogItem2, dialogItem3, dialogItem4, dialogItem5, dialogItem6);
    }

    //onMessage
    public static class Handler
    {
        public static void handle(final MessageUpdateNPCDialog pkt, Supplier<NetworkEvent.Context> ctx)
        {
            //ctx.get().enqueueWork(() -> SunderedQuesting.proxy.syncNPCDialogItem(pkt));

            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity player = ctx.get().getSender();
                World world = player.world;
                Entity targetEntity = world.getEntityByID(pkt.entityId);
                if (targetEntity instanceof NPCEntity){
                    NPCDialogItem item1 = NPCDialogItem.fromLine(pkt.dialogItem1);
                    NPCDialogItem item2 = NPCDialogItem.fromLine(pkt.dialogItem2);
                    NPCDialogItem item3 = NPCDialogItem.fromLine(pkt.dialogItem3);
                    NPCDialogItem item4 = NPCDialogItem.fromLine(pkt.dialogItem4);
                    NPCDialogItem item5 = NPCDialogItem.fromLine(pkt.dialogItem5);
                    NPCDialogItem item6 = NPCDialogItem.fromLine(pkt.dialogItem6);

                    ((NPCEntity) targetEntity).updateDialog(item1, item2, item3, item4, item5, item6);
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
    public int getEntityId() {
        return entityId;
    }

    public String getDialogItemIndex() {
        return dialogItem1;
    }
}
