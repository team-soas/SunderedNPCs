package net.grallarius.sunderednpcs.network;

import net.grallarius.sunderednpcs.SunderedNPCs;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {

    private static final String PROTOCOL_VERSION = Integer.toString(1);
    private static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(SunderedNPCs.MODID, "main_channel"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    public static void register()
    {
        HANDLER.registerMessage(1, MessageNPCEdit.class, MessageNPCEdit::encode, MessageNPCEdit::decode, MessageNPCEdit.Handler::handle);
        HANDLER.registerMessage(2, MessageUpdateNPCDetails.class, MessageUpdateNPCDetails::encode, MessageUpdateNPCDetails::decode, MessageUpdateNPCDetails.Handler::handle);
        HANDLER.registerMessage(3, MessageUpdateNPCDialog.class, MessageUpdateNPCDialog::encode, MessageUpdateNPCDialog::decode, MessageUpdateNPCDialog.Handler::handle);
        HANDLER.registerMessage(4, MessageSyncNPCDialog.class, MessageSyncNPCDialog::encode, MessageSyncNPCDialog::decode, MessageSyncNPCDialog.Handler::handle);
        HANDLER.registerMessage(5, MessageNPCCommand.class, MessageNPCCommand::encode, MessageNPCCommand::decode, MessageNPCCommand.Handler::handle);


    }

    /**
     * Credit: ProjectE PacketHandler.java
     * Sends a packet to the server.<br>
     * Must be called Client side.
     */
    public static void sendToServer(Object msg)
    {
        HANDLER.sendToServer(msg);
    }

    /**
     * Credit: ProjectE PacketHandler.java
     * Send a packet to a specific player.<br>
     * Must be called Server side.
     */
    public static void sendTo(Object msg, ServerPlayerEntity player)
    {
        if(!(player instanceof FakePlayer))
        {
            HANDLER.sendTo(msg, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
        }
    }
}
