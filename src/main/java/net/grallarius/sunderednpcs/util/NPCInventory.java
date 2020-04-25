package net.grallarius.sunderednpcs.util;

import net.grallarius.sunderednpcs.container.NPCContainer;
import net.grallarius.sunderednpcs.entity.NPCEntity;
import net.grallarius.sunderednpcs.network.MessageNPCEdit;
import net.grallarius.sunderednpcs.network.PacketHandler;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;

public class NPCInventory extends Inventory {
    private INPCContainer wrapper;

    public NPCInventory(int slotCount, INPCContainer wrapper)
    {
        super(slotCount);
        this.wrapper = wrapper;
    }

    //Credit KingParity - BetterPets
    public boolean addItemStack(final ItemStack stack)
    {
        if(stack.isEmpty())
        {
            return false;
        }
        else
        {
            try
            {
                if(stack.isDamaged())
                {
                    int slot = getFirstEmptyStack();
                    if(slot >= 0)
                    {
                        this.setInventorySlotContents(slot, stack.copy());
                        stack.setCount(0);
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    int i;
                    do
                    {
                        i = stack.getCount();
                        stack.setCount(this.storePartialItemStack(stack));
                    } while(!stack.isEmpty() && stack.getCount() < i);
                    return stack.getCount() < i;
                }
            }
            catch(Throwable throwable)
            {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Adding item to inventory");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Item being added");
                crashreportcategory.addDetail("Item ID", Item.getIdFromItem(stack.getItem()));
                //crashreportcategory.addDetail("Item data", stack.getMetadata());
                crashreportcategory.addDetail("Item name", stack.getDisplayName());
                throw new ReportedException(crashreport);
            }
        }
    }

    private int getFirstEmptyStack()
    {
        for(int i = 0; i < this.getSizeInventory(); i++)
        {
            if(this.getStackInSlot(i).isEmpty())
            {
                return i;
            }
        }
        return -1;
    }

    private int storePartialItemStack(ItemStack itemStackIn)
    {
        int i = this.storeItemStack(itemStackIn);
        if(i == -1)
        {
            i = this.getFirstEmptyStack();
        }
        return i == -1 ? itemStackIn.getCount() : this.addResource(i, itemStackIn);
    }

    private int storeItemStack(ItemStack itemStackIn)
    {
        for(int i = 0; i < this.getSizeInventory(); i++)
        {
            if(this.canMergeStacks(this.getStackInSlot(i), itemStackIn))
            {
                return i;
            }
        }
        return -1;
    }

    private boolean canMergeStacks(ItemStack stack1, ItemStack stack2)
    {
        return !stack1.isEmpty() && this.stackEqualExact(stack1, stack2) && stack1.isStackable() && stack1.getCount() < stack1.getMaxStackSize() && stack1.getCount() < this.getInventoryStackLimit();
    }

    private boolean stackEqualExact(ItemStack stack1, ItemStack stack2)
    {
        return stack1.getItem() == stack2.getItem() && ItemStack.areItemStackTagsEqual(stack1, stack2);
    }

    private int addResource(int slot, ItemStack stack)
    {
        int i = stack.getCount();
        ItemStack itemstack = this.getStackInSlot(slot);

        if(itemstack.isEmpty())
        {
            itemstack = stack.copy();
            itemstack.setCount(0);

            if(stack.hasTag())
            {
                itemstack.setTag(stack.getTag().copy());
            }

            this.setInventorySlotContents(slot, itemstack);
        }

        int j = i;

        if(i > itemstack.getMaxStackSize() - itemstack.getCount())
        {
            j = itemstack.getMaxStackSize() - itemstack.getCount();
        }

        if(j > this.getInventoryStackLimit() - itemstack.getCount())
        {
            j = this.getInventoryStackLimit() - itemstack.getCount();
        }

        if(j == 0)
        {
            return i;
        }
        else
        {
            i = i - j;
            itemstack.grow(j);
            itemstack.setAnimationsToGo(5);
            return i;
        }
    }

    public CompoundNBT writeToNBT()
    {
        CompoundNBT tagCompound = new CompoundNBT();
        ListNBT tagList = new ListNBT();
        for(int i = 0; i < this.getSizeInventory(); i++)
        {
            ItemStack stack = this.getStackInSlot(i);
            if(!stack.isEmpty())
            {
                CompoundNBT slotTag = new CompoundNBT();
                slotTag.putByte("Slot", (byte)i);
                stack.write(slotTag);
                tagList.add(slotTag);
            }
        }
        tagCompound.put("inventory", tagList);
        return tagCompound;
    }

    public void readFromNBT(CompoundNBT tagCompound)
    {
        if(tagCompound.contains("inventory", Constants.NBT.TAG_LIST))
        {
            this.clear();
            ListNBT tagList = tagCompound.getList("inventory", Constants.NBT.TAG_COMPOUND);
            for(int i = 0; i < tagList.size(); i++)
            {
                CompoundNBT slotTag = tagList.getCompound(i);
                byte slot = slotTag.getByte("Slot");
                if(slot >= 0 && slot < this.getSizeInventory())
                {
                    this.setInventorySlotContents(slot, ItemStack.read(slotTag));
                }
            }
        }
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player)
    {
        if(wrapper instanceof Entity)
        {
            Entity entity = (Entity)wrapper;
            return player.getDistanceSq(entity) <= 8.0D;
        }
        return true;
    }

    public boolean isNPCItem(ItemStack stack)
    {
        return wrapper.isNPCItem(stack);
    }

    public void openGui(ServerPlayerEntity player, NPCEntity entity)
    {
        if(entity instanceof INPCContainer)
        {
            if(player.isSpectator())
            {
                player.sendStatusMessage((new TranslationTextComponent("container.spectatorCantOpen")).setStyle((new Style()).setColor(TextFormatting.RED)), true);
            }
            else
            {
                if(player.openContainer != player.container)
                {
                    player.closeScreen();
                }


                player.getNextWindowId();
                PacketHandler.sendTo(new MessageNPCEdit(player.currentWindowId, entity.getEntityId()), player);
                player.openContainer = new NPCContainer(player.currentWindowId, player.inventory, this, entity);
                player.openContainer.addListener(player);
                net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.player.PlayerContainerEvent.Open(player, player.openContainer));
            }
        }
    }
}
