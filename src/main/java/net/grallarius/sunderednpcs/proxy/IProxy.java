package net.grallarius.sunderednpcs.proxy;

import net.grallarius.sunderednpcs.network.MessageNPCEdit;
import net.grallarius.sunderednpcs.network.MessageSyncNPCDialog;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public interface IProxy {

    void init();

    World getClientWorld();

    PlayerEntity getClientPlayer();

    void openNPCEdit(MessageNPCEdit pkt);

    void syncDialog(MessageSyncNPCDialog pkt);
}
