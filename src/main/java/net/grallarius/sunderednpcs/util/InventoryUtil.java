package net.grallarius.sunderednpcs.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class InventoryUtil {

    public static void writeInventoryToNBT(CompoundNBT compound, String tagName, @Nullable IInventory inventory)
    {
        ListNBT tagList = new ListNBT();
        for(int i = 0; i < inventory.getSizeInventory(); i++)
        {
            ItemStack stack = inventory.getStackInSlot(i);
            if(!stack.isEmpty())
            {
                CompoundNBT tagCompound = new CompoundNBT();
                tagCompound.putByte("Slot", (byte)i);
                stack.write(tagCompound);
                tagList.add(tagCompound);
            }
        }
        compound.put(tagName, tagList);
    }

    public static <T extends IInventory> T readInventoryToNBT(CompoundNBT compound, String tagName, T t)
    {
        if(compound.contains(tagName, Constants.NBT.TAG_LIST))
        {
            ListNBT tagList = compound.getList(tagName, Constants.NBT.TAG_COMPOUND);
            for(int i = 0; i < tagList.size(); i++)
            {
                CompoundNBT tagCompound = tagList.getCompound(i);
                byte slot = tagCompound.getByte("Slot");
                if(slot >= 0 && slot < t.getSizeInventory())
                {
                    t.setInventorySlotContents(slot, ItemStack.read(tagCompound));
                }
            }
        }
        return t;
    }
}
