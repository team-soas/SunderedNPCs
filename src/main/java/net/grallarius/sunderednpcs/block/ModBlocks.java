package net.grallarius.sunderednpcs.block;

import net.grallarius.sunderednpcs.SunderedNPCs;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = SunderedNPCs.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {

    public static net.grallarius.sunderednpcs.SQTab sqTab = new net.grallarius.sunderednpcs.SQTab();


    private static final List<Block> BLOCKS = new ArrayList<>();
    private static final List<Item> ITEMBLOCKS = new ArrayList<>();

    //registry example
/*    @ObjectHolder("sunderedquesting:windowbox")
    public static TileEntityType<TileEntityWindowbox> WINDOWBOX_TILE;
    @ObjectHolder("sunderedquesting:windowbox")
    public static ContainerType<ContainerWindowbox> WINDOWBOX_CONTAINER;

    @ObjectHolder("sunderedquesting:windowbox")
    public static final Block windowbox = register("windowbox", new BlockWindowbox());*/


    private static Block register(String name, Block block)
    {
        return register(name, block, new Item.Properties().group(sqTab.itemGroup));
    }

    private static Block register(String name, Block block, Item.Properties properties)
    {
        return register(name, block, new BlockItem(block, properties));
    }

    private static Block register(String name, Block block, BlockItem item)
    {
        block.setRegistryName(name);
        BLOCKS.add(block);
        if(block.getRegistryName() != null)
        {
            item.setRegistryName(name);
            ITEMBLOCKS.add(item);
        }
        return block;
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void registerBlocks(final RegistryEvent.Register<Block> event)
    {
        BLOCKS.forEach(block -> event.getRegistry().register(block));
        BLOCKS.clear();
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void registerItems(final RegistryEvent.Register<Item> event)
    {
        ITEMBLOCKS.forEach(item -> event.getRegistry().register(item));
        ITEMBLOCKS.clear();
    }
}
