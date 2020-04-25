package net.grallarius.sunderednpcs;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class SunderedNPCsTab {

    public ItemGroup itemGroup = new ItemGroup("sunderednpcs") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.BOOK);
        }
    };

    public void init() {

    }
}
