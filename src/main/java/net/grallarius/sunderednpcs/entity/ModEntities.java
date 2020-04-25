package net.grallarius.sunderednpcs.entity;

import net.grallarius.sunderednpcs.block.ModBlocks;
import net.grallarius.sunderednpcs.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import static net.grallarius.sunderednpcs.SunderedNPCs.MODID;

public class ModEntities {

    public static final EntityType<SittableEntity> SITTABLE_ENTITY = buildType(new ResourceLocation(MODID, "sittable"), EntityType.Builder.<SittableEntity>create((type, world) -> new SittableEntity(world), EntityClassification.MISC).size(0.0F, 0.0F).setCustomClientFactory((spawnEntity, world) -> new SittableEntity(world)));
    //public static final EntityType<NPCEntity> NPC_ENTITY = buildType(new ResourceLocation(MODID, "npc"), EntityType.Builder.<NPCEntity>create((type, world) -> new NPCEntity(world), EntityClassification.MISC).size(0.0F, 0.0F).setCustomClientFactory((spawnEntity, world) -> new NPCEntity(world)));

    @ObjectHolder("sunderednpcs:npc")
    public static EntityType<?> npc_entity = EntityType.Builder.create(NPCEntity::new, EntityClassification.CREATURE).build(MODID + ":npc_entity").setRegistryName(MODID, "npc_entity");


    public static Item registerEntitySpawnEgg(EntityType<?> type, int col1, int col2, String name){
        SpawnEggItem item = new SpawnEggItem(type, col1, col2, new Item.Properties().group(ModBlocks.sunderedNPCsTab.itemGroup));
        item.setRegistryName(MODID, name);
        return item;
    }

    private static <T extends Entity> EntityType<T> buildType(ResourceLocation id, EntityType.Builder<T> builder)
    {
        EntityType<T> type = builder.build(id.toString());
        type.setRegistryName(id);
        return type;
    }

    public static void registerEntitySpawnEggs(final RegistryEvent.Register<Item> event){
        event.getRegistry().registerAll(
                ModItems.NPCSpawnEgg = registerEntitySpawnEgg(npc_entity, 0x5B55AE, 0x55AEAB, "npc_entity_egg")
        );
    }


    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistrationHandler
    {

        @SubscribeEvent
        public static void register(final RegistryEvent.Register<EntityType<?>> event)
        {
            event.getRegistry().register(SITTABLE_ENTITY);
            event.getRegistry().register(npc_entity);
        }
    }
}
