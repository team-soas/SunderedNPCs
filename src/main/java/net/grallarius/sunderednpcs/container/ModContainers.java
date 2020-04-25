package net.grallarius.sunderednpcs.container;

import net.grallarius.sunderednpcs.SunderedNPCs;
import net.grallarius.sunderednpcs.client.screen.NPCEditScreen;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = SunderedNPCs.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModContainers {

    @ObjectHolder("sunderedquesting:npc")
    public static ContainerType<NPCContainer> NPC_CONTAINER;


    private static final List<ContainerType<?>> CONTAINER_TYPES = new ArrayList<>();

    @SubscribeEvent
    public static void registerContainerTypes(final RegistryEvent.Register<ContainerType<?>> event)
    {
        register("sunderedquesting:npc", NPCContainer::new);

        CONTAINER_TYPES.forEach(container_type -> event.getRegistry().register(container_type));
    }

    public static void bindScreens(FMLClientSetupEvent event)
    {
        bindScreen(NPC_CONTAINER, NPCEditScreen::new);
    }

    private static <T extends Container> void register(String name, IContainerFactory<T> container)
    {
        ContainerType<T> type = IForgeContainerType.create(container);
        type.setRegistryName(name);
        CONTAINER_TYPES.add(type);
    }

    private static <M extends Container, U extends Screen & IHasContainer<M>> void bindScreen(ContainerType<M> container, ScreenManager.IScreenFactory<M, U> screen)
    {
        ScreenManager.registerFactory(container, screen);
    }

    @SubscribeEvent
    public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
/*            event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                return new ContainerWindowbox(windowId, SunderedQuesting.proxy.getClientWorld(), pos, inv, SunderedQuesting.proxy.getClientPlayer());
            }).setRegistryName("windowbox"));*/

    }

}
