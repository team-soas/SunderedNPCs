package net.grallarius.sunderednpcs.network;

import net.grallarius.sunderednpcs.SunderedNPCs;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageSyncNPCDialog {

    private int entityId;

    //TODO add literally ALL of the dialog items eventually
    private String dialogItem1;
    private String dialogItem2;
    private String dialogItem3;
    private String dialogItem4;
    private String dialogItem5;
    private String dialogItem6;

    public MessageSyncNPCDialog(int entityId, String dialogItem1, String dialogItem2, String dialogItem3, String dialogItem4, String dialogItem5, String dialogItem6) {
        this.entityId = entityId;
        this.dialogItem1 = dialogItem1;
        this.dialogItem2 = dialogItem2;
        this.dialogItem3 = dialogItem3;
        this.dialogItem4 = dialogItem4;
        this.dialogItem5 = dialogItem5;
        this.dialogItem6 = dialogItem6;
    }

    //fromBytes
    public static void encode(MessageSyncNPCDialog pkt, PacketBuffer buf)
    {
        buf.writeInt(pkt.entityId);
        buf.writeString(pkt.dialogItem1);
        buf.writeString(pkt.dialogItem2);
        buf.writeString(pkt.dialogItem3);
        buf.writeString(pkt.dialogItem4);
        buf.writeString(pkt.dialogItem5);
        buf.writeString(pkt.dialogItem6);
    }

    //toBytes
    public static MessageSyncNPCDialog decode(PacketBuffer buf)
    {
        int entityId = buf.readInt();
        String dialogItem1 = buf.readString();
        String dialogItem2 = buf.readString();
        String dialogItem3 = buf.readString();
        String dialogItem4 = buf.readString();
        String dialogItem5 = buf.readString();
        String dialogItem6 = buf.readString();

        return new MessageSyncNPCDialog(entityId, dialogItem1, dialogItem2, dialogItem3, dialogItem4, dialogItem5, dialogItem6);
    }

    //onMessage
    public static class Handler
    {
        public static void handle(final MessageSyncNPCDialog pkt, Supplier<NetworkEvent.Context> ctx)
        {
            ctx.get().enqueueWork(() -> SunderedNPCs.proxy.syncDialog(pkt));
            ctx.get().setPacketHandled(true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public int getEntityId()
    {
        return entityId;
    }

    @OnlyIn(Dist.CLIENT)
    public String getDialogItem1() {
        return dialogItem1;
    }

    @OnlyIn(Dist.CLIENT)
    public String getDialogItem2() {
        return dialogItem2;
    }

    @OnlyIn(Dist.CLIENT)
    public String getDialogItem3() {
        return dialogItem3;
    }

    @OnlyIn(Dist.CLIENT)
    public String getDialogItem4() {
        return dialogItem4;
    }

    @OnlyIn(Dist.CLIENT)
    public String getDialogItem5() {
        return dialogItem5;
    }

    @OnlyIn(Dist.CLIENT)
    public String getDialogItem6() {
        return dialogItem6;
    }

}
