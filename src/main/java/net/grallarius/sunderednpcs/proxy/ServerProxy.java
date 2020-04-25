package net.grallarius.sunderednpcs.proxy;

import net.grallarius.sunderednpcs.network.MessageNPCEdit;
import net.grallarius.sunderednpcs.network.MessageSyncNPCDialog;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ServerProxy implements IProxy {

    @Override
    public void init() {

    }

    @Override
    public World getClientWorld() {
        throw new IllegalStateException("Only run this on the client!");
    }

    @Override
    public PlayerEntity getClientPlayer() {
        throw new IllegalStateException("Only run this on the client!");
    }

    @Override
    public void openNPCEdit(MessageNPCEdit pkt) {
        throw new IllegalStateException("Only run this on the client!");
    }

    @Override
    public void syncDialog(MessageSyncNPCDialog pkt) {
        throw new IllegalStateException("Only run this on the client!");
    }


    }
