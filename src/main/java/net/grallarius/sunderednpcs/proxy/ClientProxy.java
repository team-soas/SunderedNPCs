package net.grallarius.sunderednpcs.proxy;


import net.grallarius.sunderednpcs.client.ModNPCTextureManager;
import net.grallarius.sunderednpcs.client.NPCRenderer;
import net.grallarius.sunderednpcs.client.screen.NPCEditScreen;
import net.grallarius.sunderednpcs.container.ModContainers;
import net.grallarius.sunderednpcs.container.NPCContainer;
import net.grallarius.sunderednpcs.entity.NPCEntity;
import net.grallarius.sunderednpcs.entity.SittableEntity;
import net.grallarius.sunderednpcs.entity.SittableRenderer;
import net.grallarius.sunderednpcs.network.MessageNPCEdit;
import net.grallarius.sunderednpcs.network.MessageSyncNPCDialog;
import net.grallarius.sunderednpcs.util.INPCContainer;
import net.grallarius.sunderednpcs.util.NPCDialog;
import net.grallarius.sunderednpcs.util.NPCDialogItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import java.io.File;

public class ClientProxy implements IProxy {

    @Override
    public void init() {

        //match containers with their guis
        //ScreenManager.registerFactory(ModBlocks.WINDOWBOX_CONTAINER, WindowboxScreen::new);
        ScreenManager.registerFactory(ModContainers.NPC_CONTAINER, NPCEditScreen::new);

        //ResourcePackList<ClientResourcePackInfo> DefaultResourcePacks = ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getInstance(), "resourcePackRepository") ;
        //if (DefaultResourcePacks != null) DefaultResourcePacks.add(new NPCTextureLocation());


        File resourcesDirectory = new File("");

        String path = resourcesDirectory.getAbsolutePath().substring(0, resourcesDirectory.getAbsolutePath().lastIndexOf("\\"))+ "\\src\\main\\resources\\assets\\sunderedquesting\\textures\\entity";
        ModNPCTextureManager.locateAllTextureFiles(path);

        registerRenderers();

        RenderingRegistry.registerEntityRenderingHandler(SittableEntity.class, SittableRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(NPCEntity.class, NPCRenderer::new);

    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    public void registerRenderers() {

        //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWindowbox.class, new WindowboxRenderer());

    }

    @Override
    public void openNPCEdit(MessageNPCEdit pkt)
    {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        World world = player.getEntityWorld();
        Entity entity = world.getEntityByID(pkt.getEntityId());
        INPCContainer wrapper = (INPCContainer) entity;

        if(entity instanceof NPCEntity)
        {
            NPCEntity npcEntity = (NPCEntity) entity;
            NPCContainer container = new NPCContainer(pkt.getWindowId(), player.inventory, wrapper.getInventory(), npcEntity);
            Minecraft.getInstance().displayGuiScreen(new NPCEditScreen(container, player.inventory, npcEntity.getDisplayName()));
        }
    }


    @Override
    public void syncDialog(MessageSyncNPCDialog pkt){
        ClientPlayerEntity playerEntity = Minecraft.getInstance().player;
        Entity entity = playerEntity.world.getEntityByID(pkt.getEntityId());
        if(entity instanceof NPCEntity)
        {
            NPCEntity npcEntity = (NPCEntity) entity;

            //make a new dialog with existing dialogID. If the id is 0 then there is no dialog to sync yet so just do nothing
            if (npcEntity.getDialogID() > 0) {

                System.out.println("dialog id is: " + npcEntity.getDialogID());

                npcEntity.setDialog(new NPCDialog(npcEntity.getDialogID()));

                NPCDialog dialog = npcEntity.getDialog();

                //update each entry from packet
                dialog.dialogItems[0] = NPCDialogItem.fromLine(pkt.getDialogItem1());
                dialog.dialogItems[1] = NPCDialogItem.fromLine(pkt.getDialogItem2());
                dialog.dialogItems[2] = NPCDialogItem.fromLine(pkt.getDialogItem3());
                dialog.dialogItems[3] = NPCDialogItem.fromLine(pkt.getDialogItem4());
                dialog.dialogItems[4] = NPCDialogItem.fromLine(pkt.getDialogItem5());
                dialog.dialogItems[5] = NPCDialogItem.fromLine(pkt.getDialogItem6());
            }

        }
    }


}
