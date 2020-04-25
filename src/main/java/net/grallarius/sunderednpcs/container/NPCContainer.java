package net.grallarius.sunderednpcs.container;

import net.grallarius.sunderednpcs.entity.NPCEntity;
import net.grallarius.sunderednpcs.util.NPCInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import static net.grallarius.sunderednpcs.container.ModContainers.NPC_CONTAINER;

public class NPCContainer extends Container {

    private NPCInventory npcInventory;
    private NPCEntity entity;

    public NPCContainer(int windowId, PlayerInventory playerInventory, PacketBuffer extraData)
    {
        this(windowId, playerInventory, new NPCInventory(6, (NPCEntity)(playerInventory.player.world.getEntityByID(extraData.readVarInt()))), (NPCEntity) (playerInventory.player.world.getEntityByID(extraData.readVarInt())));
    }

    public NPCContainer(int id, PlayerInventory playerInventory, NPCInventory npcInventory, final NPCEntity entity) {
        super(NPC_CONTAINER, id);
        this.npcInventory = npcInventory;
        this.entity = entity;

        npcInventory.openInventory(playerInventory.player);


        //example custom slot
/*        this.addSlot(new Slot(npcInventory, 0, 6, 18)
        {
            @Override
            public int getSlotStackLimit()
            {
                return 1;
            }

            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return true;
            }
        });*/

        //should be 6 slots TODO change to armour/equip slots
        for(int i = 0; i < 3; ++i)
        {//row
            for(int j = 0; j < 2; ++j)
            {//col
                this.addSlot(new Slot(this.npcInventory, ((i * 2) + j), 12 + j * 28, 25 + i * 28));
            }
        }


        //player Inv
        for(int k = 0; k < 3; ++k)
        {
            for(int i1 = 0; i1 < 9; ++i1)
            {
                this.addSlot(new Slot(playerInventory, i1 + k * 9 + 9, 107 + i1 * 18, 99 + k * 18 + -18));
            }
        }

        //Player Hotbar
        for(int l = 0; l < 9; ++l)
        {
            this.addSlot(new Slot(playerInventory, l, 107 + l * 18, 141));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.npcInventory.isUsableByPlayer(playerIn) && this.entity.isAlive() && this.entity.getDistance(playerIn) < 8;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if(slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if(index < this.npcInventory.getSizeInventory())
            {
                if(!this.mergeItemStack(itemstack1, this.npcInventory.getSizeInventory(), this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(this.getSlot(1).isItemValid(itemstack1) && !this.getSlot(1).getHasStack())
            {
                if(!this.mergeItemStack(itemstack1, 1, 2, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (this.getSlot(0).isItemValid(itemstack1))
            {
                if (!this.mergeItemStack(itemstack1, 0, 1, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(this.npcInventory.getSizeInventory() <= 2 || !this.mergeItemStack(itemstack1, 2, this.npcInventory.getSizeInventory(), false))
            {
                return ItemStack.EMPTY;
            }

            if(itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    /**
     * Called when the container is closed.
     */
    @Override
    public void onContainerClosed(PlayerEntity playerIn)
    {
        super.onContainerClosed(playerIn);
        this.npcInventory.closeInventory(playerIn);
    }

    public NPCEntity getNPC()
    {
        return this.entity;
    }
}
