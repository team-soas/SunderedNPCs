package net.grallarius.sunderednpcs;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class SQTab {

    public ItemGroup itemGroup = new ItemGroup("sunderedquesting") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.BOOK);
        }
    };

    public void init() {

    }
}
