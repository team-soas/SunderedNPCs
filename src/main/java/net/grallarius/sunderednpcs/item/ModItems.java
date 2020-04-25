package net.grallarius.sunderednpcs.item;

import net.grallarius.sunderednpcs.SunderedNPCs;
import net.grallarius.sunderednpcs.entity.ModEntities;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = SunderedNPCs.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {

    @ObjectHolder("sunderednpcs:npcwand")
    public static NPCWand NPCWAND;


    //spawn eggs
    public static Item NPCSpawnEgg;

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void registerItems(final RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(new NPCWand());

        ModEntities.registerEntitySpawnEggs(event);
    }
}
