package net.grallarius.sunderednpcs.entity;

import net.grallarius.sunderednpcs.client.ModNPCTextureManager;
import net.grallarius.sunderednpcs.client.screen.NPCChatScreen;
import net.grallarius.sunderednpcs.container.NPCContainer;
import net.grallarius.sunderednpcs.network.MessageSyncNPCDialog;
import net.grallarius.sunderednpcs.network.PacketHandler;
import net.grallarius.sunderednpcs.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkHooks;


public class NPCEntity extends CreatureEntity implements IInventoryChangedListener, INPCContainer {

    private boolean smallArms;



    private ResourceLocation skin;
    public NPCInventory inventory;

    private NPCDialog dialog;

    private static final DataParameter<String> NPC_NAME = EntityDataManager.createKey(NPCEntity.class, DataSerializers.STRING);
    private static final DataParameter<Integer> DIALOG_ID = EntityDataManager.createKey(NPCEntity.class, DataSerializers.VARINT);


    @SuppressWarnings("unchecked")
    protected NPCEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
        //(EntityType<? extends CreatureEntity>) npc_entity instead of type

        if (ModNPCTextureManager.textureOptions.size() > 0) {
            this.skin = ModNPCTextureManager.textureOptions.get(0);
        } else this.skin = null;

        //this.skin = new ResourceLocation(SunderedQuesting.MODID, "textures/entity/npc_peasant.png");

        this.smallArms = true;
        this.enablePersistence();

    }


    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(NPC_NAME, "");
        this.dataManager.register(DIALOG_ID, 0);

    }


    @Override
    protected boolean processInteract(PlayerEntity player, Hand hand) {

        if(!this.world.isRemote && this.getDialogID()>0) {
            this.syncDialog();
        }

        ItemStack itemstack = player.getHeldItem(hand);
        Item item = itemstack.getItem();

        //TODO only talk if type for index 0 is chat and exists, otherwise do something other than open the chat gui
        //TODO Packet doesn't arrive in time to access the synced data, need to sync on load somehow too!
        if (!player.isSneaking() && this.world.isRemote){
            if (this.dialog != null && this.dialog.dialogItems[0] != null){
                Minecraft.getInstance().displayGuiScreen(new NPCChatScreen(this, this.dialog.dialogItems[0]));
            }
        }

            if(player.isSneaking()) {
                if (!this.world.isRemote) {
                    ITextComponent displayName = this.getDisplayName();
                    int entityId = this.getEntityId();
                    NPCInventory petInventory = this.getInventory();
                    NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {
                        @Override
                        public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity p_createMenu_3_) {
                            return new NPCContainer(windowId, playerInventory, petInventory, (NPCEntity) playerInventory.player.world.getEntityByID(entityId));
                        }

                        @Override
                        public ITextComponent getDisplayName() {
                            return displayName;
                        }
                    }, extraData -> {
                        extraData.writeVarInt(entityId);
                        extraData.writeVarInt(entityId);
                    });
                }
                return true;
            }

        return super.processInteract(player, hand);
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        //this.goalSelector.addGoal(1, new RandomWalkingGoal(this, 0.5D));
        this.goalSelector.addGoal(2, new LookRandomlyGoal(this));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
    }

    private void initInventory()
    {
        Inventory original = this.inventory;
        this.inventory = new NPCInventory(6, this);
        // Copies the inventory if it exists already over to the new instance
        if(original != null)
        {
            original.removeListener(this);
            int x = Math.min(original.getSizeInventory(), this.inventory.getSizeInventory());

            for(int i = 0; i < x; ++i)
            {
                ItemStack stack = original.getStackInSlot(i);
                if(!stack.isEmpty())
                {
                    this.inventory.setInventorySlotContents(i, stack.copy());
                }
            }
        }
        this.inventory.addListener(this);
    }

    @Override
    public void onInventoryChanged(IInventory iInventory)
    {
        //this.updateSlots();
        ItemStack stack = inventory.getStackInSlot(0);

    }

    private void updateSlots()
    {
        if(!this.world.isRemote)
        {
        }
    }

    public NPCInventory getInventory()
    {
        if(/*this.hasChest() && */inventory == null)
        {
            this.initInventory();
        }
        return inventory;

    }



    @Override
    public void writeAdditional(CompoundNBT compound) {

        super.writeAdditional(compound);

        compound.putString("npc_name", this.getNpc_name());
        //compound.putString("npc_skin", this.getSkin().getPath());

        compound.putInt("dialog_id", this.getDialogID());

        if(inventory != null)
        {
            InventoryUtil.writeInventoryToNBT(compound, "inventory", inventory);
        }

    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if(compound.contains("npc_name")) {
            if (compound.getString("npc_name").equals("")){
                this.setNpc_name("Alex");
            } else this.setNpc_name(compound.getString("npc_name"));
        }
        else {
            this.setNpc_name("Alex");
        }

        this.setDialogID(compound.getInt("dialog_id"));

        if(compound.contains("inventory", Constants.NBT.TAG_LIST))
        {
            this.initInventory();
            InventoryUtil.readInventoryToNBT(compound, "inventory", inventory);
        }

        //this.skin = new ResourceLocation(SunderedQuesting.MODID, compound.getString("npc_skin"));

    }

    //TODO check that you are on server world!
    public void reloadDialogFromFile(){
        if (!this.world.isRemote) {
            this.dialog = NPCDialogManager.readLinesFromFile(this.getDialogID());
        }
    }

    public void updateDialog(NPCDialogItem changedItem1, NPCDialogItem changedItem2, NPCDialogItem changedItem3,
                             NPCDialogItem changedItem4, NPCDialogItem changedItem5, NPCDialogItem changedItem6){

        //note: even if all the items are blank, the packet still needs to be sent and updated, as someone may be trying to clear dialog

        if (this.dialog == null && this.getDialogID() == 0){
            createNewDialog();
        } else if (this.dialog == null){
            this.reloadDialogFromFile();
        }
        this.dialog.editDialog(changedItem1, changedItem2, changedItem3, changedItem4, changedItem5, changedItem6);
    }

    public void createNewDialog(){
        this.dialog = new NPCDialog();
        this.setDialogID(this.dialog.getDialogID());
    }

    public void syncDialog()
    {

        this.reloadDialogFromFile();

        this.world.getPlayers().forEach((player) -> PacketHandler.sendTo(new MessageSyncNPCDialog(this.getEntityId(),
                this.dialog.dialogItems[0].toLine(), this.dialog.dialogItems[1].toLine(), this.dialog.dialogItems[2].toLine(),
                this.dialog.dialogItems[3].toLine(), this.dialog.dialogItems[4].toLine(), this.dialog.dialogItems[5].toLine()), (ServerPlayerEntity)player));

    }


    public String getNpc_name() {
        return this.dataManager.get(NPC_NAME);
    }


    public void setNpc_name(String npc_name) {
        this.dataManager.set(NPC_NAME, npc_name);

    }

    public int getDialogID() {
        return this.dataManager.get(DIALOG_ID);
    }


    public void setDialogID(int dialogID) {
        this.dataManager.set(DIALOG_ID, dialogID);
    }

    public NPCDialog getDialog() {
        return dialog;
    }

    public void setDialog(NPCDialog dialog) {
        this.dialog = dialog;
    }

    public ResourceLocation getSkin() {
        return skin;
    }

    public void setSkin(ResourceLocation skin) {
        this.skin = skin;
    }
}
